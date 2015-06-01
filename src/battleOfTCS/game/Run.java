package battleOfTCS.game;

import java.awt.EventQueue;

public class Run {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					
					Modes modes = new Modes();
					modes.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
