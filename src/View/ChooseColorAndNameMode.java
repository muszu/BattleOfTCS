package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Control.Controller;
import Model.Game;
import net.miginfocom.swing.MigLayout;


public class ChooseColorAndNameMode {
	
	private final String [] colors = {"black", "red", "green", "blue", "white", "yellow"};
	private final String [] gameModes = {"annihilation", "capture flag"};
	private final String [] gamePoints = {"500", "1000", "1500","2000"};
	
	private final JComboBox<String> colorListA = new JComboBox<String>(colors);
	private final JComboBox<String> colorListB = new JComboBox<String>(colors);
	private final JComboBox<String> gameModeList = new JComboBox<String>(gameModes);
	private final JComboBox<String> gamePointsList = new JComboBox<String>(gamePoints);
	
	private JButton btnChooseUnits = new JButton("Choose Units");
	private JButton btnBack = new JButton("Back");
	
	private JTextField nameA = new JTextField("Player's A name", 1);
	private JTextField nameB = new JTextField("Player's B name", 1);
	
	private String colorA = "black";
	private String colorB = "red";
	private String playerAname = "Player's A Name";
	private String playerBname = "Player's B Name";
	private boolean flagOrNot = true;
	
	JPanel panel;
	Controller controller;
	JFrame frame;
	Game game;
	
	
	public ChooseColorAndNameMode(JFrame frame, Controller controller, JPanel panel, Game game) {
		this.frame = frame;
		this.controller = controller;
		this.panel = panel;
		this.game = game;
		
		this.setButtons();
		this.setComboBoxes();
		this.setTextFields();
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
		gameModeList.setSelectedIndex(0);
		gameModeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if(gameModeList.getSelectedItem().equals(gameModeList.getItemAt(0)))
		        	flagOrNot = false;
				else
					flagOrNot = true;
			}
		});
		
		gamePointsList.setForeground(new Color(0f, 0f, 0f));
		gamePointsList.setBackground(new Color(0.7f, 0.7f, 0.7f));
		gamePointsList.setSelectedIndex(0);
		gamePointsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int pointNum =  gamePointsList.getSelectedIndex();
		        switch (pointNum) {
				case 0:
					System.out.println(500);
					break;
				case 1:
					System.out.println(1000);			
					break;
				case 2:
					System.out.println(1500);
					break;

				default:
					System.out.println(2000);
					break;
				}
			}
		});
	}
	
	
	private void setButtons() {
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMainMenuMode();
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
				controller.setChooseUnitsMode();
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
	
	
	public void paintChooseColorAndNameMode() {
		panel.removeAll();
		panel.setLayout(new MigLayout("", (frame.getWidth()/ 2 - 580/2)+"[][]20[][]", 
			(frame.getHeight()/2 - 340/2)+"[]30[]50[]100[]"));
		
		panel.add(gameModeList, "cell 1 0, width 100:140:150, height 20:40:40");
		panel.add(gamePointsList, "cell 2 0, width 100:140:150, height 20:40:40");
		panel.add(nameA, "cell 0 1, width 100:140:150, height 20:40:40");
		panel.add(nameB, "cell 3 1, width 100:140:150, height 20:40:40");
		panel.add(colorListA, "cell 0 2, width 100:140:150, height 20:40:40");
		panel.add(colorListB, "cell 3 2, width 100:140:150, height 20:40:40");
		panel.add(btnBack, "cell 2 3, width 100:140:150, height 20:40:40");
		panel.add(btnChooseUnits, "cell 1 3, width 100:140:150, height 20:40:40");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
}
