package flint.business.active.dao.impl;

import static kiss.util.Helper.$;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Show;
import entity.ShowComment;
import entity.ShowVote;
import flint.base.BaseDAOImpl;
import flint.business.active.dao.ShowDAO;
import flint.business.constant.ShowConst;
import flint.common.Page;
import flint.util.StringHelper;

@Repository
public class ShowDAOImpl extends BaseDAOImpl<Show, Long> implements ShowDAO {

	@Override
	public Page<Show> seach(String where, String order, long page, long size, Object... parameters) {
		return this.findPage(Show.class, "select * from TB_SHOW " + where + " " + order, "select count(1) from TB_SHOW " + where, page, size, parameters);
	}
	
	@Override
	public Page<Show> searchManager(long page, int size, Map<String, String[]> params) throws Exception {
		byte status = $($(params.get("status")).get()).toByte((byte)99);// 默认显示全部
		long showId = $($(params.get("showId")).get()).toLong();// 编号
		String title = $($(params.get("title")).get()).toString();// 主题
		String startTime = $($(params.get("start")).get()).toString();// 开始时间
		String endTime = $($(params.get("end")).get()).toString();// 结束时间
		String where = "  where 1=1";
		if (showId != 0)
			where += " and SHOW_ID = " + showId;
		if (!Q.isBlank(title))
			where += " and TITLE like '%" + title +"%'";
		if (!Q.isBlank(startTime))
			where += " and DATELINE >= " + $(startTime).toTime();
		if (!Q.isBlank(endTime))
			where += " and DATELINE <= " + $(endTime).toTime();
		if (status != 99)
			where += " and STATUS = " + status;
		return seach(where, "order by STATUS asc,DATELINE desc", page, size);
	}

	@Override
	public List<Show> hot(int top) {
		return this.find(Show.class, " select show_id,title,cover,user_id,ups,scores,views,comments from  TB_SHOW where status=? order by ups desc,scores desc,views desc,comments desc,dateline desc limit 0,?", ShowConst.OK, top);
	}

	@Override
	public Page<ShowComment> listComments(long id, long page, long size) {
		return this.findPage(ShowComment.class, "select * from TB_SHOW_COMMENT where show_id=? and status=1 order by dateline desc", page, size, id);
	}

	@Override
	public int deleteComment(long id, String memo) {
		return update("delete from  TB_SHOW_COMMENT  where  comment_Id=? and memo=?", id, memo);
	}

	@Override
	public int vote(ShowVote vote) {
		if (vote.isSupport())
			update("update TB_SHOW set ups=ups+1 where show_id=?", vote.getShowId());
		return save(vote);
	}

	@Override
	public int comment(ShowComment comment) {
		update("update TB_SHOW set comments=comments+1 where show_id=?", comment.getShowId());
		return save(comment);
	}

	@Override
	public int delete(long id) {
		return update("update TB_SHOW set status=? where show_id=? ", ShowConst.DELETE, id);
	}
	
	@Override
	public int addview(long id) {
		return update("update TB_SHOW set views = views + 1 where show_id=?", id);
	}

	@Override
	public int update(Show show) {
		return update("update TB_SHOW set  title=?, intro=?, tag=?, category=?,cover=? where show_id=? ",
				show.getTitle(), show.getIntro(), show.getTag(), show.getCategory(), show.getCover(), show.getShowId());
	}

	@Override
	public List<Show> findQuestionByIds(Collection<String> ids) {
		return find("select * from TB_SHOW where show_id in("+ StringHelper.collectionToDelimitedString(ids,",") + ")");
	}
}
