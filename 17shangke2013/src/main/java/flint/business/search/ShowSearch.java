package flint.business.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;

import flint.business.constant.ShowConst;
import flint.business.util.KindHelper;
import flint.search.SearchException;
import flint.search.SearchSupport;
import flint.util.SpringHelper;

public class ShowSearch extends SearchSupport {
	
	String sql = " SELECT SHOW_ID,ACTIVE_ID,USER_ID,TITLE,CATEGORY,GRADE,SUBJECT,INTRO,TYPE,TAG,STATUS,DATELINE,DIR,ROOM_ID,VIEWS,UPS,DOWNS,COMMENTS,COLLECTS,SCORES,FINALIST,REWARDED,MEMO FROM TB_SHOW ";

	@Override
	public Document mapperDocument(ResultSet rs) throws SearchException, SQLException {
		KindHelper kindHelper = SpringHelper.getBean(KindHelper.class);
		Document doc = new Document();
		doc.add(new Field("id", rs.getString("SHOW_ID"), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("active_id", rs.getString("ACTIVE_ID"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("user_id", rs.getString("USER_ID"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("category", rs.getString("CATEGORY"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("grade", rs.getString("GRADE"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("subject", rs.getString("SUBJECT"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("type", rs.getString("TYPE"), Field.Store.NO,Field.Index.NOT_ANALYZED));
		doc.add(new Field("status", rs.getString("STATUS"), Field.Store.NO,Field.Index.NOT_ANALYZED));
		doc.add(new Field("room_id", rs.getString("ROOM_ID"), Field.Store.NO,Field.Index.NOT_ANALYZED));
		doc.add(new Field("views", rs.getString("VIEWS"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 最多播放 */
		doc.add(new Field("ups", rs.getString("UPS"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 支持数，人气 */
		doc.add(new Field("downs", rs.getString("DOWNS"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 踩的人数 */
		doc.add(new Field("comments", rs.getString("COMMENTS"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 评论数 */
		doc.add(new Field("scores", rs.getString("SCORES"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 得分 */
		doc.add(new Field("dateline", rs.getString("DATELINE"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 得分 */
		doc.add(new Field("collects", rs.getString("DATELINE"), Field.Store.NO,Field.Index.NOT_ANALYZED));/* 收藏人数 */
		
		
		/* 全文检索字段 */
		doc.add(new Field("title", rs.getString("TITLE"), Field.Store.NO, Field.Index.ANALYZED));/* 标题 */
		doc.add(new Field("intro", rs.getString("INTRO"), Field.Store.NO, Field.Index.ANALYZED));/* 简介 */
		doc.add(new Field("grade", kindHelper.getGradeLabel(rs.getString("GRADE")), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("subject", kindHelper.getKindLabel(rs.getString("SUBJECT")), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("tag", rs.getString("TAG"), Field.Store.NO, Field.Index.ANALYZED));/* 标签 */
		return doc;
	}

	/**
	 * 创建秀秀视频索引
	 * 
	 * @param questionId
	 */
	public void createIndex(long showid) {
		rebuild(sql + " where SHOW_ID = ? ", showid);
	}

	public void delete(long showid) {
		delete(new Term("id", String.valueOf(showid)));
	}

	public void rebulidIndex() {
		rebuild(sql + " where TB_SHOW.STATUS != ?", ShowConst.DELETE);
	}
}
