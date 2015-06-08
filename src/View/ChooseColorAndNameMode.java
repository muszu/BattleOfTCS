package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Control.Controller;
import Model.Game;
import net.miginfocom.swing.MigLayout;


public class ChooseColorAndNameMode {
	
	private final String [] colors = {"black", "red", "green", "blue", "white", "yellow"};
	private final String [] gameModes = {"annihilation", "capture flag"};
	private final String [] gamePoints = {"500 $", "1000 $", "1500 $","2000 $"};
	
	private final JComboBox<String> colorListA = new JComboBox<String>(colors);
	private final JComboBox<String> colorListB = new JComboBox<String>(colors);
	private final JComboBox<String> gameModeList = new JComboBox<String>(gameModes);
	private final JComboBox<String> gamePointsList = new JComboBox<String>(gamePoints);
	
	private JButton btnChooseUnits = new JButton("Choose Units");
	private JButton btnBack = new JButton("Back");
	
	private JTextField nameA = new JTextField("Player A", 1);
	private JTextField nameB = new JTextField("Player B", 1);
	
	private JLabel labMode = new JLabel();
	private JLabel labPoints = new JLabel();
	private JLabel labNames = new JLabel();
	private JLabel labColors = new JLabel();
	
	private String colorA = "black";
	private String colorB = "red";
	private String playerAname = "Player's A Name";
	private String playerBname = "Player's B Name";
	private Integer points = 500;
	private boolean flagOrNot = false;
	
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
		this.setPointsLab();
		this.setModeLab();
		this.setColorsLab();
		this.setNamesLab();
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
					points=500;
					break;
				case 1:
					points=1000;			
					break;
				case 2:
					points=1500;
					break;

				default:
					points=2000;
					break;
				}
		        System.out.println(points);
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
				if (playerAname.length()<1 || playerAname.length()>10 ||
					 playerBname.length()<1 || playerBname.length()>10)
						nameLengthError();
				else if (playerAname.equals(playerBname))
					nameDistinctionError();
				else if (colorA.equals(colorB))
					colorDistinctionError();
				else{		
					game.playerA = playerAname;
					game.playerB = playerBname;
					game.modeOfGame = flagOrNot;
					game.colorA = colorA;
					game.colorB = colorB;
					game.points = points;
					controller.setChooseUnitsMode();
				}
			}
		});
	}
	
	private void getNames() {
		playerAname = nameA.getText();
		playerBname = nameB.getText();
	}
	
	private void nameLengthError(){
		JOptionPane.showConfirmDialog(frame, "Player's name should be 1-10 characters long", 
				"Error", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void nameDistinctionError(){
		JOptionPane.showConfirmDialog(frame, "Players' names should be different", 
				"Error", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void colorDistinctionError(){
		JOptionPane.showConfirmDialog(frame, "Colors should be different", 
				"Error", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);		
	}
	private void setTextFields() {
		nameA.setForeground(new Color(0f, 0f, 0f));
		nameA.setBackground(new Color(0.7f, 0.7f, 0.7f));

		nameB.setForeground(new Color(0f, 0f, 0f));
		nameB.setBackground(new Color(0.7f, 0.7f, 0.7f));
	}
	private void setPointsLab(){
		labPoints.setBackground(new Color(0.7f, 0.7f, 0.7f));
		labPoints.setHorizontalAlignment(SwingConstants.CENTER);
		labPoints.setForeground(Color.black);
		labPoints.setOpaque(true);
	
		String param = "Points";

		labPoints.setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");
	}
	private void setModeLab(){
		labMode.setBackground(new Color(0.7f, 0.7f, 0.7f));
		labMode.setHorizontalAlignment(SwingConstants.CENTER);
		labMode.setForeground(Color.black);
		labMode.setOpaque(true);
	
		String param = "Game Mode";

		labMode.setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");

	}
	private void setNamesLab(){
		labNames.setBackground(new Color(0.7f, 0.7f, 0.7f));
		labNames.setHorizontalAlignment(SwingConstants.CENTER);
		labNames.setForeground(Color.black);
		labNames.setOpaque(true);
	
		String param = "Players' names";

		labNames.setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");

	}
	private void setColorsLab(){
		labColors.setBackground(new Color(0.7f, 0.7f, 0.7f));
		labColors.setHorizontalAlignment(SwingConstants.CENTER);
		labColors.setForeground(Color.black);
		labColors.setOpaque(true);
	
		String param = "Players' colors";

		labColors.setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");

	}

	
	
	public void paintChooseColorAndNameMode() {
		panel.removeAll();
		panel.setLayout(new MigLayout("", (frame.getWidth()/ 2 - 580/2)+"[]30[]20[]30[]", 
			(frame.getHeight()/2 - 340/2)+"[]20[]50[]20[]100[]"));
		
		panel.add(labMode, "cell 1 0, width 100:140:150, height 20:40:40");
		panel.add(labPoints, "cell 1 1, width 100:140:150, height 20:40:40");
		panel.add(gameModeList, "cell 2 0, width 100:140:150, height 20:40:40");
		panel.add(gamePointsList, "cell 2 1, width 100:140:150, height 20:40:40");
		panel.add(nameA, "cell 0 2, width 100:140:150, height 20:40:40");
		panel.add(nameB, "cell 3 2, width 100:140:150, height 20:40:40");
		panel.add(labNames, "cell 1 2 2 1, width 200:300:300, height 20:40:40");
		panel.add(colorListA, "cell 0 3, width 100:140:150, height 20:40:40");
		panel.add(colorListB, "cell 3 3, width 100:140:150, height 20:40:40");
		panel.add(labColors, "cell 1 3 2 1, width 200:300:300, height 20:40:40");
		panel.add(btnBack, "cell 1 4, width 100:140:150, height 20:40:40");
		panel.add(btnChooseUnits, "cell 2 4, width 100:140:150, height 20:40:40");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
}
