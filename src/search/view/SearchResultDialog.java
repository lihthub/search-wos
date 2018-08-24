package search.view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import search.entity.SearchResult;
import search.entity.Title;
import search.util.Utils;

public class SearchResultDialog extends JSearchDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public SearchResultDialog(SearchResult result1, SearchResult result2) {
		setTitle("检索结果");
		setSize(300, 280);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		
		int notMatchCount = 0;
		List<Title> searchResultTitles = Utils.getContrastTitleList(result1.getTitles(), result2.getTitles());
		for (Title title : searchResultTitles) {
			if (title.getTitles().size() == 0) {
				notMatchCount++;
			}
		}
		
		JPanel resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(300, 50));
		Font font = new Font("微软雅黑", Font.PLAIN, 12);
		JLabel resultLabel1 = new JLabel();
		resultLabel1.setFont(font);
		resultLabel1.setText("第 " + result1.getPage() + " 页 " + result1.getCount() + " 个论文题目共检索到 " + result2.getCount() + " 条记录");
		resultPanel.add(resultLabel1);
		if (notMatchCount > 0) {
			JLabel resultLabel3 = new JLabel();
			resultLabel3.setFont(font);
			resultLabel3.setText("有 " + notMatchCount + " 个未匹配的论文题目");
			resultPanel.add(resultLabel3);
		}
		
		JPanel linkPanel = new JPanel();
		linkPanel.setPreferredSize(new Dimension(300, 80));
		String text = "<a style=\"font:9px 微软雅黑\" href=\"" + result1.getUrl() + "\">在浏览器中打开高级检索页面</a><br/><br/><a style=\"font:9px 微软雅黑\" href=\"" + result2.getUrl() + "\">在浏览器中打开检索结果页面</a>";
		JTextPane textPane = new JTextPane();
		textPane.setBackground(getBackground());
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		textPane.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		textPane.setText(text);
		// 超链接事件监听
		textPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) 
					 return; // 如果不是鼠标点击则返回
				URL linkUrl = e.getURL();
				try {
					Desktop.getDesktop().browse(linkUrl.toURI()); // 打开超链接关键代码
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(getParent(), "打开链接失败！", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		linkPanel.add(textPane);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(300, 80));
		if (notMatchCount > 0) {
			JButton showNotMatchBtn = new JSearchButton("展示未匹配的题目（" + notMatchCount + "）");
			showNotMatchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					NotMatchDialog dialog = new NotMatchDialog(searchResultTitles);
					dialog.setCenterOfMainFrame(getWidth(), getHeight(), getX(), getY());
					dialog.setVisible(true);
				}
			});
			buttonPanel.add(showNotMatchBtn);
		}
		JButton showMatchedBtn = new JSearchButton("对比所有检索结果（" + result1.getCount() + "）");
		showMatchedBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MatchedDialog dialog = new MatchedDialog("对比第" + result1.getPage() + "页检索结果", searchResultTitles);
				dialog.setCenterOfMainFrame(getWidth(), getHeight(), getX(), getY());
				dialog.setVisible(true);
			}
		});
		buttonPanel.add(showMatchedBtn);
		
		getContentPane().add(resultPanel);
		getContentPane().add(linkPanel);
		getContentPane().add(buttonPanel);
	}

}
