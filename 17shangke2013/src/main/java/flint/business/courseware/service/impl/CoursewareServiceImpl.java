package flint.business.courseware.service.impl;

import java.util.List;
import java.util.Map;

import kiss.storage.Item;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import entity.Chapter;
import entity.ChapterResource;
import entity.Document;
import entity.Lesson;
import flint.base.BaseServiceImpl;
import flint.business.courseware.dao.CoursewareDAO;
import flint.business.courseware.service.CoursewareService;
import flint.business.document.service.DocumentService;

@Service
public class CoursewareServiceImpl extends BaseServiceImpl implements
		CoursewareService {

	@Autowired
	private CoursewareDAO dao;

	@Autowired
	private DocumentService documentService;

	@Override
	public List<Lesson> find(long schoolId, long groupId, long userId,
			byte status) {
		return dao.find(schoolId, groupId, userId, status);
	}

	@Override
	public long saveLesson(Lesson lesson) {
		lesson.setCreateTime(Q.now());
		lesson.setUpdateTime(Q.now());
		lesson.setStatus((byte) 1);
		return dao.insert(lesson);
	}

	@Override
	public int updateLesson(long lessionId) {
		return dao.updateLesson(lessionId);
	}

	@Override
	public long saveChapter(Chapter chapter) {
		chapter.setDateline(Q.now());
		return dao.insert(chapter);
	}

	@Override
	public int updateChapter(Chapter chapter) {
		return dao.updateChapter(chapter);
	}

	@Override
	public int deleteChapter(long lessonId, long id) {
		return dao.deleteChapter(id);
	}

	@Override
	public Map<String, Object> getChapter(long id) {
		Map<String, Object> map = new ModelMap();
		map.put("chapter", dao.getChapter(id));
		map.put("resources", getResource(id));
		return map;
	}

	@Override
	public Map<String, List<ChapterResource>> getResource(long id) {
		return dao.getResource(id);
	}

	@Override
	public Lesson item(long lessonId) {
		return dao.item(lessonId);
	}

	@Override
	public long resource(Document doc, Item item, long lessonId,
			long chapterId, long userId) {
		long docId = documentService.save(doc, item);
		updateLesson(lessonId);
		return dao.resource(chapterId, lessonId, doc.getCategory(), docId,
				doc.getName(), userId);
	}

	@Override
	public long resource(long lessonId, long chapterId, long userId, int type,
			String name, String url) {
		updateLesson(lessonId);
		return dao.resource(chapterId, lessonId, type, url, name, userId);
	}

	@Override
	public long resource(long lessonId, long chapterId, long userId,
			String title, String text) {
		updateLesson(lessonId);
		return dao.resource(chapterId, lessonId, title, text, userId);
	}

	@Override
	public int deleteLesson(long id) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> timetable(long schoolId, long groupId,
			long userId, int start, int end) {
		return dao.timetable(schoolId, groupId, userId, start, end);
	}

}
