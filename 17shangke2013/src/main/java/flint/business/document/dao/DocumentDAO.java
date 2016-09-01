package flint.business.document.dao;

import entity.Document;
import flint.base.BaseDAO;
import flint.business.document.FileMetadata;
import flint.common.Page;

public interface DocumentDAO extends BaseDAO<Document, Long> {

	/**
	 * 删除用户文档
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	int delete(long id);

	/**
	 * 重命名用户文档
	 * 
	 * @param id
	 * @param name
	 * @param userId
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

	String getPath(String fileId);

	FileMetadata get(long id);

}
