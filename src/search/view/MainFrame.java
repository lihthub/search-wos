package search.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import search.entity.SearchResult;
import search.util.PropertiesUtils;
import search.util.Utils;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 3831560528952707134L;
	private JTextField searchTextField;
	private JTextField sidTextField;
	private ButtonGroup ssflBg = new ButtonGroup();
	private Integer page;
	private Integer pageCount;
	private JLabel pageLabel;
	private JButton saveBtn;
	private String runMode;

	public MainFrame() {
		try {
			// 设置界面风格为系统默认风格
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		int screenWidth = kit.getScreenSize().width; // 屏幕宽度
		int screenHeight = kit.getScreenSize().height; // 屏幕高度
		int frameWidth = 450; // 主窗口宽度
		int frameHeight = 500; // 主窗口高度
		setBounds(screenWidth / 2 - frameWidth / 2, screenHeight / 2 - frameHeight / 2, frameWidth, frameHeight);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPanel = new JPanel();
		
		RunModeDialog chooseRunModeDialog = new RunModeDialog();
		chooseRunModeDialog.setCenterOfMainFrame(screenWidth, screenHeight, 0, 0);
		chooseRunModeDialog.setVisible(true);
		runMode = chooseRunModeDialog.getValue();
		
		String pageStr = PropertiesUtils.getInstance().getProperty(PropertiesUtils.PAGE);
		String pageCountStr = PropertiesUtils.getInstance().getProperty(PropertiesUtils.PAGECOUNT);
		page = Integer.parseInt(pageStr);
		pageCount = pageCountStr == null || pageCountStr.isEmpty() ? 0 : Integer.parseInt(pageCountStr);
		
		JPanel sidPanel = new JPanel();
		sidPanel.setPreferredSize(new Dimension(frameWidth, 50));
		sidPanel.add(new JSearchLabel("SID :"));
		sidTextField = new JTextField();
		sidTextField.setText(PropertiesUtils.getInstance().getProperty(PropertiesUtils.SID));
		sidTextField.setPreferredSize(new Dimension(300, 30));
		sidPanel.add(sidTextField);
		
		JPanel ssflPanel = new JPanel();
		ssflPanel.add(new JSearchLabel("依托设施 :"));
		ssflPanel.setPreferredSize(new Dimension(frameWidth, 250));
		Map<String, String> ssflMap = null;
		try {
			ssflMap = Utils.getSsflMap(runMode);
		} catch (NullPointerException e) {
			e.printStackTrace();
			String message = Utils.RUNMODE_DATABASE.equals(runMode) ? "请检查网络或数据库配置！" : "请检查网络！";
			JOptionPane.showMessageDialog(getRootPane(), message, "Error!", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			// getRootPane() 表示在根面板中间位置弹出对话框
			JOptionPane.showMessageDialog(getRootPane(), e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
		String ssfl = PropertiesUtils.getInstance().getProperty(PropertiesUtils.SSFL);
		if (ssflMap != null) {
			for (String value : ssflMap.keySet()) {
				JRadioButton radioBtn = new JRadioButton(ssflMap.get(value));
				radioBtn.setActionCommand(value);
				radioBtn.setFocusPainted(false); // 设置无焦点边框
				radioBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
				radioBtn.setContentAreaFilled(false);
				ssflPanel.add(radioBtn);
				ssflBg.add(radioBtn);
			}
		}
		// 默认选中
		Enumeration<AbstractButton> buttons = ssflBg.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if (button.getActionCommand().equals(ssfl)) {
				ssflBg.setSelected(button.getModel(), true);
				break;
			}
		}
		
		JPanel pagePanel = new JPanel();
	    pagePanel.setPreferredSize(new Dimension(frameWidth, 50));
	    pageLabel = new JSearchLabel(page + "/"+ pageCount +"页");
		pagePanel.add(pageLabel);
		JButton previousBtn = new JSearchButton("上一页");
		previousBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (page > 1) {
					search(page - 1);
				} else {
					JOptionPane.showMessageDialog(getRootPane(), "当前已是首页", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton nextBtn = new JSearchButton("下一页");
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (page < pageCount) {
					search(page + 1);
				} else {
					JOptionPane.showMessageDialog(getRootPane(), "当前已是最后一页", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pagePanel.add(previousBtn);
		pagePanel.add(nextBtn);
		pagePanel.add(new JSearchLabel("转到第"));
		searchTextField = new JTextField();
		searchTextField.setPreferredSize(new Dimension(30, 20));
		searchTextField.setText(String.valueOf(page));
		pagePanel.add(searchTextField);
		pagePanel.add(new JSearchLabel("页"));
		JButton searchBtn = new JSearchButton("检索");
	    searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pageNo = page;
				if (searchTextField != null && searchTextField.getText() != null && !searchTextField.getText().isEmpty()) {
					if (Pattern.matches("^\\d+$", searchTextField.getText())) {
						int searchPage = Integer.parseInt(searchTextField.getText());
						if (searchPage > 0) {
							pageNo = searchPage;
						}
						if (searchPage > pageCount) {
							pageNo = pageCount;
						}
					} else {
						searchTextField.setText(String.valueOf(page));
					}
				}
				search(pageNo);
			}
	    });
	    pagePanel.add(searchBtn);
	    
	    JPanel processPanel = new JPanel();
	    processPanel.setPreferredSize(new Dimension(frameWidth, 26));
	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setStringPainted(true);
		progressBar.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		progressBar.setVisible(false);
	    processPanel.add(progressBar);
		
	    FileSystemView fsv = FileSystemView.getFileSystemView(); // 得到系统视图对象
	    File desktop = fsv.getHomeDirectory(); // 获取桌面的路径
	    File allFolder = new File(desktop, "/论文标题_全部");
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setPreferredSize(new Dimension(frameWidth, 50));
		FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
		buttonPanel.setLayout(fl_buttonPane);
		if (Utils.RUNMODE_DATABASE.equals(runMode)) {
			JButton loadDataBtn = new JSearchButton("导入数据到数据库");
			loadDataBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Thread() {
						public void run() {
							try {
								Utils.loadDataToDatabase(progressBar, ssflBg.getSelection().getActionCommand());
							} catch (NullPointerException ex) {
								ex.printStackTrace();
								String message = Utils.RUNMODE_DATABASE.equals(runMode) ? "请检查网络或数据库配置！" : "请检查网络！";
								JOptionPane.showMessageDialog(getRootPane(), message, "Error!", JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex) {
								ex.printStackTrace();
								JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
							}
						}
					}.start();
				}
			});
			buttonPanel.add(loadDataBtn);
		}
		JButton saveAllBtn = new JSearchButton("导出所有标题");
		saveAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						try {
							int totalCount = Utils.saveAllResult(progressBar, runMode, allFolder, ssflBg.getSelection().getActionCommand());
							if (totalCount > 0) {
								JOptionPane.showMessageDialog(getRootPane(), "已将" + totalCount + "条数据成功保存至" + allFolder, "Success!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(getRootPane(), "导出失败", "Error!", JOptionPane.ERROR_MESSAGE);
							}
						} catch (NullPointerException ex) {
							ex.printStackTrace();
							String message = Utils.RUNMODE_DATABASE.equals(runMode) ? "请检查网络或数据库配置！" : "请检查网络！";
							JOptionPane.showMessageDialog(getRootPane(), message, "Error!", JOptionPane.ERROR_MESSAGE);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						}
					};
				}.start();
			}
		});
		saveBtn = new JSearchButton("导出第" + page + "页标题");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File oneFolder = new File(desktop, "/论文标题_第" + page + "页");
				try {
					int totalCount = Utils.saveOneResult(runMode, oneFolder, ssflBg.getSelection().getActionCommand(), page);
					if (totalCount > 0) {
						JOptionPane.showMessageDialog(getRootPane(), "已将第" + page + "页的" + totalCount + "条数据成功保存至" + oneFolder, "Success!", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(getRootPane(), "导出失败", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
					String message = Utils.RUNMODE_DATABASE.equals(runMode) ? "请检查网络或数据库配置！" : "请检查网络！";
					JOptionPane.showMessageDialog(getRootPane(), message, "Error!", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonPanel.add(saveAllBtn);
		buttonPanel.add(saveBtn);
		
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.add(sidPanel);
		contentPanel.add(ssflPanel);
		contentPanel.add(pagePanel);
		contentPanel.add(processPanel);
		contentPanel.add(buttonPanel);
		setContentPane(contentPanel);
	}
	
	private void search(int pageNo) {
		PropertiesUtils.getInstance().setProperty(PropertiesUtils.SID, sidTextField.getText());
		PropertiesUtils.getInstance().setProperty(PropertiesUtils.SSFL, ssflBg.getSelection().getActionCommand());
		try {
			SearchResult result = Utils.search(runMode, sidTextField.getText(), ssflBg.getSelection().getActionCommand(), pageNo);
			page = result.getPage();
			pageCount = result.getPageCount();
			PropertiesUtils.getInstance().setProperty(PropertiesUtils.PAGE, String.valueOf(page));
			PropertiesUtils.getInstance().setProperty(PropertiesUtils.PAGECOUNT, result.getPageCount() + "");
			pageLabel.setText(result.getPage() + "/"+ result.getPageCount() +"页");
			searchTextField.setText(String.valueOf(result.getPage()));
			saveBtn.setText("保存第" + result.getPage() + "页标题");
			SearchResult result2 = Utils.getSearchResultInWOS(result.getUrl());
			SearchResultDialog dialog = new SearchResultDialog(result, result2);
			dialog.setCenterOfMainFrame(getWidth(), getHeight(), getX(), getY());
			dialog.setVisible(true);
		} catch (NullPointerException e) {
			e.printStackTrace();
			String message = Utils.RUNMODE_DATABASE.equals(runMode) ? "请检查网络或数据库配置！" : "请检查网络！";
			JOptionPane.showMessageDialog(getRootPane(), message, "Error!", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
}