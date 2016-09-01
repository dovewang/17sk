package flint.business.abc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flint.base.BaseServiceImpl;
import flint.business.abc.dao.AbcDAO;
import flint.business.abc.service.VideoService;

@Service
public class VideoServiceImpl extends BaseServiceImpl implements VideoService {

	@Autowired
	public AbcDAO dao;

	@Override
	public String getRecord(long id) {
		return dao.getRecord(id);
	}

	@Override
	public String getConfig(long id) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		sb.append("<data><url>");
		sb.append("<beurl>").append("/player/resource/").append("</beurl>");
		sb.append("<video>").append(
				configHelper.getString("fms", "video.http", "") + "/video/");
		sb.append(id).append("/teacher" + id + ".flv").append("</video>");
		sb.append("</url>");
		sb.append("<replay time=\"" + dao.getRecordTime(id) + "\">");
		sb.append("<item id=\"1\" url=\"/v/data" + id
				+ ".html\" /></replay></data>");
		return sb.toString();
	}

}
