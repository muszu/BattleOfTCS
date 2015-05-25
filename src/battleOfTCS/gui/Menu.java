package battleOfTCS.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

public class Menu {
	public static int mode;

	static LinkedList<Unit> units = new LinkedList<Unit>();
	static ArrayList<Unit> UnitsToChoose = new ArrayList<Unit>();
	static ArrayList<Unit> ChosenUnits = new ArrayList<Unit>();
	public boolean Player1Ready;
	public HexMap map;
	private JFrame frame;
	private Game game;
	private DragNDrop listener;
	private String colorA = "black";
	private String colorB = "red";
	private String playerA = "TCS";
	private String playerB = "Free Folk";

	/**
	 * Launch the Battle Of TCS.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mode = 1;
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Menu() {
		initialize();
	}

	void mode2(final JPanel panel) {
		mode = 2;
		panel.removeAll();
		
		Options.setFields(panel, frame, this);
		Options.repaintOptions();
	}

	void mode4(final JPanel panel) {
		mode = 4;
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout("", "390[]", "600[]20[]20[]20[]"));
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeMenu(panel);
			}
		});
		panel.add(btnBack, "cell 0 2, width 150:250:300, height 30:50:80");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}

	void modeMenu(final JPanel panel) {
		mode = 1;
		panel.removeAll();

		JButton btnNewGame = new JButton("New game");
		btnNewGame.setForeground(new Color(0f, 0f, 0f));
		btnNewGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Player1Ready = false;
				modePrepareGame(panel);
			}
		});

		JButton btnLoadGame = new JButton("Load game");
		btnLoadGame.setForeground(new Color(0f, 0f, 0f));
		btnLoadGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnitToNewGame();
				modeGame(panel, true);
			}
		});

		JButton btnOptions = new JButton("Options");
		btnOptions.setForeground(new Color(0f, 0f, 0f));
		btnOptions.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode2(panel);
			}
		});

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(new Color(0f, 0f, 0f));
		btnExit.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane
						.showConfirmDialog(frame,
								"Are you sure to end your game?", "Exit",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		panel.setLayout(new MigLayout("", new StringBuilder()
				.append(frame.getWidth() / 2 - 125).append("[]").toString(),
				new StringBuilder().append(frame.getHeight() - 400)
						.append("[]20[]20[]20[]").toString()));
		panel.add(btnNewGame, "cell 0 0, width 150:250:300, height 30:50:80");
		panel.add(btnLoadGame, "cell 0 1, width 150:250:300, height 30:50:80");
		panel.add(btnOptions, "cell 0 2, width 150:250:300, height 30:50:80");
		panel.add(btnExit, "cell 0 3, width 150:250:300, height 30:50:80");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}

	void modeGame(final JPanel panel, boolean flagMode) {  // true == capture flag
		LinkedList<HexMapElement> listOfHexA, listOfHexB;
		listOfHexA = new LinkedList<>();
		listOfHexB = new LinkedList<>();
		HexMapElement tempHex;
		for (HexMapElement tmp : map.hexes) {
			tmp.setRandomTerrainType();
			tmp.isFlag = false;
		}
		mode = 3;
		game = new Game(units, map, playerA, playerB); 
		Game.modeOfGame = flagMode;
		Game.tacticSet=2;
		if(flagMode){
			Game.flagHex = map.hexes.get( 6+(new Random().nextInt(9))*13);
			Game.flagHex.isFlag = true;
		}
		int i;
		i = 0;
		for (HexMapElement hex : map.hexes) {
			i++;
			hex.tacticSet=0;
			if (i % 13 == 1 || i % 13 == 2|| i % 13 == 3) { // 1
				listOfHexA.add(hex);
				hex.tacticSet=1;
			}
			if (i % 13 == 0 || i % 13 == 12|| i % 13 == 11) { // 2
				listOfHexB.add(hex);
				hex.tacticSet=2;
			}

		}
		for (Unit setUnit : units) {
			if (setUnit.getOwner() == 1) {
				tempHex = listOfHexA.getFirst();
				listOfHexA.remove();
				tempHex.unit = setUnit;
				setUnit.setMyHex(tempHex);
				tempHex.occupied = true;
				setUnit.setX(tempHex.getCenterX() - setUnit.getWidth() / 2);
				setUnit.setY(tempHex.getCenterY() - setUnit.getHeight() / 2);
			} else {
				tempHex = listOfHexB.getFirst();
				listOfHexB.remove();
				tempHex.unit = setUnit;
				setUnit.setMyHex(tempHex);
				tempHex.occupied = true;
				setUnit.setX(tempHex.getCenterX() - setUnit.getWidth() / 2);
				setUnit.setY(tempHex.getCenterY() - setUnit.getHeight() / 2);
			}
		}
		if (listener != null) {
			panel.removeMouseListener(listener);
			panel.removeMouseMotionListener(listener);
		}
		listener = new DragNDrop(units, panel, map, game);
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);

		game.refresh();
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout("",
				new StringBuilder().append(frame.getWidth() / 2 - 260).append("[]20[]").toString(), 
				new StringBuilder().append( frame.getHeight()-80).append("[]2[]").toString()));

		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.setForeground(new Color(0f, 0f, 0f));
		btnEndTurn.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnEndTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Game.tacticSet!=0){
					Game.tacticSet--;
				}
				game.endTurn();
				panel.repaint();
			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				units.clear();
				map.clear();
				modeMenu(panel);
				panel.repaint();
			}
		});
		panel.add(btnEndTurn, "cell 0 0, width 150:250:300, height 20:30:40");
		panel.add(btnBack, "cell 1 0, width 150:250:300, height 20:30:40");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}


	void modePrepareGame(final JPanel panel){
		mode = 2;
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout(
				"", 
				(frame.getWidth()/ 2 - 320)+"[]20[]20[]20[]", 
				(frame.getHeight()/2 - 100)+"[]50[]100[]"
			));
		
		
		String [] colors = {"black", "red", "green", "blue", "white", "yellow"};
		JComboBox<String> colorListA = new JComboBox<String>(colors);
		colorListA.setForeground(new Color(0f, 0f, 0f));
		colorListA.setBackground(new Color(0.7f, 0.7f, 0.7f));
		colorListA.setSelectedIndex(0);
		colorListA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        JComboBox<String> cb = (JComboBox<String>)e.getSource();
				colorA = (String) cb.getSelectedItem();
			}
		});
		JComboBox<String> colorListB = new JComboBox<String>(colors);
		colorListB.setForeground(new Color(0f, 0f, 0f));
		colorListB.setBackground(new Color(0.7f, 0.7f, 0.7f));
		colorListB.setSelectedIndex(1);
		colorListB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
		        JComboBox<String> cb = (JComboBox<String>)e.getSource();
				colorB = (String) cb.getSelectedItem();
			}
		});

		JTextField nameA = new JTextField("Player's A name", 1);
		nameA.setForeground(new Color(0f, 0f, 0f));
		nameA.setBackground(new Color(0.7f, 0.7f, 0.7f));
		
		JTextField nameB = new JTextField("Player's B name", 1);
		nameB.setForeground(new Color(0f, 0f, 0f));
		nameB.setBackground(new Color(0.7f, 0.7f, 0.7f));
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeMenu(panel);
			}
		});
		JButton btnChooseUnits = new JButton("Choose Units");
		btnChooseUnits.setForeground(new Color(0f, 0f, 0f));
		btnChooseUnits.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnChooseUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeChooseUnits(panel);
			}
		});
		
		panel.add(nameA, "cell 0 0, width 120:150:170, height 20:30:40");
		panel.add(nameB, "cell 3 0, width 120:150:170, height 20:30:40");
		panel.add(colorListA, "cell 0 1, width 120:150:170, height 20:30:40");
		panel.add(colorListB, "cell 3 1, width 120:150:170, height 20:30:40");
		panel.add(btnBack, "cell 2 2, width 100:120:140, height 20:30:40");
		panel.add(btnChooseUnits, "cell 1 2, width 100:120:140, height 20:30:40");
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
	
	void modeChooseUnits(final JPanel panel) {
		mode = 2;
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout(
				"", 
				(frame.getWidth() / 2 - 325) + "[]100[]20[]60[]20[]", 
				40+"[]20[]20[]20[]20[]80[]"
			));
		createUnitLists(colorA);
		JButton btnReady = new JButton("Ready!");
		btnReady.setForeground(new Color(0f, 0f, 0f));
		btnReady.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ChosenUnits.size() == 0) {
					JOptionPane.showConfirmDialog(frame, "No units chosen",
							"Error", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}

				else {
					for (int i = 0; i < ChosenUnits.size(); i++) {
						if (Player1Ready) {
							ChosenUnits.get(i).setOwner(2);
						} else {
							ChosenUnits.get(i).setOwner(1);
						}
					}
					units.addAll(ChosenUnits);
					ChosenUnits.clear();
					if (Player1Ready)
						modeGame(panel,false); // true == capture flag mode
					else {
						Player1Ready = true;
						UnitsToChoose.clear();
						createUnitLists(colorB);
						modeChooseUnits(panel);
					}
				}
			}
		});
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeMenu(panel);
			}
		});
		JButton[] btnChosenUnit = new JButton[5];
		for (int i = 0; i < 5; i++) {
			btnChosenUnit[i] = new JButton();
			btnChosenUnit[i].setForeground(new Color(0f, 0f, 0f));
			btnChosenUnit[i].setBackground(new Color(0.7f, 0.7f, 0.7f));

			if (ChosenUnits.size() <= i) {
				btnChosenUnit[i].setIcon(null);
				btnChosenUnit[i].setText("Empty");
			} else {
				btnChosenUnit[i].setText("");
				btnChosenUnit[i].setIcon(ChosenUnits.get(i).getIcon());
			}
			final int ii = i;
			btnChosenUnit[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ChosenUnits.size() > ii) {
						ChosenUnits.remove(ii);
						modeChooseUnits(panel);
					}
				}
			});
		}
		JButton[] btnUnit = new JButton[10];
		for (int i = 0; i < 10; i++) {
			btnUnit[i] = new JButton();
			btnUnit[i].setBackground(new Color(0.7f, 0.7f, 0.7f));
			btnUnit[i].setIcon(UnitsToChoose.get(i).getIcon());
			final int ii = i;
			btnUnit[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ChosenUnits.size() < 5) {
						ChosenUnits.add(new Unit(UnitsToChoose.get(ii)));
						modeChooseUnits(panel);
					}
				}
			});
		}

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
	

	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(0, 0, 1024, 768);
		frame.setResizable(false);
		frame.setUndecorated(false);

		HexMap.width = frame.getWidth();
		HexMap.height = frame.getHeight();
		HexMap.firstHexCenterX = (int) (HexMap.width - HexMapElement.width
				* HexMap.getAmountInOddRow()) / 2;
		map = new HexMap();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0, false);
		@SuppressWarnings("serial")
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane
						.showConfirmDialog(frame,
								"Are you sure to end your game?", "Exit",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		};

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escapeKeyStroke, "ESCAPE");
		frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);

		final Image backGround = new ImageIcon(
				Menu.class.getResource("images/backbroundmenuv3.jpg"))
				.getImage();
		final Image backGroundGame = new ImageIcon(
				Menu.class.getResource("images/backbroundmenuv2.jpg")).getImage();
		final Image backGroundPrim = new ImageIcon(
				Menu.class.getResource("images/backbroundmenuprim.png"))
				.getImage();
		final Image olaBackGround = new ImageIcon(
				Menu.class.getResource("images/background.png")).getImage();

		@SuppressWarnings("serial")
		class JPanelBackground extends JPanel {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (backGround != null) {
					if (mode == 2)
						g.drawImage(backGroundGame, 0, 0, this.getWidth(),
								this.getHeight(), this);
					if (mode == 1)
						g.drawImage(backGround, 0, 0, this.getWidth(),
								this.getHeight(), this);
					if (mode == 3) {
						g.drawImage(olaBackGround, 0, 0, this.getWidth(),
								this.getHeight(), this);
						for (HexMapElement hex : map.hexes)
							hex.drawIt(g);
						for (Unit unit : units)
							unit.drawIt(g, false);
						for (Unit unit : units)
							unit.drawIt(g, true);
						game.paint(g);
					}
					if (mode == 4)
						g.drawImage(backGroundPrim, 0, 0, this.getWidth(),
								this.getHeight(), this);
				}
			}
		}

		final JPanel panel = new JPanelBackground();
		frame.add(panel);
		modeMenu(panel);
	}

	static void createUnitLists(String colorOfUnit) {
		ImageIcon testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/small/" + colorOfUnit
						+ "/M - Griffin.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 200, 1, 4, 60));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/M - Gnoll.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 100, 1, 2, 30));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/hus1.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 120, 1, 3, 40));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/Fighter - Scimitar2.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 140, 1, 2, 25));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/Lantern2.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 160, 1, 2, 40));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/M - Ogre.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 180, 1, 2, 50));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/swordnshield1.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 150, 1, 2, 30));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/Fighter - Bow.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 100, 7, 2, 20));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/Mage1.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 100, 8, 2, 50));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/small/"
				+ colorOfUnit + "/M - Hydra.png"));
		UnitsToChoose.add(new Unit(testImgIcon, 200, 2, 2, 50));
	}

	void UnitToNewGame() {
		units.clear();
		ImageIcon testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/hus1test.png"));
		Image testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 625, 390, 50, 7, 6, 25, 2));
		testImg = new ImageIcon(
				Menu.class.getResource("images/units/MrKozikMaster.png"))
				.getImage();
		units.add(new Unit(testImg, 305, 220, 999, 1, 5, 82, 1));
		testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/deer1test.png"));
		testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 675, 320, 43, 5, 3, 20, 2));
		testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/Szczypka.png"));
		testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 300, 100, 50, 7, 4, 30, 1));
		testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/goat2.png"));
		testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 660, 170, 100, 1, 2, 40, 2));
		testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/havycav2.png"));
		testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 335, 400, 100, 4, 3, 60, 1));
		testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/Lantern3.png"));
		testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 300, 300, 100, 2, 3, 20, 1));
		testImgIcon = new ImageIcon(
				Menu.class.getResource("images/units/deer.png"));
		testImg = testImgIcon.getImage();
		units.add(new Unit(testImg, 730, 600, 100, 2, 3, 20, 2));

	}
}
