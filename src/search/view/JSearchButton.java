package search.view;

import java.awt.Font;

import javax.swing.JButton;

public class JSearchButton extends JButton {
	private static final long serialVersionUID = -4235079229297936964L;

	public JSearchButton(String text) {
		super(text);
		this.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		this.setFocusPainted(false);
	}
}
