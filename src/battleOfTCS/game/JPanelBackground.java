package battleOfTCS.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JPanelBackground extends JPanel {
	
	public int mode = 1;
	Controller controller;
	
	static Image backGround = new ImageIcon(Controller.class.getResource("images/backbroundmenuv3.jpg")).getImage();
	static Image backGroundOptions = new ImageIcon(Controller.class.getResource("images/backbroundmenuv2.jpg")).getImage();
	static Image backGroundGame = new ImageIcon(Controller.class.getResource("images/background.png")).getImage();
	static Image newBackGround = new ImageIcon(Controller.class.getResource("images/newbackground.png")).getImage();
	static Image newBackGround2 = new ImageIcon(Controller.class.getResource("images/newbackground2.png")).getImage();
	static Image battle = new ImageIcon(Controller.class.getResource("images/battle.png")).getImage();

	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backGround != null) {
			if (mode == 1) {
				g.drawImage(newBackGround, 0, 0, this);
				g.drawImage(battle, (this.getWidth() - battle.getWidth(null))/2, 40, this);
			}
				
			if (mode == 2)
				g.drawImage(newBackGround, 0, 0, this);
			if (mode == 3) {
				g.drawImage(backGroundGame, 0, 0, this.getWidth(), this.getHeight(), this);
				for (HexMapElement hex : controller.game.map.hexes)
					hex.drawIt(g);
				for (Unit unit : controller.game.units)
					unit.drawIt(g, false);
				for (Unit unit : controller.game.units)
					unit.drawIt(g, true);
				controller.game.paint(g);
			}
		}
	}
}