package com.mygdx.game;

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
import com.mygdx.game.enteties.Player;

public class BetaPlayScreen implements Screen {


	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private Player player;
	
	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/myMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);		
		camera = new OrthographicCamera();		
		//player = new Player(new Sprite(new Texture("img/player.png")),
			//	(TiledMapTileLayer) (map.getLayers().get(0)));
		player.setPosition(1 * player.getCollisonLayer().getTileWidth(),
				6* player.getCollisonLayer().getTileHeight());

		//Gdx.input.setInputProcessor(player);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
		
		renderer.setView(camera);
	
		
		renderer.getBatch().begin();
			renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("background"));
			player.draw(renderer.getBatch());
			renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("foreground"));
		renderer.getBatch().end();

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 2f;
		camera.viewportHeight = height / 2f;
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
		map.dispose();
		renderer.dispose();
	}

}
