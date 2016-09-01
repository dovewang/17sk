package flint.business.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kiss.util.Q;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.jdbc.datasource.DataSourceUtils;

import flint.business.util.KindHelper;
import flint.search.SearchException;
import flint.search.SearchSupport;
import flint.util.SpringHelper;

public class UserSearch extends SearchSupport {

	String sql = "  select user_id as id,USER_TYPE,teacher_level,fee,active,students,name,real_name,expert,experience from TB_USER ";

	@Override
	public Document mapperDocument(ResultSet rs) throws SearchException, SQLException {
		KindHelper kindHelper = SpringHelper.getBean(KindHelper.class);
		Document doc = new Document();
		doc.add(new Field("id", rs.getString("id"), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("teacher_level", rs.getString("teacher_level"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("fee", rs.getString("fee"), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("active", String.valueOf(rs.getInt("active") & 1), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("active", String.valueOf(rs.getInt("active") & 2), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("active", String.valueOf(rs.getInt("active") & 4), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("active", String.valueOf(rs.getInt("active") & 8), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("active", String.valueOf(rs.getInt("active") & 16), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("students", rs.getString("students"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		/* 全文检索字段 */
		doc.add(new Field("name", Q.isEmpty(rs.getString("name"),""), Field.Store.NO, Field.Index.ANALYZED));
		if(!Q.isEmpty(rs.getString("real_name"))){
			doc.add(new Field("real_name", rs.getString("real_name"), Field.Store.NO, Field.Index.ANALYZED));
		}
		if(!Q.isEmpty(rs.getString("expert"))){
			doc.add(new Field("expert", rs.getString("expert"), Field.Store.NO, Field.Index.ANALYZED));
			doc.add(new Field("expert", kindHelper.getKindLabels(rs.getString("expert")), Field.Store.NO, Field.Index.ANALYZED));
		}
		if(!Q.isEmpty(rs.getString("experience"))){
			doc.add(new Field("experience", rs.getString("experience"), Field.Store.NO, Field.Index.ANALYZED));
		}
		/* 用户所在的学校，按身份加入检索，格式 学校编号#用户类型 */
		doc.add(new Field("school_user_type", "0OF" + rs.getString("user_type"), Field.Store.NO, Field.Index.NOT_ANALYZED));// 默认的公共学校
		String school_sql = "SELECT CONCAT(school_id,'OF',USER_TYPE) school_user_type from TB_SCHOOL_MEMBER where USER_ID=? and `STATUS`=1 ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs2 = null;
		try {
			conn = DataSourceUtils.getConnection(this.getDataSource());
			ps = conn.prepareStatement(school_sql);
			ps.setInt(1, rs.getInt("id"));
			rs2 = ps.executeQuery();
			while (rs2.next()) {
				doc.add(new Field("school_user_type", rs2.getString(1), Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
		} catch (SQLException e) {
			logger.error("datasource connection error:{}", e);
		} finally {
			DataSourceUtils.releaseConnection(conn, this.getDataSource());
		}
		return doc;
	}

	public void createIndex(long userId) {
		rebuild(sql + " where user_id=? ", userId);
	}

	public void rebulidIndex() {
		rebuild(sql);
	}

}
