package battleOfTCS.game;

import java.awt.Image;

public class FlagAndOtherObjectives implements GameObject{

	private Image img;
	private int x;
	private int y;

	public FlagAndOtherObjectives(Image img, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
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

	public int getWidth() {
		return img.getHeight(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}
}
