package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemies extends AbstractGameObject{	
	
	private boolean jumpLeft;
	private boolean jumpRight;
	private boolean fallRight;
	private boolean fallLeft;
	
	public enum TypeOfEnemies{
		
	}
	
	public Enemies(TiledMapTileLayer collisionLayer) {
		super(collisionLayer);	
		this.set(new Sprite(new Texture("img/player.png")));
		float width = getCollisonLayer().getTileWidth();
		float height = getCollisonLayer().getTileHeight();	
		setPosition(8 * width,2 * height);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
			if(jumpLeft) jumpLeftScenario();	
			if(jumpRight) jumpRightScenario();	
			if(fallLeft) fallLeftScenario();	
			if(fallRight) fallRightScenario();	
	}
	
	private void fallLeftScenario() {
		if(isCanJump()){			
			stopMoving();
			fallLeft = false;
		}
		
	}
	
	public void fallLeft(){
		setCanJump(false);
		fallLeft = false;
		moveLeft();
	}

	private void fallRightScenario() {
		if(isCanJump()){			
			stopMoving();
			fallRight = false;
		}
	}
	
	public void fallRight(){
		setCanJump(false);
		fallRight = false;
		moveRight();
	}

	private void jumpRightScenario() {
		
		//Gdx.app.debug("velocity.y",  (new Float(velocity.y)).toString());
		if (velocity.y <= -10){
			moveRight();
			
		};
		if(isCanJump()){
			//Gdx.app.debug("Enemy ","Grounded");
			stopMoving();
			jumpRight = false;
			return;
		}
		
	}

	private void jumpLeftScenario(){					
		//Gdx.app.debug("velocity.y",  (new Float(velocity.y)).toString());
		if (velocity.y <= -10){
			moveLeft();
			
		};	
		if(isCanJump()){
		//	Gdx.app.debug("Enemy ","Grounded");
			stopMoving();
			jumpLeft = false;
			return;
		}
		
	}
	
	public void jumpLeft(){
		if (isCanJump()){
			setCanJump(false);
			 jump();
		 }
		 jumpLeft = true;
	}
	
	public void jumpRight(){
		if (isCanJump()){
			setCanJump(false);
			 jump();
		 }
		jumpRight = true;
	}
		

}
