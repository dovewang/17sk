package flint.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 问题无人解答时定时关闭工具
 * 
 * @author Dove Wang
 * 
 */
public class QuestionCloseJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

	}

}
