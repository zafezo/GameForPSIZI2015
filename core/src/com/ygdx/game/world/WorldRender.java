package com.ygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.enteties.Enemies;
import com.mygdx.game.enteties.Player;
import com.mygdx.game.util.AssetsStore;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;

public class WorldRender implements Disposable{
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Batch batch;
	
	private Player player;
	public Array<Enemies> enemies;
	private int size = 11;
	
	
	private WorldController worlController;
	
	public WorldRender (WorldController worlController) {
		this.worlController = worlController;
		init();
	}

	private void init() {
		
		camera = new OrthographicCamera();
		renderer = new OrthogonalTiledMapRenderer(worlController.map);
		batch = renderer.getBatch();
		player = worlController.player;
		enemies = worlController.enemies;		
	}

	public void render (){
		renderWorld(); 
		renderGUI();
		
	}
	
	private void renderGUI() {
		Vector3 position = new Vector3(camera.position);
		position.set(position.x - camera.viewportWidth/2,  
				position.y+ camera.viewportHeight/2 
				, 0);
		renderer.setView(camera);
		batch.begin();
		renderGuiScore(position);
		renderLifePoint(position);
		renderTime(position);
		batch.end();
		
	}

	private void renderTime(Vector3 position) {
		float x = position.x+5;
		float y = position.y-30;
		batch.draw(AssetsStore.instance.eneties.icon_time, x-2, y-8, 2, 0, size, size, 1, 1, 1);
		AssetsStore.instance.fonts.gameFont.draw(batch,
				"" + worlController.getTime()+" / "+ worlController.maxTime
				, x+10, y);
		
		
		
	}

	private void renderLifePoint(Vector3 position) {
		float x = position.x+5;
		float y = position.y-15;
		int startLife = 100;
		float begin = 100 + 40*GamePreferences.instance.getLifeLevel();
		Pixmap pixmap = new Pixmap(startLife+4,10, Format.RGBA8888);
		pixmap.setColor(Color.GRAY);
		pixmap.drawRectangle(0, 0, startLife+2, 8);
		pixmap.setColor(Color.RED);
		pixmap.fillRectangle(1, 1, 
				(int) ((player.getLife().getLifePoint())/begin*startLife), 
				6);
		Sprite xpLine = new Sprite(new Texture(pixmap));
		batch.draw(AssetsStore.instance.eneties.icon_hp, x-1, y+2, 2, 0, size, size, 1, 1, 1);
		xpLine.setPosition(x+12, y);
		xpLine.draw(batch);
		
	}

	private void renderGuiScore(Vector3 position) {
		float x = position.x+5;
		float y = position.y-20;
		batch.draw(AssetsStore.instance.eneties.icon_money, x-1, y-6, 4, 4, size, size, 1, 1, 1);
		AssetsStore.instance.fonts.gameFont.draw(batch,"" + worlController.player.getScore(), x+12, y+1);
		
		
	}

	private void renderWorld() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateGameCamera();		
		
		renderer.setView(camera);
	
		
		batch.begin();
		// render background
			renderer.renderImageLayer((TiledMapImageLayer) worlController.map.getLayers().get(0));
			renderer.renderTileLayer((TiledMapTileLayer)worlController.map.getLayers()
					.get("background"));
			
			//draw Enemies and Enemies's bullets
			//enemie.draw(batch);
			drawEnemies(batch);
			drawEnemiesBullet(batch);
			//draw Player
			player.draw(batch);
			//draw Player's bullets
			drawPlayerBullet(batch);			
		batch.end();
	
	}
	
	
	private void drawEnemies(Batch batch) {		
		for(Enemies enemy: enemies){
			enemy.draw(batch);
		}
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
