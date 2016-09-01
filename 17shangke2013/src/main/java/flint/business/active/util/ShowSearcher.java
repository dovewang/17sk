package flint.business.active.util;

import java.util.Date;

import kiss.util.Q;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.SortField;

import flint.business.constant.CollectConst;
import flint.business.constant.ShowConst;
import flint.business.core.Searcher;
import flint.exception.ApplicationException;
import flint.search.QueryBuilder;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.ObjectHelper;
import flint.util.StringHelper;

public class ShowSearcher extends Searcher {

	private QueryBuilder qb;

	private final static int[] SCORES = { 0, 6, 7, 8, 9 };

	private String[] reponseParams;

	/**
	 * 默认的搜索条件 标签、类别、得分、发布时间、排序字段
	 */
	public final static String DEFAULT_CONDITION = "0,-1,0,0,0";

	/**
	 * 排序字段
	 */
	private final static String[] ORDER_FIELDS = { "dateline", "dateline",
			"scores", "views", "collects" };

	/**
	 * 搜索所有
	 */
	public final static int TYPE_ALL = 0;

	/**
	 * 搜索我评论过的的
	 */
	public final static int TYPE_COMMENT = 1;

	/**
	 * 搜索我的收藏夹中的
	 */
	public final static int TYPE_FAV = 2;

	/**
	 * 搜索指定用户
	 */
	public final static int TYPE_USER = 3;

