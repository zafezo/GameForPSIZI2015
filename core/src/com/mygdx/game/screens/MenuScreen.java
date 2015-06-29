package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.util.AssetsStore;
import com.mygdx.game.util.AudioManager;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;

public class MenuScreen extends AbstractScreen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private Window winOptions;
	private Window winHelp;
	private CheckBox chkSound;
	private Slider sldSound;
	private CheckBox chkMusic;
	private Slider sldMusic;
	private boolean enable = true;
	
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
				stage.addActor(background());
				stage.addActor(stack);
				stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
				stack.add(createButtons());
				stage.addActor(layerOptionsWindow());
				stage.addActor(layerHelpWindow());

		
	}

	private Actor layerHelpWindow() {
		winHelp = new Window("Help", skin);
		// + Audio Settings: Sound/Music CheckBox and Volume Slider
		winHelp.add(buildHelpLabel()).row();
		// + Separator and Buttons ( Cancel)
		winHelp.add(buildHelpWinButton()).pad(40f, 0, 20f, 40f);

		// Make options window slightly transparent
		winHelp.setColor(1, 1, 1, 0.8f);
		
		// Let TableLayout recalculate widget sizes and positions
		winHelp.pack();
		// Move options window to bottom right corner
		winHelp.setPosition(
				(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth())/2, 
				(Constants.VIEWPORT_GUI_HEIGHT - winOptions.getHeight())/2);
		winHelp.setVisible(false);
		return winHelp;
	}

	private Actor buildHelpWinButton() {
		Table table=new Table();
		TextButton button =new TextButton("  OK  ", skin);	
		button.scaleBy(3f);
		//button.pad(15f);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onOKClicked();
			}

			private void onOKClicked() {
				winHelp.setVisible(false);
				winHelp.setModal(false);
				
			}
			
		});	
		table.center();
		table.addActor(button);
		return table;
	}

	private Actor buildHelpLabel() {
		Table table = new Table();
		BitmapFont 	font = AssetsStore.instance.fonts.menuFont;
		Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
		Label temp = new Label("WASD - for moving", style);
		table.add(temp).center();
		table.row();
		temp = new Label("Z - Standart Gun", style);
		table.add(temp).center();
		table.row();
		temp = new Label("X - Frozen Gun", style);
		table.add(temp).center();
		table.row();	
		temp = new Label("ESC - Exit from game", style);
		table.add(temp).center();
		table.row();		
		return table;
	}

	private Actor layerOptionsWindow() {
			winOptions = new Window("Options", skin);
			// + Audio Settings: Sound/Music CheckBox and Volume Slider
			winOptions.add(buildOptWinAudioSettings()).row();
			// + Separator and Buttons (Save, Cancel)
			winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);

			// Make options window slightly transparent
			winOptions.setColor(1, 1, 1, 0.8f);
			
			// Let TableLayout recalculate widget sizes and positions
			winOptions.pack();
			// Move options window to bottom right corner
			winOptions.setPosition(
					(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth())/2, 
					(Constants.VIEWPORT_GUI_HEIGHT - winOptions.getHeight())/2);
			winOptions.setVisible(false);
			return winOptions;
	}
	
	private Table buildOptWinAudioSettings() {
		Table tbl = new Table();
		// + Title: "Audio"
		tbl.pad(10, 10, 0, 10);
		tbl.add(new Label("Audio", skin, "default-font", Color.ORANGE))
				.colspan(3);
		tbl.row();
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Sound" label, sound volume slider
		chkSound = new CheckBox("", skin);
		tbl.add(chkSound);
		tbl.add(new Label("Sound", skin));
		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skin);
		tbl.add(sldSound);
		tbl.row();
		// + Checkbox, "Music" label, music volume slider
		chkMusic = new CheckBox("", skin);
		tbl.add(chkMusic);
		tbl.add(new Label("Music", skin));
		sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skin);
		tbl.add(sldMusic);
		tbl.row();

		return tbl;
	}
	private Actor buildOptWinButtons() {
		Table table=new Table();
		TextButton upgrateButton=new TextButton("Save", skin);
		if(!enable){
			upgrateButton.setTouchable(Touchable.disabled);
		}
		upgrateButton.pad(15);
		upgrateButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSaveClicked();
			}

			private void onSaveClicked() {
				saveSettings();
				onCancelClicked();
			}
			private void saveSettings() {
				GamePreferences prefs = GamePreferences.instance;
				prefs.sound = chkSound.isChecked();
				prefs.volSound = sldSound.getValue();
				prefs.music = chkMusic.isChecked();
				prefs.volMusic = sldMusic.getValue();
				prefs.saveMusic();
			}	
		});	
		table.add(upgrateButton).left().space(40);
		TextButton CANCLEButton=new TextButton("Cancel", skin);		
		CANCLEButton.pad(15);
		CANCLEButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onCancelClicked();
			}

		});	
		table.add(CANCLEButton).space(40);		
		return table;
	}

	
	private void onCancelClicked() {
		winOptions.setVisible(false);
		winOptions.setModal(false);
		AudioManager.instance.onSettingsUpdated();
	}
	
	private Actor background() {
		Image temp = AssetsStore.instance.gui.back_Menu;
		return temp ;
	}

	private Actor createButtons() {
		table=new Table();
		TextButton	button=new TextButton("PLAY GAME", skin);
		button.pad(15f);
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onPlayClicked();
			}		
		});	
		table.center();
		table.add(button).space(5).padTop(95f);
		table.row();
		button=new TextButton("Settings", skin);
		button.pad(15f);
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onSettingClicked();
			}

		});	
		table.add(button).space(5);
		table.row();
		button=new TextButton("  Help  ", skin);
		button.pad(15f);
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onHelpClicked();
			}

			private void onHelpClicked() {
				winHelp.setVisible(true);	
				winHelp.setModal(true);
				
			}		
		});	
		table.add(button).space(5);
		table.row();
		button=new TextButton("EXIT GAME", skin);
		button.pad(15f);
		button.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onExitClicked();
			}		
		});	
		table.add(button).space(5);
		table.row();
		return table;
	}
	
	private void loadSettings() {
		GamePreferences prefs = GamePreferences.instance;
		prefs.loadMusic();
		chkSound.setChecked(prefs.sound);
		sldSound.setValue(prefs.volSound);
		chkMusic.setChecked(prefs.music);
		sldMusic.setValue(prefs.volMusic);
	}
	
	private void onSettingClicked() {
		loadSettings();
		winOptions.setVisible(true);	
		winOptions.setModal(true);
		
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
