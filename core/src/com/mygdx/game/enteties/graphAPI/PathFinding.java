package com.mygdx.game.enteties.graphAPI;

import com.badlogic.gdx.math.Vector2;

public class PathFinding {
	
	private  byte arrayOfMap[][];
	private Digraph graph;
	private Vector2 listOfNodes[];
	private BreadthFirstDirectedPaths breadth;
	
	public PathFinding(byte[][] arrayOfMap, byte numberOfNodes) {
		super();
		this.arrayOfMap = arrayOfMap;
		graph = new Digraph(numberOfNodes+1);	
		listOfNodes = new Vector2[numberOfNodes+1];
		generateGraph();
		breadth = new BreadthFirstDirectedPaths(graph);
		breadth.setStartPoint(1);
		System.out.println(breadth.pathTo(46));
	}
	
	
	private void generateGraph (){
		for(int i = 1; i < arrayOfMap.length; i++){
			for(int j = 1; j < arrayOfMap[i].length; j++){
				if (arrayOfMap[i][j] > 0){
					addNode(i,j);
					addNodesToGraph(i,j);
				}
			}
		}
		//showNodes();
		//System.out.println(graph.toString());
	}

	private void addNodesToGraph(int i, int j) {
		
		int delta;
		//NOT last right platform
		if(arrayOfMap[i][j+1]>0){
		//what is right?
			delta = 1;
		while(arrayOfMap[i][j+delta] > 0)	{	
			//right is platform
			graph.addEdge(arrayOfMap[i][j], arrayOfMap[i][j+delta]);
			delta++;
			}
		}else if (i > 2){
			//last platform
			//find floor
			//floor at one right block
			findFlorAt(i, j, 1);
			findFlorAt(i, j, 2);
			findFlorAt(i, j, 3);	
		}
		
		
		//NOT last left platform
		if(arrayOfMap[i][j-1]> 0){
		//what is left?
		 delta = -1;
		while(arrayOfMap[i][j+delta] > 0)	{	
			//left is platform
			graph.addEdge(arrayOfMap[i][j], arrayOfMap[i][j+delta]);
			delta--;
			}
		}else if (i>2){
			//last platform
			//find floor
			//floor at one left block
			findFlorAt(i, j, -1);
			findFlorAt(i, j, -2);
			findFlorAt(i, j, -3);	
		}
		
		
	}

	private void findFlorAt(int i, int j,int distance){
		int delta = 0;
		boolean notPlatform = true;
		while((arrayOfMap[i+delta][j+distance] < 0) && ((i+delta) > 0)){
			if (arrayOfMap[i+delta][j+distance] == -1){
				notPlatform = false;
			}
			delta--;			
		}
		if((arrayOfMap[i+delta][j+distance] > 0) && ((i+delta) > 0)){
			graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j+distance] );
			if(Math.abs(delta) < 3 && notPlatform)
			graph.addEdge( arrayOfMap[i+delta][j+distance], arrayOfMap[i][j]);
		}
	}

	private void addNode(int i, int j) {
		listOfNodes[arrayOfMap[i][j]] = new Vector2(j,i);
	}
	
	public void showNodes(){
		for (int i = 1 ; i<listOfNodes.length; i++){
			System.out.println(i + " - " + (listOfNodes[i].x) + " " + (listOfNodes[i].y));
		}
	}
}
