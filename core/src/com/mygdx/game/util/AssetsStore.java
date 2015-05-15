package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class AssetsStore implements Disposable{
	
	public static AssetsStore instance = new AssetsStore();
	
	private AssetManager manager;
	public AssetFonts fonts;
	public GUI gui;
	
	
	private AssetsStore(){	
	}
	
	public void init(){
		//instance = new AssetsStore();
		manager = new AssetManager();
		gui = new GUI();
		fonts = new AssetFonts();
		manager.finishLoading();
	}

	@Override
	public void dispose() {
		manager.dispose();
		fonts.dispose();
		
	}

	public class AssetFonts implements Disposable{
		public final BitmapFont gameFont;
		
		public AssetFonts (){
			
			gameFont = new BitmapFont(
					Gdx.files.internal(Constants.GUI_FONT), false);
			gameFont.setScale(0.5f);
			gameFont.getRegion().getTexture().setFilter(
					TextureFilter.Linear, TextureFilter.Linear);
		}

		@Override
		public void dispose() {
			gameFont.dispose();
			
		}		
	}
	
	public class GUI implements Disposable{
		public final Texture button;
		
		public GUI (){
			
			button = new Texture(Gdx.files.internal("img/button/button.png"));
		}

		@Override
		public void dispose() {
			button.dispose();
			
		}
		
		
		
	}
}
