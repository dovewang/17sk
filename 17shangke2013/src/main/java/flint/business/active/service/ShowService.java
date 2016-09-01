package flint.business.active.service;

import java.util.List;
import java.util.Map;

import kiss.security.Identity;

import entity.Show;
import entity.ShowComment;
import entity.ShowVote;
import flint.base.BaseService;
import flint.business.active.util.ShowSearcher;
import flint.common.Page;
import flint.exception.ApplicationException;

public interface ShowService extends BaseService {

	Page<Show> search(ShowSearcher searcher, long page, long size);

	/**
	 * 发布我来秀秀
	 * 
	 * @param show
	 * @return
	 */
	int publish(Show show);

	/**
	 * 更新我来秀秀
	 * 
	 * @param show
	 * @return
	 */
	int update(Show show);

	/**
	 * 删除我来秀秀
	 * 
	 * @param id
	 * @return
	 */
	int delete(long id);

	/**
	 * 获取一个秀秀的详细信息
	 * 
	 * @param id
	 * @return
	 */
	Show findById(long id);
	
	/**
	 * 管理员删除秀秀
	 * 
	 * @param qid
	 * @return
	 * @throws ApplicationException
	 */
	int managerDelete(long sid, String memo, Identity user) throws ApplicationException ;

	/**
	 * 评论
	 * 
	 * @param comment
	 * @param title
	 * @param ups
	 * @param score
	 * @return
	 */
	int comment(ShowComment comment, String title);

	Page<ShowComment> listComments(long id, long page, long size);

	List<Show> hot(int top);

	int deleteComment(long id, String memo);

	int vote(ShowVote vote);
	
	int addview(long id);

	/**
	 * 秀秀索引重建
	 * @return
	 */
	public int createIndexs();
	
	/**
	 * 秀秀后台列表
	 * @param page
	 * @param size
	 * @param params 查询条件
	 * @return
	 * @throws Exception 
	 */
	Page<Show> searchManager(long page, int size, Map<String, String[]> params) throws Exception;
}
