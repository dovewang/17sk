package flint.business.message;

import static kiss.util.Helper.$;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Message;
import flint.business.user.service.MessageService;
import flint.business.util.SchoolHelper;
import flint.exception.ApplicationException;
import flint.security.word.DefaultWordFilter;
import flint.web.WebControl;

@Controller
public class IMControl extends WebControl {

	@Autowired
	private CometdService cometdService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private DefaultWordFilter bbcodeHelper;

	/**
	 * 显示2个人24小时以内的聊天记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/im/now.html", method = RequestMethod.POST)
	public ModelAndView now(long friend, HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Message> list = messageService.now(this.getUserId(request), friend);
		for (Message m : list) {
			if(m.getType()==32){//系统发出问题回答连接
				continue;
			}
			m.setMessage(bbcodeHelper.doFilter(m.getMessage(), "1").getReplaceContent());
		}
		map.put("listResult", list);
		return render(RESULT, map);
	}

	/**
	 * 默认显示最近1周内联系过的用户，默认按时间排序
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/im/recent.users.html", method = RequestMethod.POST)
	public ModelAndView recentUsers(HttpServletRequest request) {
		return render(RESULT, messageService.recentUsers(this.getUserId(request)));
	}
	
	/**
	 * 获取用户未读的系统消息的数量
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/im/getMessageCount.html", method = RequestMethod.POST)
	public ModelAndView getSystemMessageCount(HttpServletRequest request) {
		return render(RESULT, messageService.getSystemMessageCount(getUserId(request), SchoolHelper.getSchool(getDomain(request))));
	}

	/**
	 * <pre>
	 *            shower : cim,
	 * 					message : this.value,
	 * 					sender : Env.USER_ID,
	 * 					name : Env.USER_NAME,
	 * 					face : Env.face,
	 * 					receiver : cim,
	 * 					dateline : window.parseInt(new Date().getTime() / 1000)
	 * </pre>
	 * 
	 * @param friend
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/im/post.html", method = RequestMethod.POST)
	public ModelAndView post(HttpServletRequest request) throws ApplicationException {
		int dateline = Q.now();
		byte type=$(request.getParameter("type")).toByte();
		long userId = this.getUserId(request);

		/* 保存消息 */
		Message message = new Message();
		message.setMessage(request.getParameter("message"));
		message.setSendTime(dateline);
		message.setSchoolId(SchoolHelper.getSchool(getDomain(request)));
		message.setSender(userId);
		message.setReceiver($(request.getParameter("receiver")).toLong());
		message.setType(type);
		long mid = messageService.im(message);

		/* 发送消息 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", mid);
		data.put("shower", request.getParameter("shower"));
		data.put("message", bbcodeHelper.doFilter(request.getParameter("message"), "1").getReplaceContent());
		data.put("name", request.getParameter("name"));
		data.put("face", request.getParameter("face"));
		data.put("type", type);
		data.put("sender", String.valueOf(userId));
		data.put("receiver", message.getReceiver());
		data.put("dateline", dateline);
		cometdService.privateBroadcast(message.getReceiver(), data);

		return render("message", (String) data.get("message"));
	}
}
