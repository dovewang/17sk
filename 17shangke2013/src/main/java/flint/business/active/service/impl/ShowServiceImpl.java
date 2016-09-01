package flint.business.active.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Mblog;
import entity.Message;
import entity.Show;
import entity.ShowComment;
import entity.ShowVote;
import flint.base.BaseServiceImpl;
import flint.business.active.dao.ShowDAO;
import flint.business.active.service.ShowService;
import flint.business.active.util.ShowSearcher;
import flint.business.constant.MblogConst;
import flint.business.constant.MessageConst;
import flint.business.constant.ShowConst;
import flint.business.constant.TagConst;
import flint.business.mblog.service.MblogService;
import flint.business.search.ShowSearch;
import flint.business.tag.service.TagService;
import flint.business.user.service.MessageService;
import flint.business.util.ImageHelper;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.DateHelper;

@Service
public class ShowServiceImpl extends BaseServiceImpl implements ShowService {

	@Autowired
	private ShowDAO dao;

	@Autowired
	private TagService tagService;

	@Autowired
	private MblogService mblogService;

	@Autowired
	private ShowSearch showSearch;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ImageHelper imageHelper;

	@Override
	public Page<Show> search(ShowSearcher searcher, long page, long size) {
		if (searcher.isFullText()) {
			Page pageSearch = showSearch.queryPage(searcher.getQueryBuilder(),
					(int) page, (int) size);
			List<String> searchResult = (List<String>) pageSearch.getResult();
			if (pageSearch.getTotalCount() > 0) {
				pageSearch.setResult(dao.findQuestionByIds(searchResult));
			} else {
				pageSearch.setResult(new ArrayList());
			}
			return pageSearch;
		} else {
			return dao.seach(searcher.getWhere(), searcher.getOrder(), page,
					size, searcher.getParamters());
		}
	}

	@Override
	public int publish(Show show) {
		show.setDateline(Q.now());
		show.setStatus(ShowConst.OK);
		long showId = dao.insert(show);
		/* 创建标签 */
		tagService.save(show.getTag(), showId, TagConst.SHOW);
		/* 创建索引 */
		showSearch.createIndex(showId);
		/* 发布动态 */
		Mblog mblog = new Mblog();
		mblog.setDateline(Q.now());
		mblog.setType(MblogConst.TYPE_SHOW);
		mblog.setSchoolId(0);
		mblog.setUserId(show.getUserId());
		mblog.setMedia(String.valueOf(showId));

		String cover = imageHelper.resolve(show.getCover(), "s120,90");
		mblog.setContent("发布了秀秀<a>#" + show.getTitle()
				+ "#</a><br/><a><img src=\"" + cover
				+ "\" width=\"120\" height=\"90\"></a>");

		mblogService.post(mblog);
		return 1;
	}

	@Override
	public int update(Show show) {
		int result = dao.update(show);
		if (result > 0) {
			long showid = show.getShowId();
			/* 更新标签 */
			tagService.save(show.getTag(), showid, TagConst.SHOW);

			/* 更新索引 */
			showSearch.createIndex(showid);

			/* 发布动态 */
			Mblog mblog = new Mblog();
			mblog.setDateline(Q.now());
			mblog.setType(MblogConst.TYPE_SHOW);
			mblog.setSchoolId(0);
			mblog.setUserId(show.getUserId());
			mblog.setMedia(String.valueOf(showid));

			String cover = imageHelper.resolve(show.getCover(), "s120,90");
			mblog.setContent("更新了秀秀<a>#" + show.getTitle()
					+ "#</a><br/><a><img src=\"" + cover
					+ "\" width=\"120\" height=\"90\"></a>");
			mblogService.post(mblog);
		}
		return result;
	}

	@Override
	public int delete(long id) {
		int result = dao.delete(id);
		if (result > 0) {
			/* 删除全文检索 */
			showSearch.delete(id);
			/* 删除标签 */
			tagService.drop(id, TagConst.SHOW);
		}
		return result;
	}

	@Override
	public int managerDelete(long sid, String memo, Identity user)
			throws ApplicationException {
		int result = delete(sid);
		int dateline = Q.now();
		Show show = dao.findById(sid);
		Message message = new Message();
		message.setMessage("您的秀秀#" + show.getTitle() + "#已被管理员删除，原因是" + memo);
		message.setSendTime(dateline);
		message.setType(MessageConst.SYSTEM);
		message.setReceiver(show.getUserId());
		messageService.send(message, user);
		return result;
	}

	@Override
	public int deleteComment(long id, String memo) {
		/* 记录删除日志 */
		return dao.deleteComment(id, memo);
	}

	@Override
	public Show findById(long id) {
		return dao.findById(id);
	}

	@Override
	public int addview(long id) {
		return dao.addview(id);
	}

	@Override
	public int comment(ShowComment comment, String title) {
		/* 转发到微博 */
		Mblog mblog = new Mblog();
		mblog.setDateline(comment.getDateline());
		mblog.setType(MblogConst.TYPE_SHOW);
		mblog.setSchoolId(0);
		mblog.setContent("评价了秀秀<a>#" + title + "#</a>:" + comment.getContent());
		mblog.setUserId(comment.getUserId());
		mblog.setMedia(String.valueOf(comment.getShowId()));
		mblogService.post(mblog);
		return dao.comment(comment);
	}

	@Override
	public Page<ShowComment> listComments(long id, long page, long size) {
		return dao.listComments(id, page, size);
	}

	@Override
	public List<Show> hot(int top) {
		return dao.hot(top);
	}

	@Override
	public int vote(ShowVote vote) {
		return dao.vote(vote);
	}

	@Override
	public int createIndexs() {
		showSearch.rebulidIndex();
		return 1;
	}

	@Override
	public Page<Show> searchManager(long page, int size,
			Map<String, String[]> params) throws Exception {
		return dao.searchManager(page, size, params);
	}

}
