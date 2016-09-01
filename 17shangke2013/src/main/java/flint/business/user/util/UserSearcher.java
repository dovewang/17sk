package flint.business.user.util;

import org.apache.lucene.queryparser.classic.ParseException;

import flint.exception.ApplicationException;
import flint.search.QueryBuilder;
import flint.search.QueryBuilder.Direction;
import flint.util.NumberHelper;
import flint.util.StringHelper;

public class UserSearcher {

	public static int TYPE_STUDENT = 1;

	public static int TYPE_TEACHER = 2;

	public final static String DEFAULT_CONDITION = "0,0,0,0,0,0";

	private static final String[] ORDER_FIELDS = { "students", "fee", "teacher_level" };

	private QueryBuilder qb;

	private boolean fullText = false;

	private StringBuilder where = new StringBuilder("  where 1=1  ");

	private StringBuilder orderString = new StringBuilder("  ");

	private Object[] paramters = null;

	private Direction direction;

	private String orderField;

	private String[] reponseParams;

	private String course_sql = "  select 1 from TB_USER_EXPERT where TB_USER_EXPERT.user_id=TB_USER.user_id   ";

	public UserSearcher(long school, String condition, String keyword, int type) throws ApplicationException {
		condition = StringHelper.defaultIfEmpty(condition, DEFAULT_CONDITION);
		String[] c = condition.split(",");
		/* 年级 */
		int grade = NumberHelper.toInt(c[0], 0);
		/* 所教的课程 */
		int course = NumberHelper.toInt(c[1], 0);
		/* 教师等级 */
		int level = NumberHelper.toInt(c[2], 0);
		
		String order = StringHelper.defaultIfEmpty(c[3], "");
		
		if (!StringHelper.isEmpty(keyword)) {
			fullText = true;
			qb = new QueryBuilder();
			qb.andEq("school_user_type", school + "OF" + type);

			try {
				if (course > 0) {
					qb.andFullText("expert", String.valueOf(course));
				}
				/* 教师在线 */
				qb.andFullText(keyword, new String[] { "name", "real_name", "grade", "expert", "experience" });
			} catch (ParseException e) {
				throw new ApplicationException(e);
			}
		} else {
			if (school != 0)
				where.append("  and exists(select 1 from TB_SCHOOL_MEMBER where school_id=" + school + " and TB_SCHOOL_MEMBER.USER_ID=TB_USER.USER_ID and user_type=" + type + ") ");
			else
				where.append("   and user_type=" + type + "   ");
			/* 检索老师 */
			if (type == TYPE_TEACHER) {
				if (grade > 0) {
					where.append(" and grade=" + grade);
				}
				if (course > 0) {
					course_sql += " and EXPERT=" + course;
					where.append("  and exists( " + course_sql + " )  ");
				}
				/* 星级判断 */
				if (level > 0) {
					where.append(" and teacher_level>=" + level);
				}
			} else {
				/* 检索学生 */
				if (grade > 0)
					where.append(" and grade=" + grade);
			}

			if (order.length() > 1) {
				if (order.length() == 2 || order.endsWith("d")) {// 降序
					direction = QueryBuilder.Direction.DESC;
				} else {// 升序
					direction = QueryBuilder.Direction.ASC;
				}
				/* 去掉排序 */
				order = order.substring(0, order.indexOf(":"));
			}

			int findex = NumberHelper.toInt(order, 0);
			/* 排序字段 */
			this.orderField = this.ORDER_FIELDS[findex];
			orderString = new StringBuilder(" order by " + orderField + " " + ((direction == QueryBuilder.Direction.ASC) ? "ASC" : "DESC"));
			/* 是否在线，与学位认证 */
			if ("t".equals(c[4])) {
				where.append(" and online=1 ");
			}

			if ("t".equals(c[5])) {
				where.append(" and (active&8)!=0 ");
			}
		}
		reponseParams = new String[6];
		reponseParams[0] = String.valueOf(grade);
		reponseParams[1] = String.valueOf(course);
		reponseParams[2] = String.valueOf(level);
		reponseParams[3] = StringHelper.defaultIfEmpty(c[3], "");
		reponseParams[4] = c[4];
		reponseParams[5] = c[5];
	}

	public Object getReponseParams() {
		return reponseParams;
	}

	public boolean isFullText() {
		return fullText;
	}

	public QueryBuilder getQueryBuilder() {
		return qb;
	}

	public String getWhere() {
		return where.toString();
	}

	public String getOrderBy() {
		return orderString.toString();
	}

}
