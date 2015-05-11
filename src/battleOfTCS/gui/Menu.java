package battleOfTCS.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComponent;

import net.miginfocom.swing.MigLayout;

public class Menu {
	public static int mode;
		
	static LinkedList<Unit> units;
	private HexMap map = new HexMap();
	private JFrame frame;
	private Game game;

	/**
	 * Launch the Battle Of TCS.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mode=1;
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Menu() {
		initialize();
	}
	
	
	
	void mode2(final JPanel panel){
		mode=2;
		panel.removeAll();
		panel.invalidate();
	    panel.validate();
	    panel.repaint();
	    panel.setLayout( new MigLayout(
				 "", 
				 "390[]",
				 "600[]20[]20[]20[]")
			);
	    	JButton btnBack = new JButton("Back");
	    	btnBack.setForeground(new Color(0f,0f,0f));
			btnBack.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeMenu(panel);
            }
	    	});
			panel.add(btnBack,  "cell 0 2, width 150:250:300, height 30:50:80");
			panel.invalidate();
		    panel.validate();
		    panel.repaint();
	}
	
	void mode4(final JPanel panel){
		mode=4;
		panel.removeAll();
		panel.invalidate();
	    panel.validate();
	    panel.repaint();
	    panel.setLayout( new MigLayout(
				 "", 
				 "390[]",
				 "600[]20[]20[]20[]")
			);
	    	JButton btnBack = new JButton("Back");
	    	btnBack.setForeground(new Color(0f,0f,0f));
			btnBack.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeMenu(panel);
            }
	    	});
			panel.add(btnBack,  "cell 0 2, width 150:250:300, height 30:50:80");
			panel.invalidate();
		    panel.validate();
		    panel.repaint();
	}
	
	
	
	void modeMenu(final JPanel panel){
        mode=1;
        panel.removeAll();
        
        JButton btnNewGame = new JButton("New game");
		btnNewGame.setForeground(new Color(0f,0f,0f));
		btnNewGame.setBackground(new Color(0.7f,0.7f,0.7f));
		btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	modeGame(panel);
            }
        });
		
		JButton btnLoadGame = new JButton("Load game");
		btnLoadGame.setForeground(new Color(0f,0f,0f));
		btnLoadGame.setBackground(new Color(0.7f,0.7f,0.7f));
		btnLoadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mode4(panel);            
            }
        });	
		
		JButton btnOptions = new JButton("Options");
		btnOptions.setForeground(new Color(0f,0f,0f));
		btnOptions.setBackground(new Color(0.7f,0.7f,0.7f));
		btnOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode2(panel);
            }
        });
		 
		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(new Color(0f,0f,0f));
		btnExit.setBackground(new Color(0.7f,0.7f,0.7f));
		btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                   if (JOptionPane.showConfirmDialog(frame, 
                           "Are you sure to end your game?", "Exit", 
                           JOptionPane.YES_NO_OPTION,
                           JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                           System.exit(0);
                       }
            }
        });
		
		panel.setLayout( new MigLayout(
				 "", 
				 new StringBuilder().append(frame.getWidth()/2-125).append("[]").toString(),
				 new StringBuilder().append(frame.getHeight()-400).append("[]20[]20[]20[]").toString()
				)
		);
		panel.add(btnNewGame,  "cell 0 0, width 150:250:300, height 30:50:80");
		panel.add(btnLoadGame,  "cell 0 1, width 150:250:300, height 30:50:80");
		panel.add(btnOptions,  "cell 0 2, width 150:250:300, height 30:50:80");
		panel.add(btnExit,  "cell 0 3, width 150:250:300, height 30:50:80");
		panel.invalidate();
	    panel.validate();
	    panel.repaint();
	}
	
	
	void modeGame(final JPanel panel){
		units = new LinkedList<>();
		mode=3;
		ImageIcon testImgIcon = new ImageIcon(Menu.class.getResource("images/units/hus1test.png"));
        Image testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,625,390,50,7,6,25,2));
        testImg = new ImageIcon(Menu.class.getResource("images/units/MrKozikMaster.png")).getImage();
		units.add(new Unit(testImg,105,220,999,1,5,42,1));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/deer1test.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,675,320,43,5,3,5,2));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/Szczypka.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,105,100,50,7,4,30,1));
		game = new Game( units, map );
		for( Unit setUnit : units){
			for(HexMapElement hex : map.hexes){
				if( hex.contains( setUnit.getX()+setUnit.getWidth()/2, setUnit.getY()+setUnit.getHeight()/2) ){
					hex.unit=setUnit;
					setUnit.setMyHex(hex);
					hex.occupied=true;
					setUnit.setX(hex.getCenterX()-setUnit.getWidth()/2);
					setUnit.setY(hex.getCenterY()-setUnit.getHeight()/2);
					break;
				}
				
			}
		}
		DragNDrop listener = new DragNDrop(units, panel, map, game);
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);
		
		panel.removeAll();
		panel.invalidate();
	    panel.validate();
	    panel.repaint();
	    panel.setLayout( new MigLayout(
				 "", 
				 "390[]",
				 "600[]20[]20[]20[]")
			);
	    	JButton btnBack = new JButton("End turn");
	    	btnBack.setForeground(new Color(0f,0f,0f));
			btnBack.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.endTurn();
                panel.repaint();
            }
	    	});
			panel.add(btnBack,  "cell 0 2, width 150:250:300, height 30:50:80");
			panel.invalidate();
		    panel.validate();
		    panel.repaint();
	}
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1024, 768);
		frame.setResizable(false);
		//frame.setBounds(0, 0, 1366, 768);
		//frame.setUndecorated(true);      //FULLSCREEN
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		@SuppressWarnings("serial")
		Action escapeAction = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(frame, 
	                           "Are you sure to end your game?", "Exit", 
	                           JOptionPane.YES_NO_OPTION,
	                           JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
	                           System.exit(0);
	                       }
		    }
		}; 
		
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
        final Image backGround = new ImageIcon(Menu.class.getResource("images/backbroundmenuv3.jpg")).getImage();
        final Image backGroundGame = new ImageIcon(Menu.class.getResource("images/backbroundmenu.png")).getImage();
        final Image backGroundPrim =  new ImageIcon(Menu.class.getResource("images/backbroundmenuprim.png")).getImage();
        final Image olaBackGround = new ImageIcon(Menu.class.getResource("images/background.png")).getImage();
        
        @SuppressWarnings("serial")
		class JPanelBackground extends JPanel{
        	protected void paintComponent(Graphics g){
        		super.paintComponent(g);
        		if(backGround != null){
        			if (mode==2)
        				g.drawImage(backGroundGame, 0, 0, this.getWidth(), this.getHeight(), this);
        			if(mode==1)
        				g.drawImage(backGround, 0, 0, this.getWidth(), this.getHeight(), this);
        			if(mode==3){
        				g.drawImage(olaBackGround, 0, 0, this.getWidth(), this.getHeight(), this);
        				for (HexMapElement hex : map.hexes)
        					hex.drawIt(g);
        				for (Unit unit : units)
        					unit.drawIt(g,true);
        				game.paint(g);
        			}
        			if(mode==4)
        				g.drawImage(backGroundPrim, 0, 0, this.getWidth(), this.getHeight(), this);
        		}
        	}
        }
        
        final JPanel panel = new JPanelBackground();
		frame.add(panel);
		modeMenu(panel);
	}
}
