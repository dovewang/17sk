package flint.business.courseware.dao;

import java.util.List;
import java.util.Map;

import entity.Chapter;
import entity.ChapterResource;
import entity.Lesson;
import flint.base.BaseDAO;
import flint.business.document.FileMetadata;

public interface CoursewareDAO extends BaseDAO<Lesson, Long> {

	List<Lesson> find(long schoolId, long groupId, long userId, byte status);

	void saveLessonPermission(long id, int type, String scope);

	Chapter getChapter(long id);

	Lesson item(long lessonId);

	long resource(long chapterId, long lessonId, int type, long docId,
			String url, String title, String text, long userId);

	long resource(long chapterId, long lessonId, int type, long docId,
			String name, long userId);

	long resource(long chapterId, long lessonId, String title, String text,
			long userId);

	long resource(long chapterId, long lessonId, int type, String url,
			String name, long userId);

	List<FileMetadata> getDocuments(long chapterId);

	Map<String, List<ChapterResource>> getResource(long chapterId);

	int updateChapter(Chapter chapter);

	int deleteChapter(long id);

	int updateLesson(long lessionId);

	List<Map<String, Object>> timetable(long schoolId, long groupId,
			long userId, int start, int end);

}
