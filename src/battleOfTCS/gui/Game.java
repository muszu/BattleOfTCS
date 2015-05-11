package battleOfTCS.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;


public class Game {
	private static int turn;
	private String playerA;
	private String playerB;
	public static Unit selectedUnit;
	public static HexMapElement selectedHex;
	private int win;
	private LinkedList<Unit> units;
	public LinkedList<Unit> turnList;
	private HexMap map;
	
	public final static Image imgBoard150 =new ImageIcon(Menu.class.getResource("images/board150.png")).getImage();
	public final static Image imgBoard200 =new ImageIcon(Menu.class.getResource("images/board200.png")).getImage();
	public final static Image imgBoard250 =new ImageIcon(Menu.class.getResource("images/board250.png")).getImage();
	
	private static Font smallFont = new Font ("Arial", Font.BOLD, 15);
	private static Font bigFont = new Font ("Arial", Font.BOLD, 40);
	
	public Game(LinkedList<Unit> unitsList, HexMap map){
		win=0;
		turnList = new LinkedList<Unit>();
		this.units=unitsList;
		this.map = map;
		for(Unit unit : units){
			unit.rest();
		}
		playerA="TCS";
		playerB="Free Folk";
		turn=1;
		for(Unit unit : units){
			if(unit.getOwner()==1)
				turnList.add(unit);
		}
	}
	
	public void refresh(){ //need to refresh panel
		for(Unit unit : units){
			if(unit.getHealth()<=0){
				unit.getMyHex().occupied=false;
				unit.getMyHex().unit=null;
				units.remove(unit);
				break;
			}
		}
		if(turnList.isEmpty()){
			for(Unit unit : units){
				unit.rest();
			}
			if(Game.turn==1){
				Game.turn=2;
				for(Unit addme : units)
					if(addme.getOwner()==2)
						turnList.add( addme);
				if(turnList.isEmpty())
					win=1;
			}
			else{
				Game.turn=1;
				for(Unit addme : units)
					if(addme.getOwner()==1)
						turnList.add(addme);
				if(turnList.isEmpty())
					win=2;
			}
		}
		int winA,winB;
		winA=1;
		winB=1;
		for(Unit unit : units){
			if(unit.getOwner()==1)
				winB=0;
			if(unit.getOwner()==2)
				winA=0;
		}
		if(winA==1)
			win=1;
		if(winB==1)
			win=2;
		
		for(Unit unit : turnList) {
			for(HexMapElement hex : map.hexes)
				if(hex.unit != null && hex.unit.equals(unit))
					hex.green(true);
		}
		
	}
	
	public void endTurn(){
		turnList.clear();
		for(HexMapElement hexik : map.hexes){
			hexik.clearColor();
			hexik.shadow(false);
		}
		refresh();
	}
	
	
	
	public void paint(Graphics g){
		g.setColor(new Color(90, 90, 90));
		
		g.setFont(bigFont);
		
		g.drawImage(imgBoard250, 405, 0, null);
		g.setColor(new Color(255, 255, 255));
		if(win!=0)
			g.drawString( win == 1 ? playerA + "'s vicotry!" : playerB + "'s vicotry!", 460, 50);
		else{
			if(turn == 1)
				g.drawString( playerA , 502-(playerA.length()*6), 50);
			else
				g.drawString(  playerB, 502-(playerB.length()*6), 50);
		}
		g.setColor(new Color(90, 90, 90));
		g.drawImage(imgBoard200, 5, 5,null);
		
		g.setFont(smallFont);
		
		g.setColor(new Color(255, 255, 255));
		if(selectedUnit!=null){
			g.drawString("Unit owner " + this.getOwnerName(selectedUnit.getOwner()), 30, 30);
			g.drawString("Atack: " + selectedUnit.getAttack(), 30, 40);
			g.drawString("Move: " + selectedUnit.getMaxMovePoint(), 30, 50);
			g.drawString("Shot range: " + selectedUnit.getRange(), 30, 60);
		}
		else if(selectedHex != null){
			g.drawString("Terrain type: " + selectedHex.getTerrainType(), 30, 30);
			g.drawString("Distance: " + selectedHex.getDistance(), 30, 40);
		}
	}

	public String getOwnerName(int x){
		if(x==1)
			return playerA;
		if(x==2)
			return playerB;
		return "???";
	}


	public int getTurn() {
		return turn;
	}
	public void setTurn(int turnset) {
		Game.turn = turnset;
	}
	
}
