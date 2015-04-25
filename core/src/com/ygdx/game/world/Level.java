package com.ygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.enteties.graphAPI.PathFinding;

public class Level {
	
	public static final Level instance = new Level();
	
	private  TiledMap map ;
	private  byte arrayOfMap[][];
	private byte numberOfNodes;
	private PathFinding pathFinding;
	
	private Level (){}
	
	public  TiledMap getLevelMap (int level){
		TmxMapLoader loader = new TmxMapLoader();	
		String path = null;
		switch (level){
			case 1:
				path = "maps/myMap.tmx";
				break;
				
			default:
				Gdx.app.debug(Level.class.getName(), "Wrong number of level");				
		}
		
		if (path != null){
			map = loader.load(path);
		}
		
		arrayOfMap = createPathingGraph();
		show();
		
		pathFinding = new PathFinding(arrayOfMap, numberOfNodes);
		//pathFinding.showNodes();
		
		
		return map;
	}
	
	private  byte[][] createPathingGraph(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		//byte array[][] = new byte [layer.getWidth()][ layer.getHeight()];
		byte array[][] = new byte [ layer.getHeight()] [layer.getWidth()];
		byte count = 0;
		Cell cell;
		for (int i = 0; i<layer.getHeight(); i++ ){
			for (int j = 0; j<layer.getWidth(); j++){
				cell = layer.getCell(j, i);
				if (cell != null && cell.getTile() != null && cell.getTile()
						.getProperties().containsKey("blocked")){
					array[i][j] = -1;
				}else{
					if (array[i-1][j] == -1){
						count++;
						array[i][j] = count;
					}else{
						array[i][j] = -2;
					}
					
				}
			}
		}
		numberOfNodes = count;
		return array;
	}
	

	private  void show(){
		for (int i = 0; i<arrayOfMap.length; i++ ){
			for (int j = 0; j<arrayOfMap[i].length; j++){
				System.out.print(arrayOfMap[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println(numberOfNodes);	
	}
	
}
