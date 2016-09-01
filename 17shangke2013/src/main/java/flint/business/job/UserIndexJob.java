package flint.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import flint.business.search.UserSearch;
import flint.util.SpringHelper;

public class UserIndexJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		UserSearch teacherSearch = SpringHelper.getBean(UserSearch.class);
		teacherSearch.mergeIndex();
	}

}
