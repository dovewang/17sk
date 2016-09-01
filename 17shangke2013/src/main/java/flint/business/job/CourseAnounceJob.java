package flint.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 课程通知,主要用于即将开始的课程，通知用户参加课程
 * 
 * @author Dove Wang
 * 每隔10分钟检查一次：
 * 1、
 * 2、距离上课时间30小时邮件通知一次，短信通知一次
 * 3、具体上课时间15钟短信通知一次
 * 4、距离上课前5分钟，WEB页面通知，如果用户不在线，短信通知
 * 5、老师如果没有按时上课？
 */
public class CourseAnounceJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

	}

}