	/**
	 * @param keyword
	 *            关键字
	 * @param condition
	 *            参数集合
	 * @param userId
	 *            用户
	 * @param showType
	 *            搜索类型
	 */
	public ShowSearcher(String keyword, String condition, long userId,
			int type, byte courseType) throws ApplicationException {

		condition = StringHelper.defaultIfEmpty(condition, DEFAULT_CONDITION);

		String[] c = condition.split(",");
		/* 标签 */
		String tag = StringHelper.defaultIfEmpty(c[0], "0");
		/* 类别 */
		long category = NumberHelper.toLong(c[1]);
		/* 得分 */
		int score = NumberHelper.toInt(c[2]);
		/* 发布时间 */
		int publishTimeRange = NumberHelper.toInt(c[3]);

		/* 排序,默认按发布时间排序，得分，最多播放，最多收藏 */
		String order = StringHelper.defaultIfEmpty(c[4], "");
		QueryBuilder.Direction direction;
		if (order.length() == 1) {
			direction = QueryBuilder.Direction.DESC;
		} else {
			/* 得分 */
			if (order.length() == 2 || order.endsWith("d")) {// 降序
				direction = QueryBuilder.Direction.DESC;
			} else {// 升序
				direction = QueryBuilder.Direction.ASC;
			}
			/* 去掉排序 */
			order = order.substring(0, order.indexOf(":"));
		}
		int findex = NumberHelper.toInt(order);
		/* 排序字段 */
		String orderField = ORDER_FIELDS[findex];

		/****** 全文检索 *****/
		if (!Q.isBlank(keyword) || (!Q.isBlank(tag) && !"0".equals(tag))) {
			try {
				this.setFullText(true);

				qb = new QueryBuilder();

				// qb.andNeq("status",
				// String.valueOf(ShowConst.DELETE));//建索引时候已经过滤掉

				/*
				 * 秀秀类型
				 */
				if (courseType != 0) {
					qb.andEq("type", String.valueOf(courseType));
				}

				/* 类别 */
				if (category > -1) {
					qb.andEq("category", String.valueOf(category));
				}

				if (score > 0) {
					qb.andBetween("score", SCORES[score], 10);
				}

				int publishTime = getPublishTime(publishTimeRange);
				if (publishTime > 0) {
					qb.setBetweenFilter("dateline",
							String.valueOf(publishTime),
							String.valueOf(Integer.MAX_VALUE));
				}

				// keyword = tag.equals("0") ? "" + keyword : tag + keyword;
				if (!tag.equals("0")) {
					qb.andFullText("tag", tag);
				}

				if (!Q.isBlank(keyword))
					qb.andFullText(keyword.trim(), new String[] { "title",
							"intro", "grade", "subject", "tag" });

				if (StringHelper.isEmpty(orderField)) {// 排序
					qb.addSortField(orderField, SortField.Type.INT, direction);
				}
			} catch (ParseException e) {
				throw new ApplicationException(e);
			}
		} else {
			StringBuilder ws = new StringBuilder("  where status<>"
					+ ShowConst.DELETE + "  ");

			if (category > -1) {
				ws.append("  and category = ?  ");
				this.setParamters(ObjectHelper.addObjectToArray(
						this.getParamters(), category));
			}

			if (score > 0) {
				ws.append("  and scores >= ?  ");
				this.setParamters(ObjectHelper.addObjectToArray(
						this.getParamters(), SCORES[score]));
			}

			/* 发布时间选择 */
			int publishTime = getPublishTime(publishTimeRange);
			if (publishTime > 0) {
				ws.append(" and dateline >= ?  ");
				this.setParamters(ObjectHelper.addObjectToArray(
						this.getParamters(), publishTime));
			}

			if (courseType != 0) {
				ws.append(" and type = " + courseType);
			}

			/* 归类检索 */
			switch (type) {
			case TYPE_ALL:
				break;
			case TYPE_USER:
				ws.append(" and user_id = ? ");
				this.setParamters(ObjectHelper.addObjectToArray(
						this.getParamters(), userId));
				break;
			case TYPE_COMMENT:
				ws.append(" and exists(select 1 from TB_SHOW_COMMENT where TB_SHOW_COMMENT.show_id = TB_SHOW.show_id and TB_SHOW_COMMENT.user_id=? )  ");
				this.setParamters(ObjectHelper.addObjectToArray(
						this.getParamters(), userId));
				break;
			case TYPE_FAV:
				ws.append(" and exists(select 1 from TB_COLLECT where TB_COLLECT.object_id = TB_SHOW.show_id and TB_COLLECT.object_type = ");
				ws.append(CollectConst.SHOW);
				ws.append(" and TB_COLLECT.user_id = ? )  ");
				this.setParamters(ObjectHelper.addObjectToArray(
						this.getParamters(), userId));
				break;
			}
			this.setOrder(" order by "
					+ orderField
					+ " "
					+ ((direction == QueryBuilder.Direction.ASC) ? "ASC"
							: "DESC"));
			this.setWhere(ws.toString());
		}

		reponseParams = new String[5];
		reponseParams[0] = String.valueOf(c[0]);
		reponseParams[1] = String.valueOf(category);
		reponseParams[2] = String.valueOf(score);
		reponseParams[3] = String.valueOf(publishTimeRange);
		reponseParams[4] = StringHelper.defaultIfEmpty(c[4], "");
	}

	/**
	 * 发布时间转换
	 * 
	 * @param publishTimeRange
	 * @return
	 */
	private int getPublishTime(int publishTimeRange) {
		int publishTime = -1;
		if (publishTimeRange > 0) {
			Date date = new Date();
			switch (publishTimeRange) {
			case 1:
				/* 最近3天 */
				publishTime = DateHelper.getTime(date, 0, 0, -3);
				break;
			case 2:
				/* 最近一周 */
				publishTime = DateHelper.getTime(date, 0, 0, -7);
				break;
			case 3:
				/* 最近两周 */
				publishTime = DateHelper.getTime(date, 0, 0, -14);
				break;
			case 4:
				/* 最近一月 */
				publishTime = DateHelper.getTime(date, 0, -1, 0);
				break;
			case 5:
				/* 最近两月 */
				publishTime = DateHelper.getTime(date, 0, -2, 0);
				break;
			case 6:
				/* 最近三月 */
				publishTime = DateHelper.getTime(date, 0, -3, 0);
				break;
			default:
				publishTime = -1;
			}
		}
		return publishTime;
	}

	public String[] getReponseParams() {
		return reponseParams;
	}

	public QueryBuilder getQueryBuilder() {
		return qb;
	}
}
