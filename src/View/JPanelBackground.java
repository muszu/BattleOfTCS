package View;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Control.Controller;
import Model.HexMapElement;
import Model.Unit;

@SuppressWarnings("serial")
public class JPanelBackground extends JPanel {
	
	public int mode = 1;
	public Controller controller;
	static Image backGround;
	static Image backGroundGame;
	static Image newBackGround;
	static Image battle;
	static Image credits;
	static Image tutorialImg1;
	static Image tutorialImg2;
	static Image tutorialImg3;
	static Image tutorialImg4;
	static Image tutorialImg5;
	private Image tutorialImg;
	
	static {
		try {
			backGround = ImageIO.read(JPanelBackground.class.getResource("/backbroundmenuv2.jpg"));
			newBackGround = ImageIO.read(JPanelBackground.class.getResource("/newbackground.png"));
			backGroundGame = ImageIO.read(JPanelBackground.class.getResource("/background.png"));
			battle = ImageIO.read(JPanelBackground.class.getResource("/battle.png"));
			credits = ImageIO.read(JPanelBackground.class.getResource("/credits.png"));
			tutorialImg1 = ImageIO.read(JPanelBackground.class.getResource("/tutorial1.png"));
			tutorialImg2 = ImageIO.read(JPanelBackground.class.getResource("/tutorial2.png"));
			tutorialImg3 = ImageIO.read(JPanelBackground.class.getResource("/tutorial3.png"));
			tutorialImg4 = ImageIO.read(JPanelBackground.class.getResource("/tutorial4.png"));
			tutorialImg5 = ImageIO.read(JPanelBackground.class.getResource("/tutorial5.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
			if(mode == 5){
				g.drawImage(newBackGround, 0, 0, this);
				switch (controller.tutorialImgNumber) {
				case 1:
					tutorialImg=tutorialImg1;
					break;
				case 2:
					tutorialImg=tutorialImg2;
					break;
				case 3:
					tutorialImg=tutorialImg3;
					break;
				case 4:
					tutorialImg=tutorialImg4;
					break;
				case 5:
					tutorialImg=tutorialImg5;
					break;
				default:
					break;
				}
				g.drawImage(tutorialImg, (int) (getWidth()-tutorialImg.getWidth(null))/2 , (int) (getHeight()-tutorialImg.getHeight(null))/2, this);
			}
		}
	}
}