package flint.business.exercise.dao;

import java.util.List;
import java.util.Map;

import entity.Exercise;
import entity.QuestionBank;
import entity.UserExercise;
import flint.base.BaseDAO;
import flint.common.Page;

public interface ExerciseDAO extends BaseDAO<Exercise, Long> {

	/**
	 * 获取某个练习的问题列表
	 * 
	 * @param userId
	 * @param exerciseId
	 * @return
	 */
	List<Map<String, Object>> get(long exerciseId);

	Exercise getSubjectExercise(long schoolId, long userId, long subjectId, byte subjectType);

	/**
	 * 添加一个问题到练习题中
	 * 
	 * @param exerciseId
	 * @param questionId
	 * @param displayOrder
	 * @return
	 */
	int add(long exerciseId, long questionId, int displayOrder, int score);

	/**
	 * 从练习中移除一个问题
	 * 
	 * @param userId
	 * @param exerciseId
	 * @param questionId
	 * @return
	 */
	int remove(long userId, long exerciseId, long questionId);

	int order(long exerciseId, long questionId, int upOrDown);

	Page<Exercise> list(long userId, int type, String keyword, long page, int size, String order);

	long saveOrUpdate(Exercise exercise);

	int mark(long userId, long exerciseId);

	int autoMark(long userId, long exerciseId, long id);

	List<UserExercise> result(long roomId, long exerciseId);

	int delete(long userId, long exerciseId);

	QuestionBank getQuestion(long questionId);

	void receive(long userId, long roomId, long exerciseId);

	Page<QuestionBank> searchQ(long page, long size, long schoolId, long userId, int type, String condition);

	int deleteQuestion(long questionId);
}
