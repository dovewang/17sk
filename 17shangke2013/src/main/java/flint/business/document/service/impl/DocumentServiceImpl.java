package flint.business.document.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import kiss.document.convert.Converter;
import kiss.io.IOHelper;
import kiss.io.check.FileTyper;
import kiss.storage.Item;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Document;
import flint.base.BaseServiceImpl;
import flint.business.document.DocumentConverterListener;
import flint.business.document.FileMetadata;
import flint.business.document.dao.DocumentDAO;
import flint.business.document.service.DocumentService;
import flint.common.Page;

@Service
public class DocumentServiceImpl extends BaseServiceImpl implements
		DocumentService {

	@Autowired
	private DocumentDAO dao;

	@Autowired
	private Converter converter;

	@Override
	public long save(Document doc, Item item) {
		/* 只对文档进行转换，其他的不进行转换 */
		if (FileTyper.isDocument(item.getAbsolutePath())) {
			kiss.document.Document document = new kiss.document.Document(
					item.getId());
			document.setFrom(item.getAbsolutePath());
			document.setTo(IOHelper.stripFilenameExtension(item
					.getAbsolutePath()) + ".swf");
			document.setConverterListener(new DocumentConverterListener());
			try {
				converter.to(document);
			} catch (IOException e) {
				logger.error("文档转换异常", e);
			}
		}
		return dao.insert(doc);
	}

	@Override
	public int delete(long id) {
		return dao.delete(id);
	}

	@Override
	public int rename(long id, String name) {
		return dao.rename(id, name);
	}

	@Override
	public Page<FileMetadata> search(long schoolId, long groupId, long userId,
			int category, String tag, String keyword, long page, long size,
			String order) {
		return dao.search(schoolId, groupId, userId, category, tag, keyword,
				page, size, order);
	}

	@Override
	public String load(long id, int page) throws IOException {
		FileMetadata doc = dao.get(id);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<doc id=\"").append(doc.getDoc().getDocId()).append("\"");
		sb.append("  name=\"" + doc.getName() + "\"  type=\"swf\" dir=\""
				+ IOHelper.stripFilenameExtension(doc.getHttp()) + "/" + page
				+ ".swf\"  totalpage=\"1\" />");
		return sb.toString();
	}

	@Override
	public void view(String path, OutputStream out) {
		view(path, 0, out);
	}

	@Override
	public void view(String path, int page, OutputStream out) {
		/* 分页模式的文档 */
		if (page != 0) {
			path = IOHelper.stripFilenameExtension(path) + "/" + page + ".swf";
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(path);
			IOUtils.copy(in, out);
		} catch (IOException e) {
			logger.error("IOException:", e);
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				logger.error("IOException:", e);
			}
		}
	}

	@Override
	public void view(String home, String fileId, int page, OutputStream out) {
		view(home + dao.getPath(fileId), page, out);
	}

	@Override
	public FileMetadata get(long id) {
		return dao.get(id);
	}
}
