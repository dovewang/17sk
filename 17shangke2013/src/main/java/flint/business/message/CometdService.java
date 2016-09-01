package flint.business.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kiss.security.Passport;
import kiss.security.authc.Authenticator;

import org.cometd.bayeux.server.BayeuxContext;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.AbstractService;
import org.cometd.server.authorizer.GrantAuthorizer;
import org.cometd.server.ext.TimesyncExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flint.util.SpringHelper;

public class CometdService extends AbstractService {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	public final static int NORMAL = 0;

	/**
	 * 用户有可能再多处登录： 例如：A用户在a登录：sid=1，b出登录sid=2 需要记录的数据格式： A：[{sid1:15},{sid2:22}]
	 * 
	 */
	private final ConcurrentHashMap<String, List<Tracer>> online = new ConcurrentHashMap<String, List<Tracer>>();

	public CometdService(final BayeuxServer bayeux) {
		super(bayeux, "i");
		bayeux.addExtension(new TimesyncExtension());
		bayeux.createIfAbsent("/**", new ServerChannel.Initializer() {
			public void configureChannel(ConfigurableServerChannel channel) {
				channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
			}
		});

		/*
		 * 每有一个页面，就会创建一个连接， 用户在线状态检测 ;
		 * 关于用户断网问题，客户端现在检查复杂，我们只能通过消息机制保障在线用户在线状态的准确性， 断网时很难保障
		 */
		bayeux.addListener(new BayeuxServer.SessionListener() {
			@Override
			public void sessionAdded(ServerSession session) {
				BayeuxContext context = bayeux.getContext();
				Passport passport = (Passport) context
						.getHttpSessionAttribute(Passport.PRINCIPAL);
				boolean isNew = add(passport.getId(),
						context.getHttpSessionId());
				logger.debug("当前在线用户：" + online);
				/* 由于被移除后，已经无法取得httpsession的信息，这里只能通过消息绑定 */
				session.setAttribute(Passport.PRINCIPAL, passport.getId()
						+ "##" + context.getHttpSessionId());
				/* 用户有可能存在两种上线：登录上线，断网自动重连上线 */
				if (isNew) {
					Authenticator authenticator = SpringHelper
							.getBean(Authenticator.class);
					authenticator.on(passport.getId());
				}
			}

			@Override
			public void sessionRemoved(ServerSession session, boolean timedout) {
				String passport = (String) session
						.getAttribute(Passport.PRINCIPAL);
				/* 如果不是游客 */
				if (passport != null) {
					String p[] = passport.split("##");
					/* 销毁一个session */
					int flag = remove(p[0], p[1]);
					/* 用户从某一处退出,目前没有做分布式应用，session不需要单独处理 */
					if (flag == 0) {
						// context.invalidateHttpSession();
						/* 关于用户是否退出，这里需要判断一下 */
					} else if (flag == -1) {
						/* 用户至少从一处完全退出 */
						Authenticator authenticator = SpringHelper
								.getBean(Authenticator.class);
						/* 用户从多处下线，才算下线 */
					    authenticator.off(p[0]);
					}
					logger.debug("当前在线用户：" + online);
				}
			}
		});

	}

	/**
	 * 加入一个会话
	 * 
	 * @param userId
	 * @param sid
	 * @return 是否是一个新用户的链接
	 */
	private boolean add(String userId, String sid) {
		boolean isNew = false;
		List<Tracer> list = online.get(userId);
		Tracer tracer = null;
		if (list == null) {
			list = new ArrayList<Tracer>();
			online.put(userId, list);
			isNew = true;
		} else {
			for (Tracer t : list) {
				if (sid.equals(t.getId())) {
					tracer = t;
					break;
				}
			}
		}
		/* 记录用户在多处登录的信息 */
		if (tracer == null) {
			tracer = new Tracer(sid, 0);
			list.add(tracer);
		}
		tracer.setValue(tracer.getValue() + 1);
		return isNew;
	}

	/**
	 * 
	 * @param userId
	 * @param sid
	 */
	private int remove(String userId, String sid) {
		List<Tracer> list = online.get(userId);
		Tracer tracer = null;
		for (Tracer t : list) {
			if (sid.equals(t.getId())) {
				tracer = t;
				break;
			}
		}
		tracer.setValue(tracer.getValue() - 1);
		if (tracer != null && tracer.getValue() == 0) {
			list.remove(tracer);
			if (list.size() == 0) {
				online.remove(userId);
				return -1;
			}
			return 0;
		}
		return 1;
	}

	/**
	 * 个人实时消息，其他方法都是延迟到达
	 * 
	 * @param userId
	 * @param data
	 */
	public void instance(final long userId, final Map<String, Object> data) {
		broadcast("/pvi/" + userId, data, false, null);
	}

	/**
	 * 私人消息发送广播消息，当用户在多个地方登录或使用多个页面时
	 * 
	 * @param data
	 */
	public void privateBroadcast(final long userId,
			final Map<String, Object> data) {
		broadcast("/pv/" + userId, data, true, null);
	}

	/**
	 * 学校发送的消息,包括圈子
	 * 
	 * @param data
	 */
	public void broadcast(long schoolId, long groupId, Map<String, Object> data) {
		broadcast("/sc/" + schoolId + "/" + groupId, data, true, null);
	}

	/**
	 * 系统消息 ：发送给所有人
	 * 
	 * @param data
	 */
	public void systemBroadcast(Map<String, Object> data) {
		broadcast("/sys", data, true, null);
	}

	/**
	 * 系统消息：发送给指定用户
	 * 
	 * @param data
	 */
	public void systemBroadcast(Map<String, Object> data, long userId) {
		broadcast("/sys/" + userId, data, true, null);
	}

	public void broadcast(final String channel, final Map<String, Object> data,
			final boolean lazy, final String messageId) {
		new Thread() {
			public void run() {
				logger.debug("message channel:" + channel);
				try {
					ServerChannel ch = getBayeux().getChannel(channel);
					/* bugfix:用户未登录时，不存在消息通道 */
					if (ch != null) {
						// ch.setLazy(lazy);
						ch.publish(getServerSession(), data, messageId);
					}
				} catch (Exception e) {
					logger.error("instance message error!", e);
				}
			}
		}.start();
	}
}

/**
 * 保存sessionID，详细的可以用来做用户访问页面的统计； 现在用户是每一个页面建立一个消息机制，可以计算出用户在每个页面停留的时间；
 * 
 * @author Dove Wang
 * 
 */
class Tracer {
	private String id;
	private int value;

	public Tracer(String id, int value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String toString() {
		return "{sessionid:" + id + ",value:" + value + "}";
	}

}
