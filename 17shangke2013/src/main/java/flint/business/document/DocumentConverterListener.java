package flint.business.document;

import org.springframework.jdbc.core.JdbcTemplate;

import flint.util.SpringHelper;

import kiss.document.Document;
import kiss.document.listener.ConverterListener;
import kiss.util.Q;

public class DocumentConverterListener implements ConverterListener {

	private Document document;
	
	private JdbcTemplate jdbcTemplate = SpringHelper.getBean(JdbcTemplate.class);

	@Override
	public void prepare(Document document) {
		this.document = document;
	}

	@Override
	public Document get() {
		return document;
	}

	@Override
	public void monitor() {

	}

	@Override
	public void done() {

		/* 将元数据存储到数据库 */
		jdbcTemplate.update("update $FILE set metadata=? where ID=?",
				Q.toJSONString(document.getMetadata()), document.getId());
		/*更新文档转换状态*/
	}

	@Override
	public void exception(Throwable throwable) {
		/*将异常记入数据库*/
		jdbcTemplate.update("update $FILE set metadata=? ,memo=? where ID=?",
				"EXCEPTION",throwable.getMessage(),document.getId());
	}

}
