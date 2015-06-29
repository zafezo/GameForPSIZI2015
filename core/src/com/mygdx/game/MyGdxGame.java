package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.util.AssetsStore;

public class MyGdxGame extends Game {
	
	
	@Override
	public void create () {
		AssetsStore.instance.init();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
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
	public void dispose() {
		super.dispose();
		
	}
}
