package Model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;


public class Game implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1034765971413943883L;
	private int turn;
	public String playerA;
	public String playerB;
	public String colorA;
	public String colorB;
	public Unit selectedUnit;
	public int tacticSet;
	public int turnToWin;
	public int ownerOfFlag,lastOwnerOfFlag;
	public HexMapElement flagHex;
	public HexMapElement selectedHex;
	public boolean modeOfGame;
	private int win;
	public List<Unit> units = new LinkedList<>();
	public LinkedList<Unit> turnList;
	public HexMap map = new HexMap(this);
	public Integer points;

	public final static Image imgBoard150 = new ImageIcon("images/board150.png").getImage();
	public final static Image imgBoard200 = new ImageIcon("images/board200.png").getImage();
	public final static Image imgBoard250 = new ImageIcon("images/board250.png").getImage();
	public final static Image imgBoard360 = new ImageIcon("images/board360.png").getImage();

	private static Font smallFont = new Font("Arial", Font.BOLD, 15);
	private static Font mediumFont = new Font("Arial", Font.BOLD, 25);
	private static Font bigFont = new Font("Arial", Font.BOLD, 40);
	
	public Game() {
		win = 0;
		turnToWin = 42;
		ownerOfFlag = 42;
		lastOwnerOfFlag = 41;
		turnList = new LinkedList<Unit>();
	}
	
	public Game(LinkedList<Unit> unitsList, HexMap map) {
		win = 0;
		turnToWin=42;
		ownerOfFlag=42;
		lastOwnerOfFlag=41;
		turnList = new LinkedList<Unit>();
		this.units = unitsList;
		this.map = map;
		for (Unit unit : units) {
			unit.rest();
		}
		turn = 1;
		for (Unit unit : units) {
			if (unit.owner == 1)
				turnList.add(unit);
		}
	}
	
	public void prepareTurnList() {
		turnList = new LinkedList<Unit>();
		for (Unit unit : units) {
			unit.rest();
		}
		turn = 1;
		for (Unit unit : units) {
			if (unit.owner == 1)
				turnList.add(unit);
		}
	}

	public void refresh() { // need to refresh panel
		for (Unit unit : units) {
			if (unit.health <= 0) {
				unit.getMyHex().occupied = false;
				unit.getMyHex().unit = null;
				units.remove(unit);
				break;
			}
		}
		if (turnList.isEmpty()) {
			for (Unit unit : units) {
				unit.rest();
			}
			if (turn == 1) {
				turn = 2;
				for (Unit addme : units)
					if (addme.owner == 2)
						turnList.add(addme);
				if (turnList.isEmpty())
					win = 1;
			} else {
				turn = 1;
				for (Unit addme : units)
					if (addme.owner == 1)
						turnList.add(addme);
				if (turnList.isEmpty())
					win = 2;
			}
			if(flagHex!=null){
				if(flagHex.unit!=null){
					ownerOfFlag = flagHex.unit.owner;
					if(lastOwnerOfFlag!=ownerOfFlag){
						turnToWin=10;
						lastOwnerOfFlag=ownerOfFlag;
					}
					else{
						--turnToWin;
					}
				}
				else{
					turnToWin=42;
					lastOwnerOfFlag=42;
				}
				if(turnToWin==0){
					win = ownerOfFlag;
				
				}
			}
			
		}
		int winA, winB;
		winA = 1;
		winB = 1;
		for (Unit unit : units) {
			if (unit.owner == 1)
				winB = 0;
			if (unit.owner == 2)
				winA = 0;
		}
		if (winA == 1)
			win = 1;
		if (winB == 1)
			win = 2;

		for (Unit unit : turnList) {
			for (HexMapElement hex : map.hexes)
				if (hex.unit != null && hex.unit.equals(unit))
					hex.isGreen = true;
		}
	}

	public void endTurn() {
		turnList.clear();
		for (HexMapElement hexik : map.hexes) {
			hexik.clearColor();
			hexik.isShadow = false;
		}
		refresh();
	}

	public void paint(Graphics g) {
		g.drawImage(imgBoard360,
				(int) (HexMap.width - imgBoard360.getWidth(null)) / 2, 0, null);
		g.setColor(new Color(255, 255, 255));
		if (win != 0) {
			g.setFont(mediumFont);
			if (win == 1)
				g.drawString(
						playerA + " win!",
						(int) (HexMap.width - g.getFontMetrics().stringWidth(
								playerA + "win!")) / 2, 50);
			else
				g.drawString(
						playerB + " win!",
						(int) (HexMap.width - g.getFontMetrics().stringWidth(
								playerB + "win!")) / 2, 50);
		} else {
			g.setFont(bigFont);
			if (turn == 1)
				g.drawString(playerA, (int) (HexMap.width - g.getFontMetrics()
						.stringWidth(playerA)) / 2, 50);
			else
				g.drawString(playerB, (int) (HexMap.width - g.getFontMetrics()
						.stringWidth(playerB)) / 2, 50);
			if(turnToWin<=10){
				g.setFont(smallFont);
				if(ownerOfFlag==1)
					g.drawString("Left " + turnToWin + " turn to " + playerA + " win", (int) (HexMap.width - g.getFontMetrics()
						.stringWidth("Left " + turnToWin + " turn to " + playerA + " win")) / 2, 70);
				else
					g.drawString("Left " + turnToWin + " turn to " + playerB + " win", (int) (HexMap.width - g.getFontMetrics()
							.stringWidth("Left " + turnToWin + " turn to " + playerB + " win")) / 2, 70);
			}
		}
		g.drawImage(imgBoard200, 5, 5, null);
		g.drawImage(imgBoard200, (int) HexMap.width * 4 / 5 - 10, 5, null);
		g.setFont(smallFont);
		g.setColor(new Color(255, 255, 255));
		if (selectedUnit != null) {
			g.drawString(
					"Unit owner " + (selectedUnit.owner == 1  ? playerA : playerB),
					30, 30);
			g.drawString("Atack: " + selectedUnit.attack, 30, 40);
			g.drawString("Move: " + selectedUnit.getMaxMovePoint(), 30, 50);
			g.drawString("Shot range: " + selectedUnit.getRange(), 30, 60);
		}
		if (selectedHex != null) {
			String terrain;
			switch (selectedHex.terrainType) {
			case 1:
				terrain = "grass";
				break;
			case 2:
				terrain = "hill";
				break;
			case 3:
				terrain = "forest";
				break;
			default:
				terrain = "???";
			}
			g.drawString("Terrain type: " + terrain,
					(int) HexMap.width * 4 / 5, 30);
			g.drawString("Distance: " + selectedHex.distance,
					(int) HexMap.width * 4 / 5, 40);
			if(selectedHex.isFlag)
				g.drawString("Flag",(int) HexMap.width * 4 / 5, 50);
			
		}
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turnset) {
		turn = turnset;
	}

}
