package net.vladimir.multiframe.references;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Preferences data;

    public static int LAST_SELECTION;
    private static Map<String, Integer> HIGH_SCORES;

    private static String PREFERENCES_NAME = "multiframe_data";
    private static String HIGHSCORE_KEY_SUFFIX = "_highScore";
    private static String LAST_SELECTION_KEY = "lastSelection";

    public static void init() {
        data = Gdx.app.getPreferences(PREFERENCES_NAME);
        LAST_SELECTION = data.getInteger(LAST_SELECTION_KEY, 0);
        HIGH_SCORES = new HashMap<String, Integer>();
    }

    public static void setHighScore(String id, int score){
        HIGH_SCORES.put(id, score);
        data.putInteger(id + HIGHSCORE_KEY_SUFFIX, score);
        data.flush();
    }

    public static int getHighScore(String id) {
        if(HIGH_SCORES.containsKey(id))
            return HIGH_SCORES.get(id);
        return data.getInteger(id + HIGHSCORE_KEY_SUFFIX, 0);
    }

    public static void setLastSelection(int selection) {
        LAST_SELECTION = selection;
        data.putInteger(LAST_SELECTION_KEY, selection);
        data.flush();
    }

}
