package Model;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;


public class HexMapElement implements GameObject,java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087863756052824635L;

	Random generator = new Random();

	public boolean isShadow;
	public boolean isShadowLight;
	public boolean isRed;
	public boolean isGreen;
	public boolean isYellow;
	public boolean inRangeOfShot;
	public boolean isFlag;
	
	public int tacticSet;

	public final static ImageIcon imgHex = new ImageIcon("images/hex.png");
	public final static ImageIcon imgHexShadow = new ImageIcon("images/hexshadow.png");
	public final static ImageIcon imgHexShadowLight = new ImageIcon("images/hexshadowlight.png");
	public final static ImageIcon imgHexRed = new ImageIcon("images/hexred.png");
	public final static ImageIcon imgHexYellow = new ImageIcon("images/hexyellow.png");
	public final static ImageIcon imgHexGreen = new ImageIcon("images/hexgreen.png");
	public final static ImageIcon imgHexForest = new ImageIcon("images/hex_forest1.png");
	public final static ImageIcon imgHexHill = new ImageIcon("images/hex_hill.png");
	public final static ImageIcon imgHexGrass1 = new ImageIcon("images/hex_grass6.png");
	public final static ImageIcon imgHexGrass2 = new ImageIcon("images/hex_grass6.png");
	public final static ImageIcon imgHexFlag = new ImageIcon("images/flag_90red.png");

	private static int idCounter = 0;
	public static int width = imgHex.getIconWidth();
	public static int height = imgHex.getIconHeight();
	public static int side = height / 2;

	public Unit unit;
	public int id;
	private ImageIcon img;
	public int x;
	public int y;
	public int centerX;
	public int centerY;
	public LinkedList<HexMapElement> Neighbours;
	public boolean occupied;
	public int distance;

	final int GRASS = 1;
	final int HILL = 2;
	final int FOREST = 3;

	public int terrainType;

	public HexMapElement(boolean center, int centerX, int centerY) {
		isFlag = false;
		this.id = idCounter++;
		int choosing = generator.nextInt(4);
		switch (choosing) {
		case 0:
			this.img = imgHexGrass1;
			terrainType = GRASS;
			break;
		case 1:
			this.img = imgHexGrass2;
			terrainType = GRASS;
			break;
		case 2:
			this.img = imgHexHill;
			terrainType = HILL;
			break;
		case 3:
			this.img = imgHexForest;
			terrainType = FOREST;
			break;
		default:
			this.img = imgHex;
			terrainType = GRASS;
			break;
		}
		this.centerX = centerX;
		this.centerY = centerY;
		x = centerX - width / 2;
		y = centerY - side;
		occupied = false;
		distance = 100;
		isRed = false;
		isGreen = false;
		isYellow = false;
		isShadow = false;
		inRangeOfShot = false;
	}

	public boolean contains(int x, int y) {
		if (x <= this.x + 2 || x >= this.x + width - 2 || y <= this.y + 2
				|| y >= this.y + height - 2)
			return false;
		int a = (x - this.x > width / 2) ? (this.x + width - x) : (x - this.x);
		int b = a * side / width;

		if (y <= centerY + side / 2 + b - 2 && y >= centerY - side / 2 - b + 2)
			return true;
		else
			return false;
	}

	public void clearColor() {
		isRed = false;
		isGreen = false;
		isYellow = false;
		isShadowLight = false;
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

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setImage(ImageIcon img) {
		this.img = img;
	}

	public void setNeighbours(LinkedList<HexMapElement> neigh) {
		this.Neighbours = neigh;
	}

	public int getWidth() {
		return img.getImage().getWidth(null);
	}

	public int getHeight() {
		return img.getImage().getHeight(null);
	}

	public void drawIt(Graphics g) {
		g.drawImage(img.getImage(), x, y, null);
		if (isShadow)
			g.drawImage(HexMapElement.imgHexShadow.getImage(), x, y, null);
		if (isRed) {
			g.drawImage(HexMapElement.imgHexRed.getImage(), x, y, null);
		} else if (isGreen) {
			g.drawImage(HexMapElement.imgHexGreen.getImage(), x, y, null);
		} else if (isYellow) {
			g.drawImage(HexMapElement.imgHexYellow.getImage(), x, y, null);
		} else {
			if (isShadowLight)
				g.drawImage(HexMapElement.imgHexShadowLight.getImage(), x, y, null);
		}
		if(isFlag)
			g.drawImage(imgHexFlag.getImage(), x+4, y, null);
	}

	public void setRandomTerrainType(){
		int choosing = generator.nextInt(4);
		switch (choosing) {
		case 0:
			this.img = imgHexGrass1;
			terrainType = GRASS;
			break;
		case 1:
			this.img = imgHexGrass2;
			terrainType = GRASS;
			break;
		case 2:
			this.img = imgHexHill;
			terrainType = HILL;
			break;
		case 3:
			this.img = imgHexForest;
			terrainType = FOREST;
			break;
		default:
			this.img = imgHex;
			terrainType = GRASS;
			break;
		}
	}
	
	public static void resetIdCounter() {
		idCounter = 0;
	}

	public void recenter(int centerX2, int centerY2) {
		centerX = centerX2;
		centerY = centerY2;
		x = centerX - width / 2;
		y = centerY - side;
		if(unit != null){
			unit.setX(centerX - unit.getWidth() / 2);
			unit.setY(centerY - unit.getHeight() / 2);
		}	
	}
}
