package flint.business.exercise.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Exercise;
import entity.QuestionBank;
import entity.UserExercise;
import entity.UserExerciseRecord;
import flint.base.BaseServiceImpl;
import flint.business.exercise.dao.ExerciseDAO;
import flint.business.exercise.service.ExerciseService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;

@Service
public class ExerciseServiceImpl extends BaseServiceImpl implements ExerciseService {
	@Autowired
	private ExerciseDAO dao;

	@Override
	public long saveQuestion(QuestionBank question) throws ApplicationException {
		return dao.insert(question);
	}

	@Override
	public Page<QuestionBank> searchQ(long page, long size, long schoolId, long userId, int type, String condition) {
		return dao.searchQ(page, size, schoolId, userId, type, condition);
	}

	@Override
	public int updateQuestion(QuestionBank question) {
		return dao.update(question, " where question_id=? ", question.getQuestionId());
	}

	@Override
	public int deleteQuestion(long questionId) {
		return dao.deleteQuestion(questionId);
	}

	@Override
	public int add(long exerciseId, long questionId, int displayOrder, int score) throws ApplicationException {
		return dao.add(exerciseId, questionId, displayOrder, score);
	}

	@Override
	public int remove(long userId, long exerciseId, long questionId) throws ApplicationException {
		return dao.remove(userId, exerciseId, questionId);
	}

	@Override
	public int delete(long userId, long exerciseId) throws ApplicationException {
		return dao.delete(userId, exerciseId);
	}

	@Override
	public int done(long userId, long exerciseId) throws ApplicationException {
		return 0;
	}

	@Override
	public String random(long userId) throws ApplicationException {

		return null;
	}

	@Override
	public List<Map<String, Object>> get(long exerciseId) throws ApplicationException {
		return dao.get(exerciseId);
	}

	@Override
	public int submit(long userId, long roomId, long exerciseId, Map<String, String[]> map) throws ApplicationException {
		UserExercise userExercise = new UserExercise();
		userExercise.setExerciseTime(DateHelper.getNowTime());
		userExercise.setExerciseId(exerciseId);
		userExercise.setUserId(userId);
		userExercise.setRoomId(roomId);
		long id = dao.insert(userExercise);
		/* 这里只记录了用户做题的信息，还没有开始阅卷 */
		List<UserExerciseRecord> records = new ArrayList<UserExerciseRecord>();
		for (Map.Entry<String, String[]> m : map.entrySet()) {
			String key = m.getKey();
			if (key.startsWith("q")) {
				String questionId = key.substring(1);
				String answer = StringHelper.arrayToString(m.getValue());
				UserExerciseRecord record = new UserExerciseRecord();
				record.setAnswer(answer);
				record.setQuestionId(NumberHelper.toLong(questionId));
				record.setUserId(userId);
				record.setId(id);
				records.add(record);
			}
		}
		dao.save(records);
		return 1;
	}

	@Override
	public int mark(long userId, long exerciseId, long id) throws ApplicationException {
		/* 自动阅卷 */
		return dao.autoMark(userId, exerciseId, id);
	}

	@Override
	public List<UserExercise> result(long roomId, long exerciseId) throws ApplicationException {
		return dao.result(roomId, exerciseId);
	}

	@Override
	public Page<Exercise> list(long userId, int type, String keyword, long page, int size, String order) {
		return dao.list(userId, type, keyword, page, size, order);
	}

	@Override
	public int order(long userId, long exerciseId, long questionId, int upOrDown) throws ApplicationException {
		return dao.order(exerciseId, questionId, upOrDown);
	}

	@Override
	public Exercise getSubjectExercise(long schoolId, long userId, long subjectId, byte subjectType) {
		return dao.getSubjectExercise(schoolId, userId, subjectId, subjectType);
	}

	@Override
	public long saveOrUpdate(Exercise exercise) {
		return dao.saveOrUpdate(exercise);
	}

	@Override
	public QuestionBank getQuestion(long questionId) {
		return dao.getQuestion(questionId);
	}

	@Override
	public void receive(long userId, long roomId, long exerciseId) {
		dao.receive(userId, roomId, exerciseId);
	}

}