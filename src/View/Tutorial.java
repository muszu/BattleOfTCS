package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import Control.Controller;

public class Tutorial {

	
	private JButton btnBack = new JButton("Back");
	private JButton btnNext = new JButton("Next");
	

	JPanel panel;
	Controller controller;
	JFrame frame;
	
	public Tutorial(JPanel panel, JFrame frame, Controller controller) {
		this.panel = panel;
		this.frame = frame;
		this.controller = controller;
		setBackBtn(btnBack) ;
		setNextBtn(btnNext);
		controller.tutorialImgNumber = 1;
	}
	
	private void setBackBtn(JButton btn) {
		btn.setForeground(new Color(0f, 0f, 0f));
		btn.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controller.tutorialImgNumber <= 1)
					controller.setMainMenuMode();
				--controller.tutorialImgNumber;
				controller.panel.repaint();
			}
		});
	}
	
	private void setNextBtn(JButton btn) {
		btn.setForeground(new Color(0f, 0f, 0f));
		btn.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controller.tutorialImgNumber > 4)
					controller.setMainMenuMode();
				++controller.tutorialImgNumber;
				controller.panel.repaint();
			}
		});
	}
	

	
	public void paintTutorial() {
		panel.removeAll();
		panel.setLayout(new MigLayout("",
				frame.getWidth()/2 - 250 +	"[]10[]",
			frame.getHeight() - 60 + "[]"));
		
		panel.add(btnBack, "cell 0 0, width 150:250:300, height 30:40:60");
		panel.add(btnNext, "cell 1 0, width 150:250:300, height 30:40:60");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}

}
