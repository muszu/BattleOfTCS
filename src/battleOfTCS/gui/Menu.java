package battleOfTCS.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ArrayList;

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
		
	static LinkedList<Unit> units = new LinkedList<Unit>();
	static final ArrayList<Unit> UnitsToChoose = new ArrayList<Unit>();
	static final ArrayList<Unit> ChosenUnits = new ArrayList<Unit>();
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
				createUnitLists();
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
	        	UnitToNewGame();
            	modeGame(panel);
            }
        });
		
		JButton btnLoadGame = new JButton("Load game");
		btnLoadGame.setForeground(new Color(0f,0f,0f));
		btnLoadGame.setBackground(new Color(0.7f,0.7f,0.7f));
		btnLoadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	modeChooseUnits(panel);            
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
		mode=3;
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
		
		game.refresh();
		panel.removeAll();
		panel.invalidate();
	    panel.validate();
	    panel.repaint();
	    panel.setLayout( new MigLayout(
	    			"", 
	    			new StringBuilder().append(frame.getWidth()/2-260).append("[]20[]").toString(),
	    			new StringBuilder().append(frame.getHeight()-405).append("[]").toString()
				 )
			);
   
	    	JButton btnEndTurn = new JButton("End Turn");
	    	btnEndTurn.setForeground(new Color(0f,0f,0f));
			btnEndTurn.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnEndTurn.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            game.endTurn();
		            panel.repaint();
		        }
	    	});
	    	
		    JButton btnBack = new JButton("Back");
	    	btnBack.setForeground(new Color(0f,0f,0f));
			btnBack.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnBack.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            units.clear();
		        	modeMenu(panel);
		            panel.repaint();
		        }
	    	});
	    	panel.add(btnEndTurn,  "cell 0 2, width 150:250:300, height 20:30:40");
			panel.add(btnBack, "cell 1 2, width 150:250:300, height 20:30:40");
			panel.invalidate();
		    panel.validate();
		    panel.repaint();
	}
	
	
	void modeChooseUnits(final JPanel panel){
		mode = 2;
		panel.removeAll();
		panel.invalidate();
	    panel.validate();
	    panel.repaint();
	    panel.setLayout( new MigLayout(
    			"", 
    			new StringBuilder().append(frame.getWidth()/2-325).append("[]100[]20[]60[]20[]").toString(),
    			new StringBuilder().append(frame.getHeight()-625).append("[]20[]20[]20[]20[]100[]").toString()
			)
	    	);
	    	JButton btnReady = new JButton("Ready!");
	    	btnReady.setForeground(new Color(0f,0f,0f));
			btnReady.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnReady.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	for(int i=0; i<5; i++){
	        		ChosenUnits.get(i).setOwner(1);
	        		ChosenUnits.get(i).setX(190);
	        		ChosenUnits.get(i).setY(100+i*125); //TO CORRECT
	        	}
	            units.addAll(ChosenUnits);
	        	modeGame(panel);
	        }
	    	});
	    	JButton btnBack = new JButton("Back");
	    	btnBack.setForeground(new Color(0f,0f,0f));
			btnBack.setBackground(new Color(0.7f,0.7f,0.7f));
	    	btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            modeMenu(panel);
	        }
	    	});
	    	JButton [] btnChosenUnit = new JButton[5];
	    	for(int i=0; i<5; i++){
	    		btnChosenUnit[i] = new JButton();
	    		btnChosenUnit[i].setForeground(new Color(0f,0f,0f));
	    		btnChosenUnit[i].setBackground(new Color(0.7f,0.7f,0.7f));
	    		
	    		if (ChosenUnits.size()<=i){
	    			btnChosenUnit[i].setIcon(null);
	    			btnChosenUnit[i].setText("Empty");
	    		}
	    		else{
	    			btnChosenUnit[i].setText("");
	    			btnChosenUnit[i].setIcon(ChosenUnits.get(i).getIcon());
	    		}
	    		final int ii = i;
	    		btnChosenUnit[i].addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            if (ChosenUnits.size()>ii){
			        	ChosenUnits.remove(ii);
			            modeChooseUnits(panel);
		            }
		        }
		    	});
	    	}
	    	JButton [] btnUnit = new JButton[10];
	    	for(int i=0; i<10; i++){
	    		btnUnit[i] = new JButton();
	    		btnUnit[i].setBackground(new Color(0.7f,0.7f,0.7f));
	    		btnUnit[i].setIcon(UnitsToChoose.get(i).getIcon());
	    		final int ii = i;
	    		btnUnit[i].addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				if(ChosenUnits.size()<5){
		    				ChosenUnits.add(UnitsToChoose.get(ii));
				            modeChooseUnits(panel);
	    				}
    				}   
		        });
	    	}

	    	for(int i=0; i<5; i++)
		    	panel.add(btnChosenUnit[i], "cell 0 "+i+", width 80:80:80, height 80:80:80");	    	
	    	for(int i=0; i<10; i++)
		    	panel.add(btnUnit[i], "cell "+((i/5)+3)+" "+i%5+", width 80:80:80, height 80:80:80");	    	
	    	panel.add(btnReady,  "cell 1 5, width 100:150:200, height 20:30:40");
	    	panel.add(btnBack,  "cell 2 5, width 100:150:200, height 20:30:40");
			panel.invalidate();
		    panel.validate();
		    panel.repaint();
	}	

	
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		frame = new JFrame();
		frame.setBounds(0, 0, (int) width, (int) height);
		//frame.setBounds(0, 0, 1024, 768);
		//frame.setBounds(0, 0, 1366, 768);
		//frame.setBounds(0, 0, 1920, 1080);
		frame.setResizable(false);
		frame.setUndecorated(true);      //FULLSCREEN
		
		
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
        			if(mode==2)
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


	static void createUnitLists(){
		
		ImageIcon testImgIcon = new ImageIcon(Menu.class.getResource("images/units/hus1test.png"));
        UnitsToChoose.add(new Unit(testImgIcon,50,7,6,25));
        testImgIcon = new ImageIcon(Menu.class.getResource("images/units/MrKozikMaster.png"));
        UnitsToChoose.add(new Unit(testImgIcon,999,1,5,82));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/deer1test.png"));
        UnitsToChoose.add(new Unit(testImgIcon,43,5,3,20));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/Szczypka.png"));
        UnitsToChoose.add(new Unit(testImgIcon,50,7,4,30));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/goat2.png"));
        UnitsToChoose.add(new Unit(testImgIcon,100,1,2,40));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/havycav2.png"));
        UnitsToChoose.add(new Unit(testImgIcon,100,4,3,60));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/Lantern3.png"));
        UnitsToChoose.add(new Unit(testImgIcon,100,2,3,20));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/deer.png"));
        UnitsToChoose.add(new Unit(testImgIcon,100,2,3,20));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/goat.png"));
        UnitsToChoose.add(new Unit(testImgIcon,100,2,3,20));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/Mage1.png"));
        UnitsToChoose.add(new Unit(testImgIcon,100,2,3,20));
	}
	
	void UnitToNewGame(){
		units.clear();
		ImageIcon testImgIcon = new ImageIcon(Menu.class.getResource("images/units/hus1test.png"));
        Image testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,625,390,50,7,6,25,2));
        testImg = new ImageIcon(Menu.class.getResource("images/units/MrKozikMaster.png")).getImage();
		units.add(new Unit(testImg,305,220,999,1,5,82,1));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/deer1test.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,675,320,43,5,3,20,2));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/Szczypka.png"));
        testImg = testImgIcon.getImage();
        units.add(new Unit(testImg,300,100,50,7,4,30,1));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/goat2.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,660,170,100,1,2,40,2));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/havycav2.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,335,400,100,4,3,60,1));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/Lantern3.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,300,300,100,2,3,20,1));
		testImgIcon = new ImageIcon(Menu.class.getResource("images/units/deer.png"));
        testImg = testImgIcon.getImage();
		units.add(new Unit(testImg,730,600,100,2,3,20,2));
		
	}
}
