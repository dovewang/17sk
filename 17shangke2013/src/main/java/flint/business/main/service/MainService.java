package flint.business.main.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flint.common.Page;
import flint.jdbc.JdbcDaoSupport;
import flint.util.DateHelper;

@Service
public class MainService {

	@Autowired
	private JdbcDaoSupport jdbcDaoSupport;

	public void suggest(String content, long userId) {
		jdbcDaoSupport
				.update("insert into TB_SUGGEST(USER_ID,STATUS,CONTENT,DATELINE) values(?,0,?,?)",
						userId, content, DateHelper.getNowTime());
	}

	public Page<Map<String, Object>> findCover(long category, long page, int size) {
		if (category == -1)
			return jdbcDaoSupport.findPageForMap("select * from TB_COVER ",
					page, size);
		else
			return jdbcDaoSupport.findPageForMap("select * from TB_COVER where category=? ",
					page, size,category);
	}
}
