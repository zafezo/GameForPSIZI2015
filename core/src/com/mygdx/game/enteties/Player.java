package com.mygdx.game.enteties;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;


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
	
	
}
