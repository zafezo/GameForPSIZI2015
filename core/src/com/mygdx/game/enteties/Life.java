package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.enteties.guns.AbstractGun;

public class Life {
	private float lifePoint;
	private boolean freeze;
	private boolean poison;
	private boolean dead;
	private float poisonTimer;
	private float freezeTimer;
	
	public Life(){
		lifePoint = 100;
		freeze = false;
		poison = false;
		dead = false;
		poisonTimer = 0;
		freezeTimer = 0;
	}
	
	public void hit(AbstractGun gun){
		float instance = gun.getInstance();
		switch (gun.getType()){
		case Freeze:
			freezeHit(instance);
			break;
		case Poison:
			poisonHit(instance);
			break;
		case Standrat:
			standartHit(instance);
			break;
		default:
			break;		
		}
		dead();
	}

	private void freezeHit(float instance) {
		freeze = true;		
		freezeTimer = instance;
	}

	private void poisonHit(float instance) {
		poison = true;
		poisonTimer = instance;	
	}

	private void dead() {
		dead = lifePoint < 0;
		
	}

	private void standartHit(float instance) {
			lifePoint -=instance;
			Gdx.app.debug("Hint", lifePoint +"");		
	}
	
	public void update(float deltaTime){
		if(freeze){
			freezeTimer -= deltaTime;
			if(freezeTimer < 0){
				freeze = false;
			}
		}
		if(poison){
			poisonTimer -= deltaTime;
			if(poisonTimer < 0){
				poison = false;
			}else{
				lifePoint -= deltaTime*10;
			}
		}
	}

	public int getLifePoint() {
		return (int) lifePoint;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public boolean isDead() {
		return dead;
	}

}
