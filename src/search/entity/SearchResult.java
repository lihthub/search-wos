package search.entity;

import java.util.List;

public class SearchResult {
	// 页数
	private Integer pageCount;
	private String url;
	// 记录数
	private Integer count;
	// 页码
	private Integer page;
	private List<String> titles;

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	public List<String> getTitles() {
		return titles;
	}

}
