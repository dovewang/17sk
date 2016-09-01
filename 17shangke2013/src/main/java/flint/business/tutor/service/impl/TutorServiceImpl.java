package flint.business.tutor.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Mblog;
import entity.Tutor;
import entity.TutorDemand;
import entity.User;
import flint.base.BaseServiceImpl;
import flint.business.constant.MblogConst;
import flint.business.mblog.service.MblogService;
import flint.business.tutor.dao.TutorDAO;
import flint.business.tutor.service.TutorService;
import flint.business.util.KindHelper;
import flint.common.Page;
import flint.exception.DataAccessException;
import flint.util.DateHelper;

@Service
public class TutorServiceImpl extends BaseServiceImpl implements TutorService {

	@Autowired
	private TutorDAO dao;

	@Autowired
	private MblogService mblogService;

	@Autowired
	private KindHelper kindHelper;

	@Override
	public int postService(Tutor tuor) {
		tuor.setDateline(DateHelper.getNowTime());
		long id = dao.insert(tuor);

		/* 发布动态 */
		Mblog mblog = new Mblog();
		mblog.setDateline(tuor.getDateline());
		mblog.setType(MblogConst.TYPE_DEMAND);
		mblog.setSchoolId(0);
		String kinds = kindHelper.getKindLabel(String.valueOf(tuor.getKind1()));
		if (tuor.getKind2() != 0)
			kinds += "、"
					+ kindHelper.getKindLabel(String.valueOf(tuor.getKind2()));
		if (tuor.getKind3() != 0)
			kinds += "、"
					+ kindHelper.getKindLabel(String.valueOf(tuor.getKind3()));
		mblog.setContent("发布了辅导服务科目:<a>#" + kinds + "，价格" + tuor.getPrice()
				+ configHelper.getString("money", "uint", "点") + "#</a>");
		mblog.setUserId(tuor.getProvider());
		mblog.setMedia(String.valueOf(id));
		mblogService.post(mblog);
		return 1;
	}

	@Override
	public int postDemand(TutorDemand demand) {
		demand.setDateline(DateHelper.getNowTime());
		long id = dao.insert(demand);

		/* 发布动态 */
		Mblog mblog = new Mblog();
		mblog.setDateline(demand.getDateline());
		mblog.setType(MblogConst.TYPE_TUTOR);
		mblog.setSchoolId(0);
		String kinds = kindHelper
				.getKindLabel(String.valueOf(demand.getKind1()));
		if (demand.getKind2() != 0)
			kinds += "、"
					+ kindHelper
							.getKindLabel(String.valueOf(demand.getKind2()));
		if (demand.getKind3() != 0)
			kinds += "、"
					+ kindHelper
							.getKindLabel(String.valueOf(demand.getKind3()));
		mblog.setContent("发布了辅导需求科目:<a>#" + kinds + "，价格"
				+ getPrice(demand.getPrice()) + "#</a>");
		mblog.setUserId(demand.getUserId());
		mblog.setMedia(String.valueOf(id));
		mblogService.post(mblog);
		return 1;
	}

	private String getPrice(int p) {
		String price = "";
		switch (p) {
		case 0:
			price = "面议";
			break;
		case 1:
			price = "30" + configHelper.getString("money", "uint", "点") + "以下";
			break;
		case 2:
			price = "30" + configHelper.getString("money", "uint", "点") + "-60"
					+ configHelper.getString("money", "uint", "点");
			break;
		case 3:
			price = "60" + configHelper.getString("money", "uint", "点") + "-90"
					+ configHelper.getString("money", "uint", "点");
			break;
		case 4:
			price = "100" + configHelper.getString("money", "uint", "点") + "以上";
			break;
		}
		return price;
	}

	@Override
	public Page<Tutor> search(byte type, String condition, String keyword,
			int page, long size) {
		return dao.search(type, page, size);
	}

	@Override
	public long addCandidate(long userId, long candidate) {
		return dao.addCandidate(userId, candidate);
	}

	@Override
	public Page<User> findCandidate(long userId, int page, long size) {
		return dao.findCandidate(userId, page, size);
	}

	@Override
	public Page<TutorDemand> searchDemand(long userId, int page, long size) {
		return dao.searchDemand(userId, page, size);
	}

	@Override
	public Page<Tutor> searchTutor(long userId, int page, long size) {
		return dao.searchTutor(userId, page, size);
	}

	@Override
	public int deleteTutor(long tutorId) throws DataAccessException {
		return dao.deleteTutor(tutorId);
	}

	@Override
	public int deleteTutorDemand(long tutordemandid) {
		return dao.deleteTutorDemand(tutordemandid);
	}

	@Override
	public Tutor selectTutor(long id) {
		return dao.selectTutor(id);
	}

	@Override
	public TutorDemand selectTutorDemand(long id) {
		return dao.selectTutorDemand(id);
	}

	@Override
	public int updateTutor(long id, long kind1, long kind2, long kind3,
			int price, String intro, long userid) {
		dao.updateTutor(id, kind1, kind2, kind3, price, intro);

		/* 发布动态 */
		Mblog mblog = new Mblog();
		mblog.setDateline(DateHelper.getNowTime());
		mblog.setType(MblogConst.TYPE_TUTOR);
		mblog.setSchoolId(0);
		mblog.setContent("更新了辅导服务<a>#" + intro + "#</a>");
		mblog.setUserId(userid);
		mblog.setMedia(String.valueOf(id));
		mblogService.post(mblog);
		return 1;
	}

	@Override
	public int updateTutorDemand(long id, long kind1, long kind2, long kind3,
			int price, String intro, long userid) {
		dao.updateTutorDemand(id, kind1, kind2, kind3, price, intro);

		/* 发布动态 */
		Mblog mblog = new Mblog();
		mblog.setDateline(DateHelper.getNowTime());
		mblog.setType(MblogConst.TYPE_TUTOR);
		mblog.setSchoolId(0);
		mblog.setContent("更新了辅导服务<a>#" + intro + "#</a>");
		mblog.setUserId(userid);
		mblog.setMedia(String.valueOf(id));
		mblogService.post(mblog);
		return 1;
	}

	@Override
	public List<TutorDemand> guessDemand(String focus, long userId, long page,
			long size) {
		List<TutorDemand> list = dao.guessDemand(focus, userId, page, size);
		Iterator<TutorDemand> it = list.iterator();
		while (it.hasNext()) {
			TutorDemand demand = it.next();
			String memo = "";
			if (demand.getKind1() != 0)
				memo += kindHelper.getKindLabel(String.valueOf(demand
						.getKind1()));
			if (demand.getKind2() != 0)
				memo += "、"
						+ kindHelper.getKindLabel(String.valueOf(demand
								.getKind2()));
			if (demand.getKind3() != 0)
				memo += "、"
						+ kindHelper.getKindLabel(String.valueOf(demand
								.getKind3()));
			demand.setMemo(memo);
		}
		return list;
	}

}
