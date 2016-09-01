package flint.business.course.util;

import static kiss.util.Helper.$;

import java.util.Date;

import kiss.util.Q;
import kiss.util.wrapper.ArrayWrapper;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.SortField;

import flint.business.constant.CollectConst;
import flint.business.constant.CourseConst;
import flint.business.constant.TradeConst;
import flint.exception.ApplicationException;
import flint.search.QueryBuilder;
import flint.util.DateHelper;
import flint.util.NumberHelper;
import flint.util.StringHelper;

public class CourseSearcher {
	/**
	 * 在所有课程中检索
	 */
	public final static int TYPE_ALL = 0;

	/**
	 * 在我创建的课程中检索
	 */
	public final static int TYPE_MY_CREATE = 1;
	/**
	 * 在我上过的课程中
	 */
	public final static int TYPE_MY_STUDY = 2;
	/**
	 * 在我的收藏夹中检索
	 */
	public final static int TYPE_FAV = 3;

	/**
	 * 相关课程
	 */
	public final static int TYPE_RELATED = 4;

	public final static String DEFAULT_CONDITION = "0,0,-1,0,0,0,0,0,0";

	private final static long FLAG = 10L;

	private final static int[] SCORES = { 0, 6, 7, 8, 9 };

	private final static String[] ORDER_FIELDS = { "publish_time", "price",
			"score", "teacher_level", "views", "collects" };

	private final static int[][] PRICE_RNAGR = { { 0, 65535 }, { 0, 0 },
			{ 0, 50 }, { 50, 100 }, { 100, 150 }, { 150, 65535 } };

	private QueryBuilder qb;

	private boolean fullText = false;

	private StringBuilder where = new StringBuilder("  where 1=1  ");

	private Object[] paramters = null;

	private StringBuilder orderString = new StringBuilder("  ");

	private String[] reponseParams;

