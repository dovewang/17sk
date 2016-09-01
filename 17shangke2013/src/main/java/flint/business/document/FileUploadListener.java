package flint.business.document;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import kiss.io.IOHelper;
import kiss.storage.Item;
import kiss.storage.listener.AbstractUploadListener;
import kiss.util.Q;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FileUploadListener extends AbstractUploadListener {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean check(Item item, byte[] data, String originalName,
			String type, long maxSize, final String home, String web,
			boolean check) throws IOException {
		super.check(item, data, originalName, type, maxSize, home, web, check);
		/* 检查文件是否已存在，存在则不需要上传，直接创建元数据 */
		Item i = jdbcTemplate.query(
				"select * from  $FILE where md5=? and sha=? and crc32=?",
				new ResultSetExtractor<Item>() {
					@Override
					public Item extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						if (rs.next()) {
							Item m = new Item();
							m.setName(rs.getString("name"));
							m.setHost(rs.getString("host"));
							m.setPath(rs.getString("path"));
							m.setMetadata(rs.getString("metadata"));
							m.setExtend(rs.getString("type"));
							m.setCrc32(rs.getString("crc32"));
							m.setSha(rs.getString("sha"));
							m.setSize(rs.getLong("size"));
							m.setId(rs.getString("id"));
							return m;
						}
						return null;
					}

				}, item.getMd5(), item.getSha(), item.getCrc32());
		if (i != null) {
			item.setCreateTime(Q.now());
			item.setDirectory(i.getDirectory());
			item.setName(i.getName());
			item.setHost(i.getHost());
			item.setExtend(i.getExtend());
			item.setCrc32(i.getCrc32());
			item.setSha(i.getCrc32());
			item.setSize(i.getSize());
			item.setId(i.getId());
			item.setPath(i.getPath());
			item.setHttp(item.getHost() + i.getPath());
			item.setAbsolutePath(home + i.getPath());
			item.setDirectory(IOHelper.getFileDirectory(item.getAbsolutePath()));
			return true;
		}
		return false;
	}

	@Override
	public void after(Item item) {
		String sql = "insert into $FILE(ID,`NAME`,HOST,PATH,TYPE,DATELINE,SIZE,MD5,SHA,CRC32,METADATA)values(?,?,?,?,?,?,?,?,?,?,?)";
		/* 存储信息 */
		jdbcTemplate.update(sql, item.getId(), item.getName(), item.getHost(),
				item.getPath(), item.getExtend(), item.getCreateTime(),
				item.getSize(), item.getMd5(), item.getSha(), item.getCrc32(),
				item.getMetadata());
	}

}
