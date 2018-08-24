package search.util;

import java.text.NumberFormat;

public class NumberUtils {

	public static String getPercent(double num) {
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);
		return format.format(num);
	}
	
	public static int getIndexOfMax(double[] nums) {
		int indexOfMax = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > nums[indexOfMax]) {
				indexOfMax = i;
			}
		}
		return indexOfMax;
	}
	
}
