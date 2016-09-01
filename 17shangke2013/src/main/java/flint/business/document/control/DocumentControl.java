package flint.business.document.control;

import static kiss.util.Helper.$;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import entity.Document;
import flint.business.document.FileMetadata;
import flint.business.document.service.DocumentService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.web.WebControl;

@Controller
public class DocumentControl extends WebControl {

	@Autowired
	private SpringSmarty springSmarty;

	@Autowired
	private DocumentService documentService;

	/**
	 * 文档管理器
	 * 
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/doc/manager.html", method = RequestMethod.GET)
	public String manager(HttpServletRequest request)
			throws ApplicationException {
		return "global:/app/doc/manager";
	}

	@RequestMapping(value = "/doc/my.html", method = RequestMethod.GET)
	public ModelAndView my(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("global:/app/doc/list");
		String tag = Q.isEmpty(request.getParameter("tag"), "all");
		int category = $(request.getParameter("c")).toInteger(-1);
		long page = $(request.getParameter("p")).toLong(1);
		String keyword = Q.isEmpty(request.getParameter("q"), "");
		String order = Q.isEmpty(request.getParameter("o"), "");
		Page<FileMetadata> list = documentService.search(0, 0,
				getUserId(request), category, tag, keyword, page, 10, order);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 文档列表
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/doc/list.html", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView("global:/app/doc/list");
		String tag = Q.isEmpty(request.getParameter("tag"), "all");
		int category = $(request.getParameter("c")).toInteger(-1);
		long schoolId = getSchoolId(request);
		long groupId = $(request.getParameter("g")).toLong(0);
		long page = $(request.getParameter("p")).toLong(1);
		String keyword = Q.isEmpty(request.getParameter("q"), "");
		String order = Q.isEmpty(request.getParameter("o"), "");
		Page<FileMetadata> list = documentService.search(schoolId, groupId, 0,
				category, tag, keyword, page, 10, order);
		list.setRequest(request);
		mav.addObject(RESULT, list);
		return mav;
	}

	/**
	 * 文档上传表单
	 * 
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/doc/form.html", method = RequestMethod.GET)
	public String form() throws ApplicationException {
		return "global:/app/doc/form";
	}

	/**
	 * 上传文档，需要存储在两个表中，一个是文档表，也个是文档与用户对应关系表
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws ApplicationException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping(value = "/doc/upload.html", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("filedata") MultipartFile file,
			HttpServletRequest request) throws ApplicationException,
			IllegalStateException, IOException {
		/* 使用范围 */
		String scope = request.getParameter("scope");
		Item item = springSmarty.upload(file, null, 50 * 1024 * 1024);
		Identity user = getUser(request);
		Document doc = new Document();
		doc.setName(Q.isEmpty(request.getParameter("name"),
				item.getOriginalName()));
		doc.setMemo(Q.isEmpty(request.getParameter("memo"), ""));
		doc.setScope(scope);
		doc.setOwner(Long.parseLong(user.getId()));
		doc.setTags($(request.getParameterValues("tags")).join(","));
		doc.setDateline(Q.now());
		doc.setSchoolId(user.getDomainId());
		doc.setGroupId($(request.getParameter("groupid")).toLong());
		doc.setFileId(item.getId());
		/* 设定文件分类 */
		doc.setCategory(FileTyper.category(item.getOriginalName()));
		if (documentService.save(doc, item) > 0) {
			return renderHTML("上传文档成功!", SUCCESS);
		} else {
			return renderHTML("对不起，上传文档失败，请稍后再试！", FAILURE);
		}
	}

	// @RequestMapping(value = "/doc/list1.html", method = RequestMethod.GET)
	// public ModelAndView list1(HttpServletRequest request) {
	// ModelAndView mav = new ModelAndView("global:/app/doc/list1");
	// mav.addObject(
	// "list",
	// documentService.my(getUserId(request),
	// request.getParameter("attr1"),
	// request.getParameter("attr2"),
	// request.getParameter("attr3")));
	// return mav;
	// return null;
	// }

	/**
	 * 文档预览
	 * 
	 * @param id
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/doc/preview/{id}.html", method = RequestMethod.GET)
	public ModelAndView preview(@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileMetadata doc = documentService.get(id);
		String name = "tmp." + doc.getExtend();
		if (FileTyper.isDocument(name)) {
			ModelAndView mav = new ModelAndView("global:/app/doc/preview");
			if (doc.getMetadata() == null) {
				return this.failureView("文档正在转换中，请稍后……");
			} else if ("EXCEPTION".equals(doc.getMetadata())) {
				return this.failureView("很抱歉，您上传的文档无法正常预览。");
			}
			mav.addObject("doc", doc);
			return mav;
		}
		/* 图片预览，这个直接使用ajax在页面进行预览，这里不在处理了 */
		else if (FileTyper.isImage(name)) {
			ServletOutputStream out = response.getOutputStream();
			String home = springSmarty.getConfig().getProperty("storage.home");
			Q.HTTP.cache(response);
			response.setContentType("image/"
					+ ("jpg".equals(doc.getExtend()) ? "jpeg" : doc.getExtend()));
			documentService.view(home + doc.getPath(), out);
		} else if (FileTyper.isAudio(name)) {

		} else if (FileTyper.isVideo(name)) {

		}
		return null;
	}

	@RequestMapping(value = "/doc/download/{id}.html")
	public String download(@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileMetadata doc = documentService.get(id);
		return "redirect:" + doc.getHttp();
	}

	@RequestMapping(value = "/doc/view.html", method = RequestMethod.GET)
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int page = $(request.getParameter("page")).toInteger();
		String fileId = request.getParameter("id");
		ServletOutputStream out = response.getOutputStream();
		String home = springSmarty.getConfig().getProperty("storage.home");
		Q.HTTP.cache(response);
		response.setContentType("application/x-shockwave-flash");
		response.setHeader("Accept-Ranges", "bytes");
		documentService.view(home, fileId, page, out);
	}

	/**
	 * 加载文档资源
	 * 
	 * <pre>
	 * <doc id="" name="" type="image"
	 * dir="http://127.0.0.1/test/#page#.jpg?verify=1231231" totalpage="1"/>
	 * </pre>
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doc/load.html", method = RequestMethod.GET)
	public ModelAndView load(long id, HttpServletRequest request)
			throws IOException {
		int page = NumberHelper.toInt(request.getParameter("page"), 1);
		return renderXML(documentService.load(id, page));
	}

	/**
	 * 删除文档
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/doc/delete.html", method = RequestMethod.POST)
	public ModelAndView delete(long id) {
		if (documentService.delete(id) > 0) {
			return success("删除文档成功！");
		} else {
			return failure("删除文档失败！");
		}
	}

	/**
	 * 文档重命名
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/doc/rename.html", method = RequestMethod.POST)
	public ModelAndView rename(long id, String name) {
		if (documentService.rename(id, name) > 0) {
			return success("文档重命名成功！");
		} else {
			return failure("文档重命名失败！");
		}
	}

}
