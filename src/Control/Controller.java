package Control;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import Model.Game;
import Model.HexMap;
import Model.HexMapElement;
import View.ChooseColorAndNameMode;
import View.ChooseUnitsMode;
import View.GameMode;
import View.JPanelBackground;
import View.MainMenu;
import View.Options;


public class Controller {
	
	public JPanelBackground panel = new JPanelBackground();
	public JFrame frame = new JFrame();
	public Game game = new Game();
	
	
	public Controller() {
		panel.controller = this;
		
		frame.add(panel);
		
		frame.setBounds(0, 0, 1024, 768);
		frame.setResizable(false);
		frame.setUndecorated(false);
		
		HexMap.width = frame.getWidth();
		HexMap.height = frame.getHeight();
		HexMap.firstHexCenterX = (int) (HexMap.width - HexMapElement.width
				* HexMap.amountInOddRow) / 2;
		
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		@SuppressWarnings("serial")
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
								"Are you sure to end your game?", "Exit",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		};
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
		this.setMainMenuMode();
	}

	
	public void setMainMenuMode() {
		panel.mode = 1;
		
		MainMenu menu = new MainMenu(frame, this, panel);
		menu.paintMainMenu();
	}
	
	
	public void setOptionsMode() {
		panel.mode = 2;
		
		Options options = new Options(panel, frame, this);
		options.paintOptions();
	}
	
	
	public void setGameMode() {
		panel.mode = 3;
		
		GameMode gameMode = new GameMode(frame, panel, game, this);
		gameMode.paintGameMode();
	}
	
	
	public void setChooseColorAndNameMode() {
		game = new Game();
		
		panel.mode = 2;
		
		ChooseColorAndNameMode chooseColor = new ChooseColorAndNameMode(frame, this, panel, game);
		chooseColor.paintChooseColorAndNameMode();
	}
	
	
	public void setChooseUnitsMode() {
		panel.mode = 2;
		
		ChooseUnitsMode choose = new ChooseUnitsMode(frame, panel, game, this);
		choose.paintChooseUnitsMode();
	}
	
	
	public void setLoadGameMode() {
		panel.mode = 3;
		GameMode gameMode = new GameMode(frame, panel, game, this);
		gameMode.loadGame();
		gameMode.paintGameMode();
	}
	
public void setCreditsMode() {
		
	}
}
