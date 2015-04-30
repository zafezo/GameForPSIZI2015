package com.mygdx.game.enteties.ai;

import java.util.LinkedList;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enteties.AbstractGameObject;
import com.mygdx.game.enteties.graphAPI.BreadthFirstDirectedPaths;
import com.mygdx.game.enteties.graphAPI.Digraph;
import com.ygdx.game.world.Level;

public class EnemyAI {

	private  LinkedList<Vector2> listOfNodes;
	private AbstractGameObject destinationOb;
	private AbstractGameObject myOb;
	private LinkedList<Integer> path;
	private  BreadthFirstDirectedPaths breadthPath;
	private int lastPosition;
	
	public EnemyAI (AbstractGameObject ob){	
		//showNodes();
		listOfNodes = Level.instance.getListOfNodes();
		breadthPath = new BreadthFirstDirectedPaths(Level.instance.getGraph());
		myOb = ob;
	}
	
	
	private int parsePosition(Sprite ob){
		int position = 0;
		System.out.println(ob.getX() / Level.instance.getTiledWidth() + "-"+ ob.getY() / Level.instance.getTiledHeight() + " = "+ position);
		int x = (int)( (int)ob.getX() / Level.instance.getTiledWidth());
		int y = (int)( (int)ob.getY() / Level.instance.getTiledHeight());	
		position = listOfNodes.indexOf(new Vector2(x, y));
		System.out.println(x + "-"+ y + " = "+ position);
		if(position == -1)
			return 0;
		return position;
	}
	
	
	public Vector2 getNextPosition(){	
		if(path.isEmpty()){
			return null;
		}
		return  new Vector2(listOfNodes.get(path.pop()));
	}

	public void setStart(AbstractGameObject ob) {
		destinationOb = ob;
		breadthPath.setCurrentPoint(parsePosition(myOb));
		path = breadthPath.pathTo(parsePosition(destinationOb));
		Gdx.app.debug("SetStart: ", path + "");
	}
	public void setStart(){
		if(!myOb.isCanJump())
			return;
		breadthPath.setCurrentPoint(parsePosition(myOb));
		int tempPosition = parsePosition(destinationOb);
		if(tempPosition != 0 ){
			lastPosition = tempPosition;			
		}else
			return;
		Gdx.app.debug("SetStartNewStart: lastposition: ", lastPosition + "");
		if(lastPosition >14)
			return;
		path = breadthPath.pathTo(lastPosition);			
		Gdx.app.debug("SetStartNewStart: lastposition2: ", lastPosition + "");
		Gdx.app.debug("SetStartNewStart: ", path + "");
	}
	
	
	public void showNodes(){
		System.out.println(listOfNodes.toString());
	}
	public boolean haveTogo(){
		if((int)destinationOb.getY() == (int)myOb.getY()){
			Gdx.app.debug("haveToGO: ", "GetY ==");
			int distance = Math.abs((int)destinationOb.getX() - (int)myOb.getX());
			Gdx.app.debug("haveToGO: ", "distance "+ distance/Level.instance.getTiledWidth());
			if(distance/Level.instance.getTiledWidth() < 6){
				Gdx.app.debug("haveToGO: ", "distance < 10 ");
				return false;	
			}
					
		}			
		return true;
	}
	
	public boolean missionComplete(){
		return path.isEmpty();
	}
	
}
