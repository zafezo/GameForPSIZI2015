package com.mygdx.game.util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.screens.MapSelectedScreen;

public class GamePreferences {
	public static final GamePreferences instance =
				new GamePreferences();
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
	
	public void saveTime(int timeSecond){
		levelsPrefs.putInteger("time" + MapSelectedScreen.currentLevelIndex, timeSecond);
		levelsPrefs.flush();
	}
	
	public int getTime(){
		return levelsPrefs.getInteger("time" + MapSelectedScreen.currentLevelIndex,0);
	}

	
}

