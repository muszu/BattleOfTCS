package battleOfTCS.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;

public class HexMapElement implements GameObject {
	
	Random generator = new Random();
	
    public boolean isShadow;
    public boolean isRed;
    public boolean isGreen;
    public boolean isYellow;
    public boolean inRangeOfShot;
    
    public final static Image imgHex =new ImageIcon(Menu.class.getResource("images/hex.png")).getImage();
    public final static Image imgHexMarked =  new ImageIcon(Menu.class.getResource("images/hexmarked.png")).getImage();
    public final static Image imgHexNeigh =new ImageIcon(Menu.class.getResource("images/hexneigh.png")).getImage();
    public final static Image imgHexShot = new ImageIcon(Menu.class.getResource("images/hexshot.png")).getImage();
    public final static Image imgHexShadow = new ImageIcon(Menu.class.getResource("images/hexshadow.png")).getImage();
    public final static Image imgHexRed = new ImageIcon(Menu.class.getResource("images/hexred.png")).getImage();
    public final static Image imgHexYellow = new ImageIcon(Menu.class.getResource("images/hexyellow.png")).getImage();
    public final static Image imgHexGreen = new ImageIcon(Menu.class.getResource("images/hexgreen.png")).getImage();
    public final static Image imgHexForest = new ImageIcon(Menu.class.getResource("images/hex_forest1.png")).getImage();
    public final static Image imgHexHill = new ImageIcon(Menu.class.getResource("images/hex_hill.png")).getImage();
    public final static Image imgHexGrass1 = new ImageIcon(Menu.class.getResource("images/hex_grass6.png")).getImage();
    public final static Image imgHexGrass2 = new ImageIcon(Menu.class.getResource("images/hex_grass6.png")).getImage();
    
    private static int idCounter = 0;
	public static int width = imgHex.getWidth(null);
	public static int height = imgHex.getHeight(null);
	public static int side = height / 2;
	
	public Unit unit;
	public int id;
    
	private Image img;
	private int x;
	private int y;
	private int centerX;
	private int centerY;
	public LinkedList<HexMapElement> Neighbours;
	public boolean occupied;
	public Integer distance;
	
	public HexMapElement(int x, int y) {
		this.id = idCounter ++;
		this.unit=null;
		this.img = imgHex;
		this.x = x;
		this.y = y;
		this.centerX = this.x + width/2;
		this.centerY = this.y + side;
		this.occupied = false;
		this.distance = null;
		isRed  = false;
	    isGreen = false;
	    isYellow = false;
	    isShadow = false;
	    inRangeOfShot=false;
	}
	
	public HexMapElement(boolean center, int centerX, int centerY) {
		this.id = idCounter ++;
		int choosing = generator.nextInt(4);
		switch (choosing) {
		case 0:
			this.img = imgHexGrass1;
			break;
		case 1:
			this.img = imgHexGrass2;
			break;
		case 2:
			this.img = imgHexHill;
			break;
		case 3:
			this.img = imgHexForest;
			break;
		default:
			this.img = imgHex;
			break;
		}
		this.centerX = centerX;
		this.centerY = centerY;
		this.x = this.centerX - width/2;
		this.y = this.centerY - side;
		this.occupied = false;
		this.distance = null;
	    isRed  = false;
	    isGreen = false;
	    isYellow = false;
	    isShadow = false;
	    inRangeOfShot=false;
	}
	
	public boolean contains(int x, int y) {
		if(x <= this.x + 2  ||  x >= this.x + width - 2  ||  y <= this.y + 2  ||  y >= this.y + height - 2)
			return false;
		int a = (x - this.x > width/2) ? this.x + width - x : x - this.x;
		int b = a * side / width;
		
		if(y <= centerY + side/2 + b - 2 && y >= centerY - side/2 - b + 2) return true;
		else return false;
	}
	
	
	public void shadow(boolean yesOrNo) {
		if(yesOrNo)
			isShadow=true;
		else
			isShadow=false;
	}
	
	public void red(boolean yesOrNo) {
		if(yesOrNo)
			isRed=true;
		else
			isRed=false;
	}
	
	public void green(boolean yesOrNo) {
		if(yesOrNo)
			isGreen=true;
		else
			isGreen=false;
	}
	
	public void yellow(boolean yesOrNo) {
		if(yesOrNo)
			isYellow=true;
		else
			isYellow=false;
	}
	
	public void clearColor(){
		isRed = false;
		isGreen = false;
		isYellow = false;
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
	
	public int getCenterX() {
		return this.centerX;
	}

	public int getCenterY() {
		return this.centerY;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setImage(Image img) {
		this.img = img;
	}

	public void setNeighbours(LinkedList<HexMapElement> neigh) {
		this.Neighbours = neigh;
	}
	
	public int getWidth() {
		return img.getWidth(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}
	public void inRangeOfShot(){
		inRangeOfShot=true;
	}
	
	public void drawIt(Graphics g){
		g.drawImage(img, x, y, null);
		if(isShadow)
			g.drawImage(HexMapElement.imgHexShadow, x,y, null);
		if(isRed)
			g.drawImage(HexMapElement.imgHexRed, x,y, null);
		if(isGreen)
			g.drawImage(HexMapElement.imgHexGreen, x,y, null);
		if(isYellow)
			g.drawImage(HexMapElement.imgHexYellow, x,y, null);
	}

}
