package flint.business.abc.service;

import flint.base.BaseService;

public interface VideoService extends BaseService {

	String getRecord(long id);

	String getConfig(long id);

}
