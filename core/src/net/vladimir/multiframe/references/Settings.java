package net.vladimir.multiframe.references;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import net.vladimir.multiframe.screen.OptionsScreen;

public class Settings {

    private static Preferences data;
    private static Preferences settings;

    public static final String VERSION = "Alpha 5";

    public static final float MENU_WIDTH = 1280;
    public static final float MENU_HEIGHT = 720;

    public static final float SCREEN_WIDTH = 1280;
    public static final float SCREEN_HEIGHT = 720;

    public static final int DEFAULT_PLAYER_SWITCH = 1;
    public static final int DEFAULT_PLAYER_SPEED = 800;
    public static final int DEFAULT_PLAYER_Y = -25;
    public static final int DEFAULT_OBSTACLE_COUNT = 2;
    public static final int DEFAULT_OBSTACLE_SWITCH = -1;
    public static final int DEFAULT_OBSTACLE_HEIGHT = 100;
    public static final int DEFAULT_OBSTACLE_GAP = 150;
    public static final int DEFAULT_OBSTACLE_DISTANCE = 300;
    public static final int DEFAULT_OBSTACLE_SPEED = 300;
    public static final int DEFAULT_WALL_WIDTH = 20;

    public static int OBSTACLE_HEIGHT = 100;
    public static int OBSTACLE_GAP = 150;
    public static int PLAYER_SWITCH = 1;
    public static int WALL_WIDTH = 20;
    public static int OBSTACLE_SWITCH = -1;
    public static int OBSTACLE_COUNT = 3;
    public static int OBSTACLE_DISTANCE = 300;
    public static int OBSTACLE_SPEED = 300;
    public static int PLAYER_SPEED = 800;
    public static int PLAYER_Y = -25;
    public static int LAST_SCORE = 0;
    public static int HIGH_SCORE = 0;

    public static void init(){
        settings = Gdx.app.getPreferences("Settings");
        PLAYER_SWITCH = settings.getInteger(EnumSettings.PLAYER_SWITCH.getId(), DEFAULT_PLAYER_SWITCH);
        PLAYER_SPEED = settings.getInteger(EnumSettings.PLAYER_SPEED.getId(), DEFAULT_PLAYER_SPEED);
        PLAYER_Y = settings.getInteger(EnumSettings.PLAYER_Y.getId(), DEFAULT_PLAYER_Y);
        OBSTACLE_COUNT = settings.getInteger(EnumSettings.OBSTACLE_COUNT.getId(), DEFAULT_OBSTACLE_COUNT);
        OBSTACLE_SWITCH = settings.getInteger(EnumSettings.OBSTACLE_SWITCH.getId(), DEFAULT_OBSTACLE_SWITCH);
        OBSTACLE_HEIGHT = settings.getInteger(EnumSettings.OBSTACLE_HEIGHT.getId(), DEFAULT_OBSTACLE_HEIGHT);
        OBSTACLE_GAP = settings.getInteger(EnumSettings.OBSTACLE_GAP.getId(), DEFAULT_OBSTACLE_GAP);
        OBSTACLE_DISTANCE = settings.getInteger(EnumSettings.OBSTACLE_DISTANCE.getId(), DEFAULT_OBSTACLE_DISTANCE);
        OBSTACLE_SPEED = settings.getInteger(EnumSettings.OBSTACLE_SPEED.getId(), DEFAULT_OBSTACLE_SPEED);
        WALL_WIDTH = settings.getInteger(EnumSettings.WALL_WIDTH.getId(), DEFAULT_WALL_WIDTH);

        OBSTACLE_COUNT = EnumSettings.OBSTACLE_COUNT.recalculate();

        data = Gdx.app.getPreferences("Data");
        HIGH_SCORE = data.getInteger("highScore");
        LAST_SCORE = data.getInteger("lastScore");
    }

    public static void setHighScore(int score){
        HIGH_SCORE = score;
        data.putInteger("highScore", score);
        data.flush();
    }

    public static void setLastScore(int score){
        LAST_SCORE = score;
        data.putInteger("lastScore", score);
        data.flush();
    }

    //TODO Test Back Button
    public static void set(EnumSettings setting, int value, OptionsScreen screen){
        switch(setting){
            case PLAYER_SWITCH:
                PLAYER_SWITCH = value;
                break;
            case PLAYER_SPEED:
                PLAYER_SPEED = value;
                break;
            case PLAYER_Y:
                PLAYER_Y = value;
                break;
            case OBSTACLE_COUNT:
                OBSTACLE_COUNT = value;
                break;
            case OBSTACLE_SWITCH:
                OBSTACLE_SWITCH = value;
                break;
            case OBSTACLE_HEIGHT:
                OBSTACLE_HEIGHT = value;
                screen.setOption(EnumSettings.OBSTACLE_COUNT, EnumSettings.OBSTACLE_COUNT.recalculate());
                break;
            case OBSTACLE_GAP:
                OBSTACLE_GAP = value;
                Integer val1 = EnumSettings.WALL_WIDTH.recalculate();
                if(val1!=null)
                    screen.setOption(EnumSettings.WALL_WIDTH, val1);
                break;
            case OBSTACLE_DISTANCE:
                OBSTACLE_DISTANCE = value;
                screen.setOption(EnumSettings.OBSTACLE_COUNT, EnumSettings.OBSTACLE_COUNT.recalculate());
                break;
            case OBSTACLE_SPEED:
                OBSTACLE_SPEED = value;
                break;
            case WALL_WIDTH:
                WALL_WIDTH = value;
                Integer val2 = EnumSettings.OBSTACLE_GAP.recalculate();
                if(val2!=null)
                    screen.setOption(EnumSettings.OBSTACLE_GAP, val2);
                break;
        }
        settings.putInteger(setting.getId(), value);
        settings.flush();
    }

    public static int get(EnumSettings setting){
        switch(setting){
            case PLAYER_SWITCH:
                return PLAYER_SWITCH;
            case PLAYER_SPEED:
                return PLAYER_SPEED;
            case PLAYER_Y:
                return PLAYER_Y;
            case OBSTACLE_COUNT:
                return OBSTACLE_COUNT;
            case OBSTACLE_SWITCH:
                return OBSTACLE_SWITCH;
            case OBSTACLE_HEIGHT:
                return OBSTACLE_HEIGHT;
            case OBSTACLE_GAP:
                return OBSTACLE_GAP;
            case OBSTACLE_DISTANCE:
                return OBSTACLE_DISTANCE;
            case OBSTACLE_SPEED:
                return OBSTACLE_SPEED;
            case WALL_WIDTH:
                return WALL_WIDTH;
        }
        return 0;
    }

}