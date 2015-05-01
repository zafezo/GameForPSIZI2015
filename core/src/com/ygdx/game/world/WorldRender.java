package com.ygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.enteties.Enemies;
import com.mygdx.game.enteties.Player;
import com.mygdx.game.enteties.guns.Bullet;
import com.mygdx.game.util.CameraHelper;
import com.mygdx.game.util.Constants;

public class WorldRender implements Disposable{
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	public CameraHelper cameraHelper;
	private Batch batch;
	
	private Player player;
	private Enemies enemie;
	
	
	private WorldController worlController;
	
	public WorldRender (WorldController worlController) {
		this.worlController = worlController;
		init();
	}

	private void init() {
		
		camera = new OrthographicCamera();
		//cameraHelper = new CameraHelper();
		//cameraHelper.applyTo(camera);
		
		renderer = new OrthogonalTiledMapRenderer(worlController.map);
		batch = renderer.getBatch();
		player = worlController.player;
		enemie = worlController.enemy;
		//cameraHelper.setTarget(player);
	}

	public void render (){
		renderWorld();
		
	}
	
	private void renderWorld() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateGameCamera();		
		
		renderer.setView(camera);
	
		
		batch.begin();
		// render background
			renderer.renderTileLayer((TiledMapTileLayer)worlController.map.getLayers()
					.get("background"));
			
			//draw Player
			player.draw(batch);
			enemie.draw(batch);
			drawEnemiesBullet(batch);
			//draw Player's bullets
			drawPlayerBullet(batch);
			
			
			
			
		//render foreground
			//renderer.renderTileLayer((TiledMapTileLayer)worlController.map.getLayers()
				//	.get("foreground"));
		batch.end();
	
	}
	
	private void drawPlayerBullet(Batch batch){
		for (int i=0 ; i < worlController.playerBullets.size; i++){
			worlController.playerBullets.get(i)
					.render(batch);
		}		
	}
	
	private void drawEnemiesBullet(Batch batch){
		for (int i=0 ; i < worlController.enemiesBullets.size; i++){
			worlController.enemiesBullets.get(i)
					.render(batch);
		}		
	}


	private void updateGameCamera() {
		float x = player.getX() + player.getWidth() / 2;
		float y = player.getY() + player.getHeight() / 2;
		
		// player.posithion < half of map
		//left position
		if (x < camera.viewportWidth /2) x = camera.viewportWidth /2;
		if (y < camera.viewportHeight /2) y = camera.viewportHeight /2;
		
		//right position		
		if (x > (player.getCollisonLayer().getWidth()*player.getCollisonLayer().getTileWidth() - camera.viewportWidth /2))  
			x = player.getCollisonLayer().getWidth()*player.getCollisonLayer().getTileWidth() - camera.viewportWidth /2;
		if (y > (player.getCollisonLayer().getHeight()*player.getCollisonLayer().getTileHeight() - camera.viewportHeight /2))  
			y = player.getCollisonLayer().getHeight()*player.getCollisonLayer().getTileHeight() - camera.viewportHeight /2;	
		camera.position.set(x,y,0);
		camera.update();
		//Gdx.app.debug("layer", (new Float (player.getCollisonLayer().getWidth()*player.getCollisonLayer().getTileWidth()).toString()));	
		//Gdx.app.debug("Camera position", camera.position.toString());		
	}

	@Override
	public void dispose() {
		worlController.map.dispose();
		renderer.dispose();
		
	}

	public void resize(int width, int height) {
		camera.viewportWidth = width / Constants.Scale_world;
		camera.viewportHeight = height / Constants.Scale_world;		
	}
	
}
