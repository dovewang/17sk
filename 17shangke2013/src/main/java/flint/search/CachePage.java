package flint.search;

import java.io.Serializable;
import java.util.List;

public class CachePage implements Serializable {
	/* 命中的记录总数 */
	private int totalCount;

	/* 全部结果集 */
	private List result;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}
}
