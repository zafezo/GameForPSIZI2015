package com.mygdx.game.enteties.graphAPI;

import com.badlogic.gdx.math.Vector2;

public class PathFinding {
	
	private  byte arrayOfMap[][];
	private Digraph graph;
	private Vector2 listOfNodes[];
	
	public PathFinding(byte[][] arrayOfMap, byte numberOfNodes) {
		super();
		this.arrayOfMap = arrayOfMap;
		graph = new Digraph(numberOfNodes+1);	
		listOfNodes = new Vector2[numberOfNodes+1];
		generateGraph();
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
		System.out.println(graph.toString());
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
		delta = -1;
			while((arrayOfMap[i+delta][j+1] < 0) && ((i+delta) > 0)){
				delta--;
			}
			if((arrayOfMap[i+delta][j+1] > 0) && ((i+delta) > 0)){
				graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j+1] );
				if(Math.abs(delta) < 3)
				graph.addEdge( arrayOfMap[i+delta][j+1], arrayOfMap[i][j]);
			}
			//floor at two right block
			delta = -1;
			while((arrayOfMap[i+delta][j+2] < 0) && ((i+delta) > 0)){
				delta--;
			}
			if((arrayOfMap[i+delta][j+2] > 0) && ((i+delta) > 0)){
				graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j+2] );
				if(Math.abs(delta) < 3)
				graph.addEdge(arrayOfMap[i+delta][j+2],arrayOfMap[i][j] );
			}
			//floor at three right block
			delta = -1;
			while((arrayOfMap[i+delta][j+3] < 0) && ((i+delta) > 0)){
				delta--;
			}
			if((arrayOfMap[i+delta][j+3] > 0) && ((i+delta) > 0)){
				graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j+3] );
				if(Math.abs(delta) < 3)
				graph.addEdge(arrayOfMap[i+delta][j+3],arrayOfMap[i][j] );
			}
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
		delta = -1;
		while((arrayOfMap[i+delta][j-1] < 0) && ((i+delta) > 0)){
					delta--;
		}
		if((arrayOfMap[i+delta][j-1] > 0) && ((i+delta) > 0)){
					graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j-1] );
					if(Math.abs(delta) < 3)
					graph.addEdge( arrayOfMap[i+delta][j-1], arrayOfMap[i][j]);
			}
		//floor at two left block
		delta = -1;
		while((arrayOfMap[i+delta][j-2] < 0) && ((i+delta) > 0)){
					delta--;
		}
		if((arrayOfMap[i+delta][j-2] > 0) && ((i+delta) > 0)){
					graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j-2] );
					if(Math.abs(delta) < 3)
					graph.addEdge( arrayOfMap[i+delta][j-2],arrayOfMap[i][j]);
			}
		//floor at three left block
		delta = -1;
		while((arrayOfMap[i+delta][j-3] < 0) && ((i+delta) > 0)){
					delta--;
		}
		if((arrayOfMap[i+delta][j-3] > 0) && ((i+delta) > 0)){
					graph.addEdge(arrayOfMap[i][j], arrayOfMap[i+delta][j-3] );
					if(Math.abs(delta) < 3)
					graph.addEdge( arrayOfMap[i+delta][j-3],arrayOfMap[i][j]);
			}
		}
		
		
		//add jump-Nodes
		// three up-square are free
		if(arrayOfMap[i+1][j] < 0 
				&& arrayOfMap[i+2][j] < 0
				 && arrayOfMap[i+3][j] < 0){
			
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
