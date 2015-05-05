package battleOfTCS.gui;

import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class HexMapElement implements GameObject {
	
	private static ImageIcon sourceHex = new ImageIcon(Menu.class.getResource("images/hex.png"));
    public final static Image imgHex = sourceHex.getImage();
    private static ImageIcon sourceHexMarked = new ImageIcon(Menu.class.getResource("images/hexmarked.png"));
    public final static Image imgHexMarked = sourceHexMarked.getImage();
    private static ImageIcon sourceHexNeigh = new ImageIcon(Menu.class.getResource("images/hexneigh.png"));
    public final static Image imgHexNeigh = sourceHexNeigh.getImage();
    private static ImageIcon sourceHexShadow = new ImageIcon(Menu.class.getResource("images/hexshadow.png"));
    public final static Image imgHexShadow = sourceHexShadow.getImage();
    public boolean isShadow;
    
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

	public HexMapElement(int x, int y) {
		this.id = idCounter ++;
		this.unit=null;
		this.img = imgHex;
		this.x = x;
		this.y = y;
		this.centerX = this.x + width/2;
		this.centerY = this.y + side;
		this.occupied = false;
	}
	
	public HexMapElement(boolean center, int centerX, int centerY) {
		this.id = idCounter ++;
		this.img = imgHex;
		this.centerX = centerX;
		this.centerY = centerY;
		this.x = this.centerX - width/2;
		this.y = this.centerY - side;
		this.occupied = false;
	}
	
	public boolean contains(int x, int y) {
		if(x <= this.x + 2  ||  x >= this.x + width - 2  ||  y <= this.y + 2  ||  y >= this.y + height - 2)
			return false;
		int a = (x - this.x > width/2) ? this.x + width - x : x - this.x;
		int b = a * side / width;
		
		if(y <= centerY + side/2 + b - 2 && y >= centerY - side/2 - b + 2) return true;
		else return false;
	}
	
	public void switchImgs() {
		if(this.img == imgHex) {
			this.img = imgHexMarked;
			for(HexMapElement neigh : Neighbours)
				neigh.setImage(imgHexNeigh);
		}
		else {
			this.img = imgHex;
			for(HexMapElement neigh : Neighbours)
				neigh.setImage(imgHex);
		}
	}
	
	public void shadow() {
		if(isShadow)
			isShadow=false;
		else
			isShadow=true;
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

}
