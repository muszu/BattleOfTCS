package battleOfTCS.game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

/*
 * Not needed anymore.
 */



public class Menu {
	public static int mode;

	static LinkedList<Unit> units = new LinkedList<Unit>();
	static ArrayList<Unit> UnitsToChoose = new ArrayList<Unit>();
	static ArrayList<Unit> ChosenUnits = new ArrayList<Unit>();
	public boolean Player1Ready;
	public HexMap map;
	private JFrame frame;
	private Game game;
	private DragNDrop listener;
	private String colorA;
	private String colorB;
	private static Boolean flagOrNot;
	JTextField nameA, nameB;

	/**
	 * Launch the Battle Of TCS.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mode = 1;
				flagOrNot = false;
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

	void modeMenu(final JPanel panel) {
		mode = 1;
	}

	protected void loadGame() {}

	void modeGame(final JPanel panel, boolean flagMode) {  // true == capture flag
		mode = 3;
	}

	void modePrepareGame(final JPanel panel){
		mode = 2;
	}

	void modeChooseUnits() {}
	
	private void initialize() {
		map = new HexMap();
	}
}
