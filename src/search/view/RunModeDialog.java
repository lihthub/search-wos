package search.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RunModeDialog extends JSearchDialog {
	private static final long serialVersionUID = 1L;
	private ButtonGroup bg;

	/**
	 * Create the dialog.
	 */
	public RunModeDialog() {
		setTitle("运行模式");
		setSize(300, 150);
		Font font = new Font("微软雅黑", Font.PLAIN, 12);
		
		JPanel askPanel = new JPanel();
		askPanel.setPreferredSize(new Dimension(Double.valueOf(getSize().getWidth()).intValue(), 40));
		JLabel askLabel = new JLabel("请选择运行模式：");
		askLabel.setFont(font);
		askPanel.add(askLabel);
		
		JPanel radioBtnPanel = new JPanel();
		radioBtnPanel.setPreferredSize(new Dimension(Double.valueOf(getSize().getWidth()).intValue(), 40));
		JRadioButton databaseRadioBtn = new JRadioButton("数据库模式（推荐）");
		databaseRadioBtn.setActionCommand("database");
		databaseRadioBtn.setFocusPainted(false);
		databaseRadioBtn.setFont(font);
		databaseRadioBtn.setContentAreaFilled(false);
		databaseRadioBtn.setSelected(true);
		JRadioButton networkRadioBtn = new JRadioButton("网络模式");
		networkRadioBtn.setActionCommand("network");
		networkRadioBtn.setFocusPainted(false);
		networkRadioBtn.setFont(font);
		networkRadioBtn.setContentAreaFilled(false);
		radioBtnPanel.add(databaseRadioBtn);
		radioBtnPanel.add(networkRadioBtn);
		
		bg = new ButtonGroup();
		bg.add(databaseRadioBtn);
		bg.add(networkRadioBtn);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(Double.valueOf(getSize().getWidth()).intValue(), 40));
		JButton okButton = new JSearchButton("确认");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(okButton);
		
		getContentPane().add(askLabel);
		getContentPane().add(radioBtnPanel);
		getContentPane().add(buttonPanel);
	}
	
	public String getValue() {
		String value = this.bg.getSelection().getActionCommand();
		return value == null ? "database" : value;
	}
	
	public static void main(String[] args) {
		RunModeDialog dialog = new RunModeDialog();
		dialog.setCenterOfMainFrame(1366, 768, 0, 0);
		dialog.setVisible(true);
		System.out.println(dialog.getValue());
	}

}
