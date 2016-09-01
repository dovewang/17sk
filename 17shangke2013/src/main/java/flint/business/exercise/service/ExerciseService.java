package flint.business.exercise.service;

import java.util.List;
import java.util.Map;

import entity.Exercise;
import entity.QuestionBank;
import entity.UserExercise;
import flint.base.BaseService;
import flint.common.Page;
import flint.exception.ApplicationException;

public interface ExerciseService extends BaseService {

	/**
	 * 保存一个问题
	 * 
	 * @param question
	 * @return
	 */
	long saveQuestion(QuestionBank question) throws ApplicationException;

	/**
	 * 添加一道题
	 * 
	 * @param exerciseId
	 * @param questionId
	 * @param displayOrder
	 * @return
	 * @throws ApplicationException
	 */
	int add(long exerciseId, long questionId, int displayOrder, int score) throws ApplicationException;

	/**
	 * 移除一道题
	 * 
	 * @param userId
	 * @param exerciseId
	 * @param questionId
	 * @return
	 * @throws ApplicationException
	 */
	int remove(long userId, long exerciseId, long questionId) throws ApplicationException;

	/**
	 * 删除整个练习
	 * 
	 * @param userId
	 * @param exerciseId
	 * @return
	 * @throws ApplicationException
	 */
	int delete(long userId, long exerciseId) throws ApplicationException;

	/**
	 * 上移下移
	 * 
	 * @param userId
	 * @param questionId
	 * @return
	 * @throws ApplicationException
	 */
	int order(long userId, long exerciseId, long questionId, int upOrDown) throws ApplicationException;

	int done(long userId, long exerciseId) throws ApplicationException;

	String random(long userId) throws ApplicationException;

	/**
	 * 获取练习题
	 * 
	 * @param userId
	 * @param exerciseId
	 * @return
	 * @throws ApplicationException
	 */
	List<Map<String, Object>> get(long exerciseId) throws ApplicationException;

	Exercise getSubjectExercise(long schoolId, long userId, long subjectId, byte subjectType);

	int submit(long userId, long roomId, long exerciseId, Map<String, String[]> map) throws ApplicationException;

	int mark(long userId, long exerciseId, long id) throws ApplicationException;

	List<UserExercise> result(long roomId, long exerciseId) throws ApplicationException;

	Page<Exercise> list(long userId, int type, String keyword, long page, int size, String order);

	long saveOrUpdate(Exercise exercise);

	void receive(long id, long roomId, long exerciseId);

	/* ==============练习题库============ */

	/**
	 * 题库检索
	 * 
	 * @param schoolId
	 * @param type
	 * @param condition
	 * @return
	 */
	Page<QuestionBank> searchQ(long page, long size, long schoolId, long userId, int type, String condition);

	/**
	 * 获得单个问题信息
	 * 
	 * @param questionId
	 * @return
	 */
	QuestionBank getQuestion(long questionId);

	int updateQuestion(QuestionBank question);

	int deleteQuestion(long questionId);

}
