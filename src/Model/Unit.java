package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import View.JPanelBackground;


public class Unit implements GameObject, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5689501156384474474L;
	private ImageIcon icon;
	private ImageIcon img;
	private int x;
	private int y;
	public int attack;
	private int maxHealth;
	public int health;
	public int owner;
	private int maxMovePoint;
	private int movePoint;
	private int range;
	public int cost;
	private HexMapElement myHex;

	final int GRASS = 1;
	final int HILL = 2;
	final int FOREST = 3;

	public Unit(Unit unit) {
		this.icon = unit.icon;
		this.img = unit.icon;
		this.maxHealth = unit.maxHealth;
		this.health = unit.maxHealth;
		this.range = unit.range;
		this.maxMovePoint = unit.maxMovePoint;
		this.attack = unit.attack;
		this.owner = unit.owner;
		this.cost = unit.cost;
	}

	public Unit(ImageIcon img, int x, int y, int maxHealth, int range,
			int maxMovePoint, int attack, int owner) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.range = range;
		this.maxMovePoint = maxMovePoint;
		this.attack = attack;
		this.owner = owner;
	}

	public Unit(ImageIcon icon, int maxHealth, int range, int maxMovePoint,
			int attack, int cost) {
		this.icon = icon;
		this.img = icon;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.range = range;
		this.maxMovePoint = maxMovePoint;
		this.attack = attack;
		this.cost = cost;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public Image getImage() {
		return img.getImage();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getWidth() {
		return img.getIconWidth();
	}

	public int getHeight() {
		return img.getIconHeight();
	}

	public void drawIt(Graphics g, Boolean printHealth) {
		int przes = 0;
		if (img.getIconWidth() > 100) {
			przes = 40;
		}
		if (printHealth) {
			StringBuilder life = new StringBuilder().append(health).append("/")
					.append(maxHealth);
			g.setColor(new Color(0, 0, 0));
			g.fillRect(x + przes, y, 80, 12);
			g.setColor(new Color(100, 0, 0));
			g.fillRect(x + 1 + przes, y + 1, 78, 10);
			g.setColor(new Color(0, 100, 0));
			g.fillRect(x + 1 + przes, y + 1, (health * 78) / maxHealth, 10);
			g.setColor(new Color(255, 255, 255));
			g.drawString(life.toString(), (40 - life.length() * 3) + x + przes,
					y + 11);
		}
		else{
			g.drawImage(img.getImage(), x, y, null);
		}
		
	}
	

	public int getMaxMovePoint() {
		return maxMovePoint;
	}

	public void setMaxMovePoint(int maxMovePoint) {
		this.maxMovePoint = maxMovePoint;
		this.movePoint = maxMovePoint;
	}

	public int getMovePoint() {
		return movePoint;
	}

	public int getRange() {
		return range;
	}

	void substractMovePoint(int substracter) {
		movePoint = movePoint--;
	}

	public void rest() {
		movePoint = maxMovePoint;
	}

	public String toString() {
		return String.valueOf(owner);
	}

	public void attack(Unit attacker) {
		int hit = (int) attacker.attack - ( attacker.getMaxMovePoint() / attacker.getMovePoint() ) / 2 ;
		// if unit's range is not greater than 1 that means its a meele unit
		if (hit == 0)
			hit = 1;
		if (attacker.range > 1) {
			if (myHex.terrainType == FOREST)
				health -= hit * 0.5;
			else
				health -= hit;
		} else {
			if (hit == 0)
				hit = 1;
			if (myHex.terrainType == HILL)
				health -= hit * 0.6;
			else if (myHex.terrainType == FOREST)
				health -= hit * 0.8;
			else
				health -= hit;
		}
	}

	public HexMapElement getMyHex() {
		return myHex;
	}

	public void setMyHex(HexMapElement myHex) {
		this.myHex = myHex;
		x=( myHex.getCenterX() - img.getIconWidth() / 2);
		y=( myHex.getCenterY() - img.getIconHeight() / 2);
	}

	public void move(int x) {
		if(myHex.tacticSet==0)
			movePoint -= x;
	}
	
	public static List<Unit> createUnitLists(String colorOfUnit) {
		List<Unit> unitsToChoose = new LinkedList<>();
		ImageIcon testImgIcon;
		try {
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/M - Griffin.png")));
			unitsToChoose.add(new Unit(testImgIcon, 600, 1, 5, 200, 600));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/M - Ogre.png")));
			unitsToChoose.add(new Unit(testImgIcon, 1000, 1, 2, 200, 800));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/M - Hydra.png")));
			unitsToChoose.add(new Unit(testImgIcon, 600, 2, 2, 180, 500));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/hus1.png")));
			unitsToChoose.add(new Unit(testImgIcon, 300, 1, 3, 120, 250));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/Lantern2.png")));
			unitsToChoose.add(new Unit(testImgIcon, 250, 1, 2, 140, 200));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/M - Gnoll.png")));
			unitsToChoose.add(new Unit(testImgIcon, 200, 1, 2, 120, 150));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/Fighter - Scimitar2.png")));
			unitsToChoose.add(new Unit(testImgIcon, 250, 1, 2, 100, 150));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/swordnshield1.png")));
			unitsToChoose.add(new Unit(testImgIcon, 200, 1, 2, 50, 100));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/Mage1.png")));
			unitsToChoose.add(new Unit(testImgIcon, 250, 8, 3, 100, 300));
			testImgIcon = new ImageIcon(ImageIO.read(JPanelBackground.class.getResource("/units/small/" + colorOfUnit + "/Fighter - Bow.png")));
			unitsToChoose.add(new Unit(testImgIcon, 100, 7, 2, 40, 100));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unitsToChoose;
	}
}
