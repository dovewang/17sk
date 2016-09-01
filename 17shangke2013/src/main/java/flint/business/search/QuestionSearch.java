package flint.business.search;

import static kiss.util.Helper.$;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;

import flint.business.constant.QuestionConst;
import flint.business.util.KindHelper;
import flint.business.util.KnowledgeHelper;
import flint.search.SearchException;
import flint.search.SearchSupport;
import flint.util.SpringHelper;
import flint.util.StringHelper;

public class QuestionSearch extends SearchSupport {
	String sql = " SELECT QUESTION_ID,SCHOOL_ID,TITLE,INTRO,GRADE,ANSWERS,KIND,K1,K2,K3,K4,K5,TAG,STATUS,ADD_INFO FROM TB_QUESTION ";

	@Override
	public Document mapperDocument(ResultSet rs) throws SearchException, SQLException {
		KindHelper kindHelper = SpringHelper.getBean(KindHelper.class);
		KnowledgeHelper knowledgeHelper = SpringHelper.getBean(KnowledgeHelper.class);
		Document doc = new Document();
		doc.add(new Field("id", rs.getString("QUESTION_ID"), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("school_id", rs.getString("SCHOOL_ID"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("grade", rs.getString("GRADE"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("grade", kindHelper.getGradeLabel(rs.getString("GRADE")), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("answers", rs.getString("ANSWERS"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("status", rs.getString("STATUS"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("kind", rs.getString("KIND"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("kind", kindHelper.getKindLabel(rs.getString("KIND")), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("k1", rs.getString("K1"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("k2", rs.getString("K2"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("k3", rs.getString("K3"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("k4", rs.getString("K4"), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("k5", rs.getString("K5"), Field.Store.NO, Field.Index.NOT_ANALYZED));

		/* 全文检索字段 */
		doc.add(new Field("knowledge", $(knowledgeHelper.getKnowledgeLink(rs.getLong("K1"), rs.getLong("K2"), rs.getLong("K3"), rs.getLong("K4"), rs.getLong("K5"))).joinValues(","), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("title", rs.getString("TITLE"), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("intro", rs.getString("INTRO"), Field.Store.NO, Field.Index.ANALYZED));
		String tag = rs.getString("tag");
		if (!StringHelper.isEmpty(tag))
			doc.add(new Field("tag", rs.getString("TAG"), Field.Store.NO, Field.Index.ANALYZED));
		String addInfo = rs.getString("ADD_INFO");
		if (!StringHelper.isEmpty(addInfo)) {
			doc.add(new Field("add_info", addInfo, Field.Store.NO, Field.Index.ANALYZED));
		}
		return doc;
	}

	/**
	 * 创建问题索引
	 * 
	 * @param questionId
	 */
	public void createIndex(long questionId) {
		rebuild(sql + " where QUESTION_ID=? ", questionId);
	}

	public void delete(long questionId) {
		delete(new Term("id", String.valueOf(questionId)));
	}

	public void rebulidIndex() {
		rebuild(sql + " where TB_QUESTION.STATUS != ?",
				QuestionConst.QUESTION_STATUS_DELETE);
	}
}
