package flint.business.tutor.service;

import java.util.List;

import entity.Tutor;
import entity.TutorDemand;
import entity.User;
import flint.base.BaseService;
import flint.common.Page;
import flint.exception.DataAccessException;

public interface TutorService extends BaseService {

	/**
	 * 发布辅导服务
	 * 
	 * @param tuor
	 * @return
	 */
	int postService(Tutor tuor);
	
	/**
	 * 个人铺导服务删除
	 * @param tutorId
	 * @return
	 */
	int deleteTutor(long tutorId) throws DataAccessException;
	
	/**
	 * 个人铺导需求删除
	 * @param tutorId
	 * @return
	 */
	int deleteTutorDemand(long tutordemandid);

	/**
	 * 发布辅导需求
	 * 
	 * @param demand
	 * @return
	 */
	int postDemand(TutorDemand demand);
	
	/**
	 * 个人发布铺导服务
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Tutor> searchTutor(long userId, int page, long size);
	
	/**
	 * 个人铺导服务更新
	 * @param tutorId
	 * @return
	 */
	int updateTutor(long id, long kind1, long kind2, long kind3, int price, String intro, long userid);
	
	/**
	 * 个人铺导需求更新
	 * @param tutorId
	 * @return
	 */
	int updateTutorDemand(long id, long kind1, long kind2, long kind3, int price, String intro, long userid);
	
	Tutor selectTutor(long id);
	
	TutorDemand selectTutorDemand(long id);

	Page<Tutor> search(byte type, String condition, String keyword, int page, long size);
	
	Page<TutorDemand> searchDemand(long userId, int page, long size);

	long addCandidate(long userId, long candidate);

	Page<User> findCandidate(long userId, int page, long size);

	List<TutorDemand> guessDemand(String focus, long userId, long page, long size);
}
