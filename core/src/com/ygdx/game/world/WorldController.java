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
	//public Enemies enemy;
	private TiledMapTileLayer collsionLayer;
	
	private int stage;
	private float enemiesTimer;
	
	public Array<Bullet> playerBullets;
	public Array<Bullet> enemiesBullets;
	public Array<Enemies> enemies;
	

	
	public WorldController (Game game){
		this.game = game;
		init();
	}

	private void init() {		
		map = Level.instance.getLevelMap(1);	
		collsionLayer = (TiledMapTileLayer) (map.getLayers().get(0));
		Constants.mapWidth = collsionLayer.getWidth();
		Constants.mapTiledWidth = collsionLayer.getTileWidth();				
		Gdx.input.setInputProcessor(this);
		initLevel();
	}

	private void initLevel() {	
		player = new Player(collsionLayer);
		//enemy = new Enemies(collsionLayer,player);
		//enemy.folowObject(player);
		playerBullets = new Array<Bullet>();
		enemiesBullets = new Array<Bullet>();
		enemies = new Array<Enemies>();
		stage = 1;
		enemiesTimer = 0;
		initEnemies();
	}
	
	private void initEnemies() {
		enemies.add(new Enemies(collsionLayer, player));
		//for(int i = 0 ; i < Constants.numberOfEnemies[stage]; i++){
			//enemies.add(new Enemies(collsionLayer, player));
		//}
		
	}

	public void update (float deltaTime){
		//update enemies
		updateEnemies(deltaTime);
	//	enemy.update(deltaTime);
	//	updateEnemiesFiring(deltaTime);
		updateEnemiesBullet(deltaTime);
		updateNumberOfEnemies(deltaTime);
		
		//update player
		player.update(deltaTime);
		updatePlayerBullet(deltaTime);
		updatePlayerFiring(deltaTime);		
		
		
	}
	
		
	
	
	private void updateNumberOfEnemies(float deltaTime) {
		enemiesTimer +=deltaTime;
		//if(enemiesTimer > Constants.numberOfEnemies[stage]*2){
		if(enemiesTimer > 4){
			enemiesTimer = 0;
			if(enemies.size < 6)
			initEnemies();
		};
		
		for(int i = 0; i< enemies.size; i++){
			Enemies enemy = enemies.get(i);
			if(enemy.getLife().isDead()){
				enemies.removeIndex(i);
				}
		}
		
		
	}

	private void updateEnemies(float deltaTime) {
		for(Enemies enemy: enemies){
			enemy.update(deltaTime);
			updateEnemiesFiring(deltaTime, enemy);
		}
		
	}

	private void updatePlayerFiring(float deltaTime){
		if(player.getGun().haveToFire(deltaTime)){
			playerBullets.add(player.getGun().getBullet());
		}
	}
	
	private void updateEnemiesFiring(float deltaTime, Enemies enemy){
		if(enemy.getGun().haveToFire(deltaTime)){
			enemiesBullets.add(enemy.getGun().getBullet());
		}
	}
	

	private void updateEnemiesBullet(float deltaTime){
		for (int i=0 ; i < enemiesBullets.size; i++){			
			Bullet bullet = enemiesBullets.get(i);
			if(bullet.isCollision(player)){
				player.hit(bullet);
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
			boolean deleted = false;
			updateEnemiesBulletAI(bullet);
			for(Enemies enemy: enemies){
				if(bullet.isCollision(enemy)){
					enemy.hit(bullet);
					playerBullets.removeIndex(i);
					deleted = true;
					break;
				}
			}
			if (bullet.isCollision()
					|| bullet.isCollision(collsionLayer)
					&& !deleted)
			{
				playerBullets.removeIndex(i);
				
			}else{
				bullet.update(deltaTime);
			}
		}
	}
	
	private void updateEnemiesBulletAI(Bullet bullet) {
		for(Enemies enemy: enemies){
			enemy.bulletAI(bullet);
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
