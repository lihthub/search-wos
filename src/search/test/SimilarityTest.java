package search.test;

import search.util.SimilarityUtils;

public class SimilarityTest {

	public static void main(String[] args) {
		String str1 = "ATRX tolerates activity-dependent histone H3 methyl/phos switching to maintain repetitive element silencing in neurons";
		String str2 = "ATRX tolerates activity-dependent histone H3 methyl/phos switching to maintain repetitive element silencing in neurons (vol 112, pg 6820, 2014)";
		System.out.println(SimilarityUtils.getSimilarityRatio(str1, str2));
		System.out.println(SimilarityUtils.getSimilarityPercent(str1, str2));
		System.out.println();
		
		String str3 = "?Investigating the microstructures of piston carbon deposits in a large-scale marine diesel engine using synchrotron X-ray microtomography";
		String str4 = "Investigating the microstructures of piston carbon deposits in a large-scale marine diesel engine using synchrotron X-ray microtomography";
		System.out.println(SimilarityUtils.getSimilarityRatio(str3, str4));
		System.out.println(SimilarityUtils.getSimilarityPercent(str3, str4));
		System.out.println();
		
		String str5 = "Structural Principles in the Development of Cyclic Peptidic Enzyme Inhibitors";
		String str6 = "A Cyclic Peptidic Serine Protease Inhibitor: Increasing Affinity by Increasing Peptide Flexibility";
		System.out.println(SimilarityUtils.getSimilarityRatio(str5, str6));
		System.out.println(SimilarityUtils.getSimilarityPercent(str5, str6));
		System.out.println();
		
		String str7 = "A Cyclic Peptidic Serine Protease Inhibitor: Increasing Affinity by Increasing Peptide Flexibility";
		String str8 = "Structural Principles in the Development of Cyclic Peptidic Enzyme Inhibitors";
		System.out.println(SimilarityUtils.getSimilarityRatio(str7, str8));
		System.out.println(SimilarityUtils.getSimilarityPercent(str7, str8));
	}

}
