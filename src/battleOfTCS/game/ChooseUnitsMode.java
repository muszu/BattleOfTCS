package battleOfTCS.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class ChooseUnitsMode {
	
	List<Unit> unitsToChoose;
	List<Unit> chosenUnits = new ArrayList<Unit>();
	public boolean player1Ready;

	
	JTextField nameA, nameB;
	
	JFrame frame;
	JPanel panel;
	Modes modes;
	Game game;
	
	
	JButton btnReady = new JButton("Ready!");
	JButton btnBack = new JButton("Back");
	JButton[] btnChosenUnit = new JButton[5];
	JButton[] btnUnit = new JButton[10];
	
	
	
	public ChooseUnitsMode(JFrame frame, JPanel panel, Game game, Modes modes) {
		this.frame = frame;
		this.panel = panel;
		this.game = game;
		this.modes = modes;
		
		unitsToChoose = Unit.createUnitLists(game.colorA);	
		
		this.setBtnBack();
		this.setBtnChosenUnit();
		this.setBtnReady();
		this.setBtnUnit();
	}
	
	public void repaintChooseUnitsMode() {
	
		this.setBtnChosenUnit();
		this.setBtnUnit();
		
		panel.removeAll();
		panel.setLayout(new MigLayout("", (frame.getWidth() / 2 - 325) + "[]100[]20[]60[]20[]", 
			40+"[]20[]20[]20[]20[]80[]"
			));
		
		for (int i = 0; i < 5; i++)
			panel.add(btnChosenUnit[i], "cell 0 " + i
					+ ", width 80:80:80, height 80:80:80");
		for (int i = 0; i < 10; i++)
			panel.add(btnUnit[i], "cell " + ((i / 5) + 3) + " " + i % 5
					+ ", width 80:80:80, height 80:80:80");
		panel.add(btnReady, "cell 1 5, width 100:150:200, height 20:30:40");
		panel.add(btnBack, "cell 2 5, width 100:150:200, height 20:30:40");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
	
	public void setBtnReady() {
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
							chosenUnits.get(i).setOwner(2);
						} else {
							chosenUnits.get(i).setOwner(1);
						}
					}
					game.units.addAll(chosenUnits);
					chosenUnits.clear();
					if (player1Ready) 
						modes.setGameMode(); // true == capture flag mode
					else {
						player1Ready = true;
						unitsToChoose = Unit.createUnitLists(game.colorB);
						repaintChooseUnitsMode();
					}
				}
			}
		});
	}
	
	
	public void setBtnUnit() {
		for (int i = 0; i < 10; i++) {
			btnUnit[i] = new JButton();
			btnUnit[i].setBackground(new Color(0.7f, 0.7f, 0.7f));
			btnUnit[i].setIcon(unitsToChoose.get(i).getIcon());
			final int ii = i;
			btnUnit[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chosenUnits.size() < 5) {
						chosenUnits.add(new Unit(unitsToChoose.get(ii)));
						repaintChooseUnitsMode();
					}
				}
			});
		}
	}
	
	
	public void setBtnChosenUnit() {
		for (int i = 0; i < 5; i++) {
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
						chosenUnits.remove(ii);
						modes.setChooseUnitsMode();
					}
				}
			});
		}
	}
	
	
	
	public void setBtnBack() {
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modes.setMainMenuMode();
			}
		});
	}

}
