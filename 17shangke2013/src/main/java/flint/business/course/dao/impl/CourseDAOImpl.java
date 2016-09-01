package flint.business.course.dao.impl;

import static kiss.util.Helper.$;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Course;
import entity.CourseComments;
import flint.base.BaseDAOImpl;
import flint.business.constant.ClassroomConst;
import flint.business.constant.CourseConst;
import flint.business.course.dao.CourseDAO;
import flint.common.Page;
import flint.exception.DataAccessException;
import flint.jdbc.RowMapper;
import flint.jdbc.RowSetHandler;
import flint.util.ObjectHelper;
import flint.util.StringHelper;

/**
 * 
 * 功能描述：系统自动生成，请修改<br>
 * 
 * 
 * 日 期：2011-5-18 11:12:45<br>
 * 
 * 作 者：Spirit<br>
 * 
 * 版权所有：©mosai<br>
 * 
 * 版 本 ：v0.01<br>
 * 
 * 联系方式：<a href="mailto:dream-all@163.com">dream-all@163.com</a><br>
 * 
 * 修改备注：{修改人|修改时间}<br>
 * 
 * 
 **/
@Repository
public class CourseDAOImpl extends BaseDAOImpl<Course, Long> implements
		CourseDAO {

	/**
	 * 检索数据时使用
	 */
	public final static String queryString = "SELECT  COURSE_ID,SCHOOL_ID,`NAME`,TYPE,SCOPE,COVER,USER_ID,TEACHER,open_time,apply_Time,PRICE,START_TIME,time,PUBLISH_TIME,`STATUS`,MAX_CAPACITY,SHARE_MODEL,SHARE_PRICE,SCORE,VIEWS,JOINS,COMMENTS  FROM  TB_COURSE  ";

	private RowMapper<Course> mapCourse() {
		return new RowMapper<Course>() {
			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course course = new Course();
				course.setCourseId(rs.getLong("COURSE_ID"));
				course.setSchoolId(rs.getLong("SCHOOL_ID"));
				course.setType(rs.getByte("TYPE"));
				course.setName(rs.getString("NAME"));
				course.setScope(rs.getString("SCOPE"));
				course.setCover(rs.getString("COVER"));
				course.setUserId(rs.getLong("USER_ID"));
				course.setTeacher(rs.getString("TEACHER"));
				course.setPrice(rs.getInt("PRICE"));
				course.setStartTime(rs.getInt("START_TIME"));
				course.setTime(rs.getInt("time"));
				course.setPublishTime(rs.getInt("PUBLISH_TIME"));
				course.setOpenTime(rs.getString("open_time"));
				course.setApplyTime(rs.getString("apply_Time"));
				course.setTeacher(rs.getString("TEACHER"));
				course.setStatus(rs.getByte("STATUS"));
				course.setMaxCapacity(rs.getInt("MAX_CAPACITY"));
				course.setShareModel(rs.getByte("SHARE_MODEL"));
				course.setSharePrice(rs.getInt("SHARE_PRICE"));
				course.setScore(rs.getByte("SCORE"));
				course.setViews(rs.getInt("VIEWS"));
				course.setJoins(rs.getInt("JOINS"));
				course.setComments(rs.getInt("COMMENTS"));
				return course;
			}

		};
	}

	@Override
	public int update(Course course) {
		/* 修改信息 */
		if (course.getType() == CourseConst.COURSE_TYPE_LOACL) {
			return update(
					"UPDATE  TB_COURSE  set  apply_time=?,open_time=?,price=?,intro=?,aim=?,name=?,update_time =?,tag=?,status=?,share_model=?  where course_id=? ",
					course.getApplyTime(), course.getOpenTime(),
					course.getPrice(), course.getIntro(), course.getAim(),
					course.getName(), Q.now(), course.getTag(),
					CourseConst.COURSE_CHECK/* 当线下课程更新后，需要管理员再次进行审核 */,
					course.getShareModel(), course.getCourseId());
		} else {
			return update(
					"UPDATE  TB_COURSE  set  max_capacity=?,min_capacity=?,price=?,intro=?,aim=?,name=?,update_time =?,tag=? ,share_model=? where course_id=? ",
					course.getMaxCapacity(), course.getMinCapacity(),
					course.getPrice(), course.getIntro(), course.getAim(),
					course.getName(), Q.now(), course.getTag(),
					course.getShareModel(), course.getCourseId());
		}
	}

	@Override
	public int publish(long courseId, byte status) throws DataAccessException {
		return update(
				"UPDATE  TB_COURSE  SET  status=?,publish_time=?,update_time=? WHERE course_id=?",
				status, Q.now(), Q.now(), courseId);
	}

	@Override
	public List<Course> findByIds(List<String> ids) throws DataAccessException {
		String id = StringHelper.collectionToDelimitedString(ids, ",");
		return this.query(queryString + " where course_Id in(" + id
				+ ")  ORDER BY FIELD(course_Id," + id + ")", mapCourse());
	}

	@Override
	public Page<Course> query(String where, String orderString, long page,
			long size, Object... parameters) throws DataAccessException {
		return this.queryPage(queryString + where + "  " + orderString,
				"select count(COURSE_ID)  from TB_COURSE  " + where,
				mapCourse(), page, size, parameters);
	}

	@Override
	public int postComments(CourseComments commet) {
		update("update TB_COURSE set comments=comments+1 where course_id=?",
				commet.getCourseId());
		return save(commet);
	}

	@Override
	public Page<CourseComments> findComments(long id, long page, long size,
			int score) {
		Object[] params = new Object[] { id };
		String sql = "select * from TB_COURSE_COMMENTS where course_id=? ";
		if (score >= 0) {
			sql += " and course_score=? ";
			params = ObjectHelper.addObjectToArray(params, score);
		}
		return findPage(CourseComments.class, sql
				+ " order by create_time desc ", page, size, params);
	}

	@Override
	public String tip(long userId, long schoolId) {
		String sql = "select course_id,NAME,START_TIME,`STATUS`,JOINS,max_capacity FROM TB_COURSE where school_Id=? and ( (`STATUS`>? and `STATUS`<? and USER_ID=?) or ( EXISTS(select 1 from TB_COURSE_JOIN where TB_COURSE_JOIN.COURSE_ID=TB_COURSE.COURSE_ID and TB_COURSE_JOIN.USER_ID=? and TB_COURSE_JOIN.`STATUS`=?)) ) order by  START_TIME";
		return query(
				sql,
				new RowSetHandler<String>() {
					@Override
					public String populate(ResultSet rs) throws SQLException {
						StringBuilder sb = new StringBuilder("[");
						while (rs.next()) {
							sb.append("{\"cid\":")
									.append(rs.getString("course_id"))
									.append(",");
							sb.append("\"name\":\"")
									.append(rs.getString("NAME")).append("\",");
							sb.append("\"time\":")
									.append(rs.getString("START_TIME"))
									.append(",");
							sb.append("\"status\":")
									.append(rs.getString("STATUS")).append(",");
							sb.append("\"joins\":")
									.append(rs.getString("JOINS")).append(",");
							sb.append("\"maxCapacity\":")
									.append(rs.getString("max_capacity"))
									.append("}");
							if (!rs.isLast()) {
								sb.append(",");
							}
						}
						return sb.append("]").toString();
					}

				}, schoolId, CourseConst.COURSE_NOPUBLISH,
				CourseConst.COURSE_CANCLE,
				userId, userId, ClassroomConst.JOIN_OK);
	}

	@Override
	public int cancle(long courseId, long userId, String reason) {
		return update(
				"update TB_COURSE set status=?,memo=? where course_id=? and user_id=? ",
				CourseConst.COURSE_CANCLE, reason, courseId, userId);
	}

	@Override
	public int close(long courseId, long userId, String reason) {
		return update(
				"update TB_COURSE set status=?,memo=? where course_id=? and user_id=? ",
				CourseConst.COURSE_CLOSE, reason, courseId, userId);
	}

	@Override
	public List<Course> guess(String focus, long page, long size) {
		String sql = queryString + "  where status in("
				+ CourseConst.COURSE_SIGN + "," + CourseConst.COURSE_SIGN_OVER
				+ "," + CourseConst.COURSE_START + ","
				+ CourseConst.COURSE_SUCCESS + ")";
		if (!Q.isEmpty(focus)) {
			sql = sql + "  and  CATEGORY in(" + focus + ")";
		}
		List<Course> courses = this.queryPage(
				sql + " order by update_time desc", mapCourse(), page, size)
				.getResult();
		if (courses.size() < size) {
			List<Course> b = this.find(queryString
					+ "   order by update_time desc  limit 0,?",
					size - courses.size());
			courses.addAll(b);
		}
		return courses;
	}

	@Override
	public int comment(long courseId, long seller, int score, int mark,
			String comment) {
		return 0;
	}

	@Override
	public Page<Course> search(long page, int size, Map<String, String[]> params) {
		byte status = $($(params.get("status")).get()).toByte((byte) 99);// 默认显示全部课程
		String where = "  where 1=1 ";
		if (status != 99) {
			where += " and status=" + status;
		}
		return this.query(where, "order by publish_time desc", page, size);
	}

	@Override
	public void saveCourseScope(long courseId, String scope) {
		this.update("delete from TB_COURSE_SCOPE where course_id=?", courseId);
		List<Object[]> parameters = new ArrayList<Object[]>();
		for (String a : scope.split(",")) {
			parameters.add(new Object[] { Long.parseLong(a) });
		}
		if (parameters.size() > 0)
			this.batchUpdate(
					"insert into TB_COURSE_SCOPE(course_id,grade_id) values("
							+ courseId + ",?)", parameters);
	}

	@Override
	public void add(long courseId, String field) {
		update("update TB_COURSE SET " + field + "=" + field
				+ "+1 where course_id=?", courseId);
	}

	@Override
	public int end(long roomid, String dir, long courseId, int startTime,
			int endTime) {
		return update(
				"update TB_COURSE set room_id=?,dir=?, real_start_time=?,real_end_time=?,status=? where course_id=?",
				roomid, dir, startTime, endTime, CourseConst.COURSE_SUCCESS,
				courseId);
	}

	@Override
	public boolean isOwer(long courseId, long userId) {
		return this
				.count("select count(1) from TB_COURSE where course_id=? and user_id=? ",
						courseId, userId) > 0;
	}

	@Override
	public boolean checkCourse(boolean agree, long id, byte type, int newprice,
			String reason) {
		if (agree) {
			if (type == CourseConst.COURSE_TYPE_LOACL) {
				update("UPDATE TB_COURSE set status=?,price=?  where course_id=?",
						CourseConst.COURSE_SIGN, newprice, id);
			} else {
				update("UPDATE TB_COURSE set status=?  where course_id=?",
						CourseConst.COURSE_SIGN, id);
			}
		} else {
			update("UPDATE TB_COURSE set status=?,memo=? where course_id=?",
					CourseConst.COURSE_CHECK_NOT_PASS, reason, id);
		}
		return true;
	}

	@Override
	public boolean isAllowComment(long id, long userId) {
		return this
				.count("select count(1) from TB_COURSE_COMMENTS where COURSE_ID=? and USER_ID=?",
						id, userId) == 0;
	}

	@Override
	public List<Course> get(long userId, int size) {
		return this
				.query(queryString
						+ " where user_id=? and status in(?,?,?,?) order by update_time desc  limit 0,?",
						mapCourse(), userId, CourseConst.COURSE_SIGN,
						CourseConst.COURSE_SIGN_OVER, CourseConst.COURSE_START,
						CourseConst.COURSE_SUCCESS, size);
	}

	@Override
	public List<Course> hot(long schoolId, int nums) {

		/* 目前只对shcool=0的做推荐 */

		String searchindex = "COURSE_ID,TYPE,NAME,COVER,USER_ID,PRICE,REAL_START_TIME,REAL_END_TIME,APPLY_TIME,OPEN_TIME,JOINS,COLLECTS,SCOPE,PUBLISH_TIME";
		String orderStr = " order by PUBLISH_TIME desc LIMIT 0," + nums + " ";
		// SQL拼装
		String sql = "(SELECT "
				+ searchindex
				+ ",1 AS MEMO FROM TB_COURSE WHERE  share_model=0 and status in(1,2,3,9) and  SCOPE REGEXP '[[:<:]]1[[:>:]]|[[:<:]]2[[:>:]]|[[:<:]]3[[:>:]]|[[:<:]]4[[:>:]]|[[:<:]]5[[:>:]]|[[:<:]]6[[:>:]]' "
				+ orderStr + ")";
		sql += "UNION ALL";
		sql += "(SELECT "
				+ searchindex
				+ ",2 AS MEMO FROM TB_COURSE WHERE share_model=0 and status in(1,2,3,9) and    SCOPE REGEXP '[[:<:]]7[[:>:]]|[[:<:]]8[[:>:]]|[[:<:]]9[[:>:]]' "
				+ orderStr + ")";
		sql += "UNION ALL";
		sql += "(SELECT "
				+ searchindex
				+ ",3 AS MEMO FROM TB_COURSE WHERE share_model=0 and status in(1,2,3,9) and   SCOPE REGEXP '[[:<:]]10[[:>:]]|[[:<:]]11[[:>:]]|[[:<:]]12[[:>:]]' "
				+ orderStr + ")";
		sql += "UNION ALL";
		sql += "(SELECT "
				+ searchindex
				+ ",4 AS MEMO FROM TB_COURSE WHERE share_model=0 and status in(1,2,3,9) and  TYPE = "
				+ CourseConst.COURSE_TYPE_LOACL + " " + orderStr + ")";
		return this.find(sql);
	}

	@Override
	public void clear() {
		/* 清理课程，主要是指对在线课程的清理 */
		this.update("update TB_COURSE  where start_time<? and type=? ");
	}
}
