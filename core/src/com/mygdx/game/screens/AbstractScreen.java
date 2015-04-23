package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	
	protected Game game;

	public AbstractScreen(Game game) {
		super();
		this.game = game;
	}
			
}
