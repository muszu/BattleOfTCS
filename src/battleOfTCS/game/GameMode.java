package battleOfTCS.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	public GameMode(JFrame frame, JPanel panel, Game game, Controller controller) {
		this.frame = frame;
		this.panel = panel;
		this.game = game;
		this.controller = controller;
		
		setButtons();
		prepareGame();
	}

	private void createMap() {
		map = new HexMap(game);
		for (HexMapElement hex : map.hexes) {
			hex.setRandomTerrainType();
			hex.setFlag(false);
		}
		
		game.map = map;
		
		if(game.modeOfGame){
			game.flagHex = map.hexes.get( 6 + (new Random().nextInt(9)) * 13 );
			game.flagHex.setFlag(true);
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
