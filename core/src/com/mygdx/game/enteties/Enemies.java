package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemies extends AbstractGameObject{	
	
	private boolean moving,jumping, mLeft, mRight;
	private float destinathionX, destinathionY;
	
	private float width ;
	private float height;
	
	public enum TypeOfEnemies{
		
	}
	
	public Enemies(TiledMapTileLayer collisionLayer) {
		super(collisionLayer);	
		this.set(new Sprite(new Texture("img/player.png")));
		width = getCollisonLayer().getTileWidth();
		height = getCollisonLayer().getTileHeight();	
		setPosition(8 * width,2 * height);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (moving){
			//Gdx.app.debug("Positioh of enemy", new Integer((int)(getX()/width)).toString());
			if(getX() > destinathionX && mRight){
				moving = false;
				mRight = false;
				stopMoving();
			}else if(getX() < destinathionX && mLeft){
				moving = false;
				mLeft = false;
				stopMoving();
			}
			if (jumping){
				if (velocity.y <30){
					if(getX() > destinathionX ){
						jumping = false;
						mLeft = true;
						moveTo((int)(destinathionX / width));
					}else if(getX() < destinathionX ){
						jumping = false;
						mRight = true;
						moveTo((int)(destinathionX / width));
					}
				}
			}
		}
	}
	
	

	public void moveTo(int X){
		moving = true;
		destinathionX = X * width;
		if (destinathionX > getX()){
			moveRight();
			mRight = true;
		}else if (destinathionX < getX()){
			moveLeft();
			mLeft = true;
		}
	}
	
	public void jumpTo(int X, int Y){
		jumping = moving = true;
		destinathionX = X * width;
		//destinathionY = Y * height;
		jump();
	}
		

}
