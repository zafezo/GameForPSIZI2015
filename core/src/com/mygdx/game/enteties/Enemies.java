package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enteties.ai.EnemyAI;
import com.mygdx.game.enteties.guns.Bullet;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;

public class Enemies extends AbstractGameObject{	
	
	private Vector2 nextPosition;
	private float width;
	private float height;
	private EnemyAI ai;
	
	
	public enum TypeOfEnemies{
		
	}
	
	public Enemies(TiledMapTileLayer collisionLayer) {
		super(collisionLayer);	
		this.set(new Sprite(new Texture("img/player.png")));
		width = getCollisonLayer().getTileWidth();
		height = getCollisonLayer().getTileHeight();	
		setPosition(5 * width ,1 * height);
		ai = new EnemyAI(this);
		nextPosition = new Vector2(getX(), getY());
		setGun(TypeOfBullet.Standrat);
	}
	
	public void folowObject(AbstractGameObject ob){
		if (ob instanceof Player){			
			ai.setStart(ob);
			newTask();
		}
	}	

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		ai.shootAI();
		//System.out.println("update: "+ taskIsDone() + ai.changePosition());
		if(taskIsDone()) {
			if((ai.haveTogo() && ai.missionComplete()) 
					|| ai.objectChangePosition()){	
					ai.setStart();
			}else 
			if (!ai.missionComplete())
				newTask();
		}			
		else
			goTo();		
		
			
	}
	
	private void newTask() {
		stopMoving();
		Vector2 temp = ai.getNextPosition();
		if(temp == null){
			Gdx.app.debug("newTask: temp = ", temp + "");
			return;
		}
		//Gdx.app.debug("newTask: ", temp + "");	
		temp.x *= width;
		temp.y *= height;
		//Gdx.app.debug("newTask: ", temp + "");
		nextPosition = temp;
		goTo();
	}

	private void goTo(){
		//must i jump?		
			if(nextPosition.y > (int)getY()){
				jumpTo();
			}else if(nextPosition.x != (int)getX()) 
				moveTo();
					
	}
	


	private void jumpTo(){
		
		
			if(!isCanJump()){
				if(nextPosition.y <= (int)getY())			
					moveTo();
				return;
			}
			if(isCanJump() &&(nextPosition.y > (int)getY())){
				jump();
				setCanJump(false);
			}	
	
	}	
	

	private void moveTo() {
	
		float delta = nextPosition.x - getX();
		//Gdx.app.debug("moveTo: ",(int)nextPosition.x +" "+ getX());
		if (delta > 1){
			this.moveRight();			
		}else 
			if(delta < -1){
				this.moveLeft();
			}else{
				stopMoving();
			}
		
	}



	
	private boolean taskIsDone(){
		boolean compareX = Math.abs((nextPosition.x) - (int)(getX())) < 2;
		if(compareX){
			setX(nextPosition.x);
		}
		boolean compareY = Math.abs((nextPosition.y) - (getY())) < 1;
		//Gdx.app.debug("taskIDone: ",(int)nextPosition.x +" "+ getX() + " " + (int)nextPosition.y + " " + (int)getY());
		return compareX && compareY ;
	}
	
	public void bulletAI(Bullet bull){
		ai.preventHintBulletAtEnemy(bull);
	}

}
