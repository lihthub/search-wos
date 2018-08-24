package search.entity;

import java.util.ArrayList;
import java.util.List;

public class Title {
	private Integer id;
	private Integer serialNo;
	private String content;
	private Integer page;
	private String ssfl;
	private List<SearchResultTitle> titles = new ArrayList<SearchResultTitle>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<SearchResultTitle> getTitles() {
		return titles;
	}

	public void setTitles(List<SearchResultTitle> titles) {
		this.titles = titles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSsfl() {
		return ssfl;
	}

	public void setSsfl(String ssfl) {
		this.ssfl = ssfl;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	
}
