package flint.business.exercise.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import entity.Exercise;
import entity.QuestionBank;
import entity.UserExercise;
import flint.base.BaseDAOImpl;
import flint.business.exercise.dao.ExerciseDAO;
import flint.common.Page;
import flint.jdbc.RowSetHandler;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;

@Repository
public class ExerciseDAOImpl extends BaseDAOImpl<Exercise, Long> implements ExerciseDAO {

	@Override
	public List<Map<String, Object>> get(long exerciseId) {
		return findForMap("SELECT   TB_EXERCISE_QUESTIONS.EXERCISE_ID,  TB_EXERCISE_QUESTIONS.QUESTION_ID,TB_EXERCISE_QUESTIONS.DISPLAY_NUMBER,TB_QUESTION_BANK.TYPE,TB_QUESTION_BANK.CONTENT,TB_QUESTION_BANK.PICKS,TB_QUESTION_BANK.ANSWER  FROM  TB_EXERCISE_QUESTIONS,TB_QUESTION_BANK where TB_EXERCISE_QUESTIONS.EXERCISE_ID=? and TB_EXERCISE_QUESTIONS.QUESTION_ID=TB_QUESTION_BANK.QUESTION_ID   ORDER BY TB_EXERCISE_QUESTIONS.DISPLAY_NUMBER", exerciseId);
	}

	@Override
	public Exercise getSubjectExercise(long schoolId, long userId, long subjectId, byte subjectType) {
		Exercise exercise = findFirst(Exercise.class, "select * from TB_EXERCISE where subject_id=? and subject_type=? ", subjectId, subjectType);
		if (exercise == null) {
			exercise = new Exercise();
			exercise.setSchoolId(schoolId);
			exercise.setSubjectId(subjectId);
			exercise.setSubject("练习");
			exercise.setTag("");
			exercise.setTimeLimit(10);
			exercise.setUserId(userId);
			exercise.setCreateTime(DateHelper.getNowTime());
			exercise.setSubjectType(subjectType);
			long id = insert(exercise);
			exercise = findFirst(Exercise.class, "select * from TB_EXERCISE where EXERCISE_ID=? ", id);
		}
		return exercise;
	}

	@Override
	public int add(long exerciseId, long questionId, int displayOrder, int score) {
		return update("insert into  TB_EXERCISE_QUESTIONS(EXERCISE_ID,QUESTION_ID, DISPLAY_NUMBER,SCORE) values(?,?,?,?)", exerciseId, questionId, displayOrder, score);
	}

	@Override
	public int remove(long userId, long exerciseId, long questionId) {
		return update("delete from TB_EXERCISE_QUESTIONS where EXERCISE_ID=? and question_id=? ", exerciseId, questionId);
	}

	@Override
	public int order(long exerciseId, long questionId, int upOrDown) {
		return update("update TB_EXERCISE_QUESTIONS set DISPLAY_NUMBER=DISPLAY_NUMBER+?  where EXERCISE_ID=? and QUESTION_ID=?", upOrDown, exerciseId, questionId);
	}

	@Override
	public Page<Exercise> list(long userId, int type, String keyword, long page, int size, String order) {
		String q = "";
		if (!StringHelper.isEmpty(keyword)) {
			q = "   and subject  like  '%" + keyword + "%'     ";
		}
		return findPage(Exercise.class, "select * from TB_EXERCISE where user_id=?" + q + " order by create_time desc", "select count(EXERCISE_ID) from TB_EXERCISE where user_id=?  " + q, page, size, userId);
	}

	@Override
	public long saveOrUpdate(Exercise exercise) {
		if (exercise.getExerciseId() > 0) {
			return update(exercise);
		} else {
			return insert(exercise);
		}
	}

	@Override
	public int mark(long userId, long exerciseId) {
		return 0;
	}

