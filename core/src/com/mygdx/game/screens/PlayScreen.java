package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.enteties.Player;
import com.mygdx.game.util.AssetsStore;
import com.mygdx.game.util.GamePreferences;
import com.ygdx.game.world.Level;
import com.ygdx.game.world.WorldController;
import com.ygdx.game.world.WorldRender;

public class PlayScreen extends AbstractScreen  {


	public PlayScreen(Game game) {
		super(game);		
	}	
	
	private WorldController worldController;
	private WorldRender worldRenderer;
	
	@Override
	public void show() {	
		GamePreferences.instance.load();
		worldController = new WorldController(game);
		worldRenderer = new WorldRender(worldController);
		
	}

	@Override
	public void render(float delta) {
		worldController.update(delta);
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		worldRenderer.dispose();
	}

}
