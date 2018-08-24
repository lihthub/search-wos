package search.test;

import info.debatty.java.stringsimilarity.CharacterSubstitutionInterface;
import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.OptimalStringAlignment;
import info.debatty.java.stringsimilarity.QGram;
import info.debatty.java.stringsimilarity.SorensenDice;
import info.debatty.java.stringsimilarity.WeightedLevenshtein;

public class SimilarityExamples {

	public static void main(String[] args) {
		System.out.println("\nLevenshtein");
		Levenshtein levenshtein = new Levenshtein();
		System.out.println(levenshtein.distance("My string", "My $tring"));
		System.out.println(levenshtein.distance("My string", "M string2"));
		System.out.println(levenshtein.distance("My string", "My $tring"));
		
		System.out.println("\nJaccard");
		Jaccard j2 = new Jaccard(2);
		System.out.println(j2.similarity("ABCDE", "ABCDF"));
		
		System.out.println("\nJaro-Winkler");
		JaroWinkler jw = new JaroWinkler();
		System.out.println(jw.similarity("My string", "My tsring"));
		System.out.println(jw.similarity("My string", "My ntrisg"));
		
		System.out.println("\nCosine");
		Cosine cos = new Cosine(3);
		System.out.println(cos.similarity("ABC", "ABCE"));
		cos = new Cosine(2);
		System.out.println(cos.similarity("ABAB", "BAB"));
		
		System.out.println("\nDamerau");
		Damerau damerau = new Damerau();
		System.out.println(damerau.distance("ABCDEF", "ABDCEF"));
		System.out.println(damerau.distance("ABCDEF", "BACDFE"));
		System.out.println(damerau.distance("ABCDEF", "ABCDE"));
		System.out.println(damerau.distance("ABCDEF", "BCDEF"));
		System.out.println(damerau.distance("ABCDEF", "ABCGDEF"));
		System.out.println(damerau.distance("ABCDEF", "POIU"));
		
		System.out.println("\nOptimal String Alignment");
		OptimalStringAlignment osa = new OptimalStringAlignment();
		System.out.println(osa.distance("CA", "ABC"));
		
		System.out.println("\nLongest Common Subsequence");
		LongestCommonSubsequence lcs = new LongestCommonSubsequence();
		System.out.println(lcs.distance("AGCAT", "GAC"));
		System.out.println(lcs.distance("AGCAT", "AGCT"));
		
		System.out.println("\nNGram");
		NGram twogram = new NGram(2);
		System.out.println(twogram.distance("ABCD", "ABTUIO"));
		String s1 = "Adobe CreativeSuite 5 Master Collection from cheap 4zp";
		String s2 = "Adobe CreativeSuite 5 Master Collection from cheap d1x";
		NGram ngram = new NGram(4);
		System.out.println(ngram.distance(s1, s2));
		
		System.out.println("\nNormalized Levenshtein");
		NormalizedLevenshtein l = new NormalizedLevenshtein();
		System.out.println(l.distance("My string", "My $tring"));
		System.out.println(l.distance("My string", "M string2"));
		System.out.println(l.distance("My string", "abcd"));
		
		System.out.println("\nQGram");
		QGram dig = new QGram(2);
		System.out.println(dig.distance("ABCD", "ABCE"));
		System.out.println(dig.distance("", "QSDFGHJKLM"));
		System.out.println(dig.distance("Best Deal Ever! Viagra50/100mg - $1.85 071",
				"Best Deal Ever! Viagra50/100mg - $1.85 7z3"));
		
		System.out.println("\nSorensen-Dice");
		SorensenDice sd = new SorensenDice(2);
		System.out.println(sd.similarity("ABCDE", "ABCDFG"));
		
		System.out.println("\nWeighted Levenshtein");
		WeightedLevenshtein wl = new WeightedLevenshtein(new CharacterSubstitutionInterface() {

			public double cost(char c1, char c2) {
				return c1 != 't' || c2 != 'r' ? 1.0D : 0.5D;
			}

		});
		System.out.println(wl.distance("String1", "Srring2"));
		
		System.out.println("\nK-Shingling");
		s1 = "my string,  \n  my song";
		s2 = "another string, from a song";
		Cosine cosine = new Cosine(4);
		System.out.println(cosine.getProfile(s1));
		System.out.println(cosine.getProfile(s2));
		cosine = new Cosine(2);
		System.out.println(cosine.getProfile("ABCAB"));
	}

}
