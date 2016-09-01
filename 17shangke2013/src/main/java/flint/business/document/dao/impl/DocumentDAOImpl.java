package flint.business.document.dao.impl;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Document;
import flint.base.BaseDAOImpl;
import flint.business.document.FileMetadata;
import flint.business.document.dao.DocumentDAO;
import flint.business.document.dao.FileMetadataRowMapper;
import flint.common.Page;

@Repository
public class DocumentDAOImpl extends BaseDAOImpl<Document, Long> implements
		DocumentDAO {

	public final static String sql = "select d.DOC_ID,d.FILE_ID,d.owner,d.`NAME`,d.TAGS,d.DATELINE,d.SCHOOL_ID,d.GROUP_ID,d.GIVES,d.DOWNLOADS,d.SCORES,d.BUYERS,f.TYPE,f.SIZE,d.MEMO,f.METADATA METADATA1,d.METADATA METADATA2,f.md5,CONCAT(f.host,f.PATH) http,f.path from TB_DOCUMENT d LEFT JOIN $FILE f on d.FILE_ID=f.ID where d.status!=-1  ";
	private final static String count = "select count(1) from TB_DOCUMENT d  where d.status!=-1   ";

	@Override
	public int delete(long id) {
		return update("update  TB_DOCUMENT set status=-1  where doc_id=?", id);
	}

	@Override
	public int rename(long id, String name) {
		return update("update TB_DOCUMENT set name=? where doc_id=?", name, id);
	}

	@Override
	public Page<FileMetadata> search(long schoolId, long groupId, long userId,
			int category, String tag, String keyword, long page, long size,
			String order) {
		StringBuilder queryString = new StringBuilder(sql);
		StringBuilder countString = new StringBuilder(count);
		/* 不同的级别检索的数据不同 */
		if (userId != 0) {
			queryString.append("  and  owner= " + userId);
			countString.append("  and  owner= " + userId);
		} else if (groupId != 0) {
			queryString.append("  and  group_id= " + groupId);
			countString.append("  and  group_id= " + groupId);
		} else {
			/* 默认不允许访问圈内资源 */
			queryString.append("   and  school_id= " + schoolId
					+ "  and  group_id=0  ");
			countString.append("   and  school_id= " + schoolId
					+ "  and  group_id=0  ");
		}

		if (!Q.isEmpty(keyword)) {
			queryString.append("   and d.name like '%" + keyword + "%'   ");
			countString.append("   and d.name like '%" + keyword + "%'   ");
		}

		/* 文件分类 */
		if (category != -1) {
			queryString.append(" and d.category=" + category);
			countString.append("  and d.category=" + category);
		}

		return this.queryPage(queryString.append("  order by d.dateline desc")
				.toString(), countString.toString(),
				new FileMetadataRowMapper(), page, size);
	}

	@Override
	public FileMetadata get(long id) {
		return this.queryFirst(sql + "  and d.DOC_ID=?",
				new FileMetadataRowMapper(), id);
	}


	@Override
	public String getPath(String fileId) {
		return this.getString("select PATH from $FILE where id=?", fileId);
	}

}
