package battleOfTCS.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

public class DragNDrop implements MouseListener, MouseMotionListener {

	private List<Unit> units;
	private JPanel panel;
	private Unit dragUnit;
	private Unit clickUnit;
	private int dragOffsetX;
	private int dragOffsetY;
	private int lastX;
	private int lastY;
	private HexMapElement lastHex, lastHexPosition;
	private HexMap map;
	private static Game game;

	public DragNDrop(List<Unit> units, JPanel panel, HexMap map, Game games) {
		this.map = map;
		game = games;
		this.units = units;
		this.panel = panel;
		clickUnit = null;
		dragUnit = null;
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		for (HexMapElement hex : map.hexes) {
			if (hex.contains(x, y) && hex.unit != null) {
				if (game.turnList.contains(hex.unit)) {
					lastHexPosition = hex;
					dragUnit = hex.unit;
					dragOffsetX = x - dragUnit.getX();
					dragOffsetY = y - dragUnit.getY();
					dragUnit = hex.unit;
					map.markAvailableBFS(hex, dragUnit.getMovePoint(),
							dragUnit.getRange());
					break;
				}
			}
		}
		if (this.dragUnit != null) {
			this.lastX = dragUnit.getX();
			this.lastY = dragUnit.getY();
			this.units.remove(this.dragUnit);
			this.units.add(this.dragUnit);
		}
		game.refresh();
		panel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		int x = evt.getX();
		int y = evt.getY();
		if (dragUnit != null) {
			// move
			for (HexMapElement hex : map.hexes) {
				if (hex.contains(x, y) && hex.isShadowLight) {
					if (lastHex != null) {
						if (lastHexPosition != hex) {
							dragUnit.move(hex.getDistance());
							if (dragUnit.getMovePoint() <= 0)
								game.turnList.remove(dragUnit);
						}
						lastHexPosition.shadow(false);
						lastHexPosition.unit = null;
						lastHexPosition.occupied = false;
						lastHexPosition = null;
					}
					hex.unit = dragUnit;
					hex.occupied = true;
					dragUnit.setX(hex.getCenterX() - dragUnit.getWidth() / 2);
					dragUnit.setY(hex.getCenterY() - dragUnit.getHeight() / 2);
					dragUnit.setMyHex(hex);
					dragUnit = null;
					break;
				}
			}
			// atack
			if (dragUnit != null) {
				for (HexMapElement hex : map.hexes) {
					if (hex.contains(x, y) && hex.unit != null) {
						if (hex.inRangeOfShot
								&& hex.unit.getOwner() != dragUnit.getOwner()) {
							hex.unit.attack(dragUnit);
							hex.unit.setMyHex(hex);
							game.turnList.remove(dragUnit);
						}
						break;
					}
				}
			}
			if (dragUnit != null) {
				dragUnit.setX(lastX);
				dragUnit.setY(lastY);
				dragUnit = null;
				clickUnit = null;
			}
			for (HexMapElement hexik : map.hexes) {
				hexik.clearColor();
				hexik.shadow(false);
			}
			game.refresh();
			panel.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		if (dragUnit != null) {
			dragUnit.setX(x - this.dragOffsetX);
			dragUnit.setY(y - this.dragOffsetY);
			panel.repaint();
		}
		for (HexMapElement hex : map.hexes) {
			if (hex.contains(x, y)) {
				if (lastHex != hex) {
					if (lastHex != null) {
						Game.selectedUnit = hex.unit;
						Game.selectedHex = hex;
						hex.shadow(true);
						lastHex.shadow(false);
						lastHex = hex;
					} else {
						hex.shadow(true);
						lastHex = hex;
					}
					this.panel.repaint();
					break;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		if (evt.getButton() == MouseEvent.BUTTON3) {
			clickUnit = null;
			lastHexPosition = null;
			for (HexMapElement hexik : map.hexes) {
				hexik.clearColor();
				hexik.shadow(false);
			}
		}
		// move
		if (clickUnit != null) {
			for (HexMapElement hex : map.hexes) {
				if (hex.contains(x, y) && hex.isShadowLight) {
					if (lastHexPosition != null) {
						lastHexPosition.occupied = false;
						lastHexPosition.unit = null;
					}
					lastHexPosition = null;
					hex.unit = clickUnit;
					hex.occupied = true;
					clickUnit.move(hex.getDistance());
					if (clickUnit.getMovePoint() <= 0)
						game.turnList.remove(hex.unit);
					clickUnit.setX(hex.getCenterX() - clickUnit.getWidth() / 2);
					clickUnit
							.setY(hex.getCenterY() - clickUnit.getHeight() / 2);
					clickUnit.setMyHex(hex);
					clickUnit = null;
					for (HexMapElement hexik : map.hexes) {
						hexik.clearColor();
						hexik.shadow(false);
					}
					break;
				}
			}
			// atack
			if (clickUnit != null) {
				for (HexMapElement hex : map.hexes) {
					if (hex.contains(x, y) && hex.unit != null) {
						if (hex.inRangeOfShot
								&& hex.unit.getOwner() != clickUnit.getOwner()) {
							hex.unit.attack(clickUnit);
							hex.unit.setMyHex(hex);
							game.turnList.remove(clickUnit);
							game.refresh();
							panel.repaint();
						}
						break;
					}
				}
				for (HexMapElement hexik : map.hexes) {
					hexik.clearColor();
					hexik.shadow(false);
				}
			}
			if (clickUnit != null) {
				lastX = clickUnit.getX();
				lastY = clickUnit.getY();
				units.remove(clickUnit);
				units.add(clickUnit);
				clickUnit = null;
			}
			for (HexMapElement hex : map.hexes)
				hex.inRangeOfShot = false;
		} else { // select unit
			for (HexMapElement hex : map.hexes) {
				if (hex.contains(x, y) && hex.unit != null) {
					if (game.turnList.contains(hex.unit)) {
						lastHexPosition = hex;
						clickUnit = hex.unit;
						if(hex.getTerrainType()==2)
						map.markAvailableBFS(hex, clickUnit.getMovePoint(),
								clickUnit.getRange());
						break;
					}
				}
			}
			if (clickUnit != null) {
				lastX = clickUnit.getX();
				lastY = clickUnit.getY();
				units.remove(clickUnit);
				units.add(clickUnit);
			}
		}
		game.refresh();
		panel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		for (HexMapElement hex : map.hexes) {
			if (hex.contains(x, y)) {
				if (lastHex != hex) {
					Game.selectedUnit = hex.unit;
					Game.selectedHex = hex;
					if (lastHex != null) {
						hex.shadow(true);
						lastHex.shadow(false);
						lastHex = hex;
					} else {
						hex.shadow(true);
						lastHex = hex;
					}
					panel.repaint();
					break;
				}
			}
		}

	}

}