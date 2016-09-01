package flint.business.mail;

import static kiss.util.Helper.$;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flint.business.core.Domain;
import flint.business.util.DomainHelper;
import flint.context.ConfigHelper;
import flint.mail.MailService;

@Service
public class MailManager {

	private final static String NO_REPLY = "noreply@17shangke.com";

	private final static Logger logger = LoggerFactory
			.getLogger(MailManager.class);
	@Autowired
	private MailService mailService;
	@Autowired
	private DomainHelper domainHelper;
	@Autowired
	private ConfigHelper configHelper;

	/**
	 * 用户注册方式信息
	 * 
	 * @param subdomain
	 * @param value
	 * @param mail
	 */
	public void signUp(String subdomain, Map<String, Object> value, String email) {
		batch(subdomain, "欢迎您加入一起上课", "register", value, NO_REPLY, email);
	}

	/**
	 * 企业注册新用户密码重置通知
	 * 
	 * @param subdomain
	 * @param value
	 * @param mail
	 */
	public void resetPass(String subdomain, Map<String, Object> value,
			String email) {
		batch(subdomain, "欢迎您的加入", "school.create.register", value,
				NO_REPLY, email);
	}

	/**
	 * 密码找回
	 * 
	 * @param value
	 * @param email
	 */
	public void recovery(Map<String, Object> value, String email) {
		batch("www", "密码找回", "recovery", value, NO_REPLY, email);
	}

	/**
	 * 报名课程通知
	 * 
	 * @param value
	 * @param to
	 */
	public void joinCourse(String subdomain, Map<String, Object> value,
			String teacher, String master, String... users) {
		/* 发送给学校或者老师 */
		batch(subdomain, "课程报名通知", "course.join.teacher", value, NO_REPLY,
				teacher);
		/* 发送给管理员，在线课程不用发送给管理员 */
		if (master != null)
			batch(subdomain, "课程报名通知", "course.join.master", value, NO_REPLY,
					master);
		/* 发送给报名账号和用户 */
		batch(subdomain, "课程报名通知", "course.join.student", value, NO_REPLY,
				$(users).unique().toArray());
	}

	public void sendCourseStatusToCreator(String subdomain,
			final Map<String, Object> value, final String to) {
		batch(subdomain, "课程状态信息", "course_status", value, NO_REPLY, to);
	}

	/**
	 * 课程修改通知
	 * 
	 * @param value
	 * @param to
	 */
	public void modifyCourse(String subdomain, final Map<String, Object> value,
			final String[] to) {
		batch(subdomain, "课程调整通知", "course.modify", value, NO_REPLY, to);
	}

	/**
	 * 课程取消通知
	 * 
	 * @param value
	 * @param to
	 */
	public void cancleCourse(String subdomain, final Map<String, Object> value,
			final String[] to) {
		batch(subdomain, "课程取消通知", "course.cancle", value, NO_REPLY, to);
	}

	public void closeCourse(String subdomain, final Map<String, Object> value,
			final String[] to) {
		batch(subdomain, "课程关闭通知", "course.close", value, NO_REPLY, to);
	}

	private void processSchool(String subdomain, Map<String, Object> value) {
		String maindomain = configHelper.getString("site", "domain");
		// String subdomain = WebHelper.getSubDomain(request, maindomain);
		logger.debug(" user view subdomain :" + subdomain);
		Domain domain = null;
		if (subdomain.equals("") || subdomain.equals("www")) {
			domain = new Domain();
			domain.setId(0);
			domain.setName(configHelper.getString("site", "name"));
			domain.setDomain("www");
			domain.setLogo(configHelper.getString("site", "logo"));
			domain.setThemePath(configHelper.getString("site", "theme"));
		} else {
			domain = domainHelper.getDomain(subdomain);
		}

		value.put("domain", domain.getDomain() + "." + maindomain);
		value.put("site", domain);
		value.put("mailAccount", "newsletter@" + maindomain);
	}

	/* =================学校部分=================== */
	public void joinSchool(String subdomain, final Map<String, Object> value,
			String to) {
		batch(subdomain, "欢迎您加入本校", "school_join", value, NO_REPLY, to);
	}

	/**
	 * 使用线程方式批量发送邮件
	 * 
	 * @param form
	 * @param subject
	 * @param template
	 * @param value
	 * @param to
	 */
	public void batch(String subdomain, final String subject,
			final String template, final Map<String, Object> value,
			final String from, final String... to) {
		processSchool(subdomain, value);
		new Thread() {
			public void run() {
				try {
					Domain domain = (Domain) value.get("site");
					value.put("SUBJECT", subject);
					value.put("template", template);
					mailService.send(domain.getName() + "<" + from + ">",
							subject, domain.getThemePath() + "/email/mail.ftl",
							value, to);
				} catch (Exception e) {
					logger.error("email send error! ", e);
				}
			}
		}.start();
	}

}
