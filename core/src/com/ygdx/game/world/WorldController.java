package com.ygdx.game.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.enteties.Enemies;
import com.mygdx.game.enteties.Player;
import com.mygdx.game.enteties.guns.Bullet;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;
import com.mygdx.game.screens.ShopScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;

public class WorldController extends InputAdapter implements InputProcessor{
	
	public  TiledMap map;	
	public Player player;
	private Game game;
	private TiledMapTileLayer collsionLayer;	
	private float enemiesTimer;	
	public Array<Bullet> playerBullets;
	public Array<Bullet> enemiesBullets;
	public Array<Enemies> enemies;
	private float curentTime;
	public int maxTime;
	

	
	public WorldController (Game game){
		this.game = game;
		init();
	}

	private void init() {		
		map = Level.instance.getLevelMap();	
		collsionLayer = (TiledMapTileLayer) (map.getLayers().get(1));
		Constants.mapWidth = collsionLayer.getWidth();
		Constants.mapTiledWidth = collsionLayer.getTileWidth();				
		Gdx.input.setInputProcessor(this);
		initLevel();
		
	}

	private void initLevel() {	
		player = new Player(collsionLayer);
		player.setScore(GamePreferences.instance.getScore());
		playerBullets = new Array<Bullet>();
		enemiesBullets = new Array<Bullet>();
		enemies = new Array<Enemies>();
		enemiesTimer = 0;
		curentTime = 0;
		maxTime = GamePreferences.instance.getTime();
	}
	
	private void initEnemies() {
		enemies.add(new Enemies(collsionLayer, player));		
	}

	public void update (float deltaTime){
		//update enemies
		updateEnemies(deltaTime);
		updateEnemiesBullet(deltaTime);
		updateNumberOfEnemies(deltaTime);
		
		//update player
		player.update(deltaTime);
		isGAmeOver();
		updatePlayerBullet(deltaTime);
		updatePlayerFiring(deltaTime);		
		
		curentTime += deltaTime;		
	}
	
		
	
	
	public int getTime() {
		if(curentTime > maxTime){
			maxTime = (int) curentTime;
		}
		return (int) curentTime;
	}

	private void isGAmeOver() {
		if(player.getLife().isDead()){
			backToMenu();
		}
		
	}

	private void updateNumberOfEnemies(float deltaTime) {
		enemiesTimer +=deltaTime;
		if(enemiesTimer > 4){
			enemiesTimer = 0;
			if(enemies.size < 3)
			initEnemies();
		};
		
		for(int i = 0; i< enemies.size; i++){
			Enemies enemy = enemies.get(i);
			if(enemy.getLife().isDead()){
				player.addScore(enemy.getPointScore());
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
				setGunAndFire(TypeOfBullet.Standrat);
				break;
			case Keys.X:
				setGunAndFire(TypeOfBullet.Freeze);
				break;
			case Keys.ESCAPE:
				backToMenu();
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
		case Keys.X:
			player.getGun().setFiring(false);
			break;	

		
	}
		return true;
	}
	
	private void setGunAndFire(TypeOfBullet gun){
		player.setGun(gun);
		player.getGun().setFiring(true);
	}
	
	private void backToMenu(){
		GamePreferences.instance.saveScore(player.getScore());
		GamePreferences.instance.saveTime(maxTime);		
		game.setScreen(new ShopScreen(game));
	}
}
