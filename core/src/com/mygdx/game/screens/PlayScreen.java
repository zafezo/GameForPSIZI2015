package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
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
		//GamePreferences.instance.load();
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
