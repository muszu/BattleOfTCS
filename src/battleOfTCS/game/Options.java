package battleOfTCS.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class Options {
	
	private JButton btnBack = new JButton("Back");
	private JButton btnReso1 = new JButton("1024 : 768");
	private JButton btnReso2 = new JButton("1280 : 1024");
	private JButton btnReso3 = new JButton("1366 : 768");
	private JButton btnReso4 = new JButton("1920 : 1080");
	private JButton btnReso5 = new JButton("Full screen");

	JPanel panel;
	Controller controller;
	JFrame frame;
	
	public Options(JPanel panel, JFrame frame, Controller controller) {
		this.panel = panel;
		this.frame = frame;
		this.controller = controller;
		setButtons();
	}
	
	private void setBackBtn(JButton btn) {
		btn.setForeground(new Color(0f, 0f, 0f));
		btn.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMainMenuMode();
			}
		});
	}
	
	private void setResolutionBtn(JButton btn, final int resolutionMode) {
		btn.setForeground(new Color(0f, 0f, 0f));
		btn.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeResolution(resolutionMode);
				paintOptions();
			}
		});
	}
	
	private void setButtons() {
		setBackBtn(btnBack);
		setResolutionBtn(btnReso1, 1);
		setResolutionBtn(btnReso2, 2);
		setResolutionBtn(btnReso3, 3);
		setResolutionBtn(btnReso4, 4);
		setResolutionBtn(btnReso5, 5);
	}
	
	public void paintOptions() {
		panel.removeAll();
		panel.setLayout(new MigLayout("", new StringBuilder()
			.append(frame.getWidth() / 2 - 125).append("[]").toString(),
			new StringBuilder().append(frame.getHeight() - 500)
				.append("[]20[]20[]20[]20[]").toString()));
		
		panel.add(btnBack, "cell 0 0, width 150:250:300, height 30:40:80");
		panel.add(btnReso1, "cell 0 1, width 150:250:300, height 30:40:80");
		panel.add(btnReso2, "cell 0 2, width 150:250:300, height 30:40:80");
		panel.add(btnReso3, "cell 0 3, width 150:250:300, height 30:40:80");
		panel.add(btnReso4, "cell 0 4, width 150:250:300, height 30:40:80");
		panel.add(btnReso5, "cell 0 5, width 150:250:300, height 30:40:80");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}

	
	private void changeResolution(int resolutionMode) {
		frame.dispose();
		frame.setUndecorated(false);
		switch(resolutionMode) {
			case 1: {		
				frame.setBounds(0, 0, 1024, 768);
				HexMap.firstHexCenterY = 124;
				break;
			}
			case 2: {
				frame.setBounds(0, 0, 1280, 1024);
				HexMap.firstHexCenterY = 124;
				break;

			}
			case 3: {
				frame.setBounds(0, 0, 1366, 768);
				HexMap.firstHexCenterY = 124;
				break;
			}
			case 4: {
				frame.setBounds(0, 0, 1920, 1080);
				HexMap.firstHexCenterY = 240; 
				break;
			}
			case 5: {
				 frame.setUndecorated(true);
				 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				 double width = screenSize.getWidth();
				 double height = screenSize.getHeight();
				 frame.setBounds(0, 0, (int) width, (int) height);
				 HexMap.firstHexCenterY = (int) width/10;
				 break;
			}
		}
		frame.setVisible(true);
		HexMap.width = frame.getWidth();
		HexMap.height = frame.getHeight();
		HexMap.firstHexCenterX = (int) (HexMap.width - HexMapElement.width
				* HexMap.getAmountInOddRow()) / 2;
		controller.setOptionsMode();
	}
}
