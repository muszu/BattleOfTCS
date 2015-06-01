package battleOfTCS.game;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

public class Modes {
	
	
	public JPanelBackground panel = new JPanelBackground();
	public JFrame frame = new JFrame();
	public Game game = new Game();
	
	
	
	public Modes() {
		frame.add(panel);
		
		frame.setBounds(0, 0, 1024, 768);
		frame.setResizable(false);
		frame.setUndecorated(false);
		
		HexMap.width = frame.getWidth();
		HexMap.height = frame.getHeight();
		HexMap.firstHexCenterX = (int) (HexMap.width - HexMapElement.width
				* HexMap.getAmountInOddRow()) / 2;
		
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
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout("", new StringBuilder()
				.append(frame.getWidth() / 2 - 125).append("[]").toString(),
				new StringBuilder().append(frame.getHeight() - 400)
						.append("[]20[]20[]20[]").toString()));
		
		MainMenu menu = new MainMenu(frame, this, panel);
		menu.repaintMainMenu();
	}
	
	
	public void setOptionsMode() {
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint(2);
		panel.setLayout(new MigLayout("", new StringBuilder()
			.append(frame.getWidth() / 2 - 125).append("[]").toString(),
			new StringBuilder().append(frame.getHeight() - 500)
				.append("[]20[]20[]20[]20[]").toString()));
		
		Options options = new Options(panel, frame, this);
		options.repaintOptions();
		
	}
	
	
	public void setGameMode() {
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout("",
				new StringBuilder().append(frame.getWidth() / 2 - 260).append("[]20[]").toString(), 
				new StringBuilder().append( frame.getHeight()-80).append("[]2[]").toString()));
		
		GameMode gameMode = new GameMode(frame, panel, game, this);
		gameMode.repaintGameMode();
		
	}
	
	
	public void setChooseColorAndNameMode() {
		panel.removeAll();
		panel.invalidate();
		panel.validate();
		panel.repaint();
		panel.setLayout(new MigLayout(
				"", 
				(frame.getWidth()/ 2 - 320)+"[]20[]20[]20[]", 
				(frame.getHeight()/2 - 100)+"[]30[]50[]100[]"
			));
		
		ChooseColorAndNameMode chooseColor = new ChooseColorAndNameMode(frame, this, panel, game);
		chooseColor.repaintChooseColorAndNameMode();
	}
	
	
	public void setChooseUnitsMode() {
		panel.removeAll();
		panel.setLayout(new MigLayout("", (frame.getWidth() / 2 - 325) + "[]100[]20[]60[]20[]", 
			40+"[]20[]20[]20[]20[]80[]"
			));
		
		ChooseUnitsMode choose = new ChooseUnitsMode(frame, panel, game, this);
		choose.repaintChooseUnitsMode();
	}
	
	
	public void setLoadGameMode() {
		
	}
}
