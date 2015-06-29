package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class AssetsStore implements Disposable{
	
	public static AssetsStore instance = new AssetsStore();
	
	private AssetManager manager;
	public AssetFonts fonts;
	public GUI gui;
	public AssetEneties eneties;
	public AssetSounds sounds;
	public AssetMusic music;
	
	
	private AssetsStore(){	
	}
	
	public void init(){
		//instance = new AssetsStore();
		manager = new AssetManager();
		gui = new GUI();
		fonts = new AssetFonts();
		eneties = new AssetEneties();
		//sounds = new AssetSounds();
		//music = new AssetMusic();
		manager.finishLoading();
	}

	@Override
	public void dispose() {
		manager.dispose();
		fonts.dispose();
		
	}
	
	public class AssetSounds {
		public final Sound fire;
		public AssetSounds() {
			fire = manager.get("sounds/jump.wav", Sound.class);

		}
	}

	public class AssetMusic {
		public final Music song01;

		public AssetMusic() {
			song01 = manager.get("music/keith303_-_brand_new_highscore.mp3",
					Music.class);
		}
	}
	
	public class AssetEneties implements Disposable{
		public final Texture standartPiu;
		public final Texture frozenPiu;
		public final TextureRegion icon_hp;
		public final Image icon_money_shop;
		public final TextureRegion icon_money;
		public final TextureRegion icon_time;
		public final Array<Texture>  enemies;
		
		public AssetEneties (){
			
			standartPiu	= new Texture(
						Gdx.files.internal(Constants.standarBulletImage));
			
			frozenPiu	= new Texture(
					Gdx.files.internal(Constants.freezeBulletImage));
			
			Array<Texture>  tempenemies = new Array<Texture>();
			for (int i = 0; i < 4; i++) {
				tempenemies.add(new Texture("img/enemy/monster-"+i
					+".png"));
			}			
			enemies = tempenemies;
			
			icon_hp = new TextureRegion (new Texture(Constants.icon_HP));
			Texture temp = new Texture(Constants.icon_money_shop);
			icon_money_shop = new Image (new TextureRegionDrawable(
					new TextureRegion(temp)));
			icon_money =  new TextureRegion (new Texture(Constants.icon_money));
			icon_time =  new TextureRegion (new Texture(Constants.icon_time));
			
		}

		@Override
		public void dispose() {
			frozenPiu.dispose();
			standartPiu.dispose();
			
		}		
	}

	public class AssetFonts implements Disposable{
		public final BitmapFont gameFont;
		public final BitmapFont menuFont;
		
		public AssetFonts (){
			
			gameFont = new BitmapFont(
					Gdx.files.internal(Constants.Screen_FONT), false);
			gameFont.setScale(0.3f);
			gameFont.getRegion().getTexture().setFilter(
					TextureFilter.Linear, TextureFilter.Linear);
			menuFont = new BitmapFont(
					Gdx.files.internal(Constants.Screen_FONT), false);
			menuFont.setScale(0.8f);
			menuFont.getRegion().getTexture().setFilter(
					TextureFilter.Linear, TextureFilter.Linear);
		}

		@Override
		public void dispose() {
			gameFont.dispose();
			
		}		
	}
	
	public class GUI implements Disposable{
		//public final Texture button;
		public final  Image standartGunImage; 
		public final  Image freezeGunImage; 
		public final  Image lifePointImage; 
		public final  Image back_Menu; 
		public final  Image back_screen; 
		
		public GUI (){
			
			//button = new Texture(Gdx.files.internal("img/button/button.png"));
			Texture temp = new Texture(
					Gdx.files.internal(Constants.standarGunImage));
			 standartGunImage = new Image (new TextureRegionDrawable(
					new TextureRegion(temp)));
			temp = new Texture(
					Gdx.files.internal(Constants.freezeGunImage));
			 freezeGunImage = new Image (new TextureRegionDrawable(
					new TextureRegion(temp)));
			 
			 temp = new Texture(
						Gdx.files.internal(Constants.lifePointImage));
				 lifePointImage = new Image (new TextureRegionDrawable(
						new TextureRegion(temp)));
				 temp = new Texture(
							Gdx.files.internal(Constants.back_MenuImage));
				 back_Menu = new Image (new TextureRegionDrawable(
							new TextureRegion(temp)));
				 temp = new Texture(
							Gdx.files.internal(Constants.back_screenImage));
				 back_screen = new Image (new TextureRegionDrawable(
							new TextureRegion(temp)));
		}

		@Override
		public void dispose() {
			//button.dispose();
			
		}
		
		
		
	}
	
}
