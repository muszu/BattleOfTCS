package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	
	private JButton btnBackToMenu = new JButton("Back to menu");
	private JButton btnLoadGame = new JButton("Load game");
	private JButton btnSaveGame = new JButton("Save game");
	private JButton btnEndTurn = new JButton("End Turn");
	
	JFrame frame;
	JPanel panel;
	Game game;
	Controller controller;
	
	public GameMode(final JFrame frame, JPanel panel, Game game, Controller controller) {
		this.frame = frame;
		this.panel = panel;
		this.game = game;
		this.controller = controller;
		DragNDrop.gameMode = this;
		setButtons();
		prepareGame();
	}

	
	
	public boolean loadGame() {
		try {
		
			FileInputStream loadGame = new  FileInputStream("saveGame.ser");
			ObjectInputStream in = new ObjectInputStream(loadGame);
			game = null;
			game = (Game) in.readObject();
			map = game.map;
			controller.game = game;
			DragNDrop.gameMode = this;
			in.close();
			loadGame.close();
			panel.removeMouseListener(listener);
			panel.removeMouseMotionListener(listener);
			listener = new DragNDrop(game.units, panel, map, game);
			panel.addMouseListener(listener);
			panel.addMouseMotionListener(listener);
			game.refresh();
		} catch(IOException i) {
			game.win=0;
			JOptionPane.showMessageDialog(null, "Loading failed", "ok", JOptionPane.ERROR_MESSAGE);
			//i.printStackTrace();
			return false;
		} catch (ClassNotFoundException e1) {
			game.win=0;
			JOptionPane.showMessageDialog(null, "Loading failed", "ok", JOptionPane.ERROR_MESSAGE);
			//e1.printStackTrace();
			return false;
		}
		map.recenter(frame.getWidth(), frame.getHeight());
		return true;
	}

	protected void saveGame() {
		try {
			FileOutputStream saveGame = new FileOutputStream("saveGame.ser");
			ObjectOutputStream out = new ObjectOutputStream(saveGame);
			out.writeObject(game);
			out.close();
			saveGame.close();
	      
		} catch(IOException i) {
			JOptionPane.showMessageDialog(null, "Saving failed", "ok", JOptionPane.ERROR_MESSAGE);
			//i.printStackTrace();
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
		
		btnSaveGame.setForeground(new Color(0f, 0f, 0f));
		btnSaveGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure to save your game? It will override last save.", "Save",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
					saveGame();
			}
		});
		
		btnLoadGame.setForeground(new Color(0f, 0f, 0f));
		btnLoadGame.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure to load your game?", "Load",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
					loadGame();
			}
		});
		
		btnBackToMenu.setForeground(new Color(0f, 0f, 0f));
		btnBackToMenu.setBackground(new Color(0.7f, 0.7f, 0.7f));
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure to go back to menu. \n If You didn't save your game all progress will be lost.", "Yes",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
					controller.setMainMenuMode();
			}
		});
	}
	
	public void victoryMessage(){
		if(game.playerA == null || game.playerB == null){
			controller.setMainMenuMode();
			return;
		}
		Object options[] = {"End Game"};
		String message;
		if (game.win == 1)
			message = game.playerA + " won!";
		else
			message = game.playerB + " won!";
		int jOptionResult = JOptionPane.showOptionDialog(frame, message, 
				"Victory!", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, 
				options, options[0]);
		if(jOptionResult == -1 || jOptionResult == 0)
			controller.setMainMenuMode();
	}

	public void paintGameMode() {
		panel.removeAll();
		panel.setLayout(new MigLayout("",
				frame.getWidth() / 2 - (150*4+3*20)/2 + "[]30[]30[]30[]", 
				(int) HexMap.firstHexCenterY+7*HexMapElement.height+20 + "[]"));
		
		panel.add(btnBackToMenu, "cell 0 0, width 150:150:300, height 20:30:40");
		panel.add(btnLoadGame, "cell 1 0, width 150:150:300, height 20:30:40");
		panel.add(btnSaveGame, "cell 2 0, width 150:150:300, height 20:30:40");
		panel.add(btnEndTurn, "cell 3 0, width 150:150:300, height 20:30:40");
		
		panel.invalidate();
		panel.validate();
		panel.repaint();
	}
}
