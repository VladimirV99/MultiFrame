package net.vladimir.multiframe.references;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Preferences settings;
    private static Preferences data;

    private static float volume;
    private static boolean vibrate;

    private static int lastSelection;
    private static Map<String, Integer> highScores;

    private static final String PREFERENCES_SETTINGS_NAME = "multiframe_settings";
    private static final String VOLUME_KEY = "volume";
    private static final String VIBRATE_KEY = "vibrate";

    private static final String PREFERENCES_DATA_NAME = "multiframe_data";
    private static final String HIGH_SCORE_KEY_SUFFIX = "_highScore";
    private static final String LAST_SELECTION_KEY = "lastSelection";

    public static void init() {
        settings = Gdx.app.getPreferences(PREFERENCES_SETTINGS_NAME);
        volume = settings.getFloat(VOLUME_KEY, 100f);
        vibrate = settings.getBoolean(VIBRATE_KEY, false);

        data = Gdx.app.getPreferences(PREFERENCES_DATA_NAME);
        lastSelection = data.getInteger(LAST_SELECTION_KEY, 0);
        highScores = new HashMap<String, Integer>();
    }

    public static void setVolume(float value) {
        if(volume != value) {
            volume = value;
            settings.putFloat(VOLUME_KEY, value);
            settings.flush();
        }
    }

    public static float getVolume() {
        return volume/100;
    }

    public static float getVolumePercentage() {
        return volume;
    }

    public static void setVibrate(boolean flag) {
        if(vibrate != flag) {
            vibrate = flag;
            settings.putBoolean(VIBRATE_KEY, flag);
            settings.flush();
        }
    }

    public static boolean getVibrate() {
        return vibrate;
    }

    public static void setHighScore(String id, int score) {
        highScores.put(id, score);
        data.putInteger(id + HIGH_SCORE_KEY_SUFFIX, score);
        data.flush();
    }

    public static int getHighScore(String id) {
        if(highScores.containsKey(id))
            return highScores.get(id);
        return data.getInteger(id + HIGH_SCORE_KEY_SUFFIX, 0);
    }

    public static void setLastSelection(int selection) {
        lastSelection = selection;
        data.putInteger(LAST_SELECTION_KEY, selection);
        data.flush();
    }

    public static int getLastSelection() {
        return lastSelection;
    }

}
