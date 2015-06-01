package battleOfTCS.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ChooseColorAndNameMode {
	
	JPanel panel;
	Modes modes;
	JFrame frame;
	Game game;
	
	final String [] colors = {"black", "red", "green", "blue", "white", "yellow"};
	final String [] gameModes = {"annihilation", "capture flag"};
	
	final JComboBox<String> colorListA = new JComboBox<String>(colors);
	final JComboBox<String> colorListB = new JComboBox<String>(colors);
	final JComboBox<String> gameModeList = new JComboBox<String>(gameModes);
	
	JButton btnChooseUnits = new JButton("Choose Units");
	JButton btnBack = new JButton("Back");
	
	JTextField nameA = new JTextField("Player's A name", 1);
	JTextField nameB = new JTextField("Player's B name", 1);
	
	public String colorA = "black";
	public String colorB = "red";
	public String playerAname = "Player's A Name";
	public String playerBname = "Player's B Name";
	public boolean flagOrNot = false;
	
	
	
	
	public ChooseColorAndNameMode(JFrame frame, Modes modes, JPanel panel, Game game) {
		this.frame = frame;
		this.modes = modes;
		this.panel = panel;
		this.game = game;
		
		this.setButtons();
		this.setComboBoxes();
		this.setTextFields();
	}
	
	
	
	
	public void repaintChooseColorAndNameMode() {
		panel.add(gameModeList, "cell 1 0, width 120:150:170, height 20:30:40");
		panel.add(nameA, "cell 0 1, width 120:150:170, height 20:30:40");
		panel.add(nameB, "cell 3 1, width 120:150:170, height 20:30:40");
		panel.add(colorListA, "cell 0 2, width 120:150:170, height 20:30:40");
		panel.add(colorListB, "cell 3 2, width 120:150:170, height 20:30:40");
		panel.add(btnBack, "cell 2 3, width 100:120:140, height 20:30:40");
		panel.add(btnChooseUnits, "cell 1 3, width 100:120:140, height 20:30:40");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
	
	
	private void setComboBoxes() {
		colorListA.setForeground(new Color(0f, 0f, 0f));
		colorListA.setBackground(new Color(0.7f, 0.7f, 0.7f));
		colorListA.setSelectedIndex(0);
		colorListA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				colorA = (String) colorListA.getSelectedItem(); 
			}
		});
		
		colorListB.setForeground(new Color(0f, 0f, 0f));
		colorListB.setBackground(new Color(0.7f, 0.7f, 0.7f));
		colorListB.setSelectedIndex(1);
		colorListB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				colorB = (String) colorListB.getSelectedItem();
			}
		});
		
		gameModeList.setForeground(new Color(0f, 0f, 0f));
		gameModeList.setBackground(new Color(0.7f, 0.7f, 0.7f));
		gameModeList.setSelectedIndex(1);
		gameModeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if(gameModeList.getSelectedItem().equals(gameModeList.getItemAt(0)))
		        	flagOrNot = false;
				else
					flagOrNot = true;
			}
		});
	}
	
	
	
	
	private void setButtons() {
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modes.setMainMenuMode();
			}
		});
		
		
		btnChooseUnits.setForeground(new Color(0f, 0f, 0f));
		btnChooseUnits.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnChooseUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getNames();
				game.playerA = playerAname;
				game.playerB = playerBname;
				game.modeOfGame = flagOrNot;
				game.colorA = colorA;
				game.colorB = colorB;
				modes.setChooseUnitsMode();
			}
		});
	}
	
	
	
	private void getNames() {
		playerAname = nameA.getText();
		playerBname = nameB.getText();
	}
	
	
	
	
	private void setTextFields() {
		nameA.setForeground(new Color(0f, 0f, 0f));
		nameA.setBackground(new Color(0.7f, 0.7f, 0.7f));

		nameB.setForeground(new Color(0f, 0f, 0f));
		nameB.setBackground(new Color(0.7f, 0.7f, 0.7f));
	}
	
}
