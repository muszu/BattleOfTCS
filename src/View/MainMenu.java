package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Control.Controller;
import net.miginfocom.swing.MigLayout;

public class MainMenu {

	
	private JButton btnNewGame = new JButton("New game");
	private JButton btnLoadGame = new JButton("Load game");
	private JButton btnRules = new JButton("Rules");
	private JButton btnOptions = new JButton("Options");
	private JButton btnCredits = new JButton("Credits");
	private JButton btnExit = new JButton("Exit");
	
	JFrame frame;
	Controller controller;
	JPanel panel;
	
	
	public MainMenu(JFrame frame, Controller controller, JPanel panel) {
		this.frame = frame;
		this.controller = controller;
		this.panel = panel;
		
		this.setButtons();
	}
	
	
	public void paintMainMenu() {
		panel.removeAll();
		panel.setLayout(new MigLayout("", new StringBuilder()
				.append(frame.getWidth() / 2 - 125).append("[]").toString(),
				new StringBuilder().append(frame.getHeight() - 400)
						.append("[]10[]10[]10[]10[]10[]10[]").toString()));
		
		panel.add(btnNewGame, "cell 0 0, width 150:250:300, height 30:40:60");
		panel.add(btnLoadGame, "cell 0 1, width 150:250:300, height 30:40:60");
		panel.add(btnRules, "cell 0 2, width 150:250:300, height 30:40:60");
		panel.add(btnOptions, "cell 0 3, width 150:250:300, height 30:40:60");
		panel.add(btnCredits, "cell 0 4, width 150:250:300, height 30:40:60");
		panel.add(btnExit, "cell 0 5, width 150:250:300, height 30:40:60");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
	
	private void setButtons() {
		btnNewGame.setForeground(new Color(0f, 0f, 0f));
		btnNewGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setChooseColorAndNameMode();
			}
		});

		btnLoadGame.setForeground(new Color(0f, 0f, 0f));
		btnLoadGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setLoadGameMode();
			}
		});
		
		btnRules.setForeground(new Color(0f, 0f, 0f));
		btnRules.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setTutorialMode();
			}
		});

		btnOptions.setForeground(new Color(0f, 0f, 0f));
		btnOptions.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setOptionsMode();
			}
		});
		
		btnCredits.setForeground(new Color(0f, 0f, 0f));
		btnCredits.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setCreditsMode();
			}
		});
		
		btnExit.setForeground(new Color(0f, 0f, 0f));
		btnExit.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
								"Are you sure to end your game?", "Exit",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

}
