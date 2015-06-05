package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import Control.Controller;
import Model.DragNDrop;
import Model.Game;
import Model.HexMap;
import Model.HexMapElement;
import Model.Unit;
import net.miginfocom.swing.MigLayout;


public class GameMode {
	
	private HexMap map;
	private DragNDrop listener;
	
	private LinkedList<HexMapElement> listOfHexA = new LinkedList<>();
	private LinkedList<HexMapElement> listOfHexB = new LinkedList<>();
	
	private JButton btnEndTurn = new JButton("End Turn");
	private JButton btnBack = new JButton("Back");
	
	JFrame frame;
	JPanel panel;
	Game game;
	Controller controller;
	
	public GameMode(final JFrame frame, JPanel panel, Game game, Controller controller) {
		this.frame = frame;
		this.panel = panel;
		this.game = game;
		this.controller = controller;
		
		KeyStroke saveKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false);
		@SuppressWarnings("serial")
		Action saveAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
								"Are you sure to save your game?", "Save",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					saveGame();
				}
			}
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(saveKeyStroke, "S");
		frame.getRootPane().getActionMap().put("S", saveAction);
		
		KeyStroke loadKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, 0, false);
		@SuppressWarnings("serial")
		Action loadAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
								"Are you sure to load your game?", "Load",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					 loadGame();
				}
			}
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(loadKeyStroke, "L");
		frame.getRootPane().getActionMap().put("L", loadAction);
		
		
		setButtons();
		prepareGame();
	}

	
	
	public void loadGame() {
		try
	      {
			 FileInputStream loadGame = new  FileInputStream("saveGame.ser");
	         ObjectInputStream in = new ObjectInputStream(loadGame);
	         game = null;
	         game = (Game) in.readObject();
	         map = game.map;
	         controller.game = game;
	         in.close();
	         loadGame.close();
	         panel.removeMouseListener(listener);
	         panel.removeMouseMotionListener(listener);
	         listener = new DragNDrop(game.units, panel, map, game);
	 			panel.addMouseListener(listener);
	 			panel.addMouseMotionListener(listener);
	 			game.refresh();
	         System.out.println("Game loaded");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
	      }
			map.recenter(frame.getWidth(),frame.getHeight() );
	}

	protected void saveGame() {
		 try
	      {
	         FileOutputStream saveGame = new FileOutputStream("saveGame.ser");
	         ObjectOutputStream out = new ObjectOutputStream(saveGame);
	         out.writeObject(game);
	         out.close();
	         saveGame.close();
	         System.out.println("Game saved");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}

	private void createMap() {
		map = new HexMap(game);
		for (HexMapElement hex : map.hexes) {
			hex.setRandomTerrainType();
			hex.isFlag = false;
		}
		
		game.map = map;
		
		if(game.modeOfGame){
			game.flagHex = map.hexes.get( 6 + (new Random().nextInt(9)) * 13 );
			game.flagHex.isFlag = true;
		}
	}
	
	private void prepareGame() {
		
		createMap();
		game.prepareTurnList();
		
		game.tacticSet = 2;
		
		int i = 0;
		
		for(HexMapElement hex : map.hexes) {
			i++;
			hex.tacticSet = 0;
			if (i % 13 == 1 || i % 13 == 2|| i % 13 == 3) { // 1
				listOfHexA.add(hex);
				hex.tacticSet = 1;
			}
			if (i % 13 == 0 || i % 13 == 12|| i % 13 == 11) { // 2
				listOfHexB.add(hex);
				hex.tacticSet = 2;
			}
		}
		
		HexMapElement tempHex;
		
		for (Unit setUnit : game.units) {
			if (setUnit.owner == 1) {
				tempHex = listOfHexA.getFirst();
				listOfHexA.remove();
				tempHex.unit = setUnit;
				setUnit.setMyHex(tempHex);
				tempHex.occupied = true;
			} else {
				tempHex = listOfHexB.getFirst();
				listOfHexB.remove();
				tempHex.unit = setUnit;
				setUnit.setMyHex(tempHex);
				tempHex.occupied = true;
			}
		}
		if (listener != null) {
			panel.removeMouseListener(listener);
			panel.removeMouseMotionListener(listener);
		}
		listener = new DragNDrop(game.units, panel, map, game);
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);

		game.refresh();
	}
	
	private void setButtons() {
		btnEndTurn.setForeground(new Color(0f, 0f, 0f));
		btnEndTurn.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnEndTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.tacticSet != 0){
					game.tacticSet --;
					if(game.tacticSet==0){
						for(HexMapElement hex : map.hexes) {
							if(hex.unit!=null){
								hex.unit.getMyHex().tacticSet = 0;
							}
							hex.tacticSet = 0;
						}
					}
				}
				game.endTurn();
				panel.repaint();
			}
		});
		
		btnBack.setForeground(new Color(0f, 0f, 0f));
		btnBack.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setMainMenuMode();
			}
		});
	}
	

	public void paintGameMode() {
		panel.removeAll();
		panel.setLayout(new MigLayout("",
				new StringBuilder().append(frame.getWidth() / 2 - 260).append("[]20[]").toString(), 
				new StringBuilder().append( frame.getHeight()-80).append("[]2[]").toString()));
		
		panel.add(btnEndTurn, "cell 0 0, width 150:250:300, height 20:30:40");
		panel.add(btnBack, "cell 1 0, width 150:250:300, height 20:30:40");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
}
