package flint.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import flint.business.search.CourseSearch;

public class CourseIndexJob extends QuartzJobBean {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			CourseSearch courseSearch = (CourseSearch) context.getScheduler().getContext().get("courseSearch");
			courseSearch.mergeIndex();
		} catch (SchedulerException e) {
			logger.error("course index merge error:{}", e);
		}
	}

}
