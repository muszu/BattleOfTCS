package battleOfTCS.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JPanelBackground extends JPanel {
	
	public int mode = 1;
	
	static Image backGround = new ImageIcon(Menu.class.getResource("images/backbroundmenuv3.jpg")).getImage();
	static Image backGroundOptions = new ImageIcon(Menu.class.getResource("images/backbroundmenuv2.jpg")).getImage();
	static Image backGroundGame = new ImageIcon(Menu.class.getResource("images/background.png")).getImage();

	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backGround != null) {
			if (mode == 1)
				g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(), this);
			if (mode == 2)
				//g.drawImage(backGroundOptions, 0, 0, this.getWidth(), this.getHeight(), this);
			if (mode == 3) {
				g.drawImage(backGroundGame, 0, 0, this.getWidth(), this.getHeight(), this);
				/*for (HexMapElement hex : map.hexes)
					hex.drawIt(g);
				for (Unit unit : units)
					unit.drawIt(g, false);
				for (Unit unit : units)
					unit.drawIt(g, true);
				game.paint(g);*/
			}
		}
	}
}