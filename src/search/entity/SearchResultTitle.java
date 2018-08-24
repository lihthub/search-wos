package search.entity;

import java.text.NumberFormat;

public class SearchResultTitle implements Comparable<SearchResultTitle> {
	private String content;
	private double similarityRatio;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getSimilarityRatio() {
		return similarityRatio;
	}

	public void setSimilarityRatio(double similarityRatio) {
		this.similarityRatio = similarityRatio;
	}
	
	public String getSimilarityPercent() {
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);
		return format.format(this.getSimilarityRatio());
	}

	@Override
	public int compareTo(SearchResultTitle obj) {
		if (this.getSimilarityRatio() > obj.getSimilarityRatio()) {
			return -1;
		} else if (this.getSimilarityRatio() == obj.getSimilarityRatio()) {
			return 0;
		} else {
			return 1;
		}
	}

}