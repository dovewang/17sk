package flint.business.active.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import entity.Show;
import entity.ShowComment;
import entity.ShowVote;
import flint.base.BaseDAO;
import flint.common.Page;

public interface ShowDAO extends BaseDAO<Show, Long> {

	Page<Show> seach(String where, String order, long page, long size, Object... parameters);

	/**
	 * 秀秀后台列表
	 * @param page
	 * @param size
	 * @param params 查询条件
	 * @return
	 * @throws Exception 
	 */
	Page<Show> searchManager(long page, int size, Map<String, String[]> params) throws Exception;
	
	List<Show> hot(int top);

	Page<ShowComment> listComments(long id, long page, long size);

	int deleteComment(long id, String memo);

	int vote(ShowVote vote);

	int comment(ShowComment comment);

	int delete(long id);

	int update(Show show);
	
	int addview(long id); 
	
	/**
	 * 根据ID查找秀秀
	 * 
	 * @param ids
	 * @return
	 */
	List<Show> findQuestionByIds(Collection<String> ids);
}
