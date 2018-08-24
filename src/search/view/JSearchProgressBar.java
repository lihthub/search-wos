package search.view;

import java.awt.Font;

import javax.swing.JProgressBar;

public class JSearchProgressBar extends JSearchDialog {
	private static final long serialVersionUID = 6462658364538939634L;
	private final JProgressBar progressBar = new JProgressBar();
	
	public JSearchProgressBar() {
		setSize(250, 100);
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		getContentPane().add(progressBar);
	}
	
	public JProgressBar getJProgressBar() {
		return this.progressBar;
	}

	public static void main(String[] args) {
		JSearchProgressBar example = new JSearchProgressBar();
		new Thread() {
			public void run() {
				for (int i = 0; i <= 100; i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					example.getJProgressBar().setValue(i);
					example.getJProgressBar().setString("正在导入第" + (i + 1) + "页");
				}
				example.getJProgressBar().setString("导入成功");
			};
		}.start();
		example.setVisible(true);
	}
}