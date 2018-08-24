package search.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import search.entity.Title;

public class NotMatchDialog extends JSearchDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public NotMatchDialog(List<Title> titles) {
		setTitle("未匹配的论文题目");
		setSize(500, 450);
		
		int no = 0;
		StringBuffer sb = new StringBuffer("<table align=\"center\" style=\"font: 9px 微软雅黑;\"><tbody><tr><td width=\"40\" valign=\"top\" style=\"text-align: center; font-weight: bold;\">序号</td><td width=\"440\" valign=\"top\" style=\"text-align: center; word-break: break-all; font-weight: bold;\">论文标题</td></tr>");
		for (Title title : titles) {
			if (title.getTitles().size() == 0) {
				no++;
				sb.append("<tr><td width=\"40\" valign=\"top\" style=\"text-align: center;\">");
				sb.append(no);
				sb.append("</td><td width=\"440\" valign=\"top\" style=\"word-break: break-all;\">");
				sb.append(title.getContent());
				sb.append("</td></tr>");
			}
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
