package search.view;

import java.awt.Font;

import javax.swing.JLabel;

public class JSearchLabel extends JLabel {
	private static final long serialVersionUID = -7558180408511619105L;

	public JSearchLabel(String text) {
		super(text);
		this.setFont(new Font("微软雅黑", Font.PLAIN, 12));
	}
}
