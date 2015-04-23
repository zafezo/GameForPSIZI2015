package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject extends Sprite{
	
	// ** physics properties **
		protected Vector2 velocity;
		private float speed = 102;
		private float gravity = 110;
		private float increment;
		private TiledMapTileLayer collisionLayer;
		
		private boolean canJump;
		private boolean leftFace;
		
	public  AbstractGameObject (TiledMapTileLayer collisionLayer){
		velocity = new Vector2(0,0);
		canJump = true;
		this.collisionLayer = collisionLayer;
		leftFace = true;
	}
	
	@Override
	public void draw(Batch batch) {
		update (Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}

	public void update(float deltaTime) {
		

		// save old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false;
		boolean collisionY = false;
		//apply gravity
		velocity.y -= gravity * deltaTime;
		
		//clamp velocity
		if (velocity.y > speed)
			velocity.y = speed;
		else
			if (velocity.y < -speed)
				velocity.y = -speed;
		
		// calculate the increment for step in #collidesLeft() and #collidesRight()
		increment = getWidth()/4;
		
		setX(getX() + velocity.x * deltaTime);

		collisionX = (velocity.x > 0) ? collidesRight() : collidesLeft();
		
		//Gdx.app.debug("collisonX",  (new Boolean(collisionX)).toString());
		// move on x
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
		}
		
		
		// calculate the increment for step in #collidesBottom() and #collidesTop()
		increment = getHeight()/4;			
		setY(getY() + velocity.y * deltaTime);
		if (velocity.y > 0){ // going up
			collisionY = collidesTop();
		}else{	//going down
			collisionY = collidesBottom();
			 setCanJump(collisionY);
		}
		
		//Gdx.app.debug("collisonY",  (new Boolean(collisionY)).toString());
	
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
	}
	
	public TiledMapTileLayer getCollisonLayer (){
		return collisionLayer;
	}
	
	private boolean isBlocked(float X, float Y){
		Cell cell = collisionLayer.getCell((int) (X / collisionLayer.getTileWidth()), 
				(int) (Y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()
				.getProperties().containsKey("blocked");
	}
	
	private boolean collidesTop (){
		for (float step = 0; step <=getWidth(); step += increment)
			if (isBlocked(getX()+ step, getY()+ getHeight()))
				return true;
		return false;
		
	}
	private boolean collidesBottom (){
		for (float step = 0; step <= getWidth(); step += increment)
			if (isBlocked(getX()+ step, getY()))
				return true;			
		return false;		
	}
	
	private boolean collidesRight (){
		for(float step = 0; step <= getHeight(); step += increment)
			if(isBlocked(getX()+ getWidth(), getY()   + step))
				return true;
		return false;	
	}
	
	private boolean collidesLeft (){
		for(float step = 0; step <= getHeight(); step += increment)
			if(isBlocked(getX(), getY() + step))
				return true;
		return false;		
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}		
	
	public void moveLeft (){
		velocity.x = -speed;
		leftFace = false;
	}
	
	public void moveRight (){
		velocity.x = speed;
		leftFace = true;
	}
	
	public void jump (){
		velocity.y = speed;
	}
	
	public void stopMoving(){
		velocity.x = 0;
	}

	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public boolean getLeftFace (){
		return leftFace;
	}
	
	
	public Bullet fire (Bullet.TypeOfBullet type){
		 return new Bullet(getX() + getWidth()/2, getY() + getHeight()/2,
				 getLeftFace(), type);
	}
}