package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.enteties.Player;
import com.ygdx.game.world.WorldController;
import com.ygdx.game.world.WorldRender;

public class BadPlayScreen extends AbstractScreen {

	

	private WorldController worldController;
	private WorldRender worldRender;
	
	boolean paused = false;

	public BadPlayScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void show() {	
		worldController = new WorldController(game);
		worldRender = new WorldRender(worldController);
	}

	@Override
	public void render(float delta) {
		
		
				// Do not update game world when paused.
				if (!paused) {
					// Update game world by the time that has passed
					// since last rendered frame.
					worldController.update(delta);
				}
				// Sets the clear screen color to: Cornflower Blue
				Gdx.gl.glClearColor(0,0,0,1);
				// Clears the screen
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				// Render game world to screen
				worldRender.render();
		
	}

	@Override
	public void resize(int width, int height) {
		worldRender.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;

	}

	@Override
	public void resume() {
		paused = true;

	}

	@Override
	public void hide() {
		worldRender.dispose();

	}

	@Override
	public void dispose() {
		worldRender.dispose();
	}
	
}
