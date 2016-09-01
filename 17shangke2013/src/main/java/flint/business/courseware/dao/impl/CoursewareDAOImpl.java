package flint.business.courseware.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.sql.SQLBuilder;
import kiss.sql.Select;
import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Chapter;
import entity.ChapterResource;
import entity.Lesson;
import flint.base.BaseDAOImpl;
import flint.business.constant.CoursewareConst;
import flint.business.courseware.dao.CoursewareDAO;
import flint.business.document.FileMetadata;
import flint.business.document.dao.FileMetadataRowMapper;
import flint.business.document.dao.impl.DocumentDAOImpl;

@Repository
public class CoursewareDAOImpl extends BaseDAOImpl<Lesson, Long> implements
		CoursewareDAO {

	@Override
	public List<Lesson> find(long schoolId, long groupId, long userId,
			byte status) {
		Select s = SQLBuilder.select("SELECT * from TB_LESSON");
		s.may(" where SCHOOL_ID=?   ", schoolId);
		/* 按状态查询 */
		s.may(status != -1, " and `STATUS`=? ", status);
		s.may(userId != 0, "  and USER_ID=? ", userId);
		s.may(groupId != 0, " and GROUP_ID=?", groupId);
		return this.find(s.toString(), s.getParameters());
	}

	@Override
	public Lesson item(long lessonId) {
		String lesson = "SELECT * from TB_LESSON where LESSON_ID=? ";
		Lesson l = this.findFirst(Lesson.class, lesson, lessonId);
		return l;
	}

	@Override
	public void saveLessonPermission(long id, int type, String scope) {
		this.update(" delete from TB_LESSON_PERMISSION  where LESSON_ID=?", id);
		String[] ids = scope.split(",");
		List<Object[]> list = new ArrayList<Object[]>();
		for (String i : ids) {
			list.add(new Object[] { id, type, i });
		}
		this.batchUpdate(
				"insert into TB_LESSON_PERMISSION(LESSON_ID,TYPE,ID) values(?,?,?)",
				list);
	}

	@Override
	public Chapter getChapter(long id) {
		return this.findFirst(Chapter.class,
				"select * from TB_CHAPTER where chapter_id=?", id);
	}

	public long resource(long chapterId, long lessonId, int type, long docId,
			String url, String title, String text, long userId) {
		return insert(
				"insert into TB_CHAPTER_RESOURCE(CHAPTER_ID,LESSON_ID,TYPE,DOC_ID,URL,TITLE,TEXT,DATELINE,USER_ID) values(?,?,?,?,?,?,?,?,?)",
				chapterId, lessonId, type, docId, url, title, text, Q.now(),
				userId);
	}

	public long resource(long chapterId, long lessonId, int type, long docId,
			String name, long userId) {
		return resource(chapterId, lessonId, type, docId, null, name, null,
				userId);
	}

	@Override
	public long resource(long chapterId, long lessonId, int type, String url,
			String name, long userId) {
		return resource(chapterId, lessonId, type, 0, url, name, null, userId);
	}

	public long resource(long chapterId, long lessonId, String title,
			String text, long userId) {
		/* 文本类型的 */
		return resource(chapterId, lessonId, CoursewareConst.RESOURCE_TEXT, 0,
				null, title, text, userId);
	}

	@Override
	public List<FileMetadata> getDocuments(long chapterId) {
		String sql = DocumentDAOImpl.sql
				+ "  and EXISTS(SELECT 1 from TB_CHAPTER_DOCUMENT cd WHERE cd.CHAPTER_ID=? and cd.doc_id=d.DOC_ID)";
		return this.query(sql, new FileMetadataRowMapper(), chapterId);
	}

	@Override
	public int updateChapter(Chapter chapter) {
		return this
				.update(" UPDATE TB_CHAPTER set title=?,content=?,classtime=?,classindex=?,status=?,update_time=? where chapter_id=?",
						chapter.getTitle(), chapter.getContent(),
						chapter.getClasstime(), chapter.getClassindex(),
						chapter.getStatus(), Q.now(), chapter.getChapterId());
	}

	@Override
	public int deleteChapter(long id) {
		return this.update("delete from TB_CHAPTER where CHAPTER_ID=?", id);
	}

	@Override
	public int updateLesson(long lessionId) {
		// 更新时间，重新统计课程的信息
		return this.update(
				"update TB_LESSON set UPDATE_TIME=? where LESSON_ID=?",
				Q.now(), lessionId);
	}

	@Override
	public List<Map<String, Object>> timetable(long schoolId, long groupId,
			long userId, int start, int end) {
		Select s = SQLBuilder
				.select("select a.LESSON_ID,a.LESSON,a.TEACHER,b.chapter_Id,b.TITLE,b.CLASSINDEX,b.CLASSTIME,b.STATUS FROM TB_LESSON a,TB_CHAPTER b");
		s.may(true, " where a.SCHOOL_ID=?  and a.LESSON_ID=b.LESSON_ID  ",
				schoolId);
		s.may(groupId != 0, " and group_id=?", groupId);
		s.may(userId != 0, " and a.USER_ID=?  ", userId);
		s.may(start != 0, " and CLASSTIME>=?", start);
		s.may(end != 0, " and CLASSTIME<=?", end);
		s.may(" and b.status!=?", CoursewareConst.DELETE);
		return this.findForMap(s.toString(), s.getParameters());
	}

	@Override
	public Map<String, List<ChapterResource>> getResource(long chapterId) {
		Map<String, List<ChapterResource>> map = new HashMap<String, List<ChapterResource>>(
				5);
		List<ChapterResource> list = this.findByWhere(ChapterResource.class,
				"  where  chapter_id=? ", chapterId);
		List<ChapterResource> list0 = new ArrayList<ChapterResource>();
		List<ChapterResource> list1 = new ArrayList<ChapterResource>();
		List<ChapterResource> list2 = new ArrayList<ChapterResource>();
		List<ChapterResource> list3 = new ArrayList<ChapterResource>();
		List<ChapterResource> list4 = new ArrayList<ChapterResource>();
		for (ChapterResource cr : list) {
			switch (cr.getType()) {
			case CoursewareConst.RESOURCE_TEXT:
				list0.add(cr);
				break;
			case CoursewareConst.RESOURCE_IMAGE:
				list1.add(cr);
				break;
			case CoursewareConst.RESOURCE_DOCUMENT:
				list2.add(cr);
				break;
			case CoursewareConst.RESOURCE_VIDEO:
				list3.add(cr);
				break;
			case CoursewareConst.RESOURCE_AUDIO:
				list4.add(cr);
				break;
			}
		}
		map.put("list0", list0);
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
		map.put("list4", list4);
		return map;
	}

}
