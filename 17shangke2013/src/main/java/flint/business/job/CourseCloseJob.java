package flint.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import flint.business.course.service.CourseService;
import flint.util.SpringHelper;

/**
 * 课程如果过了上课时间，没有开始上课，按配置间隔时间进行清理
 * 
 * @author Dove Wang
 * 
 *         默认每晚2点清理 0 0 2 1/1 * ? *
 */
public class CourseCloseJob extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		CourseService sevice = SpringHelper.getBean(CourseService.class);
		sevice.clear();//清理未上的课程
	}

}
