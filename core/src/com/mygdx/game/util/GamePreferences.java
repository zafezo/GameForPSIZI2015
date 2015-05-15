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
	public int score;
	private Preferences prefs;

	// singleton: prevent instantiation from other classes
	private GamePreferences () {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	public void load () { 
		score = prefs.getInteger("score");
		
	}
	public void save () {
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		prefs.putInteger("score", score);
		prefs.flush();
	}
	public void saveScore(int score){
		prefs.putInteger("score", score);
		prefs.flush();
	}
}

