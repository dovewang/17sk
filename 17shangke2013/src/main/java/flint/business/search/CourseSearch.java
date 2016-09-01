package flint.business.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import kiss.util.Q;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;

import flint.business.constant.CourseConst;
import flint.business.util.KindHelper;
import flint.search.SearchException;
import flint.search.SearchSupport;
import flint.util.SpringHelper;

public class CourseSearch extends SearchSupport {

	String queryCourse = "select course_id ,school_id,city,type,name,scope,CATEGORY,price,start_time,time,status,views,collects,score,aim,tag,publish_time,share_model from TB_COURSE  where 1=1";

	@Override
	public Document mapperDocument(ResultSet rs) throws SearchException,
			SQLException {
		KindHelper kindHelper = SpringHelper.getBean(KindHelper.class);
		Document doc = new Document();
		doc.add(new Field("id", rs.getString("course_id"), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("school_id", rs.getString("school_id"),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("share_model", rs.getString("share_model"),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("city", rs.getString("city"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));// 地区级索引
		doc.add(new Field("city",
				String.valueOf(rs.getInt("city") / 100 * 100), Field.Store.NO,
				Field.Index.NOT_ANALYZED));// 添加城市级的索引

		String scope = rs.getString("scope");
		boolean x = false;
		boolean c = false;
		boolean g = false;
		for (String s : scope.split(",")) {
			doc.add(new Field("scope", s, Field.Store.NO,
					Field.Index.NOT_ANALYZED));
			long i = Long.parseLong(s);
			if (i >= 1 && i <= 6) {
				x = true;
			} else if (i <= 9) {
				c = true;
			} else if (i <= 12) {
				g = true;
			}
		}
		if (x)
			doc.add(new Field("scope", "小学", Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		if (c)
			doc.add(new Field("scope", "初中", Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		if (g)
			doc.add(new Field("scope", "高中", Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		doc.add(new Field("scope", kindHelper.getGradeLabel(scope),
				Field.Store.NO, Field.Index.ANALYZED));

		/* 多科目分组 */
		String category = rs.getString("category");
		for (String s : category.split(",")) {
			doc.add(new Field("category", s, Field.Store.NO,
					Field.Index.NOT_ANALYZED));
		}
		doc.add(new Field("category", kindHelper.getKindLabels(rs
				.getString("category")), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("type", rs.getString("type"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("start_time", rs.getString("start_time"),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field("time", rs.getString("time"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("status", rs.getString("status"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		/* 全文检索字段 */
		doc.add(new Field("name", rs.getString("name"), Field.Store.NO,
				Field.Index.ANALYZED));
		doc.add(new Field("aim", rs.getString("aim"), Field.Store.NO,
				Field.Index.ANALYZED));
		String tag = rs.getString("tag");
		if (!Q.isEmpty(tag))
			doc.add(new Field("tag", rs.getString("tag"), Field.Store.NO,
					Field.Index.ANALYZED));
		doc.add(new Field("price", rs.getString("price"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		/* 发布时间 */
		doc.add(new Field("publish_time", rs.getString("publish_time"),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		/* 最多收藏 */
		doc.add(new Field("collects", rs.getString("collects"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		/* 最多播放 */
		doc.add(new Field("views", rs.getString("views"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		/* 得分 */
		doc.add(new Field("score", rs.getString("score"), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		return doc;
	}

	/**
	 * 创建单个课程索引
	 * 
	 * @param courseId
	 */
	public void createIndex(long courseId) {
		rebuild(queryCourse + " and course_id=? ", courseId);
	}

	public void delete(long courseId) {
		delete(new Term("id", String.valueOf(courseId)));
	}

	/**
	 * 每完定时重建索引，或者手动重建索引调用
	 */
	public void rebulidIndex() {
		rebuild(queryCourse + " and TB_COURSE.status not in(?,?)",
				CourseConst.COURSE_NOPUBLISH, CourseConst.COURSE_CLOSE);
	}
}
