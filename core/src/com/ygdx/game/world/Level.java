package com.ygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Level {
	
	public static Level instance = new Level();
	private TiledMap map;
	private  byte array[][];
	
	private Level(){
		
	}
	
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
		
		array = createPathingGraph();
		show();
		
		return map;
	}
	
	private byte[][] createPathingGraph(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		//byte array[][] = new byte [layer.getWidth()][ layer.getHeight()];
		byte array[][] = new byte [ layer.getHeight()] [layer.getWidth()];
		Cell cell;
		for (int i = 0; i<layer.getHeight(); i++ ){
			for (int j = 0; j<layer.getWidth(); j++){
				cell = layer.getCell(j, i);
				if (cell != null && cell.getTile() != null && cell.getTile()
						.getProperties().containsKey("blocked")){
					array[i][j] = 1;
				}else{
					array[i][j] = 0;
				}
			}
		}
		return array;
	}
	private  void show(){
		//for (int i = array.length -1 ; i>=0; i-- ){
		for (int i = 0; i<array.length; i++ ){
			for (int j = 0; j<array[i].length; j++){
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
	
}
