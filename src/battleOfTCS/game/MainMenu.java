package battleOfTCS.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenu {
	
	private JFrame frame;
	private Modes modes;
	private JPanel panel;
	
	JButton btnNewGame = new JButton("New game");
	JButton btnLoadGame = new JButton("Load game");
	JButton btnOptions = new JButton("Options");
	JButton btnExit = new JButton("Exit");
	
	public MainMenu(JFrame frame, Modes modes, JPanel panel) {
		this.frame = frame;
		this.modes = modes;
		this.panel = panel;
		
		this.setButtons();
	}
	
	
	public void repaintMainMenu() {
		panel.add(btnNewGame, "cell 0 0, width 150:250:300, height 30:50:80");
		panel.add(btnLoadGame, "cell 0 1, width 150:250:300, height 30:50:80");
		panel.add(btnOptions, "cell 0 2, width 150:250:300, height 30:50:80");
		panel.add(btnExit, "cell 0 3, width 150:250:300, height 30:50:80");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
	
	public void setButtons() {
		btnNewGame.setForeground(new Color(0f, 0f, 0f));
		btnNewGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modes.setChooseColorAndNameMode();
			}
		});

		btnLoadGame.setForeground(new Color(0f, 0f, 0f));
		btnLoadGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modes.setLoadGameMode();
			}
		});

		btnOptions.setForeground(new Color(0f, 0f, 0f));
		btnOptions.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modes.setOptionsMode();
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
