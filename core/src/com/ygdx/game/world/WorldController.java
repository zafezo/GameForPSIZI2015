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
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.enteties.Bullet;
import com.mygdx.game.enteties.Enemies;
import com.mygdx.game.enteties.Bullet.TypeOfBullet;
import com.mygdx.game.enteties.Player;
import com.mygdx.game.util.Constants;

public class WorldController extends InputAdapter implements InputProcessor{

	private static final String TAG = WorldController.class.getName();
	
	public  TiledMap map;	
	public Player player;
	private Game game;
	public Enemies enemy;
	private TiledMapTileLayer collsionLayer;
	
	public Array<Bullet> bullets;
	

	
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
		Gdx.input.setInputProcessor(this);
		initLevel();
	}

	private void initLevel() {		
		bullets = new Array<Bullet>();
	}
	
	public void update (float deltaTime){
		for (int i=0 ; i < bullets.size; i++){
			Bullet bullet = bullets.get(i);
			if (bullet.isCollision()
					|| bullet.isCollision(enemy)
					|| bullet.isCollision(collsionLayer))
			{
				bullets.removeIndex(i);
				
			}else{
				bullet.update(deltaTime);
			}
		}
		player.update(deltaTime);
		enemy.update(deltaTime);
		
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
				bullets.add(player.fire(TypeOfBullet.Standrat));
				break;
			case Keys.V:
				enemy.moveTo(10);
				break;
			case Keys.C:
				enemy.moveTo(6);
				break;
			case Keys.X:
				enemy.jumpTo(7, 4);
				break;
			case Keys.B:
				enemy.jumpTo(10, 5);
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
	}
		return true;
	}
}
