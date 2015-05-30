package com.mygdx.game.util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {
	public static final GamePreferences instance =
				new GamePreferences();
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	//public int score;
	private Preferences playerPrefs;
	private Preferences levelsPrefs;

	// singleton: prevent instantiation from other classes
	private GamePreferences () {
		playerPrefs = Gdx.app.getPreferences(Constants.Player_PREFERENCES);
		//test();
		levelsPrefs = Gdx.app.getPreferences(Constants.Levels_PREFERENCES);
	}
	
	private void test(){
		saveScore(0);
		saveFreezeGunLevel(0);
		saveLifeLevel(0);
		saveStandartGunLevel(0);
	}
	public int getScore () { 
		return playerPrefs.getInteger("score", 0);		
	}
	public void save () {
		
		//prefs.flush();
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

	
}

