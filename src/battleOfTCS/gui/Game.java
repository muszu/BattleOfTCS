package battleOfTCS.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

//import java.awt.Image;
//import javax.swing.ImageIcon;

public class Game {
	private static int turn;
	private String playerA;
	private String playerB;
	public static Unit selectedUnit;
	private int win;
	private LinkedList<Unit> units;
	public LinkedList<Unit> turnList;
	
	/*ImageIcon boardIcon = new ImageIcon(Menu.class.getResource("images/units/hus1test.png"));
    Image boardImg = testImgIcon.getImage();*/
	
	public Game(LinkedList<Unit> unitsList){
		win=0;
		turnList = new LinkedList<Unit>();
		this.units=unitsList;
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
		if(turnList.isEmpty()){
			System.out.println("Change!");
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
		for(Unit unit : units){
			if(unit.getHealth()<=0){
				unit.getMyHex().occupied=false;
				unit.getMyHex().unit=null;
				units.remove(unit);
				turnList.remove(unit);
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
	}
	
	
	
	
	public void paint(Graphics g){
		g.setColor(new Color(90, 90, 90));
		g.fillRect(412, 20, 200, 40);
		g.setColor(new Color(255, 255, 255));
		if(win!=0)
			g.drawString( win == 1 ? playerA + "'s vicotry!" : playerB + "'s vicotry!", 512, 40);
		else
			g.drawString( turn == 1 ? playerA : playerB, 512, 40);
		g.setColor(new Color(90, 90, 90));
		g.fillRect(20, 20, 130, 50);
		g.setColor(new Color(255, 255, 255));
		if(selectedUnit!=null){
			g.drawString("Unit owner " + this.getOwnerName(selectedUnit.getOwner()), 30, 30);
			g.drawString("Atack: " + selectedUnit.getAttack(), 30, 40);
			g.drawString("Move: " + selectedUnit.getMaxMovePoint(), 30, 50);
			g.drawString("Shot range: " + selectedUnit.getRange(), 30, 60);
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
