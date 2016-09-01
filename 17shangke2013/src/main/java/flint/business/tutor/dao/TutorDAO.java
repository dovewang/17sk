package flint.business.tutor.dao;

import java.util.List;

import entity.Tutor;
import entity.TutorDemand;
import entity.User;
import flint.base.BaseDAO;
import flint.common.Page;

public interface TutorDAO extends BaseDAO<Tutor, Long> {

	Page<Tutor> search(byte type, int page, long size);
	
	/**
	 * 个人发布铺导需求
	 * @param page
	 * @param size
	 * @return
	 */
	Page<TutorDemand> searchDemand(long userId, int page, long size);
	
	/**
	 * 个人发布铺导服务
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Tutor> searchTutor(long userId, int page, long size);
	
	/**
	 * 个人铺导服务删除
	 * @param tutorId
	 * @return
	 */
	int deleteTutor(long tutorId);
	
	/**
	 * 个人铺导需求删除
	 * @param tutorId
	 * @return
	 */
	int deleteTutorDemand(long tutordemandId);
	
	/**
	 * 个人铺导服务更新
	 * @param tutorId
	 * @return
	 */
	int updateTutor(long id, long kind1, long kind2, long kind3, int price, String intro);
	
	/**
	 * 个人铺导需求更新
	 * @param tutorId
	 * @return
	 */
	int updateTutorDemand(long id, long kind1, long kind2, long kind3, int price, String intro);

	Tutor selectTutor(long id);
	
	TutorDemand selectTutorDemand(long id);
	
	long addCandidate(long userId, long candidate);

	Page<User> findCandidate(long userId, int page, long size);

	List<TutorDemand> guessDemand(String focus, long userId, long page, long size);
	
	/**
	 * 根据用户ID获取个人辅导需求
	 * @param page
	 * @param size
	 * @return
	 */
	List<TutorDemand> searchDemandByUserids(String userids);
	
	/**
	 * 根据用户ID获取个人辅导服务
	 * @param page
	 * @param size
	 * @return
	 */
	List<Tutor> searchTutorByUserids(String userids);
}
