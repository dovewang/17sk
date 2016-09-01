package flint.business.question.util;

import org.apache.lucene.queryparser.classic.ParseException;

import flint.business.constant.CollectConst;
import flint.business.constant.QuestionConst;
import flint.exception.ApplicationException;
import flint.search.QueryBuilder;
import flint.util.NumberHelper;
import flint.util.ObjectHelper;
import flint.util.StringHelper;

/**
 * 组合问题检索条件工具类: 目前检索的条件主要有： 年级，科目，知识点
 * 
 * @author Dove Wang
 * 
 */
public class QuestionSearcher {

	/**
	 * 不过滤问题
	 */
	public final static int NO_FILTER = 9;

	/**
	 * 在所有问题中检索
	 */
	public final static int TYPE_ALL = 0;

	/**
	 * 在我提的问题中检索
	 */
	public final static int TYPE_MY_QUESTION = 1;
	/**
	 * 在我的答案中检索
	 */
	public final static int TYPE_MY_ANSWER = 2;
	/**
	 * 在我的收藏夹中检索
	 */
	public final static int TYPE_FAV = 3;

	/**
	 * 相关问题
	 */
	public final static int TYPE_RELATED = 4;
	/**
	 * 默认检索条件
	 */
	public final static String DEFAULT_CONDITION = "0,0,9,0";

	private QueryBuilder qb;

	private boolean fullText = false;

	private StringBuilder where = new StringBuilder("  where status != "
			+ QuestionConst.QUESTION_STATUS_DELETE);

	private Object[] paramters = null;

	private String[] reponseParams;

	/**
	 * 构造检索条件
	 * 
	 * @param schoolId
	 *            学校
	 * @param condition
	 *            <pre>
	 *          filter: 过滤条件 9代表不过滤：{@link#flint.business.constant.QuestionConst}
	 *          grade: 年级
	 *   		kind: 科目
	 *  		knowledge:知识点
	 * </pre>
	 * @param keyword
	 *            关键字
	 * @param 类型
	 * 
	 * @throws ApplicationException
	 */
	public QuestionSearcher(long schoolId, String condition, String keyword,
			int type, long userId) throws ApplicationException {
		condition = StringHelper.defaultIfEmpty(condition, DEFAULT_CONDITION);
		String[] c = condition.split(",");
		/* 课程年级 */
		int grade = NumberHelper.toInt(c[0]);
		/* 课程类别 */
		long kind = NumberHelper.toLong(c[1]);
		/* 是否过滤问题分类 */
		int filter = NumberHelper.toInt(c[2], QuestionSearcher.NO_FILTER);
		/* 知识点 */
		String knowledgePath = StringHelper.defaultIfEmpty(c[3], "0");
		String[] knowledges = knowledgePath.split("\\-");
		String klast = knowledges[knowledges.length - 1];// 最后节点知识点

		/* 全文检索 */
		if (!StringHelper.isEmpty(keyword)) {
			fullText = true;
			qb = new QueryBuilder();
			/* 0代表主站 */
			if (schoolId != 0) {
				qb.andEq("school_id", String.valueOf(schoolId));
			}
			if (grade != 0) {
				qb.andEq("grade", String.valueOf(grade));
			}
			if (kind != 0) {
				qb.andEq("kind", String.valueOf(kind));
			}
			if (type == TYPE_RELATED) {
				qb.andEq("status",
						String.valueOf(QuestionConst.QUESTION_STATUS_SOLVED));
			} else if (filter != NO_FILTER) {
				if (filter == 8) {// 零解答
					qb.andEq("answers", "0");
				} else {
					qb.andEq("status", String.valueOf(filter));
				}
			}

			if (!"0".equals(klast)) {
				for (int i = 0; i < knowledges.length; i++) {
					long ki = NumberHelper.toLong(knowledges[i]);
					if (ki == 0)
						break;
					qb.andEq("k" + i, String.valueOf(ki));
				}
			}
			try {
				qb.andFullText(keyword, new String[] {"title",
						"kind", "knowledge", "grade", "intro", "tag" });
			} catch (ParseException e) {
				throw new ApplicationException(e);
			}

			/* 关键字检索的我的问题，我解答的问题，我收藏的问题待实现 */
		} else {
			
			/* 0代表主站 */
			where.append("  and school_id =? ");
			paramters = ObjectHelper.addObjectToArray(paramters, schoolId);
			
			if (grade != 0) {
				where.append("  and grade =?  ");
				paramters = ObjectHelper.addObjectToArray(paramters, grade);
			}
			if (kind != 0) {
				where.append("  and   kind = ?   ");
				paramters = ObjectHelper.addObjectToArray(paramters, kind);
			}
			if (!"0".equals(klast)) {
				for (int i = 0; i < knowledges.length; i++) {
					long ki = NumberHelper.toLong(knowledges[i]);
					if (ki == 0)
						break;
					where.append("  and  k" + (i + 1) + " =  ?    ");
					paramters = ObjectHelper.addObjectToArray(paramters, ki);
				}
			}
			if (filter != NO_FILTER) {
				if (filter == 8) {// 零解答
					where.append("  and answers= 0    ");
				} else {
					where.append("  and status= ?    ");
					paramters = ObjectHelper
							.addObjectToArray(paramters, filter);
				}
			}

			/* 归类检索 */
			switch (type) {
			case TYPE_MY_QUESTION:
				where.append("   and asker=?  ");
				paramters = ObjectHelper.addObjectToArray(paramters, userId);
				break;
			case TYPE_MY_ANSWER:
				where.append("  and  exists(select 1 from TB_QUESTION_ANSWER where TB_QUESTION_ANSWER.QUESTION_ID=TB_QUESTION.QUESTION_ID and TB_QUESTION_ANSWER.answer=? )  ");
				paramters = ObjectHelper.addObjectToArray(paramters, userId);
				break;
			case TYPE_FAV:
				where.append(
						"  and exists(select 1 from TB_COLLECT where TB_COLLECT.object_id=TB_QUESTION.QUESTION_ID and TB_COLLECT.object_type=")
						.append(CollectConst.QUESTION)
						.append(" and TB_COLLECT.user_id=? )  ");
				paramters = ObjectHelper.addObjectToArray(paramters, userId);
				break;
			case TYPE_RELATED:
				/* 检查问题设定为已解决的 */
				where.append("  and status= "
						+ QuestionConst.QUESTION_STATUS_SOLVED);
				break;
			}
		}

		reponseParams = new String[4];
		reponseParams[0] = String.valueOf(grade);
		reponseParams[1] = String.valueOf(kind);
		reponseParams[2] = String.valueOf(filter);
		reponseParams[3] = knowledges[0];/* 最多只显示第一级知识的检索，需要更细的检索请直接在列表中取得 */
	}

	public QueryBuilder getQueryBuilder() {
		return qb;
	}

	public String getWhere() {
		return where.toString();
	}

	public Object[] getParamters() {
		return paramters;
	}

	public boolean isFullText() {
		return fullText;
	}

	public String[] getReponseParams() {
		return reponseParams;
	}
}
