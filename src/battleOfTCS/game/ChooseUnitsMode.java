package battleOfTCS.game;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class ChooseUnitsMode {
	
	private List<Unit> unitsToChoose;
	private List<Unit> chosenUnits = new ArrayList<Unit>();
	private boolean player1Ready;
	
	private JButton btnReady = new JButton("Ready!");
	private JButton btnBack = new JButton("Back");
	private JButton[] btnChosenUnit = new JButton[5];
	private JButton[] btnUnit = new JButton[10];
	private JButton[] btnParameters = new JButton[10];
	
	
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
		
		this.setBtnBack();
		this.setBtnChosenUnit();
		this.setBtnReady();
		this.setBtnUnit();
		this.setBtnParameters();
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
							chosenUnits.get(i).setOwner(2);
						} else {
							chosenUnits.get(i).setOwner(1);
						}
					}
					game.units.addAll(chosenUnits);
					chosenUnits.clear();
					if (player1Ready) 
						controller.setGameMode(); // true == capture flag mode
					else {
						player1Ready = true;
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
					if (chosenUnits.size() < 5) {
						chosenUnits.add(new Unit(unitsToChoose.get(ii)));
						paintChooseUnitsMode();
					}
				}
			});
		}
	}
	
	private void setBtnChosenUnit() {
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
						controller.setChooseUnitsMode();
					}
				}
			});
		}
	}
	
	private void setBtnParameters() {
		for (int i = 0; i < 10; i++) {
			btnParameters[i] = new JButton();
			btnParameters[i].setBackground(new Color(0.7f, 0.7f, 0.7f));
			btnParameters[i].setHorizontalAlignment(SwingConstants.LEFT);
			btnParameters[i].setMargin(new Insets(0,0,0,0));
			
			String param = "Health " + unitsToChoose.get(i).getHealth() + "\n" +
							"Attack " + unitsToChoose.get(i).getAttack() + "\n" + 
							"Move " + unitsToChoose.get(i).getMaxMovePoint() + "\n" +
							"ShotRange " + unitsToChoose.get(i).getRange();
			
			btnParameters[i].setText("<html>" + param.replaceAll("\\n", "<br>") + "</html>");
		}
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
		
		panel.removeAll();
		panel.setLayout(new MigLayout("", (frame.getWidth() / 2 - 325) + "[]100[]20[]60[]20[]", 
			40+"[]20[]20[]20[]20[]80[]"
			));
		
		for (int i = 0; i < 5; i++)
			panel.add(btnChosenUnit[i], "cell 0 " + i + ", width 80:80:80, height 80:80:80");
		for (int i = 0; i < 5; i++)
			panel.add(btnUnit[i], "cell " + ((i / 5) + 2) + " " + i % 5 + ", width 80:80:80, height 80:80:80");
		for (int i = 5; i < 10; i++)
			panel.add(btnUnit[i], "cell " + ((i / 5) + 3) + " " + i % 5 + ", width 80:80:80, height 80:80:80");
		for (int i = 0; i < 5; i++)
			panel.add(btnParameters[i], "cell " + ((i / 5) + 2) + " " + i % 5 + ", width 80:80:80, height 80:80:80");
		for (int i = 5; i < 10; i++)
			panel.add(btnParameters[i], "cell " + ((i / 5) + 3) + " " + i % 5 + ", width 80:80:80, height 80:80:80");
		panel.add(btnReady, "cell 1 5, width 100:150:200, height 20:30:40");
		panel.add(btnBack, "cell 2 5, width 100:150:200, height 20:30:40");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
}