	public CourseSearcher(long schoolId, String condition, String keyword,
			int type, long userId, int courseType) throws ApplicationException {
		condition = Q.isEmpty(condition, DEFAULT_CONDITION);
		String[] c = condition.split(",");
		ArrayWrapper<Object> array = $(paramters);
		/* 历史检索问题 */
		if (c.length < 9) {
			String[] t = new String[9];
			for (int i = 0; i < c.length; i++) {
				t[i] = c[i];
			}
			for (int i = c.length - 1; i < t.length; i++) {
				t[i] = "0";
			}
			c = t;// 覆盖原值
		}
		/* 课程年级 */
		int grade = NumberHelper.toInt(c[0]);
		/* 课程类别 */
		long kind = NumberHelper.toLong(c[1]);
		/* 课程状态 */
		byte status = NumberHelper.toByte(c[2]);
		/* 课程得分 ,已结课的才有得分 */
		int score = NumberHelper.toInt(c[3]);
		/* 开课时间 */
		int startTimeRange = NumberHelper.toInt(c[4]);
		/* 课程价格 */
		int price = NumberHelper.toInt(c[5]);
		/* 发布时间 */
		int publishTimeRange = NumberHelper.toInt(c[6]);

		/* 排序,默认按发布时间排序,价格，得分，教师等级，最多播放，最多收藏 */
		String order = Q.isEmpty(c[7], "");
		int area = Integer.parseInt(Q.isEmpty(c[8], "0"));

		if (courseType != CourseConst.COURSE_TYPE_LOACL) {
			area = 0;
		}

		QueryBuilder.Direction direction;
		if (order.length() == 1) {
			direction = QueryBuilder.Direction.DESC;
		} else {
			/* 价格和得分 */
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

		if (!StringHelper.isEmpty(keyword) || !"0".equals(kind))
			try {
				{
					fullText = true;
					qb = new QueryBuilder();
					if (schoolId != 0)
						qb.andEq("school_id", String.valueOf(schoolId));
					else
						qb.andEq("share_model", "0");
					/*
					 * 课程类型
					 */
					if (courseType != 0) {
						if (courseType != CourseConst.COURSE_TYPE_VIDEO)
							qb.andEq("type", String.valueOf(courseType));
						else
							qb.andIn("type", String.valueOf(courseType), String
									.valueOf(CourseConst.COURSE_TYPE_FORWARD));
					}

					/* 课程年级 */
					if (grade > 0) {
						qb.andEq("scope", String.valueOf(grade));
					}

					/* 课程分类 */
					if (kind > 0) {
						qb.andEq("category", String.valueOf(kind));
					}
					if (status > -1) {
						qb.andEq("status", String.valueOf(status));
					}

					if (score > 0) {
						qb.andBetween("score", SCORES[score], 10);
					}

					int predictStartTime = getPredictStartTime(startTimeRange);
					if (predictStartTime > 0) {
						qb.andNlt("start_time", predictStartTime);
					}

					int publishTime = getPublishTime(publishTimeRange);
					if (publishTime > 0) {
						qb.setBetweenFilter("publish_time",
								String.valueOf(publishTime),
								String.valueOf(Integer.MAX_VALUE));
					}

					if (price > 0) {
						int[] p = PRICE_RNAGR[price];
						qb.andBetween("price", p[0], p[1]);
					}

					if (area > 0) {
						qb.andEq("city", String.valueOf(area));
					}
					if (!Q.isBlank(keyword))
						qb.andFullText(keyword, new String[] { "name",
								"category", "tag", "aim", "scope" });
					if (!Q.isEmpty(orderField)) {
						qb.addSortField(orderField, SortField.Type.INT, direction);
					}
				}
			} catch (ParseException e) {
				throw new ApplicationException(e);
			}
		else {
			/* 0代表主站 ,各个学校独立 */
			if (schoolId != 0) {
				where.append("  AND school_id =? ");
				array.addLast(schoolId);
			} else {
				/* 查询公开模式的数据 */
				where.append("  AND SHARE_MODEL=0 ");
			}

			/*
			 * 课程类型
			 */
			if (courseType != 0) {
				if (courseType != CourseConst.COURSE_TYPE_VIDEO) {
					where.append("  AND type =? ");
					array.addLast(courseType);
				} else {
					where.append("  AND( type =? or type=?)  ");
					array.addLast(CourseConst.COURSE_TYPE_VIDEO,
							CourseConst.COURSE_TYPE_FORWARD);
				}
			}

			/* 年级 */
			if (grade > 0) {
				where.append("  AND exists( select 1 from TB_COURSE_SCOPE where  grade_id= ? and TB_COURSE_SCOPE.course_id=TB_COURSE.course_id) ");
				array.addLast(grade);
			}

			/* 科目 */
			if (kind > 0) {
				String k = String.valueOf(kind);
				char last = k.charAt(k.length() - 1);
				if (last == '0') {
					where.append("  AND CATEGORY  DIV " + FLAG + "="
							+ (kind / FLAG));
				} else {
					where.append("  AND CATEGORY   =" + (kind / FLAG) + last);
				}
			}

			/* 状态 */
			if (status == CourseConst.COURSE_SIGN) {
				where.append(" and TB_COURSE.status=" + CourseConst.COURSE_SIGN
						+ "  ");
				score = 0;// 设定为分数不限
			} else if (status == CourseConst.COURSE_SUCCESS) {
				/* 已结课 */
				where.append("  and TB_COURSE.status="
						+ CourseConst.COURSE_SUCCESS + "  ");
				startTimeRange = 0;// 报名时间不限
			} else {
				/* 全局检索只在已发布的课程中检索 */
				if (type == TYPE_ALL) {
					/* 检索所有已发布的课程 */
					where.append(" and   TB_COURSE.STATUS in("
							+ CourseConst.COURSE_SIGN + ","
							+ CourseConst.COURSE_SIGN_OVER + ","
							+ CourseConst.COURSE_START + ","
							+ CourseConst.COURSE_SUCCESS + ") ");
				} else {
					// 可以查询其他任何状态的课程
					// where.append(" and   TB_COURSE.STATUS=" + status);
				}
				score = 0;// 分数不限
				startTimeRange = 0;// 报名时间不限
			}

			/* 课程在报名中，才能查询报名时间 */
			int predictStartTime = getPredictStartTime(startTimeRange);
			if (predictStartTime > 0) {
				where.append(" and start_time>= ?  ");
				array.addLast(predictStartTime);

			}

			/* 课程结束后才能计算得分 */
			if (score > 0) {
				where.append(" and TB_COURSE.score>=?   ");
				array.addLast(SCORES[score]);
			}

			/* 课程价格 */
			if (price > 0) {
				int[] p = PRICE_RNAGR[price];
				where.append(" and (price >=  ?  and  price<=?  )  ");
				array.addLast(p[0], p[1]);
			}

			/* 发布时间选择 */
			int publishTime = getPublishTime(publishTimeRange);

			if (publishTime > 0) {
				where.append(" and publish_time>=?  ");
				array.addLast(publishTime);
			}

			if (area > 0) {
				if (area % 100 == 0) {
					where.append(" and round(city/100)=" + area / 100);
				} else {
					where.append(" and city=" + area);
				}
			}

			/* 归类检索 */
			switch (type) {
			case TYPE_MY_CREATE:
				where.append("   and user_id=?  ");
				array.addLast(userId);
				break;
			case TYPE_MY_STUDY:
				where.append("and exists( select 1  from TB_ORDER o,TB_ORDER_ITEM i  where i.ORDER_ID=o.ORDER_ID  and i.PRODUCT_TYPE in("
						+ TradeConst.TYPE_COURSE_ONLINE
						+ ","
						+ TradeConst.TYPE_COURSE_LOCAL
						+ ")  and i.PRODUCT_ID=TB_COURSE.COURSE_ID and o.`STATUS`>0  and o.buyer="
						+ userId + ") ");
				break;
			case TYPE_FAV:
				where.append(
						"  and exists(select 1 from TB_COLLECT where TB_COLLECT.object_id=TB_COURSE.course_id and TB_COLLECT.object_type=")
						.append(CollectConst.COURSE)
						.append(" and TB_COLLECT.user_id=? )  ");
				array.addLast(userId);
				break;
			}

			orderString.append(" order by  "
					+ orderField
					+ " "
					+ ((direction == QueryBuilder.Direction.ASC) ? "ASC"
							: "DESC"));
		}
		this.paramters = array.toArray();
		reponseParams = new String[9];
		reponseParams[0] = String.valueOf(grade);
		reponseParams[1] = String.valueOf(kind);
		reponseParams[2] = String.valueOf(status);
		reponseParams[3] = String.valueOf(score);
		reponseParams[4] = String.valueOf(startTimeRange);
		reponseParams[5] = String.valueOf(price);
		reponseParams[6] = String.valueOf(publishTimeRange);
		reponseParams[7] = Q.isEmpty(c[7], "");
		reponseParams[8] = String.valueOf(area);
	}

	/**
	 * 获取报名时间范围
	 * 
	 * @param startTime
	 * @return
	 */
	private int getPredictStartTime(int startTimeRange) {
		int predictStartTime = -1;
		if (startTimeRange > 0) {
			Date date = new Date();
			switch (startTimeRange) {
			case 1:
				/* 3天以内 */
				predictStartTime = DateHelper.getTime(date, 0, 0, 3);
				break;
			case 2:
				/* 本周内 */
				predictStartTime = DateHelper.getWeekLastTime(date, 1);
				break;
			case 3:
				/* 本月内 */
				predictStartTime = DateHelper.getMonthLastTime(date, 0);
				break;
			case 4:
				/* 两个月内 */
				predictStartTime = DateHelper.getMonthLastTime(date, 1);
				break;
			default:
				predictStartTime = -1;
			}
		}
		return predictStartTime;
	}

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

	public String getOrder() {
		return orderString.toString();
	}

	public String[] getReponseParams() {
		return reponseParams;
	}
}
