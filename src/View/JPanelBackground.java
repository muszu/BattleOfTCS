package View;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Control.Controller;
import Model.HexMapElement;
import Model.Unit;

@SuppressWarnings("serial")
public class JPanelBackground extends JPanel {
	
	public int mode = 1;
	public Controller controller;
	
	static Image backGround = new ImageIcon("images/backbroundmenuv3.jpg").getImage();
	static Image backGroundOptions = new ImageIcon("images/backbroundmenuv2.jpg").getImage();
	static Image backGroundGame = new ImageIcon("images/background.png").getImage();
	static Image newBackGround = new ImageIcon("images/newbackground.png").getImage();
	static Image newBackGround2 = new ImageIcon("images/newbackground2.png").getImage();
	static Image battle = new ImageIcon("images/battle.png").getImage();
	static Image credits = new ImageIcon("images/credits.png").getImage();

	
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
			if(mode == 4){
				g.drawImage(newBackGround, 0, 0, this);
				g.drawImage(credits, (int) (getWidth()-credits.getWidth(null))/2 , (int) (getHeight()-credits.getHeight(null))/2, this);
			}
		}
	}
}