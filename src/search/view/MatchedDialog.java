package search.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import search.entity.Title;

public class MatchedDialog extends JSearchDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public MatchedDialog(String title, List<Title> contents) {
		setTitle(title);
		setSize(800, 650);
		
		StringBuffer sb = new StringBuffer("<table align=\"center\" style=\"font: 9px 微软雅黑;\"><tbody><tr><td width=\"40\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">序号</td><td width=\"320\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">原论文标题</td><td width=\"40\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">匹配条数</td><td width=\"320\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">在WOS中检索到的论文标题</td><td width=\"60\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">匹配度</td></tr>");
		for (int i = 0; i < contents.size(); i++) {
			Title originalTitle = contents.get(i);
			sb.append("<tr><td width=\"40\" valign=\"middle\" style=\"text-align: center;\">");
			sb.append(i + 1);
			sb.append("</td><td width=\"320\" valign=\"middle\" style=\"word-break: break-all;\">");
			sb.append(originalTitle.getContent());
			sb.append("</td><td width=\"40\" valign=\"middle\" style=\"word-break: break-all; text-align: center;\">");
			int matchedCount = originalTitle.getTitles().size();
			if (matchedCount > 1) {
				sb.append("<a href=\"" + i + "\">" + matchedCount + "</a>");
			} else {
				sb.append(matchedCount);
			}
			if (matchedCount > 0) {
				if (originalTitle.getTitles().get(0).getSimilarityRatio() < 0.9) {
					sb.append("</td><td width=\"320\" valign=\"middle\" style=\"word-break: break-all; color: red;\">");
				} else {
					sb.append("</td><td width=\"320\" valign=\"middle\" style=\"word-break: break-all;\">");
				}
				sb.append(originalTitle.getTitles().get(0).getContent());
				sb.append("</td><td width=\"60\" valign=\"middle\" style=\"text-align: center;\">");
				sb.append(originalTitle.getTitles().get(0).getSimilarityPercent());
			} else {
				sb.append("</td><td width=\"320\" valign=\"middle\" style=\"word-break: break-all; color: red; text-align: center;\">（无记录）</td><td width=\"60\" valign=\"middle\" style=\"text-align: center;\">");
			}
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
		// 超链接事件监听
		textPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
					return; // 如果不是鼠标点击则返回
				}
				String desc = e.getDescription();
				int index = Integer.parseInt(desc);
				MoreMatchDialog moreMatchDialog = new MoreMatchDialog(contents, index);
				moreMatchDialog.setCenterOfMainFrame(getWidth(), getHeight(), getX(), getY());
				moreMatchDialog.setVisible(true);
			}
		});
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setPreferredSize(new Dimension(Double.valueOf(getSize().getWidth()).intValue(),
				Double.valueOf(getSize().getHeight()).intValue()));
		scrollPane.setBorder(new EmptyBorder(0, 6, 40, 6));
		
		getContentPane().add(scrollPane);
	}
	
	

}
