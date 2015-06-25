package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.util.AssetsStore;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.GamePreferences;

public class ShopScreen extends AbstractScreen {

	public ShopScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	private Stage stage;
	private Skin skin;
	private TextButton playButton;
	private TextButton backButton;
	private Window winOptions;
	private Skin skinLibgdx;
	private int id;
	private boolean enable = true;
	private Stack optionStack;


	@Override
	public void show() {
		   stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,
					Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	
		addOptionStack();
		rebuildStage();
		
	}

	private void addOptionStack() {
		optionStack = new Stack();	
		optionStack.setSize((float)(Constants.VIEWPORT_GUI_WIDTH/1.8),
				(float)(Constants.VIEWPORT_GUI_HEIGHT/1.8));
		optionStack.setPosition((float)(Constants.VIEWPORT_GUI_WIDTH/4),
				(float)(Constants.VIEWPORT_GUI_HEIGHT/4));
	}

	private void rebuildStage() {
		// assemble stage for menu screen
				stage.clear();
				Stack stack = new Stack();
				stage.addActor(stack);
				stage.addActor(optionStack);				
				stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
				stack.add(createUI());	
				stage.addActor(buildOptionsWindowLayer());
		
	}

	private void rebuildOptionsWindow() {
		//stage.addActor(buildOptionsWindowLayer());
		optionStack.clear();
		optionStack.add(buildOptionsWindowLayer());
		System.out.println("New Window");
		
	}

	private Actor buildOptionsWindowLayer() {
		
		skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

		winOptions = new Window("Upgate?", skinLibgdx);
		winOptions.setVisible(false);
		// + label to window
		winOptions.add(addLabelShop()).row();
		// + Separator and Buttons (Save, Cancel)
		winOptions.add(addShopButtons()).pad(10, 0, 10, 0);

		// Make options window slightly transparent
		winOptions.setColor(1, 1, 1, 0.8f);
		winOptions.pack();
		// Move options window to bottom right corner
		winOptions.setPosition(
				(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth())/2, 
				(Constants.VIEWPORT_GUI_HEIGHT - winOptions.getHeight())/2);
		return winOptions;
	}

	private String type(int number) {
		String text = null;
		switch (number) {
		case 0:
			text = "Standart Gun";
			break;
		case 1:
			text = "Freeze Gun";
			break;
		case 2:
			text = "Life Point";
			break;
		default:
			break;
		}
		return text;
	}

