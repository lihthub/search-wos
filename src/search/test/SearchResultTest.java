package search.test;

import java.util.ArrayList;
import java.util.List;

import search.entity.SearchResult;

public class SearchResultTest {

	public static SearchResult[] getData() {
		SearchResult result = new SearchResult();
		result.setPageCount(120);
		result.setCount(12);
		result.setPage(3);
		List<String> titles = new ArrayList<String>();
		titles.add("2011百度世界大会李彦宏演讲辞");
		titles.add("2012年（中国互联网大会）马化腾_雷军发言稿？");
		titles.add("阿里巴巴10周年庆典马云演讲全文实录");
		titles.add("百度CEO李彦宏在（北大毕业）典礼？上的演讲稿");
		titles.add("李开复演讲稿");
		titles.add("李彦宏2012百度年会#演讲全文：相信技术？的力量");
		titles.add("李彦宏2013百度年会演讲稿");
		titles.add("李彦宏技术创新撬动“亚洲新时代");
		titles.add("李彦宏南开演讲");
		titles.add("李彦宏浙大演讲实录：优秀年轻人才应进入企业(全文)");
		titles.add("马云经典演讲稿");
		titles.add("马云演讲全文：我的三个残酷决定");
		titles.add("HeterovalentDopingEnabled Ef  cient Dopant Luminescence and Controllable Electronic Impurity Via a New Strategy of Preparing II VI Nanocrystals");
		titles.add("Solution-Processable, Low-Voltage, and High-Performance Monolayer Field-Effect Transistors with Aqueous Stability and High Sensitivity");
		titles.add("Synthesis, structures and dehydrogenation of magnesium borohydrideeethylenediamine composites,");
		titles.add("\"Anion clamp\" allows ?flexible protein to impose ?coordination geometry on ?metal ions");
		titles.add(" Investigating the microstructures of piston carbon deposits in a large-scale marine diesel engine using synchrotron X-ray microtomography");
		titles.add("A Covalent Approach for Site-Specific ##RNA Labeling in Mammalian Cells");
		titles.add("A Cyclic Peptidic Serine Protease Inhibitor: Increasing Affinity by Increasing Peptide Flexibility");
		titles.add("A Highly Active and Stable Non-PlatinicLeanNOxTrapCatalyst MnOx K2CO3/K2Ti8O17 with Ultra-Low NOx to N2O Selectivity");
		titles.add("A Novel Tyrosine-Heme C-O-Covalent Linkage in F43Y Myoglobin: A New Post-translational Modification of Heme Proteins");
		titles.add("A comparative study in structure and reactivity of ¡°FeOx-on-Pt¡± and ¡°NiOx-on-Pt¡± catalysts");
		titles.add("A lithiation/delithiation ?mechanism of monodispersed MSn5 (M ?Fe, Co and FeCo) nanospheres");
		titles.add("A series of ceria ?supported lean-burn NOx trap catalysts LaCoO3/K2CO3/CeO2 using perovskite as active component");
		result.setTitles(titles);
		
		SearchResult result2 = new SearchResult();
		String url = "http://apps.webofknowledge.com/WOS_AdvancedSearch.do?product=WOS&search_mode=AdvancedSearch&action=search&SID=1&value(input1)=";
		result2.setUrl(url);
		result2.setPageCount(120);
		result2.setCount(15);
		List<String> titles2 = new ArrayList<String>();
		titles2.add("2011百度世界大会李彦宏");
		titles2.add("2012年中国互联网大会马化腾_雷军发言稿");
		titles2.add("阿里周年庆典马云演讲全文实录");
		titles2.add("百度CEO李彦宏在北大毕业典礼上的演讲稿");
		titles2.add("李开复和马云一起吃饭");
		titles2.add("李彦宏2012百度年会演讲全文：相信技术的力量");
		titles2.add("李彦宏2013百度年会演讲稿");
		titles2.add("李彦宏技术创新撬动“亚洲新时代");
		titles2.add("李彦宏南开演讲");
		titles2.add("李彦宏浙大演讲实录：优秀年轻人才应进入企业(全文)");
		titles2.add("马云演讲全文：我的三个残酷决定");
		titles2.add("马云演讲全文：我的三个残酷决定，知识改变命运");
		titles2.add("Heterovalent-Doping-Enabled Efcient Dopant Luminescence and Controllable Electronic Impurity Via a New Strategy of Preparing II VI Nanocrystals");
		titles2.add("Solution-Processable, Low-Voltage, and High-Performance Monolayer Field-Effect Transistors with Aqueous Stability and High Sensitivity");
		titles2.add("Synthesis, structures and dehydrogenation of magnesium borohydrideeethylenediamine composites,");
		titles2.add("\"Anion clamp\" allows flexible protein to impose coordination geometry on metal ions");
		titles2.add(" Investigating the microstructures of piston carbon deposits in a large-scale marine diesel engine using synchrotron X-ray microtomography");
		titles2.add("A Covalent Approach for Site-Specific RNA Labeling in Mammalian Cells");
		titles2.add("A Cyclic Peptidic Serine Protease Inhibitor: Increasing Affinity by Increasing Peptide Flexibility");
		titles2.add("A Highly Active and Stable Non-Platinic Lean NOx Trap Catalyst MnOx K2CO3/K2Ti8O17 with Ultra-Low NOx to N2O Selectivity");
		titles2.add("A Novel Tyrosine-Heme C-O Covalent Linkage in F43Y Myoglobin: A New Post-translational Modification of Heme Proteins");
		titles2.add("A comparative study in structure and reactivity of ¡°FeOx-on-Pt¡± and ¡°NiOx-on-Pt¡± catalysts");
		titles2.add("A lithiation/delithiation mechanism of monodispersed MSn5 (M   Fe, Co and FeCo) nanospheres");
		titles2.add("A series of ceria supported lean-burn NOx trap catalysts LaCoO3/K2CO3/CeO2 using perovskite as active component");
		result2.setTitles(titles2);
		
		SearchResult[] results = { result, result2 };
		return results;
	}
	
}
