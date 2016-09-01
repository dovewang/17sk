package flint.business.document.service;

import java.io.IOException;
import java.io.OutputStream;

import kiss.storage.Item;
import entity.Document;
import flint.base.BaseService;
import flint.business.document.FileMetadata;
import flint.common.Page;

public interface DocumentService extends BaseService {

	/**
	 * 保存文档
	 * 
	 * @param doc
	 * @return
	 */
	long save(Document doc, Item item);

	/**
	 * 删除用户文档，值删除对应关系表
	 * 
	 * @param id
	 *            对应关系编号
	 * @return
	 */
	int delete(long id);

	/**
	 * 用户文档重命名，只修改对应关系表
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	int rename(long id, String name);

	/**
	 * 检索用户的文档资料
	 * 
	 * @param schoolId
	 * @param groupId
	 * @param userId
	 * @param category
	 * @param tag
	 * @param keyword
	 * @param page
	 * @param size
	 * @param order
	 * @return
	 */
	Page<FileMetadata> search(long schoolId, long groupId, long userId,
			int category, String tag, String keyword, long page, long size,
			String order);

	/**
	 * 加载文档资源
	 * 
	 * @param id
	 * @param page
	 * @return
	 * @throws IOException
	 */
	String load(long id, int page) throws IOException;

	void view(String home, String fileId, int page, OutputStream out);

	void view(String path, OutputStream out);

	void view(String path, int page, OutputStream out);

	/**
	 * 获取单个文档的信息
	 * 
	 * @param docId
	 * @return
	 */
	FileMetadata get(long docId);

}
