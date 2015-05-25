package battleOfTCS.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;

public class HexMapElement implements GameObject {

	Random generator = new Random();

	public boolean isShadow;
	public boolean isShadowLight;
	public boolean isRed;
	public boolean isGreen;
	public boolean isYellow;
	public boolean inRangeOfShot;
	public boolean isFlag;
	
	public int tacticSet;

	public final static Image imgHex = new ImageIcon(
			Menu.class.getResource("images/hex.png")).getImage();
	public final static Image imgHexMarked = new ImageIcon(
			Menu.class.getResource("images/hexmarked.png")).getImage();
	public final static Image imgHexNeigh = new ImageIcon(
			Menu.class.getResource("images/hexneigh.png")).getImage();
	public final static Image imgHexShot = new ImageIcon(
			Menu.class.getResource("images/hexshot.png")).getImage();
	public final static Image imgHexShadow = new ImageIcon(
			Menu.class.getResource("images/hexshadow.png")).getImage();
	public final static Image imgHexShadowLight = new ImageIcon(
			Menu.class.getResource("images/hexshadowlight.png")).getImage();
	public final static Image imgHexRed = new ImageIcon(
			Menu.class.getResource("images/hexred.png")).getImage();
	public final static Image imgHexYellow = new ImageIcon(
			Menu.class.getResource("images/hexyellow.png")).getImage();
	public final static Image imgHexGreen = new ImageIcon(
			Menu.class.getResource("images/hexgreen.png")).getImage();
	public final static Image imgHexForest = new ImageIcon(
			Menu.class.getResource("images/hex_forest1.png")).getImage();
	public final static Image imgHexHill = new ImageIcon(
			Menu.class.getResource("images/hex_hill.png")).getImage();
	public final static Image imgHexGrass1 = new ImageIcon(
			Menu.class.getResource("images/hex_grass6.png")).getImage();
	public final static Image imgHexGrass2 = new ImageIcon(
			Menu.class.getResource("images/hex_grass6.png")).getImage();
	public final static Image imgHexFlag = new ImageIcon(
			Menu.class.getResource("images/flag_90red.png")).getImage();

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
	private int distance;

	final int GRASS = 1;
	final int HILL = 2;
	final int FOREST = 3;

	private int terrainType;

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
		int a = (x - this.x > width / 2) ? this.x + width - x : x - this.x;
		int b = a * side / width;

		if (y <= centerY + side / 2 + b - 2 && y >= centerY - side / 2 - b + 2)
			return true;
		else
			return false;
	}

	public void shadow(boolean yesOrNo) {
		if (yesOrNo)
			isShadow = true;
		else
			isShadow = false;
	}

	public void shadowLight(boolean yesOrNo) {
		if (yesOrNo)
			isShadowLight = true;
		else
			isShadowLight = false;
	}

	public void red(boolean yesOrNo) {
		if (yesOrNo)
			isRed = true;
		else
			isRed = false;
	}

	public void green(boolean yesOrNo) {
		if (yesOrNo)
			isGreen = true;
		else
			isGreen = false;
	}

	public void yellow(boolean yesOrNo) {
		if (yesOrNo)
			isYellow = true;
		else
			isYellow = false;
	}

	public void clearColor() {
		isRed = false;
		isGreen = false;
		isYellow = false;
		isShadowLight = false;
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

	public void inRangeOfShot() {
		inRangeOfShot = true;
	}

	public void drawIt(Graphics g) {
		g.drawImage(img, x, y, null);
		if (isShadow)
			g.drawImage(HexMapElement.imgHexShadow, x, y, null);
		if (isRed) {
			g.drawImage(HexMapElement.imgHexRed, x, y, null);
		} else if (isGreen) {
			g.drawImage(HexMapElement.imgHexGreen, x, y, null);
		} else if (isYellow) {
			g.drawImage(HexMapElement.imgHexYellow, x, y, null);
		} else {
			if (isShadowLight)
				g.drawImage(HexMapElement.imgHexShadowLight, x, y, null);
		}
		if(isFlag){
			g.drawImage(imgHexFlag, x+4, y, null);
		}
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(int terrainType) {
		this.terrainType = terrainType;
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

}
