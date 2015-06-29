package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enteties.guns.AbstractGun;
import com.mygdx.game.enteties.guns.Bullet;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;
import com.mygdx.game.enteties.guns.FreezeGun;
import com.mygdx.game.enteties.guns.StandartGun;
public abstract class AbstractGameObject extends Sprite{
	
	// ** physics properties **
		protected Vector2 velocity;
		private float speed = 105;
		private float gravity = 118;
		private float speedX = 60;
		private float increment;
		private TiledMapTileLayer collisionLayer;
		
		private boolean canJump;
		private boolean leftFace;
		
		private AbstractGun gun;
		private Life life;
		
	public  AbstractGameObject (TiledMapTileLayer collisionLayer){
		velocity = new Vector2(0,0);
		canJump = true;
		life = new Life(100);
		this.collisionLayer = collisionLayer;
		leftFace = false;
	}
	
	public AbstractGun getGun(){
		return gun;
	}
	
	public void setGun(TypeOfBullet type) {
		switch (type) {
		case Standrat:
			gun = new StandartGun(this);
			break;
		case Freeze:
			gun = new FreezeGun(this);
			break;

		default:
			break;
		}
	}
	
	public void setAbstractGun(AbstractGun gun){
		this.gun = gun;
	}
	
	public Life getLife(){
		return life;
	}
	

	@Override
	public void draw(Batch batch) {
		update (Gdx.graphics.getDeltaTime());
		setFlip(leftFace, false);
		super.draw(batch);
	}

	
	
	public void update(float deltaTime) {
			life.update(deltaTime);		
			updateXMotion(deltaTime);
			updateYMotion(deltaTime);
			
			
	}
	
	public void hit(Bullet bullet){
		life.hit(bullet);
		//Gdx.app.debug("hit", life.getLifePoint() +"");
	}
	
	private void updateYMotion(float deltaTime){
		// save old position
		float oldY = getY();
		boolean collisionY = false;
		//apply gravity
		velocity.y -= gravity * deltaTime;
		
		//clamp velocity.y
		if (velocity.y > speed)
			velocity.y = speed;
		else
			if (velocity.y < -speed)
				velocity.y = -speed;
		
		
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
		if(getY() < 0)
			setPosition(2 * getCollisonLayer().getTileWidth()
					,1 * getCollisonLayer().getTileHeight());
	}
	
	private void updateXMotion(float deltaTime){
		// save old position
				float oldX = getX();
				boolean collisionX = false;
			
				
				//clamp velocity.x
				if (velocity.x > speedX)
					velocity.x = speedX;
				else
					if (velocity.x < -speedX)
						velocity.x = -speedX;
				
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
		if(!life.isFreeze()){
		velocity.x += -speedX;
		leftFace = false;
		}
	}
	
	public void moveRight (){
		if(!life.isFreeze()){
		velocity.x += speedX;
		leftFace = true;
		}
	}
	
	public void jump (){
		if(!life.isFreeze()){
		setCanJump(false);
		velocity.y = speed;
		}
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
	
	public void setLeftFace (boolean face){
		leftFace = face;
	}
	
}
