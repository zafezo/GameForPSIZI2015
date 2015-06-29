package com.mygdx.game.util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.screens.MapSelectedScreen;

public class GamePreferences {
	public static final GamePreferences instance =
				new GamePreferences();
	
	private Preferences playerPrefs;
	private Preferences levelsPrefs;
	
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;

	// singleton: prevent instantiation from other classes
	private GamePreferences () {
		playerPrefs = Gdx.app.getPreferences(Constants.Player_PREFERENCES);	
		levelsPrefs = Gdx.app.getPreferences(Constants.Levels_PREFERENCES);
		//test();
	}
	
	private void test(){
		saveScore(0);
		saveFreezeGunLevel(0);
		saveLifeLevel(0);
		saveStandartGunLevel(0);
		levelsPrefs.putInteger("time" + 0, 0);
		levelsPrefs.putInteger("time" + 1, 0);
		levelsPrefs.putInteger("time" + 2, 0);
		levelsPrefs.putInteger("time" + 3, 0);
		levelsPrefs.flush();
	}
	
	public void loadMusic () {
		sound = playerPrefs.getBoolean("sound", true);
		music = playerPrefs.getBoolean("music", true);
		volSound = MathUtils.clamp(playerPrefs.getFloat("volSound", 0.5f), 0.0f, 1.0f);
		volMusic = MathUtils.clamp(playerPrefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f);
	}

	public void saveMusic () {
		playerPrefs.putBoolean("sound", sound);
		playerPrefs.putBoolean("music", music);
		playerPrefs.putFloat("volSound", volSound);
		playerPrefs.putFloat("volMusic", volMusic);
		playerPrefs.flush();
	}

	
	public int getScore () { 
		return playerPrefs.getInteger("score", 0);		
	}
	
	public void saveScore(int score){
		playerPrefs.putInteger("score", score);
		playerPrefs.flush();
	}
	
	public int getLifeLevel(){
		return playerPrefs.getInteger("LifePoint", 0);
	}

	public void saveLifeLevel(int point){
		playerPrefs.putInteger("LifePoint", point);
	}
	
	public int getStandartGunLevel(){
		return playerPrefs.getInteger("StandartGun", 0);
	}

	public void saveStandartGunLevel(int point){
		playerPrefs.putInteger("StandartGun", point);
	}
	
	public int getFreezeGunLevel(){
		return playerPrefs.getInteger("FreezeGun", 0);
	}

	public void saveFreezeGunLevel(int point){
		playerPrefs.putInteger("FreezeGun", point);
	}
	
	public void saveTime(int timeSecond){
		levelsPrefs.putInteger("time" + MapSelectedScreen.currentLevelIndex, timeSecond);
		levelsPrefs.flush();
	}
	
	public int getTime(){
		return levelsPrefs.getInteger("time" + MapSelectedScreen.currentLevelIndex,0);
	}

	
}

