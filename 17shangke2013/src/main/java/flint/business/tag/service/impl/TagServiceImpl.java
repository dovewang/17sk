package flint.business.tag.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Tag;
import flint.base.BaseServiceImpl;
import flint.business.tag.dao.TagDAO;
import flint.business.tag.service.TagService;

@Service
public class TagServiceImpl extends BaseServiceImpl implements TagService {

	@Autowired
	private TagDAO dao;

	@Override
	public void save(String tag, long objectId, byte group) {
		dao.save(tag, objectId, group);
	}

	@Override
	public void drop(long objectId, byte group) {
		dao.drop(objectId, group);
	}

	@Override
	public void delete(String tag) {
		dao.delete(tag);
	}

	@Override
	public void delete(long tagId) {
		dao.delete(tagId);
	}

	@Override
	public List<Tag> get(byte group) {
		return dao.get(group);
	}

	@Override
	public List<Tag> get(long objectId, byte group, boolean sameGroup) {
		return dao.get(objectId, group, sameGroup);
	}

	@Override
	public List<Tag> get(long objectId, byte group) {
		return get(objectId, group, true);
	}

	@Override
	public List<Tag> getTop(byte group, long n) {
		return dao.getTop(group, n);
	}

}
