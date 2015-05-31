package battleOfTCS.game;

import java.awt.Image;

public interface GameObject {
	
	public Image getImage();

	public int getX();

	public int getY();

	public void setX(int x);

	public void setY(int y);

	public int getWidth();

	public int getHeight();
}
