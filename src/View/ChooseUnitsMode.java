package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Control.Controller;
import Model.Game;
import Model.Unit;
import net.miginfocom.swing.MigLayout;

public class ChooseUnitsMode {
	
	private List<Unit> unitsToChoose;
	private List<Unit> chosenUnits = new ArrayList<Unit>();
	private boolean player1Ready;
	private Integer points;
	
	private JButton btnReady = new JButton("Ready!");
	private JButton btnBack = new JButton("Back");
	private JButton[] btnChosenUnit = new JButton[10];
	private JButton[] btnUnit = new JButton[10];
	private JLabel[] labParameters = new JLabel[10];
	private JLabel labPoints = new JLabel();
	
	
	JFrame frame;
	JPanel panel;
	Controller controller;
	Game game;
	
	
	public ChooseUnitsMode(JFrame frame, JPanel panel, Game game, Controller controller) {
		this.frame = frame;
		this.panel = panel;
		this.game = game;
		this.controller = controller;
		
		unitsToChoose = Unit.createUnitLists(game.colorA);
		points = game.points;
		
		this.setBtnBack();
		this.setBtnChosenUnit();
		this.setBtnReady();
		this.setBtnUnit();
		this.setLabParameters();
	}
	
	private void setBtnReady() {
		btnReady.setForeground(new Color(0f, 0f, 0f));
		btnReady.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chosenUnits.size() == 0) {
					JOptionPane.showConfirmDialog(frame, "No units chosen", 
							"Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}

				else {
					for (int i = 0; i < chosenUnits.size(); i++) {
						if (player1Ready) {
							chosenUnits.get(i).owner = 2;
						} else {
							chosenUnits.get(i).owner = 1;
						}
					}
					game.units.addAll(chosenUnits);
					chosenUnits.clear();
					if (player1Ready) 
						controller.setGameMode(); // true == capture flag mode
					else {
						player1Ready = true;
						points = game.points;
						unitsToChoose = Unit.createUnitLists(game.colorB);
						paintChooseUnitsMode();
					}
				}
			}
		});
	}
	
	private void setBtnUnit() {
		for (int i = 0; i < 10; i++) {
			btnUnit[i] = new JButton();
			btnUnit[i].setBackground(new Color(0.7f, 0.7f, 0.7f));
			btnUnit[i].setIcon(unitsToChoose.get(i).getIcon());
			final int ii = i;
			btnUnit[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chosenUnits.size() < 10) {
						if (points-unitsToChoose.get(ii).cost < 0) pointsError();
						else{
							points -= unitsToChoose.get(ii).cost;
							chosenUnits.add(new Unit(unitsToChoose.get(ii)));
							paintChooseUnitsMode();
						}
					}
				}
			});
		}
	}
	
	private void pointsError(){
		JOptionPane.showConfirmDialog(frame, "Not enough points", 
				"Error", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);		
	}
	
	private void setBtnChosenUnit() {
		for (int i = 0; i < 10; i++) {
			btnChosenUnit[i] = new JButton();
			btnChosenUnit[i].setForeground(new Color(0f, 0f, 0f));
			btnChosenUnit[i].setBackground(new Color(0.7f, 0.7f, 0.7f));

			if (chosenUnits.size() <= i) {
				btnChosenUnit[i].setIcon(null);
				btnChosenUnit[i].setText("Empty");
			} else {
				btnChosenUnit[i].setText("");
				btnChosenUnit[i].setIcon(chosenUnits.get(i).getIcon());
			}
			
			final int ii = i;
			btnChosenUnit[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chosenUnits.size() > ii) {
						points += chosenUnits.get(ii).cost;
						chosenUnits.remove(ii);
						paintChooseUnitsMode();
					}
				}
			});
		}
	}
	
	private void setLabParameters() {
		for (int i = 0; i < 10; i++) {
			labParameters[i] = new JLabel();
			labParameters[i].setBackground(new Color(0.7f, 0.7f, 0.7f));
			labParameters[i].setHorizontalAlignment(SwingConstants.CENTER);
			labParameters[i].setForeground(Color.black);
			labParameters[i].setOpaque(true);
			
			String param = "Cost " + unitsToChoose.get(i).cost + "\n" +
							"Health " + unitsToChoose.get(i).health + "\n" +
							"Attack " + unitsToChoose.get(i).attack + "\n" + 
							"Move " + unitsToChoose.get(i).getMaxMovePoint() + "\n" +
							"ShotRange " + unitsToChoose.get(i).getRange();
			
			labParameters[i].setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");
		}
	}
	
	private void setPointsLab(){
		labPoints.setBackground(new Color(0.7f, 0.7f, 0.7f));
		labPoints.setHorizontalAlignment(SwingConstants.CENTER);
		labPoints.setForeground(Color.black);
		labPoints.setOpaque(true);
	
		String param = "Points: " + points;

		labPoints.setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");

	}
	
	private void setBtnBack() {
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setChooseColorAndNameMode();
			}
		});
	}
	
	public void paintChooseUnitsMode() {
		
		this.setBtnChosenUnit();
		this.setBtnUnit();
		this.setPointsLab();
		
		panel.removeAll();
		panel.setLayout(new MigLayout(
				"", 
				(frame.getWidth() / 2 - 810/2) 
				+ "[]5[][]10[][]5[]60[]5[]", 
				(frame.getHeight() / 2 - 590/2) 
				+"[]5[]5[]5[]5[]5[]80[]"
			));
		
		for (int i = 0; i < 10; i++)
			panel.add(btnChosenUnit[i], "cell " + ( i / 5) + " " + (i%5 + 1) + ", width 80:80:80, height 80:80:80");
		for (int i = 0; i < 10; i++)
			panel.add(btnUnit[i], "cell " + (( i / 5)*2 + 4) + " " + (i%5 + 1) +  ", width 80:80:80, height 80:80:80");
		for (int i = 0; i < 10; i++)
			panel.add(labParameters[i], "cell " + ((i / 5)*2 + 5) + " " + (i%5 + 1) + ", width 80:80:80, height 80:80:80");
		panel.add(labPoints, "cell 3 0, width 80:120:150, height 20:30:40");
		panel.add(btnReady, "cell 2 6, width 80:120:150, height 20:30:40");
		panel.add(btnBack, "cell 3 6, width 80:120:150, height 20:30:40");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
}
