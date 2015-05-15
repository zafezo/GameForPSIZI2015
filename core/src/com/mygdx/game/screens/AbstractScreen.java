package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.util.AssetsStore;

public abstract class AbstractScreen implements Screen {
	
	protected Game game;

	public AbstractScreen(Game game) {
		super();
		this.game = game;
	}


	@Override
	public void resume() {
		
		AssetsStore.instance.init();
		
	}

	

	@Override
	public void dispose() {
		AssetsStore.instance.dispose();
		
	}
			
}
