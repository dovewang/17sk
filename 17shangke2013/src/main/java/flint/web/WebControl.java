package flint.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kiss.security.Identity;
import kiss.security.Passport;
import kiss.web.SecurityRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;

import flint.business.util.SchoolHelper;
import flint.common.Result;
import flint.context.CodeHelper;
import flint.context.ConfigHelper;
import flint.core.Configuration;
import flint.exception.ApplicationException;
import flint.util.WebHelper;

/**
 * 基础类
 * 
 * @author Dove Wang
 * 
 */
public class WebControl {

	/**
	 * 成功
	 */
	public final static int SUCCESS = 1;
	/**
	 * 警告
	 */
	public final static int WARN = 0;
	/**
	 * 失败
	 */
	public final static int FAILURE = -1;

	/**
	 * 异常
	 */
	public final static int EXCEPTION = -2;

	/**
	 * 用户session过期
	 */
	public final static int SESSION_TIME_OUT = -3;

	public final static String RESULT = "result";

	public final static String MESSAGE = "message";

	public final static String AJAX_MODEL = "AJAX_MODEL";

	/* 日志模块 */
	public final Logger logger = LoggerFactory.getLogger(getClass());

	/* JSON返回数据 */
	@Autowired
	private View jsonView;

	@Autowired
	private View stringView;

	@Autowired
	private View xmlView;

	@Autowired
	private View htmlView;

	@Autowired
	public CodeHelper codeHelper;

	@Autowired
	public ConfigHelper configHelper;

	public WebControl() {

	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

	}

	/**
	 * 平台统一异常处理
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
		logger.error("System Exception:", ex);
		String token = request.getParameter("ajax.token");
		boolean isAjax = request.getHeader("X-Requested-With") != null
				|| token != null;
		if (isAjax) {
			if (token != null) {
				return this.renderHTML(ex.getMessage(), EXCEPTION);
			}
			if ("debug".equals(Configuration.getModel())
					|| (ex instanceof ApplicationException)) {
				return exception(ex.getMessage());
			} else {
				return exception("网络繁忙，请稍后再试！");
			}
		} else {
			if ("debug".equals(Configuration.getModel())
					|| (ex instanceof ApplicationException)) {
				return failureView(ex.getMessage());
			} else {
				return failureView("网络繁忙，请稍后再试！");
			}
		}

	}

	public <X> ModelAndView jsonResult(Result<X> result) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"status\":").append(result.getStatus());
		if (result.getStatus() != Result.SUCCESS) {
			sb.append(",\"code\":\"").append(result.getCode()).append("\"");
			sb.append(",\"message\":\"")
					.append(codeHelper.getMessage(result.getCode()))
					.append("\"");
		}
		if (result.getResult() != null) {
			sb.append(",\"result\":").append(
					JSON.toJSONString(result.getResult()));
		}
		if (result.hasErrors()) {
			sb.append(",\"errors\":{");
			for (String err : result.getErrors()) {
				sb.append(err).append(",");
			}
			sb.replace(sb.length() - 1, sb.length(), "}");
		}
		sb.append("}");
		return new ModelAndView(stringView, MESSAGE, sb.toString());
	}

	/* =============================ajax消息传递================== */
	/**
	 * 成功消息
	 * 
	 * @param message
	 * @param value
	 * @return
	 */
	public ModelAndView success(String message, Map<String, Object> value) {
		return render(SUCCESS, message, value);
	}

	public ModelAndView success(String message, String param, Object value) {
		return render(SUCCESS, message, param, value);
	}

	public ModelAndView success(String message) {
		return success(message, null);
	}

	/**
	 * 失败消息
	 * 
	 * @param message
	 * @param value
	 * @return
	 */
	public ModelAndView failure(String message, Map<String, Object> value) {
		return render(FAILURE, message, value);
	}

	public ModelAndView failure(String message, String param, Object value) {
		return render(FAILURE, message, param, value);
	}

	public ModelAndView failure(String message) {
		return failure(message, null);
	}

	/**
	 * 警告消息
	 * 
	 * @param message
	 * @param value
	 * @return
	 */
	public ModelAndView warn(String message, Map<String, Object> value) {
		return render(WARN, message, value);
	}

	public ModelAndView warn(String message, String param, Object value) {
		return render(WARN, message, param, value);
	}

	public ModelAndView warn(String message) {
		return warn(message, null);
	}

