package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.util.Constants;

public class BetaMenuScreen extends AbstractScreen{
	private Stage stage;
	private Skin skin;
	private Image imgBackground;
	private Button btnMenuPlay;
	private Button btnMenuCancle;

	public BetaMenuScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void show() {
		    stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,
					Constants.VIEWPORT_GUI_HEIGHT));
		    Gdx.input.setInputProcessor(stage);
		    rebuildStage();
	}

	private void rebuildStage() {
		skin = new Skin(
				Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		// build all layers
		Table layerBackground = buildBackgroundLayer();
		Table layerControls = buildControlsLayer();
		
		// assemble stage for menu screen
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
		Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerControls);
	}


	private Table buildControlsLayer() {
		Table layer = new Table();
		layer.right().bottom();
		// + Play Button
		btnMenuPlay = new Button(skin, "play");
		layer.add(btnMenuPlay);
		btnMenuPlay.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onPlayClicked();
			}		
		});	
		layer.row();
		// + Options Button
		btnMenuCancle = new Button(skin, "options");
		layer.add(btnMenuCancle);
		btnMenuCancle.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onOptionsClicked();
			}

		});
		
		return layer;
	}

	private void onPlayClicked() {
		
		game.setScreen(new PlayScreen(game));
	
	}

	private void onOptionsClicked() {
		Gdx.app.exit();
		
	}

	private Table buildBackgroundLayer() {
		Table layer = new Table();
		// + Background
		imgBackground = new Image(skin, "background");
		layer.add(imgBackground);
		return layer;
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();	
	}
		
	

	@Override
	public void resize(int width, int height) {
		 stage.getViewport().update(width, height, false);
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
		stage.dispose();
		skin.dispose();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