	@Override
	public int autoMark(final long userId, long exerciseId, final long id) {
		StringBuilder sql = new StringBuilder(" update TB_USER_EXERCISE_RECORD ");
		sql.append(" INNER JOIN (SELECT TB_QUESTION_BANK.QUESTION_ID,KNOWLEDGE,ANSWER,TB_EXERCISE_QUESTIONS.SCORE from TB_QUESTION_BANK,TB_EXERCISE_QUESTIONS where TB_QUESTION_BANK.QUESTION_ID=TB_EXERCISE_QUESTIONS.QUESTION_ID and  TB_EXERCISE_QUESTIONS.EXERCISE_ID=?) r on r.QUESTION_ID=TB_USER_EXERCISE_RECORD.QUESTION_ID ");
		sql.append(" SET CORRECT=IF(r.ANSWER=TB_USER_EXERCISE_RECORD.ANSWER,1,0),TB_USER_EXERCISE_RECORD.KNOWLEDGE=r.KNOWLEDGE,TB_USER_EXERCISE_RECORD.SCORE=IF(r.ANSWER=TB_USER_EXERCISE_RECORD.ANSWER,r.SCORE,0)  where id=? ");
		update(sql.toString(), exerciseId, id);
		query("select QUESTION_ID, KNOWLEDGE,CORRECT,SCORE from TB_USER_EXERCISE_RECORD  where id =? ", new RowSetHandler<Integer>() {
			public Integer populate(ResultSet rs) throws SQLException {
				int score = 0;
				int corrects = 0;
				int wrongs = 0;
				String wrongQuestions = "";
				String weak = "";
				while (rs.next()) {
					if (rs.getBoolean("CORRECT")) {
						corrects++;
						score += rs.getInt("SCORE");
					} else {
						wrongs++;
						wrongQuestions += rs.getString("QUESTION_ID") + ",";
						weak += rs.getString("KNOWLEDGE") + ",";
					}
				}
				if (wrongQuestions.length() != 0) {
					wrongQuestions = wrongQuestions.substring(0, wrongQuestions.length() - 1);
					weak = weak.substring(0, weak.length() - 1);
				}
				return update("update TB_USER_EXERCISE set  CORRECTS=? ,WRONGS=? ,WRONG_QUESTIONS=? ,SCORE=? ,WEAK=? ,MARKER=? ,MARK_TIME=? where id=? ", corrects, wrongs, wrongQuestions, score, weak, userId, DateHelper.getNowTime(), id);
			}
		}, id);

		return 0;
	}

	@Override
	public List<UserExercise> result(long roomId, long exerciseId) {
		return find(UserExercise.class, "select * from TB_USER_EXERCISE where room_id=? and exercise_Id=? ", roomId, exerciseId);
	}

	@Override
	public int delete(long userId, long exerciseId) {
		return 0;
	}

	@Override
	public QuestionBank getQuestion(long questionId) {
		return findFirst(QuestionBank.class, "select * from TB_QUESTION_BANK where question_id=? ", questionId);
	}

	@Override
	public void receive(long userId, long roomId, long exerciseId) {

	}

	@Override
	public Page<QuestionBank> searchQ(long page, long size, long schoolId, long userId, int type, String condition) {
		String sql = "select * from TB_QUESTION_BANK  where status=1 ";
		if (schoolId != 0) {
			sql += " and school_id= " + schoolId;
		}

		if (type == 0) {
			sql += " and user_id= " + userId;
		}

		String[] c = condition.split(",");
		int grade = NumberHelper.toInt(c[0]);
		long kind = NumberHelper.toInt(c[1]);

		if (grade != 0) {
			sql += " and grade= " + grade;
		}

		if (kind != 0) {
			sql += " and kind= " + kind;
		}

		for (int k = 2; k < c.length; k++) {
			long kt = NumberHelper.toLong(c[k]);
			if (kt == 0)
				break;
			sql += " and k" + (k - 1) + "= " + kt;
		}

		return findPage(QuestionBank.class, sql, page, size);
	}

	@Override
	public int deleteQuestion(long questionId) {
		return update("update  TB_QUESTION_BANK  set status=0 where question_id=? ", questionId);
	}
}