	/**
	 * 异常消息
	 * 
	 * @param message
	 * @param value
	 * @return
	 */
	public ModelAndView exception(String message, Map<String, Object> value) {
		return render(EXCEPTION, message, value);
	}

	public ModelAndView exception(String message, String param, Object value) {
		return render(EXCEPTION, message, param, value);
	}

	public ModelAndView exception(String message) {
		return exception(message, null);
	}

	/**
	 * 一般的消息
	 * 
	 * @param status
	 * @param message
	 * @param param
	 * @param value
	 * @return
	 */
	public ModelAndView render(int status, String message, String param,
			Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(param, value);
		return render(status, message, map);
	}

	public ModelAndView render(int status, String message,
			Map<String, Object> value) {
		if (value == null) {
			value = new HashMap<String, Object>();
		}
		value.put("status", status);
		value.put("message", message);
		// value.put("success", SUCCESS==status);
		// value.put("warn", WARN==status);
		// value.put("failure", FAILURE==status);
		// value.put("exception", EXCEPTION==status);
		return render(value);
	}

	/**
	 * 字符串消息，自动转为JSON
	 * 
	 * @param result
	 * @return
	 */
	public ModelAndView render(String result) {
		return new ModelAndView(stringView, MESSAGE, result);
	}

	public ModelAndView renderHTML(String message) {
		return new ModelAndView(htmlView, MESSAGE, message);
	}

	/**
	 * 主要用来给文件上传返回值使用
	 * 
	 * @param html
	 * @return
	 */
	public ModelAndView renderHTML(String message, int status) {
		return renderHTML("{\"status\":" + status + ",\"message\":\""
				+ message.replaceAll("\"", "\\\"") + "\"}");
	}

	/**
	 * 转化为json
	 * 
	 * @param result
	 * @param value
	 * @return
	 */
	public ModelAndView render(Map<String, Object> value) {
		return new ModelAndView(jsonView, value);
	}

	public ModelAndView render(String param, Object value) {
		return new ModelAndView(jsonView, param, value);
	}

	/**
	 * 转为xml
	 * 
	 * @param value
	 * @return
	 */
	public ModelAndView renderXML(String value) {
		return new ModelAndView(xmlView, MESSAGE, value);
	}

	/**
	 * 获取用户Session
	 * 
	 * @param request
	 * @return
	 */
	public Passport getPassport(HttpServletRequest request) {
		return (Passport) request.getSession().getAttribute(Passport.PRINCIPAL);
	}

	/**
	 * 获取当前用户登录的域
	 * 
	 * @param request
	 * @return
	 */
	public Identity getUser(HttpServletRequest request) {
		// Person user = (Person) getPassport(request).get(getDomain(request));
		// /* 如果 */
		// if (!"www".equals(user.getSchoolDomain()) && user.getSchoolId() == 0)
		// user.setSchoolId(getSchoolId(request));
		return this.getPassport(request).get();
	}

	/**
	 * 获取用户ID
	 * 
	 * @param request
	 * @param check
	 *            是否检查用户登录
	 * @return
	 */
	public long getUserId(HttpServletRequest request) {
		Identity user = getUser(request);
		return Long.parseLong(user.getId());
	}

	/**
	 * 重定向
	 * 
	 * @param url
	 * @return
	 */
	public String redirect(String url) {
		return "redirect:" + Configuration.getProperty("web.mapping", "") + url;
	}

	/**
	 * 重定向
	 * 
	 * @param url
	 * @return
	 */
	public View redirectView(String url) {
		return new RedirectView(Configuration.getProperty("web.mapping", "")
				+ url);
	}

	/**
	 * 返回失败页面
	 * 
	 * @param result
	 * @return
	 */
	public ModelAndView failureView(Result<?> result) {
		ModelAndView mav = new ModelAndView("/failure", RESULT, result);
		mav.addObject("message", codeHelper.getMessage(result.getCode()));
		return mav;
	}

	public ModelAndView failureView(String message) {
		ModelAndView mav = new ModelAndView("/failure");
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * 获取域名信息
	 * 
	 * @param request
	 * @return
	 */
	public String getDomain(HttpServletRequest request) {
		return WebHelper.getSubDomain(request,
				configHelper.getString("site", "domain"));
	}

	/**
	 * 获取学校编号
	 * 
	 * @param request
	 * @return
	 */
	public long getSchoolId(HttpServletRequest request) {
		return SchoolHelper.getSchool(getDomain(request));
	}
}
