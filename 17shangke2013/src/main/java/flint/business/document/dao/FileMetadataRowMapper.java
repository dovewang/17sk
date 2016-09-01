package flint.business.document.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Document;
import flint.business.document.FileMetadata;
import flint.jdbc.RowMapper;

public class FileMetadataRowMapper implements RowMapper<FileMetadata> {
	@Override
	public FileMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
		FileMetadata data = new FileMetadata();
		/* 文件数据信息 */
		data.setId(rs.getString("FILE_ID"));
		data.setCreateTime(rs.getInt("DATELINE"));
		data.setName(rs.getString("name"));
		data.setExtend(rs.getString("type"));
		data.setSize(rs.getLong("SIZE"));
		data.setMd5(rs.getString("md5"));
		data.setPath(rs.getString("path"));
		data.setHttp(rs.getString("http"));
		data.setMetadata(rs.getString("METADATA1"));

		/* 用户元数据信息 */
		Document doc = new Document();
		doc.setDocId(rs.getLong("doc_id"));
		doc.setOwner(rs.getLong("owner"));
		doc.setTags(rs.getString("tags"));
		doc.setSchoolId(rs.getLong("SCHOOL_ID"));
		doc.setGroupId(rs.getLong("GROUP_ID"));
		doc.setGives(rs.getInt("GIVES"));
		doc.setDownloads(rs.getInt("DOWNLOADS"));
		doc.setScores(rs.getByte("SCORES"));
		doc.setBuyers(rs.getInt("BUYERS"));
		doc.setMemo(rs.getString("MEMO"));
		doc.setMetadata(rs.getString("METADATA2"));
		data.setDoc(doc);
		return data;
	}
}
