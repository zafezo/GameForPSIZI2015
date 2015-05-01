package com.ygdx.game.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.enteties.Enemies;
import com.mygdx.game.enteties.guns.Bullet;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;
import com.mygdx.game.enteties.Player;
import com.mygdx.game.util.Constants;

public class WorldController extends InputAdapter implements InputProcessor{

	private static final String TAG = WorldController.class.getName();
	
	public  TiledMap map;	
	public Player player;
	private Game game;
	public Enemies enemy;
	private TiledMapTileLayer collsionLayer;
	
	public Array<Bullet> playerBullets;
	public Array<Bullet> enemiesBullets;
	

	
	public WorldController (Game game){
		this.game = game;
		init();
	}

	private void init() {		
		map = Level.instance.getLevelMap(1);	
		collsionLayer = (TiledMapTileLayer) (map.getLayers().get(0));
		Constants.mapWidth = collsionLayer.getWidth();
		Constants.mapTiledWidth = collsionLayer.getTileWidth();
		player = new Player(collsionLayer);
		enemy = new Enemies(collsionLayer);
		enemy.folowObject(player);
		Gdx.input.setInputProcessor(this);
		initLevel();
	}

	private void initLevel() {		
		playerBullets = new Array<Bullet>();
		enemiesBullets = new Array<Bullet>();
	}
	
	public void update (float deltaTime){
		//update enemies
		enemy.update(deltaTime);
		updateEnemiesFiring(deltaTime);
		updateEnemiesBullet(deltaTime);
		
		//update player
		player.update(deltaTime);
		updatePlayerBullet(deltaTime);
		updatePlayerFiring(deltaTime);		
		
		
	}
	
	
	private void updatePlayerFiring(float deltaTime){
		if(player.getGun().haveToFire(deltaTime)){
			playerBullets.add(player.getGun().getBullet());
		}
	}
	
	private void updateEnemiesFiring(float deltaTime){
		if(enemy.getGun().haveToFire(deltaTime)){
			enemiesBullets.add(enemy.getGun().getBullet());
		}
	}
	

	private void updateEnemiesBullet(float deltaTime){
		for (int i=0 ; i < enemiesBullets.size; i++){			
			Bullet bullet = enemiesBullets.get(i);
			if(bullet.isCollision(player)){
				player.hit(enemy.getGun());
				enemiesBullets.removeIndex(i);
			}
			if (bullet.isCollision()
					|| bullet.isCollision(collsionLayer))
			{
				enemiesBullets.removeIndex(i);
				
			}else{
				bullet.update(deltaTime);
			}
		}
	}
	
	
	
	
	private void updatePlayerBullet(float deltaTime){
		for (int i=0 ; i < playerBullets.size; i++){			
			Bullet bullet = playerBullets.get(i);
			enemy.bulletAI(bullet);
			if(bullet.isCollision(enemy)){
				enemy.hit(enemy.getGun());
				playerBullets.removeIndex(i);
			}
			if (bullet.isCollision()
					|| bullet.isCollision(collsionLayer))
			{
				playerBullets.removeIndex(i);
				
			}else{
				bullet.update(deltaTime);
			}
		}
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		
		switch (keycode){
			case Keys.W:
				 if (player.isCanJump()){
					 player.setCanJump(false);
					 player.jump();
				 }
				break;
			case Keys.A:
				player.moveLeft();
				break;
			case Keys.D:
				player.moveRight();
				break;
			case Keys.Z:
				player.getGun().setFiring(true);
				break;			
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
		case Keys.W:
			break;
		case Keys.A:			
		case Keys.D:
			player.stopMoving();
			break;	
		case Keys.Z:
			player.getGun().setFiring(false);
			break;	
	}
		return true;
	}
}
