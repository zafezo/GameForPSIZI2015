package com.mygdx.game.enteties.guns;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.enteties.AbstractGameObject;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;

public abstract class AbstractGun {
	
	private boolean firing;
	private float delayTimer;
	private float firingTimer;
	private float centerGunX;
	private float centerGunY;	
	private float instance;
	private TypeOfBullet type;
	private AbstractGameObject firingOb;
	
	
	public AbstractGun(AbstractGameObject ob){
		firingOb = ob;
		firingTimer = 0;
		firing = false;
	}
	
	
	protected Bullet creatBullet (Bullet.TypeOfBullet type){
		 return new Bullet(getPositionX(), getPositionY(),
				 firingOb.getLeftFace(), type);
	}
	
	public abstract Bullet getBullet();
	
	private float getPositionX(){
		//Gdx.app.debug("getPositionX: ", centerGunX + "");
		return firingOb.getX() + centerGunX;
	}
	
	private float getPositionY(){
		return firingOb.getY() + centerGunY;
	}
	
	
	
	public void setCenterGunX(float centerGunX) {
		this.centerGunX = centerGunX;
	}

	public void setCenterGunY(float centerGunY) {
		this.centerGunY = centerGunY;
	}

	public boolean isFiring() {
		return firing;
	}

	public void setFiring(boolean firing) {
		if(firing){
			firingTimer = delayTimer;
		}
		this.firing = firing;
	}

	public float getDelayTimer() {
		return delayTimer;
	}

	public void setDelayTimer(float delayTimer) {
		this.delayTimer = delayTimer;
	}		
	
	public boolean haveToFire(float deltaTime){
		if(firing){
			firingTimer +=deltaTime;		
			if(firingTimer > delayTimer){
				firingTimer = 0;
				return true;
			}
		}
		return false;
	}


	public float getInstance() {
		return instance;
	}


	public void setInstance(float instance) {
		this.instance = instance;
	}


	public TypeOfBullet getType() {
		return type;
	}


	public void setType(TypeOfBullet type) {
		this.type = type;
	}

	
}
