package flint.business.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import flint.business.search.QuestionSearch;
import flint.util.SpringHelper;

public class QuestionIndexJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		QuestionSearch questionSearch = SpringHelper.getBean(QuestionSearch.class);
		questionSearch.mergeIndex();
	}

}
