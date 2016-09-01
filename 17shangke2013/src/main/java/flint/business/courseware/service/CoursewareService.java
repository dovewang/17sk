package flint.business.courseware.service;

import java.util.List;
import java.util.Map;

import kiss.storage.Item;
import entity.Chapter;
import entity.ChapterResource;
import entity.Document;
import entity.Lesson;
import flint.base.BaseService;

public interface CoursewareService extends BaseService {

	/**
	 * 
	 * @param schoolId
	 * @param groupId
	 * @param userId
	 *            不为0时代表查询包括归档在内的课件
	 * @return
	 */
	List<Lesson> find(long schoolId, long groupId, long userId, byte status);

	/**
	 * 保存课程
	 * 
	 * @param lesson
	 * @return
	 */
	long saveLesson(Lesson lesson);

	int updateLesson(long lessionId);

	int deleteLesson(long id);

	long saveChapter(Chapter chapter);

	int updateChapter(Chapter chapter);

	int deleteChapter(long lessonId, long id);

	Map<String, Object> getChapter(long id);

	Map<String, List<ChapterResource>> getResource(long id);

	Lesson item(long lessonId);

	/**
	 * 
	 * @param doc
	 * @param item
	 * @param lessonId
	 * @param chapterId
	 * @return
	 */
	long resource(Document doc, Item item, long lessonId, long chapterId,
			long userId);

	long resource(long lessonId, long chapterId, long userId, int type,
			String name, String url);

	long resource(long lessonId, long chapterId, long userId, String title,
			String text);

	/**
	 * 课程列表
	 * 
	 * @param schoolId
	 * @param groupId
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String, Object>> timetable(long schoolId, long groupId,
			long userId, int start, int end);

}
