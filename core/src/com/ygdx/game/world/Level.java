package com.ygdx.game.world;

import java.util.LinkedList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enteties.graphAPI.Digraph;
import com.mygdx.game.enteties.graphAPI.PathCreating;
import com.mygdx.game.screens.MapSelectedScreen;

public class Level {
	
	public static final Level instance = new Level();
	
	private  TiledMap map ;
	private  byte arrayOfMap[][];
	private byte numberOfNodes;
	private Digraph graph;
	private LinkedList<Vector2> listOfNodes;
	
	public Digraph getGraph() {
		return graph;
	}
	
	public  LinkedList<Vector2> getListOfNodes() {
		return listOfNodes;
	}

	public byte[][] getArrayOfMap() {
		return arrayOfMap;
	}
	

	public byte getNumberOfNodes() {
		return numberOfNodes;
	}
	
	public float getTiledWidth(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		return layer.getTileWidth();
	}
	

	public float getTiledHeight(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		return layer.getTileHeight();
	}
	

	private Level (){}
	
	public  TiledMap getLevelMap (){
		TmxMapLoader loader = new TmxMapLoader();	
		String path = "maps/map" + MapSelectedScreen.currentLevelIndex + ".tmx";;
		
		if (path != null){
			map = loader.load(path);
		}
		
		arrayOfMap = createPathingGraph();
		//show();
		
		PathCreating pathFinding = new PathCreating(arrayOfMap, numberOfNodes);
		listOfNodes = pathFinding.getListOfNodes();
		graph = pathFinding.getGraph();
		//pathFinding.showNodes();
		pathFinding = null;
	
		
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
		//System.out.println(numberOfNodes);	
	}
	
}
