package flint.business.user.control;

import static kiss.util.Helper.$;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import entity.Message;
import flint.business.constant.MessageConst;
import flint.business.user.service.MessageService;
import flint.business.util.SchoolHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.util.StringHelper;
import flint.web.WebControl;
import flint.web.util.CookieHelper;

@Controller
public class MessageControl extends WebControl {

	@Autowired
	private MessageService messageService;

	/**
	 * 用户消息页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/message.html")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		byte type = NumberHelper.toByte(request.getParameter("type"),(byte)0);
		String view = "/user/message";
		if (type == MessageConst.SYSTEM) {
			/* 清空缓存的系统消息的Cookie信息*/
			CookieHelper.removeCookie(request, response, "message.count");
		} else if (type == 9){
			/* 清空消息墙的Cookie信息*/
			CookieHelper.removeCookie(request, response, "message.chat");
		}
		ModelAndView mav = new ModelAndView(view);
		long userId = getUserId(request);
		mav.addObject("unread", messageService.statUnread(userId));
		mav.addObject("unsend", messageService.statUnsend(userId));
		//String schoolId = $(this.getUser(request).getSchoolIds()).join();
		String schoolId=String.valueOf(this.getUser(request).getDomainId());
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		Page<Map<String,Object>> list = messageService.inbox(userId, request.getParameter("anotherid"), schoolId, (byte) type, page);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		mav.addObject("messageType", type);
		return mav;
	}
	
	/**
	 * 学校后台消息页面
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/schoolAnnounce.html")
	public ModelAndView schoolAnnounce(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String view = "/admin/messageList";
		ModelAndView mav = new ModelAndView(view);
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		Page<Message> list = messageService.schoolAnnounce(getSchoolId(request), page, 10);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 消息发送界面
	 * 
	 * @param request
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/messageForm.html")
	public void messageForm(HttpServletRequest request) {

	}
	
	/**
	 * 查看消息
	 * 
	 * @param HttpServletRequest
	 * @return String
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/readMessage.html", method = RequestMethod.POST)
	@ResponseBody
	public String viewMessage(String ids,int type, HttpServletRequest request) throws ApplicationException {
		return String.valueOf(messageService.read(ids, type, getUserId(request)));
	}

	/**
	 * 发件箱
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/outbox.html")
	public ModelAndView outbox(HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("/user/messageList");
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		Page<Message> list = messageService.outbox(getUserId(request), page, 10);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		mav.addObject(RESULT, list);
		mav.addObject("allowReplay", false);
		return mav;
	}

	/**
	 * 草稿箱
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/user/drafts.html")
	public ModelAndView drafts(HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("/user/messageList");
		long page = NumberHelper.toLong(request.getParameter("p"), 1);
		Page<Message> list = messageService.drafts(getUserId(request), page, 10);
		list.setUrl(request.getRequestURI());
		list.setParam(StringHelper.queryStringReplace(request.getQueryString(), "p", "@"));
		mav.addObject(RESULT, list);
		mav.addObject("allowReplay", false);
		return mav;
	}

	/**
	 * 发送消息
	 * 
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/sendMessage.html", method = RequestMethod.POST)
	public ModelAndView sendMessage(HttpServletRequest request) throws ApplicationException {
		long sender = getUserId(request);
		long receiver = Long.parseLong(request.getParameter("receiver"));
		if (sender == receiver) {
			return failure("您不能给自己发消息!");
		}
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		// 保存到实体
		Message message = new Message();
		message.setMessage(content);
		message.setSubject(subject);
		message.setSchoolId(SchoolHelper.getSchool(getDomain(request)));
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setType(MessageConst.PRIVATE);
		if (messageService.send(message,getUser(request)) > 0) {
			return success("发送成功");
		} else {
			return failure("发送失败!");
		}
	}

	/**
	 * 删除我发送给他人的消息
	 * 
	 * @param messageId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/deleteMessageSendStatus.html", method = RequestMethod.POST)
	public ModelAndView deleteMessageSendStatus(long id) throws ApplicationException {
		if (messageService.deleteSendstatus(id) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}
	
	/**
	 * 删除他人发送给我的消息
	 * @param messageId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/deleteMessageReceiveStatus.html", method = RequestMethod.POST)
	public ModelAndView deleteMessageReceiveStatus(long id, HttpServletRequest request) throws ApplicationException {
		if (messageService.deleteReceivestatus(id, getUserId(request)) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}
	
	/**
	 * 删除他人发送给我和我发送给他人的消息
	 * 
	 * @param messageId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/user/deleteMessage.html", method = RequestMethod.POST)
	public ModelAndView deleteMessage(long other, HttpServletRequest request) throws ApplicationException {
		if (messageService.deleteMessage(getUserId(request), other) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}

	/* ===============后台管理部分==================== */
	/**
	 * 发布公告
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/admin/announce.html")
	@ResponseBody
	public String postAnnounce(HttpServletRequest request) throws ApplicationException {
		Identity user = getUser(request);
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		/* 发送给该学校的所有人 */
		Message message = new Message();
		message.setMessage(content);
		message.setSchoolId(SchoolHelper.getSchool(getDomain(request)));
		message.setSubject(subject);
		message.setSender(Long.parseLong(user.getId()));
		/* 这里的收信人是学校编号 */
		message.setReceiver(message.getSchoolId());
		message.setType(MessageConst.SCHOOL);
		return String.valueOf(messageService.send(message,getUser(request)));
	}

	/**
	 * 删除公告
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/admin/deleteAnnounce.html", method = RequestMethod.POST)
	public ModelAndView deleteAnnounce(long messageId, HttpServletRequest request) throws ApplicationException {
		long schoolId = SchoolHelper.getSchool(getDomain(request));
		if (messageService.deleteAnnounce(schoolId, messageId) > 0) {
			return success("删除公告信息成功！");
		} else {
			return failure("删除公告信息失败！");
		}
	}
}
