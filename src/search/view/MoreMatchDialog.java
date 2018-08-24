package search.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import search.entity.SearchResultTitle;
import search.entity.Title;

public class MoreMatchDialog extends JSearchDialog {
	private static final long serialVersionUID = 1L;

	public MoreMatchDialog(List<Title> contents, int index) {
		setTitle("更多匹配");
		setSize(500, 500);
		
		StringBuffer sb = new StringBuffer("<div style=\"font: 9px 微软雅黑; word-break: break-all; margin:10px 10px 10px 10px;\">");
		sb.append(contents.get(index).getContent());
		sb.append("</div><table align=\"center\" style=\"font: 9px 微软雅黑;\"><tbody><tr><td width=\"40\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">序号</td><td width=\"400\" valign=\"top\" style=\"text-align: center; word-break: break-all; font-weight: bold;\">在WOS中检索到的论文标题</td><td width=\"60\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">匹配度</td></tr>");
		List<SearchResultTitle> searchResults = contents.get(index).getTitles();
		for (int i = 0; i < searchResults.size(); i++) {
			sb.append("<tr><td width=\"40\" valign=\"top\" style=\"text-align: center;\">");
			sb.append(i + 1);
			if (searchResults.get(i).getSimilarityRatio() < 0.9) {
				sb.append("</td><td width=\"400\" valign=\"top\" style=\"word-break: break-all; color: red;\">");
			} else {
				sb.append("</td><td width=\"400\" valign=\"top\" style=\"word-break: break-all;\">");
			}
			sb.append(searchResults.get(i).getContent());
			sb.append("</td><td width=\"60\" valign=\"top\" style=\"text-align: center;\">");
			sb.append(searchResults.get(i).getSimilarityPercent());
			sb.append("</td></tr>");
		}
		sb.append("</tbody></table>");
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		textPane.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		textPane.setContentType("text/html");
		textPane.setText(sb.toString());
		textPane.setAutoscrolls(true);
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setPreferredSize(new Dimension(Double.valueOf(getSize().getWidth()).intValue(),
				Double.valueOf(getSize().getHeight()).intValue()));
		scrollPane.setBorder(new EmptyBorder(0, 5, 40, 5));
		
		getContentPane().add(scrollPane);
	}
	
	

}
