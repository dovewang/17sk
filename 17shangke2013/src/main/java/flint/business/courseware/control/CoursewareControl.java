package flint.business.courseware.control;

import static kiss.util.Helper.$;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import kiss.io.check.FileTyper;
import kiss.security.Identity;
import kiss.storage.Item;
import kiss.storage.client.SpringSmarty;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import entity.Chapter;
import entity.Document;
import entity.Lesson;
import flint.business.constant.CoursewareConst;
import flint.business.courseware.service.CoursewareService;
import flint.exception.ApplicationException;
import flint.web.WebControl;

@Controller
public class CoursewareControl extends WebControl {

	@Autowired
	private CoursewareService coursewareService;

	@Autowired
	private SpringSmarty springSmarty;

	/**
	 * 单个章节编辑表单
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/form.html", method = RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/courseware/form");
		return mav;
	}

	/**
	 * 返回一个学习圈内部的最新课件
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/list.html", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/courseware/list");
		long schoolId = this.getSchoolId(request);
		long groupId = $(request.getParameter("g")).toLong(0);
		mav.addObject("cws", coursewareService.find(schoolId, groupId, 0,
				CoursewareConst.OK));
		return mav;
	}

	/**
	 * 查询科目
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/lessons.html", method = RequestMethod.GET)
	public ModelAndView lessons(HttpServletRequest request)
			throws ApplicationException {
		long schoolId = this.getSchoolId(request);
		long groupId = $(request.getParameter("g")).toLong(0);
		long userId = !Q.isEmpty(request.getParameter("my")) ? getUserId(request)
				: 0;
		return render("lessons", coursewareService.find(schoolId, groupId,
				userId, CoursewareConst.OK));
	}

	/**
	 * 查看已归档的课件
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/archived.html", method = RequestMethod.GET)
	public ModelAndView archived(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/courseware/list");
		long schoolId = this.getSchoolId(request);
		long groupId = $(request.getParameter("g")).toLong(0);
		mav.addObject("cws", coursewareService.find(schoolId, groupId,
				getUserId(request), CoursewareConst.ARCHIVED));
		return mav;
	}

	@RequestMapping(value = "/courseware/item/{lessonId}.html", method = RequestMethod.GET)
	public ModelAndView item(@PathVariable("lessonId") long lessonId,
			HttpServletRequest request) throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/courseware/item");
		/* 这里需要检查一下访问的权限 */
		mav.addObject("lesson", coursewareService.item(lessonId));
		return mav;
	}

	/**
	 * 添加一个课程
	 * 
	 * @param lesson
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/lesson.add.html", method = RequestMethod.POST)
	public ModelAndView addLesson(Lesson lesson, HttpServletRequest request)
			throws ApplicationException {
		Identity user = this.getUser(request);
		lesson.setSchoolId(user.getDomainId());
		lesson.setUserId(Long.parseLong(user.getId()));
		/* 授权的班级 */
		long id = coursewareService.saveLesson(lesson);
		if (id > 0) {
			/* 这里直接添加课程的方式 */
			return success("添加成功！", "id", String.valueOf(id));
		}
		return failure("添加失败，请稍后再试！");
	}

	/**
	 * 保存课程信息到草稿箱
	 * 
	 * @param chapter
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/chapter.draft.html", method = RequestMethod.POST)
	public ModelAndView chapterDraft(Chapter chapter)
			throws ApplicationException {
		long id = coursewareService.saveChapter(chapter);
		if (id > 0) {
			return success("自动保存草稿成功！", "id", String.valueOf(id));
		}
		return failure("自动保存草稿失败，请稍后再试！");
	}

	/**
	 * 更新课程信息，主要是在发布时使用
	 * 
	 * @param chapter
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/chapter.update.html", method = RequestMethod.POST)
	public ModelAndView chapter(Chapter chapter) throws ApplicationException {
		/* 没有添加资料，直接发布的课程 */
		if (chapter.getChapterId() == 0) {
			if (coursewareService.saveChapter(chapter) > 0) {
				return success("发布课程成功！");
			}
		} else {
			if (coursewareService.updateChapter(chapter) > 0) {
				return success("发布课程成功！");
			}
		}
		return failure("发布失败，请稍后再试！");
	}

	@RequestMapping(value = "/courseware/chapter.delete.html", method = RequestMethod.POST)
	public ModelAndView deleteChapter(long lessonId, long id)
			throws ApplicationException {
		if (coursewareService.deleteChapter(lessonId, id) > 0) {
			return success("删除成功！");
		}
		return failure("删除失败，请稍后再试！");
	}

	/**
	 * 查看章节信息
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/courseware/chapter/{chapterId}.html")
	public ModelAndView chapter(@PathVariable("chapterId") long id)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/courseware/chapter");
		mav.addAllObjects(coursewareService.getChapter(id));
		return mav;
	}

	@RequestMapping(value = "/courseware/chapter.resource/{chapterId}.html")
	public ModelAndView resource(@PathVariable("chapterId") long id)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/courseware/resource");
		mav.addObject("resources", coursewareService.getResource(id));
		return mav;
	}

	/**
	 * 课件上传，包括视频，音频文件
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationException
	 * @throws IOException
	 */
	@RequestMapping(value = "/courseware/resource/upload.html", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("filedata") MultipartFile file,
			long lessonId, long chapterId, HttpServletRequest request)
			throws ApplicationException, IOException {
		Item item = springSmarty.upload(file, null, 50 * 1024 * 1024);
		Identity user = getUser(request);
		Document doc = new Document();
		doc.setName(Q.isEmpty(request.getParameter("name"),
				item.getOriginalName()));
		doc.setMemo(Q.isEmpty(request.getParameter("memo"), ""));
		doc.setOwner(Long.parseLong(user.getId()));
		doc.setTags($(request.getParameterValues("tags")).join(","));
		doc.setDateline(Q.now());
		doc.setSchoolId(user.getDomainId());
		doc.setFileId(item.getId());
		// doc.setMetadata(Q.toJSONString(item));
		/* 设定文件分类 */
		doc.setCategory(FileTyper.category(item.getOriginalName()));
		if (coursewareService.resource(doc, item, lessonId, chapterId,
				doc.getOwner()) > 0) {
			return renderHTML("上传成功！", SUCCESS);
		}
		return renderHTML("上传失败！", FAILURE);
	}

	/**
	 * 引用外部的课件资料，及老师自定义的文本资料
	 * 
	 * @return
	 */
	@RequestMapping(value = "/courseware/resource/reference.html", method = RequestMethod.POST)
	public ModelAndView reference(long lessonId, long chapterId, int type,
			String name, String url, HttpServletRequest request) {
		long id = coursewareService.resource(lessonId, chapterId,
				getUserId(request), type, name, url);
		if (id > 0) {
			return success("添加资料成功", "id", String.valueOf(id));
		}
		return failure("很抱歉，没有保成功，请稍后再试！");
	}

	@RequestMapping(value = "/courseware/resource/text.html", method = RequestMethod.POST)
	public ModelAndView text(long lessonId, long chapterId, String title,
			String text, HttpServletRequest request) {
		long id = coursewareService.resource(lessonId, chapterId,
				getUserId(request), title, text);
		if (id > 0) {
			return success("添加资料成功", "id", String.valueOf(id));
		}
		return failure("很抱歉，没有保成功，请稍后再试！");
	}

	/**
	 * 从其他资源复制到课程的目标章节
	 * 
	 * @param lessonId
	 * @param chapterId
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/courseware/resource/copy.html", method = RequestMethod.POST)
	public ModelAndView copy(long lessonId, long chapterId, String ids) {
		return null;
	}

	/**
	 * 本人资源检索
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/courseware/resource/search.my.html")
	public ModelAndView searchMy(HttpServletRequest request) {
		return null;
	}

	/**
	 * 根据不同的学校，不同的班级，不同的人员进行检索
	 * 
	 * @return
	 */
	@RequestMapping(value = "/courseware/timetable.html")
	public ModelAndView timetable(HttpServletRequest request) {
		long schoolId = this.getSchoolId(request);
		long groupId = $(request.getParameter("g")).toLong(0);
		long userId = !Q.isEmpty(request.getParameter("my")) ? getUserId(request)
				: 0;
		int start = Q.String(request.getParameter("start")).toInteger();
		int end = Q.String(request.getParameter("end")).toInteger();
		return render(RESULT, coursewareService.timetable(schoolId, groupId,
				userId, start, end));
	}
}
