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
	private HexMapElement lastHex,lastHexPosition;
	private HexMap map;
	private static Game game;
	


	public DragNDrop(List<Unit> units, JPanel panel, HexMap map, Game games) {
		this.map = map;
		game = games;
		this.units = units;
		this.panel = panel;
	}


	@Override
	public void mousePressed(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		for(HexMapElement hex : map.hexes){
			if( hex.contains(x,y) && hex.unit!=null){
				lastHexPosition=hex;
				dragUnit=hex.unit;
				this.dragOffsetX = x - dragUnit.getX();
				this.dragOffsetY = y - dragUnit.getY();
				this.dragUnit = hex.unit;
				map.markAvailableBFS(hex, dragUnit.getMovePoint());		
				break;
			}
		}
		if(this.dragUnit != null){
			this.lastX=dragUnit.getX();
			this.lastY=dragUnit.getY();
			this.units.remove( this.dragUnit );
			this.units.add(this.dragUnit);
		}
	}
	


	@Override
	public void mouseReleased(MouseEvent evt) {
		if(dragUnit!=null){
			for(HexMapElement hex : map.hexes){
				if( hex.contains(evt.getX(), evt.getY()) && hex.getImage().toString().equals(HexMapElement.imgHexNeigh.toString())){
					if(lastHex!=null){
						lastHexPosition.shadow();
						lastHexPosition.unit=null;
						lastHexPosition.occupied=false;
						lastHexPosition=null;
					}
					hex.unit=dragUnit;
					hex.occupied=true;
					this.dragUnit.setX(hex.getCenterX()-dragUnit.getWidth()/2);
					this.dragUnit.setY(hex.getCenterY()-dragUnit.getHeight()/2);
					this.dragUnit=null;
					for(HexMapElement hexik : map.hexes){
						hexik.setImage(HexMapElement.imgHex);
						if(hexik.isShadow)
							hexik.shadow();
					}
					break;
				}
			}
			if(dragUnit!=null){
				dragUnit.setX(lastX);
				dragUnit.setY(lastY);
				dragUnit=null;
				clickUnit=null;
				for(HexMapElement hexik : map.hexes){
					hexik.setImage(HexMapElement.imgHex);
					if(hexik.isShadow)
						hexik.shadow();
				}
			}
			this.panel.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		if(this.dragUnit != null){
			this.dragUnit.setX(x - this.dragOffsetX);
			this.dragUnit.setY(y - this.dragOffsetY);
			this.panel.repaint();
		}
		for(HexMapElement hex : map.hexes) {
			if(hex.contains(x, y)) {
				if(lastHex!=hex){
					if(lastHex!=null){
						hex.shadow();
						lastHex.shadow();
						lastHex=hex;
					}
					else{
						hex.shadow();
						lastHex=hex;
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
		if(evt.getButton() == MouseEvent.BUTTON3){
			clickUnit=null;
			lastHexPosition=null;
			for(HexMapElement hexik : map.hexes){
				hexik.setImage(HexMapElement.imgHex);
				if(hexik.isShadow)
					hexik.shadow();
			}
		}
		else
		if(this.clickUnit != null){ //move unit
			for(HexMapElement hex : map.hexes){
				if( hex.contains(x, y)  && hex.getImage().toString().equals(HexMapElement.imgHexNeigh.toString()) ){
					if(lastHexPosition!=null){
						lastHexPosition.occupied=false;
						lastHexPosition.unit=null;
					}
					lastHexPosition=null;
					hex.unit=clickUnit;
					hex.occupied=true;
					game.turnList.remove(hex.unit);
					this.clickUnit.setX(hex.getCenterX()-clickUnit.getWidth()/2);
					this.clickUnit.setY(hex.getCenterY()-clickUnit.getHeight()/2);
					this.clickUnit=null;
					for(HexMapElement hexik : map.hexes){
						hexik.setImage(HexMapElement.imgHex);
						if(hexik.isShadow)
							hexik.shadow();
					}
					break;
				}	
			}
			//atack
			if(this.clickUnit != null){
				for(HexMapElement hex : map.hexes){
					if( hex.contains(x, y)  && hex.unit!=null ){
						if(hex.unit.getOwner()!=game.getTurn()){
							hex.unit.attack(clickUnit.getAttack());
							hex.unit.setMyHex(hex);
							game.turnList.remove(clickUnit);
							game.refresh();
							panel.repaint();
						}
						break;
					}
				}
				for(HexMapElement hexik : map.hexes){
					hexik.setImage(HexMapElement.imgHex);
					if(hexik.isShadow)
						hexik.shadow();
				}
			
				
				
			}
			if(this.clickUnit != null){
				this.lastX=clickUnit.getX();
				this.lastY=clickUnit.getY();
				this.units.remove( this.clickUnit );
				this.units.add(this.clickUnit);
				clickUnit=null;
			}

		}
		else{ //select unit
			game.refresh();
			for(HexMapElement hex : map.hexes){
				if( hex.contains(x,y) && hex.unit!=null ){
					if(game.turnList.contains(hex.unit)){
						lastHexPosition=hex;
						this.clickUnit = hex.unit;
						map.markAvailableBFS(hex, clickUnit.getMovePoint());	
						break;
					}
				}
			}
			if(this.clickUnit != null){
				this.lastX=clickUnit.getX();
				this.lastY=clickUnit.getY();
				this.units.remove( this.clickUnit );
				this.units.add(this.clickUnit);
			}
		}		
		game.refresh();
		this.panel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		for(HexMapElement hex : map.hexes) {
			if(hex.contains(x, y)) {
				if(lastHex!=hex){
					Game.selectedUnit=hex.unit;
					if(lastHex!=null){
						hex.shadow();
						lastHex.shadow();
						lastHex=hex;
					}
					else{
						hex.shadow();
						lastHex=hex;
					}
					this.panel.repaint();
					break;
				}
			}
		}
		
	}

}