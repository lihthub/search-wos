package search.util;

import java.text.NumberFormat;

import info.debatty.java.stringsimilarity.Levenshtein;

public class SimilarityUtils {

	/**
	 * 相似度系数
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static double getSimilarityRatio(String str1, String str2) {
		Levenshtein levenshtein = new Levenshtein();
		double distance = levenshtein.distance(str1, str2);
		return 1 - distance / Math.max(str1.length(), str2.length());
	}
	
	/**
	 * 相似度百分数
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getSimilarityPercent(String str1, String str2) {
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);
		return format.format(getSimilarityRatio(str1, str2));
	}
	
}
