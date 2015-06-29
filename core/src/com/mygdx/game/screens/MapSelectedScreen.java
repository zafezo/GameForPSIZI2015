package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.util.AssetsStore;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;

public class MapSelectedScreen extends AbstractScreen {

	private Stage stage;
	private Skin skin;
	//private Skin levelsSkin;
	private Array<Level> levels;
	private TextButton buttonGo;
	private TextButton buttonBack;
	private TextButton buttonLeft;
	private TextButton buttonRight;
	public static int currentLevelIndex = 0;
	public float imageWidth = 500;
	public float imageHeight = 300;
	public MapSelectedScreen(Game game) {
		super(game);
	}

	@Override
	public void show() {
		   stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,
					Constants.VIEWPORT_GUI_HEIGHT));;
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	//	levelsSkin = new Skin(Gdx.files.internal("img/customUI.json"));		
		addLevels();
		rebuildStage();
		
	}

	private void addLevels() {
				
		// Populate level container
		levels = new Array<Level>();
		for (int i = 0; i <4; i++){
			Texture texture = new Texture(Gdx.files.internal("maps/preview-"+i+".png"));
			Level level = new Level("Map "+ (i+1), skin);
			level.setImage( new Image(new TextureRegionDrawable(new TextureRegion(texture))));
			levels.add(level);
		}
		
	}

	private void rebuildStage() {
		// assemble stage for menu screen
				stage.clear();
				stage.addActor(background());
				stage.addActor(createLayer());
	}

	

	private Actor background() {
		Image temp = AssetsStore.instance.gui.back_screen;
		return temp ;
	}

	private Actor createLayer() {
		Table table = new Table();
		// Create table				
				//Level selection menu
				Label level_menu = new Label("Map Selection Menu", skin);			
				
				table.row();
				table.add(level_menu);
				table.row();
				table.add(addLabel());	
				table.row();
				table.add(addImage());				
				table.row();
				table.add(addButtons()).padTop(15f);	
				table.row();
				table.pad(20f);
				table.pack();
				
				table.setFillParent(true);
				//table.pack();				
		//table.debugAll();
		return table;
	}

	private Actor addLabel() {
		Table table = new Table();
		Level currentLevel = levels.get(currentLevelIndex);
		table.add(currentLevel.getTitle()).pad(15f);
		Label bestTime = new Label("Best Time: "+GamePreferences.instance.getTime(), skin);
		table.add(bestTime).pad(15f);
		return table;
	}

	private Actor addImage() {
		Table table = new Table();
		this.buttonLeft = new TextButton("<", skin);
		this.buttonRight = new TextButton(">", skin);
		Level currentLevel = levels.get(currentLevelIndex);
		table.add(buttonLeft.pad(15f)).padRight(15f);
		table.add(currentLevel.getImage()).size(imageWidth, imageHeight);
		table.add(buttonRight.pad(15f)).padLeft(15f);
		buttonLeft.addListener(new ClickListener() {
			public void clicked (InputEvent event, float x, float y) {
				showPreviousLevel();
			}
		});
		buttonRight.addListener(new ClickListener() {
			public void clicked (InputEvent event, float x, float y) {
				showNextLevel();
			}
		});
		return table;
	}

	private void showPreviousLevel() {
		if(currentLevelIndex > 0) {
			currentLevelIndex--;
			rebuildStage();
		}
	}

	private void showNextLevel() {
		if(currentLevelIndex+1 < levels.size) {
			currentLevelIndex++;
			rebuildStage();
		}
	}
	
	
	private Actor addButtons() {
		Table table = new Table();
		this.buttonGo = new TextButton("Next", skin);
		this.buttonBack =  new TextButton("Back",  skin);
		table.add(buttonBack.pad(15)).space(40);
		table.add(buttonGo.pad(15)).space(40);	
		// Start game button listener
		buttonGo.addListener( new ClickListener() {             
					@Override
					public void clicked(InputEvent event, float x, float y) {
						game.setScreen(new ShopScreen(game));
					};
				});
		// Back game button listener
		buttonBack.addListener( new ClickListener() {             
					@Override
					public void clicked(InputEvent event, float x, float y) {
						game.setScreen(new MenuScreen(game));
					};
				});
		return table;
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
		//levelsSkin.dispose();
	}
	
	public static class Level {
		private Label title;
		private Image image;

		public Level(CharSequence level_name, Skin skin) {
			title = new Label(level_name, skin);
		}

		public Level(CharSequence level_name, LabelStyle labelStyle) {
			title = new Label(level_name, labelStyle);
		}

		public Level(CharSequence level_name, Image img, Skin skin) {
			title = new Label(level_name, skin);
			image = img;
		}

		public Level(CharSequence level_name, Image img, LabelStyle labelStyle) {
			title = new Label(level_name, labelStyle);
			image = img;
		}

		public Label getTitle() {
			return title;
		}

		public void setTitle(Label title) {
			this.title = title;
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image img) {
			this.image = img;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((title.getText() == null) ? 0 : title.getText().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Level other = (Level) obj;
			if (title == null) {
				if (other.title.getText() != null)
					return false;
			} else if (!title.getText().equals(other.title.getText()))
				return false;
			return true;
		}

	}
}
