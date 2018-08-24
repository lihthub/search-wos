package search.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class JSearchDialog extends JDialog {
	private static final long serialVersionUID = -3098222751214614244L;
	private final JPanel contentPanel = new JPanel();

	public JSearchDialog() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModal(true); // 设置窗口阻塞
		this.setResizable(false);
		super.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		super.getContentPane().add(contentPanel, BorderLayout.CENTER);
	}
	
	public void setCenterOfMainFrame(int mainFrameWidth, int mainFrameHeight, int mainFrameX, int mainFrameY) {
		int dialogWidth = this.getWidth();
		int dialogHeight = this.getHeight();
		int dialogX = mainFrameX + (mainFrameWidth / 2 - dialogWidth / 2);
		int dialogY = mainFrameY + (mainFrameHeight / 2 - dialogHeight / 2);
		this.setLocation(dialogX, dialogY); // 设置窗口的位置在主窗口的中间显示
	}
	
	@Override
	public Container getContentPane() {
		return this.contentPanel;
	}
	
}
