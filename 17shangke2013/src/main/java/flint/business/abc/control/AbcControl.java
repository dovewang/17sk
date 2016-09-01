package flint.business.abc.control;

import static kiss.util.Helper.$;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import entity.Room;
import flint.business.abc.room.RoomManager;
import flint.business.abc.room.context.RoomContext;
import flint.business.abc.room.context.RoomSession;
import flint.business.abc.room.support.RoomException;
import flint.business.abc.service.VideoService;
import flint.business.constant.AccountConst;
import flint.business.constant.ClassroomConst;
import flint.core.Configuration;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;

/**
 * 教室专用模块
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class AbcControl extends WebControl {

	@Autowired
	private RoomManager roomManager;

	@Autowired
	private VideoService videoService;

	/**
	 * 创建教室表单
	 * 
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/abc/form.html")
	public String form() throws ApplicationException {
		return "global:/app/abc/form";
	}

	/**
	 * 创建教室
	 * 
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/abc/create.html", method = RequestMethod.POST)
	public ModelAndView create(HttpServletRequest request)
			throws ApplicationException {
		long subjectId = $(request.getParameter("subjectId")).toLong();
		String subject = $(request.getParameter("subject")).isEmpty("临时教室");
		byte subjectType = $(request.getParameter("type")).toByte(
				ClassroomConst.ROOM_TEMP);
		int price = $(request.getParameter("price")).toInteger();
		String password = request.getParameter("password");
		Identity u = this.getUser(request);
		Room room = roomManager.create(u, (byte) 0, subjectId, subject,
				subjectType, price, password, request.getRemoteAddr());
		return success("教室创建成功！", "roomId", String.valueOf(room.getRoomId()));
	}

	/**
	 * 教室首页
	 */
	@RequestMapping("/abc/abc.html")
	public String abc() {
		return "global:/app/abc/abc";
	}

	@RequestMapping("/abc/wiziq.htm")
	public void wiziq(HttpServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("http://baidu.com").forward(request, response);
	}

	/**
	 * 教室引导页
	 * 
	 * <pre>
	 *     引导页分2种情况：
	 *      1、正在上课
	 * </pre>
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/abc/start.html")
	public ModelAndView start(HttpServletRequest request, long roomId)
			throws ApplicationException {
		Identity user = this.getUser(request);
		try {
			ModelAndView mav = new ModelAndView("global:/app/abc/start");
			RoomContext c = roomManager.get(roomId);
			long userId = $(user.getId()).toLong();
			Room room = c.getRoom();

			/* 如果没有出错信息 */
			if (Q.isEmpty(request.getParameter("e"))) {
				/* ==================已经结束的课程========================= */
				/* 课程已结束直接转向课程录像路径 */
				if (room.getStatus() == ClassroomConst.CLASS_OVER) {
					/* 检查用户是否有访问的权限，或者已经付费 */
					Room checkRoom = roomManager.playback(roomId, userId,
							false, false, request.getRemoteAddr());
					if (checkRoom != null) {
						user.setAttribute("token.play" + roomId, true);
						return new ModelAndView(new RedirectView("/v/id_"
								+ roomId + ".html"));
					}
				}

				/* ===================正在上的课程========================= */
				if (room.getStatus() == ClassroomConst.CLASS_OK) {
					/* 如果当前用户已经获得授权key就不在处理 */
					if (user.getAttribute("token." + roomId) != null) {
						return new ModelAndView(new RedirectView("/abc/room"
								+ roomId + ".html"));
					}

					/* 课程状态正常的教室，创建者或主持人直接转向教室 */
					if (room.getHost() == userId) {
						return new ModelAndView(new RedirectView(
								"/abc/login.html?roomId=" + room.getRoomId()
										+ "&password="
										+ $(room.getPassword()).isEmpty("")));
					}
				}
			}
			c.setAttribute("onlines", c.getOnlines());// 教室当前在线人数
			mav.addObject("room", c.getRoom());
			/* 查找教室相关信息 */
			mav.addObject("item", c.getAttribute("item"));
			return mav;
		} catch (RoomException e) {
			return this.failureView(e.getMessage());
		}
	}

	/**
	 * 用户登录教室，只对实时的有效
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/abc/login.html")
	public ModelAndView login(HttpServletRequest request, long roomId)
			throws ApplicationException {
		Identity user = this.getUser(request);
		try {
			long userId = $(user.getId()).toLong();
			RoomContext c = roomManager.get(roomId);
			Room room = c.getRoom();
			/* 收费的账户，需要验证key值，不验证主持人 */
			if (room.getPrice() > 0 && room.getHost() != userId
					&& room.getUserId() != userId) {
				String key = request.getParameter("key");
				String vkey = user.getAttribute(AccountConst.AUTHORIZE_TOKEN);
				if (vkey == null || !vkey.equals(key)) {
					// return mav.addObject("content", "授权码不存在，验证失败，请您重新授权！");
					return new ModelAndView(new RedirectView(
							"/abc/start.html?roomId=" + roomId
									+ "&e=auth.error"));
				}
			}
			/* 正在上课的课程，进入教室 */
			if (room.getStatus() == ClassroomConst.CLASS_OK) {
				/* 新的授权，同一用戶，不允許登錄同一教室 */
				RoomSession session = roomManager.login(
						request.getRemoteAddr(), user, roomId,
						request.getParameter("password"));
				if (session.isAuthenticated()) {
					user.setAttribute("token." + roomId, session.getId());
					return new ModelAndView(new RedirectView("/abc/room"
							+ roomId + ".html"));
				}
			} else if (room.getStatus() == ClassroomConst.CLASS_OVER) {
				user.setAttribute("token.pay." + roomId, true);// 这里验证用户观看录像会自动付款
				return new ModelAndView(new RedirectView("/v/id_" + roomId
						+ ".html"));
			}
		} catch (RoomException e) {
			return new ModelAndView(new RedirectView("/abc/start.html?roomId="
					+ roomId + "&e=" + e.getCode()));
		}
		return this.failureView("网络繁忙，请稍后再试");
	}

	/**
	 * 教室启动页面
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 * @throws RoomException
	 */
	@RequestMapping(value = "/abc/room{roomId}.html")
	public ModelAndView room(HttpServletRequest request,
			@PathVariable("roomId") long roomId) {
		try {
			Identity user = this.getUser(request);
			ModelAndView mav = new ModelAndView(new RedirectView(
					"/abc/start.html?roomId=" + roomId));
			/* 判断是否已有授权码 */
			String cs = user.getAttribute("token." + roomId);
			if (cs == null) {
				return mav;
			}
			long userId = this.getUserId(request);
			RoomSession session = roomManager.getSession(roomId, userId, cs);
			if (session == null) {
				return mav;
			}
			RoomContext c = session.getRoomContext();
			if (c.getRoom().getStatus() != ClassroomConst.CLASS_OK) {
				mav.addObject("room", c.getRoom());
				return mav;
			}
			mav = new ModelAndView("global:/app/abc/room");
			mav.addObject("room", c.getRoom());
			return mav;
		} catch (RoomException e) {
			return this.failureView(e.getMessage());
		}
	}

	/**
	 * 开始录制
	 * 
	 * @param request
	 * @param response
	 * @throws RoomException
	 */
	@RequestMapping(value = "/abc/record-start.html", method = RequestMethod.POST)
	public void recordStart(HttpServletRequest request,
			HttpServletResponse response) throws RoomException {
		String token = request.getParameter("cs");
		long roomid = Long.parseLong(request.getParameter("roomid"));
		roomManager.recordStart(roomid, token);
	}

	/**
	 * 课程结束
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 */
	@RequestMapping(value = "/abc/close.html", method = RequestMethod.POST)
	public ModelAndView close(HttpServletRequest request, long roomid) {
		try {
			Identity user = this.getUser(request);
			user.removeAttribute("token." + roomid);
			roomManager.close(roomid);
			return success("");
		} catch (RoomException e) {
			return failure(e.getMessage());
		}
	}

	/**
	 * 上课结束后的基本信息，确认付款信息
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 * @throws RoomException
	 */
	@RequestMapping("/abc/end.html")
	public ModelAndView end(HttpServletRequest request, long roomId)
			throws RoomException {
		ModelAndView mav = new ModelAndView("global:/app/abc/end");
		RoomContext c = roomManager.get(roomId);
		Room room = c.getRoom();
		mav.addObject("room", room);
		return mav;
	}

	/**
	 * 摄像头拍照
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/abc/camera.html", method = RequestMethod.POST)
	@ResponseBody
	public String camera(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {
		String picBase64 = request.getParameter("picstr");
		String userId = request.getParameter("id");
		String cs = request.getParameter("cs");
		if (!Q.isEmpty(picBase64)) {
			String fdir = "/files/photo/" + userId + "/";
			File dir = new File(Configuration.getWebRoot() + fdir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			byte[] pic = Q.base64(picBase64);
			String picpath = fdir + System.currentTimeMillis() + ".png";
			FileOutputStream out = new FileOutputStream(new File(
					Configuration.getWebRoot() + picpath));
			out.write(pic);
			out.close();
			return picpath;
		}
		return "";
	}

	/**
	 * 教室配置信息读取
	 * 
	 * @param id
	 * @param verify
	 * @param request
	 * @return
	 * @throws RoomException
	 */
	@RequestMapping(value = "/abc/config{id}.html")
	public ModelAndView classroomConfig(@PathVariable("id") long id,
			HttpServletRequest request) throws RoomException {
		return renderXML(roomManager.getConfig(id, getUser(request)));
	}

	/**
	 * 录制上课信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/abc/record.html", method = RequestMethod.POST)
	public void record(HttpServletRequest request, HttpServletResponse response) {
		long roomid = NumberHelper.toLong(request.getParameter("roomid"));
		String data = request.getParameter("data");
		if (!StringHelper.isEmpty(data)) {
			roomManager.record(roomid, data, request.getRemoteAddr(), 0);
		}
	}

	/**
	 * 课程回放
	 * 
	 * <pre>
	 *    课程回放的条件：
	 *       1、如果是在线课程、用户必须参加过该课程
	 * </pre>
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */

	/**
	 * 视频播放
	 * 
	 * @param request
	 * @param roomId
	 * @return
	 * @throws RoomException
	 * @throws ApplicationException
	 */
	@RequestMapping("/v/id_{roomId}.html")
	public ModelAndView playback(HttpServletRequest request,
			@PathVariable("roomId") long roomId) throws RoomException,
			ApplicationException {
		ModelAndView mav = new ModelAndView("global:/app/abc/player");
		Identity user = this.getUser(request);
		boolean allowPlay = Q.isNull(
				(Boolean) user.getAttribute("token.play." + roomId), false);
		boolean allowPay = Q.isNull(
				(Boolean) user.getAttribute("token.pay." + roomId), false);// 是否允许用户付款
		Room room = roomManager.playback(roomId, $(user.getId()).toLong(),
				allowPlay, allowPay, request.getRemoteAddr());
		/* 未获得授权 */
		if (room == null) {
			return new ModelAndView(new RedirectView("/abc/start.html?roomId="
					+ roomId));
		}
		mav.addObject("room", room);
		return mav;
	}

	/**
	 * 在线视频数据录制
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v/data{id}.html", method = RequestMethod.GET)
	public ModelAndView videoRecord(@PathVariable("id") long id,
			HttpServletRequest request) {
		return renderXML(videoService.getRecord(id));
	}

	/**
	 * 在线视频配置信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v/config{id}.html", method = RequestMethod.GET)
	public ModelAndView videoConfig(@PathVariable("id") long id,
			HttpServletRequest request) {
		return renderXML(videoService.getConfig(id));
	}
}
