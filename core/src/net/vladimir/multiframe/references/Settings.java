package net.vladimir.multiframe.references;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Preferences data;

    private static Map<String, Integer> HIGH_SCORES;

    public static void init() {
        data = Gdx.app.getPreferences("multiframe_data");
        HIGH_SCORES = new HashMap<String, Integer>();
    }

    public static void setHighScore(String id, int score){
        HIGH_SCORES.put(id, score);
        data.putInteger(id + "_highScore", score);
        data.flush();
    }

    public static int getHighScore(String id) {
        if(HIGH_SCORES.containsKey(id))
            return HIGH_SCORES.get(id);
        return data.getInteger(id + "_highScore", 0);
    }

}
