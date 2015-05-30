package com.mygdx.game.enteties;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.enteties.guns.AbstractGun;
import com.mygdx.game.enteties.guns.FreezeGun;
import com.mygdx.game.enteties.guns.StandartGun;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;


public class Player extends AbstractGameObject{

	private  float width;
	private  float height;
	private int score;
	
	public Player ( TiledMapTileLayer collisionLayer){
		super(collisionLayer);		
		this.set(new Sprite(new Texture("img/94.png")));
		width =  getCollisonLayer().getTileWidth();
		height = getCollisonLayer().getTileHeight();	
		setPosition(1 * width,1 * height);
		setSize(width, height-1);
		setGun(TypeOfBullet.Standrat);
		score = 0;
		getLife().setLife(100 + 40*GamePreferences.instance.getLifeLevel());
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}	
	
	public void addScore(int score){
		this.score += score;
	}
	
	@Override
	public void setGun(TypeOfBullet type) {
		AbstractGun gun = null;
		switch (type) {
		case Standrat:
			gun = new StandartGun(this);
			gun.setInstance(10*(2+GamePreferences.instance.getStandartGunLevel()));
			gun.setDelayTimer(0.025f*(Constants.maxLevel - GamePreferences.instance.getStandartGunLevel()));
			break;
		case Freeze:
			gun = new FreezeGun(this);
			gun.setInstance(10*(2+GamePreferences.instance.getFreezeGunLevel()));
			gun.setDelayTimer(0.025f*(Constants.maxLevel - GamePreferences.instance.getFreezeGunLevel()));
			break;
		default:
			break;
		}
		setAbstractGun(gun);
	}
	
}
