package battleOfTCS.gui;

import java.util.ArrayList;
import java.util.LinkedList;


public class HexMap {
	
	public LinkedList<HexMapElement> hexes;
	private static int amountInOddRow = 13;
	private static int amountInEvenRow = 13;
	public static int getAmountInOddRow() {
		return amountInOddRow;
	}

	private static int amountOfRows = 10;
	
	static double width;
	static double height;
	
	public static int firstHexCenterX; // position of the first hex in the first row
	public static int firstHexCenterY = 124;
	
	public HexMap() {
		
		hexes = new LinkedList<HexMapElement>();
        
        boolean oddRow = true;
        for(int i=1; i <= amountOfRows; i++) {
        	
        	/* position of a center of the first hex in this row */
        	int centerX = oddRow ? firstHexCenterX : firstHexCenterX + HexMapElement.width / 2;
        	int centerY = firstHexCenterY + 3*(i-1)* HexMapElement.side / 2;
        	int amountOfHexes = oddRow ? amountInOddRow : amountInEvenRow;
        	
        	for(int j=1; j <= amountOfHexes; j++) {
        		hexes.add(new HexMapElement(true, centerX, centerY));
        		centerX += HexMapElement.width;
        	}
        	
        	oddRow = !oddRow;
        }
        
        for(HexMapElement hex : hexes) {
        	getNeighbours(hex);
        }
	}
	
	public static HexMap getNewMap() {
		
		HexMapElement.resetIdCounter();
		return new HexMap();
	}
	
	public void getNeighbours(HexMapElement hex) {
		LinkedList<HexMapElement> Neighbours = new LinkedList<HexMapElement>();
		int x = hex.getCenterX();
		int y = hex.getCenterY();
		int vert = 3 * HexMapElement.side / 2;
		int hor = HexMapElement.width;
		
		for(HexMapElement cell : hexes) {
			if(cell.contains(x + hor/2, y + vert))
				Neighbours.add(cell);
			if(cell.contains(x + hor/2, y - vert))
				Neighbours.add(cell);
			if(cell.contains(x - hor/2, y + vert))
				Neighbours.add(cell);
			if(cell.contains(x - hor/2, y - vert))
				Neighbours.add(cell);
			if(cell.contains(x + hor, y))
				Neighbours.add(cell);
			if(cell.contains(x - hor, y))
				Neighbours.add(cell);
		}
		
		hex.setNeighbours(Neighbours);
	}
	
	public void markAvailableBFS(HexMapElement cell, int moveRange, int shotRange) {
		if(Game.tacticSet==0){
			/*
			 *  Highlighting cells in the range of move
			 */
			ArrayList<Integer> dist = new ArrayList<Integer>();
			for(int i=0; i<hexes.size(); i++)
				dist.add(-1);
			
			dist.set(cell.id, 0);
			cell.setDistance(0);
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(cell.id);
			
			while(!queue.isEmpty()) {
				Integer id = queue.removeFirst();
				HexMapElement hex = hexes.get(id);
				
				if(dist.get(id) <= moveRange)
					hex.shadowLight(true);
				
				for(HexMapElement neigh : hex.Neighbours) {
					if(dist.get(neigh.id).equals(-1) && neigh.occupied == false) {
						dist.set(neigh.id, dist.get(id) + 1);
						neigh.setDistance(dist.get(neigh.id));
						queue.add(neigh.id);
					}
				}			
			}
			
			/*
			 * Highlighting cells in the range of shot after move
			 */
			
			for(int i = 0; i < dist.size(); i++) 
				if(dist.get(i) != -1 && dist.get(i) < moveRange)
					markCellsInShotRangeAfterMove(hexes.get(i), cell, shotRange);
			
			/*
			 * Highlighting cells in the range of shot without move
			 */
			
			queue.clear();
			dist.clear();
			for(int i=0; i<hexes.size(); i++)
				dist.add(-1);
			dist.set(cell.id, 0);
			queue.add(cell.id);
			
			while(true) {
				Integer id;
				if(queue.size() > 0)
					id = queue.removeFirst();
				else break;
				
				if(dist.get(id) > shotRange)
					break;
				
				HexMapElement hex = hexes.get(id);
				if(hex.unit != null && hex.unit.getOwner() != cell.unit.getOwner()){
					if(hex.isGreen) hex.green(false);
					hex.red(true);
					hex.inRangeOfShot();
				}
				
				for(HexMapElement neigh : hex.Neighbours) {
					if(dist.get(neigh.id).equals(-1)) {
						dist.set(neigh.id, dist.get(id) + 1);
						queue.add(neigh.id);
					}
				}			
			}
		}
		else if(Game.tacticSet == 2 ){ // player 1
			for(HexMapElement hex : hexes) {
				if(hex.tacticSet==1 && hex.unit==null){
					hex.shadowLight(true);
				}
			}
		}
		else{ // Game.tacticSet == 1    player 2
			for(HexMapElement hex : hexes) {
				if(hex.tacticSet==2 && hex.unit==null){
					hex.shadowLight(true);
				}
			}
		}
	}
	
	private void markCellsInShotRangeAfterMove(HexMapElement cell, HexMapElement mainCell, int shotRange) {
		if(Game.tacticSet==0){
			ArrayList<Integer> dist = new ArrayList<Integer>();
			for(int i=0; i<hexes.size(); i++)
				dist.add(-1);
			dist.set(cell.id, 0);
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(cell.id);
			
			while(true) {
				Integer id;
				if(queue.size() > 0)
					id = queue.removeFirst();
				else break;
				
				if(dist.get(id) > shotRange)
					break;
				
				HexMapElement hex = hexes.get(id);
				if(hex.unit != null && hex.unit.getOwner() != mainCell.unit.getOwner()){
					if(!hex.isShadowLight) hex.yellow(true);
				}
				
				for(HexMapElement neigh : hex.Neighbours) {
					if(dist.get(neigh.id).equals(-1)) {
						dist.set(neigh.id, dist.get(id) + 1);
						queue.add(neigh.id);
					}
				}			
			}
		}
	}

	public void clear() {
		for(HexMapElement hex : hexes) {
        	hex.occupied=false;
        	hex.unit=null;
        	hex.clearColor();
		}
	}
}
