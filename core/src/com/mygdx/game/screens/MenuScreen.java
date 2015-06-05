package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.util.Constants;

public class MenuScreen extends AbstractScreen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton playButton;
	private TextButton exitButton;
	
	public MenuScreen(Game game) {
		super(game);
	}

	@Override
	public void show() {
		   stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,
					Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		rebuildStage();
		
	}

	private void rebuildStage() {
		// assemble stage for menu screen
				stage.clear();
				Stack stack = new Stack();
				stage.addActor(stack);
				stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
				stack.add(createButtons());
				//stack.add(layerControls);

		
	}

	private Actor createButtons() {
		table=new Table();
		playButton=new TextButton("PLAY GAME", skin);
		playButton.pad(15);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onPlayClicked();
			}		
		});	
		table.center();
		table.add(playButton).space(5);
		table.row();
		exitButton=new TextButton("EXIT GAME", skin);
		exitButton.pad(15);
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onExitClicked();
			}		
		});	
		table.add(exitButton);
		table.row();
		return table;
	}

	protected void onExitClicked() {
		Gdx.app.exit();
		
	}

	protected void onPlayClicked() {
		game.setScreen(new MapSelectedScreen(game));
		
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
	public void hide() {
		stage.dispose();
		skin.dispose();
		
	}

}
