package battleOfTCS.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Unit implements GameObject {

	private ImageIcon icon;
	private Image img;
	private Image img1;
	private Image img2;
	private int x;
	private int y;
	private int attack;
	private int maxHealth;
	private int health;
	private int owner;
	private int maxMovePoint;
	private int movePoint;
	private int range;
	private HexMapElement myHex;

	final int GRASS = 1;
	final int HILL = 2;
	final int FOREST = 3;

	public Unit(Unit unit) {
		this.icon = unit.icon;
		this.img = unit.icon.getImage();
		this.maxHealth = unit.maxHealth;
		this.health = unit.maxHealth;
		this.range = unit.range;
		this.maxMovePoint = unit.maxMovePoint;
		this.attack = unit.attack;
		this.owner = unit.owner;
	}

	public Unit(Image img, int x, int y, int maxHealth, int range,
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
			int attack) {
		this.icon = icon;
		this.img = icon.getImage();
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.range = range;
		this.maxMovePoint = maxMovePoint;
		this.attack = attack;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public Image getImage() {
		return img;
	}

	public Image getImage1() {
		return img1;
	}

	public Image getImage2() {
		return img2;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getWidth() {
		return img.getWidth(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}

	public void drawIt(Graphics g, Boolean printHealth) {
		g.drawImage(img, x, y, null);
		int przes = 0;
		if (img.getWidth(null) > 100) {
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
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
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

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public void rest() {
		movePoint = maxMovePoint;
	}

	public String toString() {
		return String.valueOf(owner);
	}

	public void attack(Unit attacker) {
		int hit = (int) attacker.getAttack()
				/ (attacker.getMaxMovePoint() / attacker.getMovePoint());
		// if unit's range is not greater than 1 that means its a meele unit
		if (hit == 0)
			hit = 1;
		if (attacker.range > 1) {
			if (myHex.getTerrainType() == FOREST)
				health -= hit * 0.5;
			else
				health -= hit;
		} else {
			if (hit == 0)
				hit = 1;
			if (myHex.getTerrainType() == HILL)
				health -= hit * 0.6;
			else if (myHex.getTerrainType() == FOREST)
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
	}

	public void move(int x) {
		movePoint -= x;
	}
}
