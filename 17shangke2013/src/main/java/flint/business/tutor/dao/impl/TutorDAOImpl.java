package flint.business.tutor.dao.impl;

import java.util.List;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Tutor;
import entity.TutorDemand;
import entity.User;
import flint.base.BaseDAOImpl;
import flint.business.constant.TutorConst;
import flint.business.tutor.dao.TutorDAO;
import flint.common.Page;
import flint.exception.DataAccessException;
import flint.util.StringHelper;

@Repository
public class TutorDAOImpl extends BaseDAOImpl<Tutor, Long> implements TutorDAO {

	@Override
	public Page<Tutor> search(byte type, int page, long size) {
		if (type == 0)
			return this.findPage(Tutor.class, "select * from TB_TUTOR ", page, size);
		else
			return this.findPage(Tutor.class, "select * from TB_TUTOR where provider_type=? ", page, size, type);
	}

	@Override
	public long addCandidate(long userId, long candidate) {
		if (count("select count(1) from TB_TUTOR_CANDIDATE  where DEMAND_ID=0 and CANDIDATE_ID=? and OWER=? ", candidate, userId) > 0) {
			return 0;
		}
		return this.insert("insert into TB_TUTOR_CANDIDATE (CANDIDATE_ID,DEMAND_ID,GROUP_ID,OWER) values(?,0,0,?)", candidate, userId);
	}

	@Override
	public Page<User> findCandidate(long userId, int page, long size) {
		return null;
	}
	
	@Override
	public Page<Tutor> searchTutor(long userId, int page, long size) {
		return this.findPage(Tutor.class, "select t.TUTOR_ID,t.KIND1,t.KIND2,t.KIND3,t.PRICE,t.DATELINE,t.VIEWS,t.INTRO from TB_TUTOR t where t.PROVIDER=? and t.STATUS != ? order by t.DATELINE desc", page, size, userId, TutorConst.TUTOR_DELETE);
	}

	@Override
	public Page<TutorDemand> searchDemand(long userId, int page, long size) {
		return this.findPage(TutorDemand.class, "select t.DEMAND_ID,t.KIND1,t.KIND2,t.KIND3,t.VIEWS,t.DATELINE,t.PRICE,t.INTRO from TB_TUTOR_DEMAND t where t.USER_ID = ? and t.STATUS != ? order by t.DATELINE desc", page, size, userId, TutorConst.TUTORDEMAND_DELETE);
	}
	
	@Override
	public int deleteTutorDemand(long tutordemandid) throws DataAccessException {
		return update("update TB_TUTOR_DEMAND set STATUS = ? where DEMAND_ID = ? ", TutorConst.TUTORDEMAND_DELETE, tutordemandid);
	}
	
	@Override
	public int deleteTutor(long tutorId) throws DataAccessException {
		return update("update TB_TUTOR set STATUS = ? where TUTOR_ID = ? ", TutorConst.TUTOR_DELETE, tutorId);
	}
	
	@Override
	public Tutor selectTutor(long id){
		return findFirst(Tutor.class,"SELECT * from TB_TUTOR where TUTOR_ID = ? ", id);
	}
	
	@Override
	public TutorDemand selectTutorDemand(long id){
		return findFirst(TutorDemand.class,"SELECT * from TB_TUTOR_DEMAND where DEMAND_ID = ? ", id);
	}

	@Override
	public int updateTutor(long id, long kind1, long kind2, long kind3,
			int price, String intro) {
		String sql = "update TB_TUTOR set KIND1 = ?, KIND2 = ?, KIND3 = ?, PRICE = ?,INTRO=? where TUTOR_ID = ?";
		return update(sql, kind1, kind2, kind3, price, intro, id);
	}

	@Override
	public int updateTutorDemand(long id, long kind1, long kind2,
			long kind3, int price, String intro) {
		String sql = "update TB_TUTOR_DEMAND set KIND1 = ?, KIND2 = ?, KIND3 = ?, PRICE = ?,INTRO=? where DEMAND_ID = ?";
		return update(sql, kind1, kind2, kind3, price, intro, id);
	}
	
	@Override
	public List<TutorDemand> guessDemand(String focus, long userId, long page, long size) {
		String queryString = "select distinct user_id,demand_id,kind1,kind2,kind3,dateline,price,IFNULL(intro,'没有描述') intro from TB_TUTOR_DEMAND where 1=1 and STATUS !=" + TutorConst.TUTORDEMAND_DELETE;

		/* 不显示本人的关注及已关注的 */
//		if (userId != 0) {
//			queryString += " and user_id != " + userId+" and  not exists(select 1 from TB_FOLLOW where FANS_ID = "+userId+" and TB_FOLLOW.FRIEND_ID = TB_TUTOR_DEMAND.USER_ID)";
//		}
		
		StringBuilder sql = new StringBuilder(queryString);
		
		/* 添加用户擅长科目查询条件 */
		if (!StringHelper.isEmpty(focus)) {
			String[] tmp = focus.split(",");
			for(int i=1;i<=3;i++){
				if(i==1){
					sql.append(" and ");
				}else{
					sql.append(" or ");
				}
				sql.append(" ( kind" + i + " like '%" + tmp[0] + "%'");
				for (int j = 1; j < tmp.length; j++) {
					sql.append("or  kind" + i + " like '%" + tmp[j] + "%'");
				}
				sql.append(") ");
			}
		}
		sql.append(" group by user_id order by DATELINE desc");
		List<TutorDemand> demand = this.findPage(TutorDemand.class, sql.toString(), page, size).getResult();
		if (demand.size() < size) {
			String un = "";
			for (TutorDemand t : demand) {
				if(Q.isBlank(un)) un = t.getUserId() + "";
				else un += "," + t.getUserId();
			}
			if(un.length() > 0){
				queryString += " and user_id not in("+un+")";
			}
			List<TutorDemand> b = this.findPage(TutorDemand.class, queryString + "  group by user_id order by  DATELINE desc ", 1, size - demand.size()).getResult();
			demand.addAll(b);
		}
		return demand;
	}

	@Override
	public List<TutorDemand> searchDemandByUserids(String userids) {
		return find(TutorDemand.class, "select t.USER_ID,t.DEMAND_ID,t.KIND1,t.KIND2,t.KIND3,t.VIEWS,t.DATELINE,t.PRICE,t.INTRO from TB_TUTOR_DEMAND t where t.USER_ID in (" + userids + ") and t.STATUS != ? order by t.DATELINE desc", TutorConst.TUTORDEMAND_DELETE);
	}

	@Override
	public List<Tutor> searchTutorByUserids(String userids) {
		return find(Tutor.class, "select t.PROVIDER, t.TUTOR_ID,t.KIND1,t.KIND2,t.KIND3,t.PRICE,t.DATELINE,t.VIEWS,t.INTRO from TB_TUTOR t where t.PROVIDER in (" + userids + ") and t.STATUS != ? order by t.DATELINE desc", TutorConst.TUTOR_DELETE);
	}

}
