package search.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import search.dao.SsflDao;
import search.dao.TitleDao;
import search.entity.SearchResult;
import search.entity.SearchResultTitle;
import search.entity.Title;
import search.exception.SearchException;

public class Utils {
	public static final String RUNMODE_DATABASE = "database";
	public static final String RUNMODE_NETWORK = "network";
	private static final int PAGESIZE = 15;
	
	public static Map<String, String>getSsflMapByNetwork() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = Jsoup.connect("http://lssf.cas.cn/cgjs_lb.jsp").get();
		Elements tags = doc.select(".nav1 .gueenbg dl dd a");
		for (Element tag : tags) {
			if ("全部".equals(tag.text())) {
				continue;
			}
			// javascript:document.forms[0].query_ssfl.value='ff8080814ff56599014ff5698fe50002';document.forms[0].submit();
			String onclickStr = tag.attr("onclick");
			Matcher matcher = Pattern.compile("(?<=query_ssfl\\.value=')\\S+(?=')").matcher(onclickStr);   
			if (matcher.find()) {
				map.put(matcher.group(), tag.text());
			}
		}
		return map;
	}
	
	public static Map<String, String> getSsflMapByDatabase() {
		SsflDao ssflDao = new SsflDao();
		if (ssflDao.isTableExist()) {
			return ssflDao.getSsflMap();
		}
		return new HashMap<String, String>();
	}
	
	public static Map<String, String> getSsflMap(String runMode) throws IOException {
		Map<String, String> map = null;
		if (RUNMODE_DATABASE.equals(runMode)) {
			map = getSsflMapByDatabase();
			if (map != null && map.size() > 0) {
				return map;
			}
		}
		return getSsflMapByNetwork();
	}

	public static int saveAllResult(JProgressBar progressBar, String runMode, File folder, String ssfl) throws IOException, SearchException {
		int totalCount = 0;
		int pageCount = 0;
		if (RUNMODE_DATABASE.equals(runMode)) {
			TitleDao titleDao = new TitleDao();
			if (titleDao.isTableExist() && (pageCount = titleDao.getPageCount(ssfl)) > 0) {
			} else {
				throw new SearchException("请先将数据导入到数据库！");
			}
		} else {
			Document doc = Jsoup.connect("http://lssf.cas.cn/cgjs_lb.jsp?query_ssfl=" + ssfl).get();
			Element pageBar = doc.selectFirst(".page .usrbar");
			String pageCountStr = pageBar.text().replaceAll("^[\\S\\s]*共(\\d+)页[\\S\\s]*$", "$1");
			pageCount = Integer.parseInt(pageCountStr == null || pageCountStr.isEmpty() ? "0" : pageCountStr);
			if (pageCount == 0) {
				throw new SearchException("没查到任何记录！");
			}
		}
		progressBar.setVisible(true);
		for (int i = 1; i <= pageCount; i++) {
			progressBar.setValue(Double.valueOf(Math.ceil(100.0 / pageCount * i)).intValue());
			progressBar.setString("第" + i + "页");
			int count = saveOneResult(runMode, folder, ssfl, i);
			totalCount += count;
		}
		progressBar.setString("导出成功");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progressBar.setVisible(false);
		return totalCount;
	}
	
	public static int saveOneResult(String runMode, File folder, String ssfl, int page) throws IOException, SearchException {
		List<String> titles = new ArrayList<String>();
		if (RUNMODE_DATABASE.equals(runMode)) {
			TitleDao titleDao = new TitleDao();
			if (titleDao.isTableExist() && (titleDao.getPageCount(ssfl)) > 0) {
				List<Title> titleList = titleDao.getTitles(page, ssfl);
				for (Title titleObj : titleList) {
					titles.add(titleObj.getContent());
				}
			} else {
				throw new SearchException("请先将数据导入到数据库！");
			}
		} else {
			Document doc = Jsoup.connect("http://lssf.cas.cn/cgjs_lb.jsp?query_ssfl=" + ssfl + "&pagenum=" + page).get();
			Elements newsHeadlines = doc.select(".list_wrap .list h3 a");
			if (newsHeadlines != null) {
				for (Element headline : newsHeadlines) {
					System.out.format("%s\n%s\n\n", headline.text(), headline.absUrl("href"));
					titles.add(headline.text());
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer expressSb = new StringBuffer();
		for (int i = 0; i < titles.size(); i++) {
			String text = titles.get(i)
					.replaceAll("[^A-Za-z0-9_\\-\\u4e00-\\u9fa5]|(?:(?:\\u4E2D\\u6587|\\u82F1\\u6587)\\u9898\\u540D)", " ")
					.trim().replaceAll("\\s+", " ");
			sb.append(text + "\n\n");
			text = text.replaceAll("((?<=\\s+)(?:and|And|AND|or|Or|OR|not|Not|NOT|near|Near|NEAR|same|Same|SAME)(?=\\s+))", "\"$1\"");
			if (i == titles.size() - 1) {
				expressSb.append("TS=(" + text + ")");
			} else {
				expressSb.append("TS=(" + text + ") OR ");
			}
		}
		if (!folder.exists()) {
			folder.mkdirs();
		}
		FileWriter writer = null;
		FileWriter expressWriter = null;
		File file = new File(folder, "第" + page + "页.txt");
		File expressFile = new File(folder, "第" + page + "页_表达式.txt");
		try {
			writer = new FileWriter(file);
			expressWriter = new FileWriter(expressFile);
			char[] chars = sb.toString().toCharArray();
			char[] expressChars = expressSb.toString().toCharArray();
			writer.write(chars);
			expressWriter.write(expressChars);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (expressWriter != null) {
				try {
					expressWriter.flush();
					expressWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return titles.size();
	}
	
	public static SearchResult search(String runMode, String sid, String ssfl, int page) throws IOException, SearchException {
		int pageCount = 0;
		List<String> titles = new ArrayList<String>();
		if (RUNMODE_DATABASE.equals(runMode)) {
			TitleDao titleDao = new TitleDao();
			if (titleDao.isTableExist() && (pageCount = titleDao.getPageCount(ssfl)) > 0) {
				List<Title> titleList = titleDao.getTitles(page, ssfl);
				for (Title titleObj : titleList) {
					titles.add(titleObj.getContent());
				}
			} else {
				throw new SearchException("请先将数据导入到数据库！");
			}
		} else {
			Document doc = Jsoup.connect("http://lssf.cas.cn/cgjs_lb.jsp?query_ssfl=" + ssfl + "&pagenum=" + page).get();
			Element pageBar = doc.selectFirst(".page .usrbar");
			String pageCountStr = pageBar.text().replaceAll("^[\\S\\s]*共(\\d+)页[\\S\\s]*$", "$1");
			pageCount = Integer.parseInt(pageCountStr == null || pageCountStr.isEmpty() ? "0" : pageCountStr);
			Elements newsHeadlines = doc.select(".list_wrap .list h3 a");
			if (newsHeadlines != null) {
				for (Element headline : newsHeadlines) {
					System.out.format("%s\n%s\n\n", headline.text(), headline.absUrl("href"));
					titles.add(headline.text());
				}
			}
		}
		StringBuffer expressSb = new StringBuffer();
		for (int i = 0; i < titles.size(); i++) {
			String text = titles.get(i).replaceAll("[^A-Za-z0-9_\\-\\u4e00-\\u9fa5]|(?:(?:\\u4E2D\\u6587|\\u82F1\\u6587)\\u9898\\u540D)", " ")
					.replaceAll("((?<=\\s+)(?:and|And|AND|or|Or|OR|not|Not|NOT|near|Near|NEAR|same|Same|SAME)(?=\\s+))", "\"$1\"")
					.trim().replaceAll("\\s+", " ");
			if (i == titles.size() - 1) {
				expressSb.append("TS=(" + text + ")");
			} else {
				expressSb.append("TS=(" + text + ") OR ");
			}
		}
		SearchResult result = new SearchResult();
		String url = "http://apps.webofknowledge.com/WOS_AdvancedSearch.do?product=WOS&search_mode=AdvancedSearch&action=search&SID="
				+ sid + "&value(input1)=" + URLEncoder.encode(expressSb.toString(), "utf-8");
		System.out.println(url);
		result.setUrl(url);
		result.setPageCount(pageCount);
		result.setCount(titles.size());
		result.setPage(page);
		result.setTitles(titles);
		return result;
	}
	
	public static SearchResult getSearchResultInWOS(String advancedSearchUrl) throws IOException, SearchException {
		int pageCount = 1;
		String searchResultUrl1 = null;
		List<String> titles = new ArrayList<String>();
		Document doc = Jsoup.connect(advancedSearchUrl).get();
		Element searchErrorMessage = doc.selectFirst("#searchErrorMessage");
		Element noRecordsDiv = doc.selectFirst("#noRecordsDiv");
		if (searchErrorMessage != null) {
			Element clientErrorInputMessage = searchErrorMessage.selectFirst("#client_error_input_message");
			String message = clientErrorInputMessage == null ? "检索错误！" : clientErrorInputMessage.text();
			throw new SearchException("WOS网站报错：" + message);
		}
		if (noRecordsDiv != null) {
			Element newErrorHead = noRecordsDiv.selectFirst(".newErrorHead");
			String message = newErrorHead == null ? "检索后没有发现记录。" : newErrorHead.text();
			throw new SearchException("WOS: " + message);
		}
		Elements trs = doc.select(".block-history form table tbody tr");
		if (trs != null && trs.size() >= 3) {
			String linkDivId = trs.get(2).attr("id").replaceAll("row", "div");
			Element searchLink = trs.get(2).selectFirst("#" + linkDivId + " a");
			int searchResultsCount = Integer.parseInt(searchLink.text().replaceAll(",", ""));
			if (searchResultsCount > 0) {
				List<Document> searchResultsDocList = new ArrayList<Document>();
				int page = 1;
				do {
					String searchResultUrl = searchLink.absUrl("href") + "&page=" + page + "&action=changePageSize&pageSize=50";
					Document searchResultDoc = Jsoup.connect(searchResultUrl).get();
					searchResultsDocList.add(searchResultDoc);
					if (page == 1) {
						Element pageCountBottom = searchResultDoc.selectFirst("[id=\"pageCount.bottom\"]");
						pageCount = pageCountBottom == null || pageCountBottom.text().isEmpty() ? 0
								: Integer.parseInt(pageCountBottom.text().replaceAll(",", ""));
						searchResultUrl1 = searchResultUrl;
					}
					page++;
				} while(page <= pageCount);
				for (int i = 1; i <= searchResultsCount; i++) {
					Document searchResultsDoc = searchResultsDocList.get(0);
					if (i % 50 == 0) {
						searchResultsDoc = searchResultsDocList.get(i / 50 - 1);
					} else {
						searchResultsDoc = searchResultsDocList.get(i / 50);
					}
					// #records_chunks .search-results .search-results-item
					Element searchResultsItem = searchResultsDoc.selectFirst("#RECORD_" + i);
					Element itemValue = searchResultsItem.selectFirst(".search-results-content value[lang_id]");
					titles.add(itemValue.html().replaceAll("<span class=\"hitHilite\">|</span>|\r\n|\n|\r", ""));
				}
			}
		}
		SearchResult result = new SearchResult();
		result.setTitles(titles);
		result.setPageCount(pageCount);
		result.setUrl(searchResultUrl1);
		result.setCount(titles.size());
		return result;
	}
	
	public static List<Title> getContrastTitleList(List<String> titles1, List<String> titles2) {
		List<Title> titles = new ArrayList<Title>();
		for (String title1 : titles1) {
			Title originalTitle = new Title();
			originalTitle.setContent(title1);
			titles.add(originalTitle);
		}
		for (String title2 : titles2) {
			double[] similarityRatios = new double[titles.size()];
			for (int i = 0; i < titles1.size(); i++) {
				double similarityRatio = SimilarityUtils.getSimilarityRatio(title2, titles1.get(i));
				similarityRatios[i] = similarityRatio;
			}
			int indexOfMax = NumberUtils.getIndexOfMax(similarityRatios);
			SearchResultTitle searchResultTitle = new SearchResultTitle();
			searchResultTitle.setContent(title2);
			searchResultTitle.setSimilarityRatio(similarityRatios[indexOfMax]);
			titles.get(indexOfMax).getTitles().add(searchResultTitle);
		}
		for (Title title : titles) {
			Collections.sort(title.getTitles());
		}
		return titles;
	}
	
	public static int loadDataToDatabase(JProgressBar progressBar, String ssfl) throws IOException {
		progressBar.setString("正在导入");
		SsflDao ssflDao = new SsflDao();
		if (!ssflDao.isTableExist()) {
			ssflDao.createTable();
		}
		Map<String, String> ssflMap = getSsflMapByNetwork();
		for (String key : ssflMap.keySet()) {
			if (!ssflDao.isExist(key)) {
				ssflDao.addSsfl(ssflMap.get(key), key);
			}
		}
		TitleDao titleDao = new TitleDao();
		if (!titleDao.isTableExist()) {
			titleDao.createTable();
		}
		int page = 1;
		int pageCount = 0;
		int totalCount = 0;
		do {
			Document doc = Jsoup.connect("http://lssf.cas.cn/cgjs_lb.jsp?query_ssfl=" + ssfl + "&pagenum=" + page).get();
			if (page == 1) {
				Element pageBar = doc.selectFirst(".page .usrbar");
				String pageCountStr = pageBar.text().replaceAll("^[\\S\\s]*共(\\d+)页[\\S\\s]*$", "$1");
				pageCount = Integer.parseInt(pageCountStr == null || pageCountStr.isEmpty() ? "0" : pageCountStr);
				progressBar.setVisible(true);
			}
			Elements newsHeadlines = doc.select(".list_wrap .list h3 a");
			if (newsHeadlines != null) {
				progressBar.setValue(Double.valueOf(Math.ceil(100.0 / pageCount * page)).intValue());
				progressBar.setString("第" + page + "页");
				for (int i = 0; i < newsHeadlines.size(); i++) {
					Element headline = newsHeadlines.get(i);
					int serialNo = PAGESIZE * (page - 1) + i + 1;
					Title getTitle = titleDao.getTitle(serialNo, headline.text(), ssfl);
					if (getTitle == null || getTitle.getId() == null) {
						Title title = new Title();
						title.setContent(headline.text());
						title.setPage(page);
						title.setSerialNo(serialNo);
						title.setSsfl(ssfl);
						titleDao.addTitle(title);
						totalCount++;
					}
				}
			}
			page++;
		} while (page <= pageCount);
		if (totalCount > 0) {
			progressBar.setString("导入成功");
		} else {
			progressBar.setString("导入失败");
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progressBar.setVisible(false);
		return totalCount;
	}
	
}
