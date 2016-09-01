package flint.business.core;

public class Searcher {
	
	private boolean fullText;
	private String where;
	private String order;
	private String group;
	private Object[] paramters;

	public boolean isFullText() {
		return fullText;
	}

	public void setFullText(boolean fullText) {
		this.fullText = fullText;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Object[] getParamters() {
		return paramters;
	}

	public void setParamters(Object[] paramters) {
		this.paramters = paramters;
	}
}
