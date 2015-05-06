package battleOfTCS.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Unit implements GameObject {
	
	private Image img;
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

	public Unit(Image img, int x, int y, int maxHealth, int range) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.range = range;
	}
	
	public Image getImage() {
		return img;
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
	public void setHealth( int health ){
		this.health=health;
	}

	public int getWidth() {
		return img.getHeight(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}
	
	public void drawIt(Graphics g, Boolean printHealth){
		g.drawImage(img, x, y, null);
		if(printHealth){
			StringBuilder life = new StringBuilder().append(health).append("/").append(maxHealth);
			g.setColor(new Color(0, 0, 0));
			g.fillRect(x, y, 80, 12);
			g.setColor(new Color(100, 0, 0));
			g.fillRect(x+1, y+1, 78, 10);
			g.setColor(new Color(0, 100, 0));
			g.fillRect(x+1, y+1, (health*78)/maxHealth, 10);
			g.setColor(new Color(255, 255, 255));
			g.drawString(life.toString(), (40-life.length()*3)+x, y+11);
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
		movePoint=movePoint--;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	public void rest(){
		movePoint=maxMovePoint;
	}
	
	
	public String toString(){
		return String.valueOf(owner);
	}
	
	public void attack(int hit){
		health-=hit;
	}

	public HexMapElement getMyHex() {
		return myHex;
	}

	public void setMyHex(HexMapElement myHex) {
		this.myHex = myHex;
	}
}
