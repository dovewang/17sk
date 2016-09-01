package flint.business.school.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.School;
import entity.SchoolAddress;
import flint.base.BaseServiceImpl;
import flint.business.constant.SchoolConst;
import flint.business.constant.UserConst;
import flint.business.school.dao.SchoolDAO;
import flint.business.school.service.SchoolService;
import flint.common.Page;
import flint.context.RegionHelper;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;

@Service
public class SchoolServiceImpl extends BaseServiceImpl implements SchoolService {

	@Autowired
	private SchoolDAO dao;

	@Autowired
	private RegionHelper regionHelper;

	@Override
	public School findSchoolByDomain(String domain) {
		return dao.findSchoolByDomain(domain);
	}

	@Override
	public Page<Map<String, Object>> findClass(long schooIdl, long page,
			int size) {
		return dao.findClass(schooIdl, page, size);
	}

	@Override
	public int updateUser(long userid, String username, String tel, String id) {
		return dao.updateUser(userid, username, tel, id);
	}

	@Override
	public int addSchoolUser(long userid, String name, String email,
			String tel, byte type, String id, long schoolid, long classid,
			String activeCode) throws ApplicationException {
		boolean flay = false;
		/* 用户是否存在主站 */
		Map<String, Object> user = dao.findUserByEmail(email);
		if (user != null) {
			userid = NumberHelper.toLong(String.valueOf(user.get("user_id")));
			tel = String.valueOf(user.get("tel"));
			flay = true;
		}
		/* 判断用户在同一个班下是否存在 */
		if (classid == 0 && dao.findSchoolMember(schoolid, userid) != null) {// 添加的是未分组用户
			throw new ApplicationException("SCHOOL_GROUP_USER_EXIST");
		}
		if (dao.checkClassUser(schoolid, classid, userid)) {
			throw new ApplicationException("SCHOOL_GROUP_USER_EXIST");
		}
		return dao.addSchoolUser(userid, name, email, tel, type, id, schoolid,
				classid, flay, activeCode);
	}

	@Override
	public Map<String, Object> getClassUser(long classId, long userId) {
		return dao.getClassUser(classId, userId);
	}

	@Override
	public Map<String, Object> getClassName(long classId) {
		return dao.getClassName(classId);
	}

	@Override
	public int lockUser(long schoolId, long userId) {
		return dao.lockUser(schoolId, userId);
	}

	@Override
	public int unlockUser(long schoolId, long userId) {
		return dao.unlockUser(schoolId, userId);
	}

	@Override
	public int deleteUser(long classId, long userId) {
		return dao.deleteUser(classId, userId);
	}

	@Override
	public Page<Map<String, Object>> search(long schoolId, long classId,
			byte type, long page, long size) {
		if (classId == 0) {// 获取未分组的成员
			return dao.searchUserUnGroup(schoolId, type, page, size);
		}
		return dao.search(schoolId, classId, type, page, size);
	}

	@Override
	public String checkEmail(String email, long schoolId) {
		return dao.checkEmail(email, schoolId);
	}

	@Override
	public Page<School> list(long page, long size) {
		return dao.list(page, size);
	}

	@Override
	public School get(long schoolId) {
		return dao.findById(schoolId);
	}

	@Override
	public long getSchoolIdByWebmaster(long webmaster) {
		return dao.getSchoolIdByWebmaster(webmaster);
	}

	@Override
	public int create(School school, String activeCode) throws Exception {
		long schoolId = dao.insert(school);
		/* 自动生成管理员账号 */
		return addSchoolUser((long) 0, school.getName(), school.getEmail(),
				String.valueOf(school.getTel()), UserConst.ENTERPRISE, null,
				schoolId, 0, activeCode);
	}

	@Override
	public int update(School school) {
		/* 更新数据后需要将学校的站点信息重新载入内存 */
		return dao.update(school);
	}

	@Override
	public List<SchoolAddress> findAddress(long schooId) {
		return dao.findAddress(schooId);
	}

	@Override
	public long saveAddress(SchoolAddress address) {
		if (address.getId() != 0) {
			if (dao.update(address) > 0) {
				return address.getId();
			}
			return 0;
		}
		return dao.insert(address);
	}

	@Override
	public int deleteAddress(long id) {
		return dao.deleteAddress(id);
	}

	@Override
	public SchoolAddress getAddress(long id) {
		return dao.getAddress(id);
	}

	@Override
	public String getAddressFullText(long id) {
		SchoolAddress address = getAddress(id);
		if (address != null)
			return regionHelper.getFullName(address.getArea()) + "  "
					+ address.getMain() + "-" + address.getBranch() + "("
					+ address.getContact() + "/" + address.getPhone() + ")";
		return "";
	}

	@Override
	public String getAddressShortText(long id) {
		SchoolAddress address = getAddress(id);
		if (address != null)
			return address.getMain() + "-" + address.getBranch();
		return "";
	}

	@Override
	public int setRole(long classid, long userid, byte role) {
		return dao.setRole(classid, userid, role);
	}

	@Override
	public int frozen(String domain) {
		return dao.updataSchoolStatus(domain, SchoolConst.SCHOOL_FROZEN);
	}

	@Override
	public int unFrozen(String domain) {
		return dao.updataSchoolStatus(domain, SchoolConst.SCHOOL_STATUS);
	}

	@Override
	public int verifySchoolPass(long schoolId, String memo) {
		return dao.verifySchool(schoolId, memo, SchoolConst.SCHOOL_STATUS);
	}

	@Override
	public int verifySchoolNotPass(long schoolId, String memo) {
		return dao.verifySchool(schoolId, memo,
				SchoolConst.SCHOOL_VERIFY_NOT_PASS);
	}

	@Override
	public String timetable(long schoolId) {
		return dao.timetable(schoolId);
	}

	@Override
	public int saveTimetable(long schoolId, boolean isNew, String timetable) {
		return dao.saveTimetable(schoolId, isNew, timetable);
	}

}