	private Actor addShopButtons() {
		Table table=new Table();
		TextButton upgrateButton=new TextButton("UPGRADE", skin);
		if(!enable){
			upgrateButton.setTouchable(Touchable.disabled);
		}
		upgrateButton.pad(15);
		upgrateButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onUpgradeClicked();
			}	
		});	
		table.add(upgrateButton).left().space(40);
		TextButton CANCLEButton=new TextButton("CANCEL", skin);		
		CANCLEButton.pad(15);
		CANCLEButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onCancleClicked();
			}

			private void onCancleClicked() {
				winOptions.setVisible(false);
				winOptions.setModal(false);
			}
		});	
		table.add(CANCLEButton).space(40);		
		return table;
	}

	private void onUpgradeClicked() {
		priceTransaction();
		levelTransaction();
		winOptions.setVisible(false);	
		rebuildStage();
	}

	private void levelTransaction() {
		switch (id) {
		case 0:
			GamePreferences.instance.saveStandartGunLevel(
					GamePreferences.instance.getStandartGunLevel()+1);
			break;
		case 1:
			GamePreferences.instance.saveFreezeGunLevel(
					GamePreferences.instance.getFreezeGunLevel()+1);
			break;
		case 2:
			GamePreferences.instance.saveLifeLevel(
					GamePreferences.instance.getLifeLevel()+1);
			break;


		default:
			break;
		}
		
	}

	private void priceTransaction() {
		if(enable){
			int price = GamePreferences.instance.getScore()-
					(curentLevel(id)+3)*25 ;
			GamePreferences.instance.saveScore(price);	
		}
		
	}

	private Actor addLabelShop() {
		String text = type(id);
		System.out.println(text + " " + id);
		Table table = new Table();
		BitmapFont font = new BitmapFont(Gdx.files.internal(Constants.Screen_FONT));
		Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
		Label temp = new Label("Upgrate your "+ text, style);
		table.add(temp).pad(0, 10, 20, 10);
		table.row();
		temp = new Label("  You have: "+ GamePreferences.instance.getScore(), style);
		table.add(temp).pad(0, 10, 20, 10);
		table.row();
		int price = (curentLevel(id)+3)*25 ;
		enable = (GamePreferences.instance.getScore() >= price);
		temp = new Label("  You need:  "+ price, style);
		table.add(temp).pad(0, 10, 20, 10);
		table.row();
		return table;
	}

	private int curentLevel(int id2) {
		int curentLevel = 0;
		switch (id2) {
		case 0:
			curentLevel = GamePreferences.instance.getStandartGunLevel();
			break;
		case 1:
			curentLevel = GamePreferences.instance.getFreezeGunLevel();
			break;
		case 2:
			curentLevel = GamePreferences.instance.getLifeLevel();
			break;
		default:
			break;
		}
		return curentLevel;
	}

	private Actor createUI() {
		Table table=new Table();	
		
		table.row();
		table.add(addCoins()).left();
		table.row();
		table.add(addName());
		table.row();
		table.add(addImage()).pad(10);
		table.row();
		table.add(addLevelPosition());
		table.row();
		table.add(addPrice());
		table.row();
		table.add(addButtons()).pad(12);
		//table.debugAll();
		return table;
	}

	private Actor addCoins() {
		Table table =  new Table();
		BitmapFont font = new BitmapFont(Gdx.files.internal(Constants.Screen_FONT));
		Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
		Label temp = new Label("   "+ GamePreferences.instance.getScore(), style);
		table.add(temp);
		return table;
	}

	private Actor addButtons() {
		Table table=new Table();
		backButton=new TextButton("BACK", skin);
		backButton.pad(15);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onBackClicked();
			}

			private void onBackClicked() {
				game.setScreen(new MapSelectedScreen(game));
				
			}		
		});	
		table.add(backButton).left().space(40);
		playButton=new TextButton("PLAY", skin);
		playButton.pad(15);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onPlayClicked();
			}

			private void onPlayClicked() {
				game.setScreen(new PlayScreen(game));
				
			}		
		});	
		table.add(playButton).space(40);		
		return table;
	}
	

	private Actor addPrice() {
		Table table = new Table();
		BitmapFont font = new BitmapFont(Gdx.files.internal(Constants.Screen_FONT));
		Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
		int price = (GamePreferences.instance.getStandartGunLevel()+3)*25 ;
		Label temp = new Label(price + "   ", style);
		table.add(temp).spaceLeft(200);
		price = (GamePreferences.instance.getFreezeGunLevel()+3)*25;
		temp = new Label(price + " ", style);
		table.add(temp).spaceLeft(200);
		price = (GamePreferences.instance.getLifeLevel()+3) * 25;
		temp = new Label(price+"", style);
		table.add(temp).spaceLeft(200);
		return table;
	}

	private Actor addLevelPosition() {
		Table table = new Table();
		BitmapFont font = new BitmapFont(Gdx.files.internal(Constants.Screen_FONT));
		Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
		
		Label temp = new Label(createLabelLevel(0), style);
		table.add(temp).padRight(10);
		temp = new Label(createLabelLevel(1), style);
		table.add(temp).padLeft(25);
		temp = new Label(createLabelLevel(2), style);
		table.add(temp).padLeft(25);
		return table;
	}
	
	private String createLabelLevel(int i){
		String textInLabel = "Max Level";
		switch (i) {
		case 0:
			if(GamePreferences.instance.getStandartGunLevel()< Constants.maxLevel){
				textInLabel = "Level " 
						+ GamePreferences.instance.getStandartGunLevel()
							+ " / " + Constants.maxLevel;
			}
			break;
		case 1:
			if(GamePreferences.instance.getFreezeGunLevel()< Constants.maxLevel){
				textInLabel = "Level " 
						+ GamePreferences.instance.getFreezeGunLevel()
							+ " / " + Constants.maxLevel;
			}
			break;
		case 2:
			if(GamePreferences.instance.getLifeLevel()< Constants.maxLevel){
				textInLabel = "Level " 
						+ GamePreferences.instance.getLifeLevel()
							+ " / " + Constants.maxLevel;
			}
			break;

		default:
			break;
		}
		return textInLabel;
	}

	private Actor addName() {
		Table table = new Table();
		BitmapFont font = new BitmapFont(Gdx.files.internal(Constants.Screen_FONT));
		Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
		Label temp = new Label("Standart Gun", style);
		table.add(temp).padLeft(20).uniform();
		temp = new Label("Freeze Gun", style);
		table.add(temp).padLeft(20).uniform();
		temp = new Label("Life Point", style);
		table.add(temp).padLeft(20).uniform();
		return table;
	}


	private Actor addImage() {
		Table table = new Table();
		int size = 200;
		// add Image of Life
		Image standartGunIage = AssetsStore.instance.gui.standartGunImage; 
		standartGunIage.addListener(new ClickListener() {
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
		    {
		        onImageClicked(0);
		        return true;
		    }
		});
		
		table.add(standartGunIage).pad(10).prefSize(size);
		
		// add Image of Standart Gun
		Image freezeGunIage = AssetsStore.instance.gui.freezeGunImage;
		 freezeGunIage.addListener(new ClickListener() {
			    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			    {
			        onImageClicked(1);
			        return true;
			    }
			});
		 table.add(freezeGunIage).pad(10).prefSize(size);
		 
		 //add image of Freeze Gun
		 Image  lifePointImage = AssetsStore.instance.gui.lifePointImage;

			 lifePointImage.addListener(new ClickListener() {
				    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				    {
				        onImageClicked(2);
				        return true;
				    }
				});
			 table.add(lifePointImage).pad(10).prefSize(size);
		return table;
	}

	

	private void onImageClicked(int i) {
		id = i;
		//rebuildStage();
		rebuildOptionsWindow();
		//loadText();
		System.out.println("On Image Click");
		winOptions.setVisible(true);	
		winOptions.setModal(true);
	}

	private void loadText() {
		// TODO Auto-generated method stub
		
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
