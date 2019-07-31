package net.vladimir.multiframe.modes.dualframe.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import net.vladimir.multiframe.screen.OptionsScreen;

public class DualFrameSettings {

    private static Preferences settings;

    public static int PLAYER_SWITCH;
    public static int PLAYER_SPEED;
    public static int PLAYER_Y;
    public static int PLAYER_SIZE = 50;
    public static int OBSTACLE_SWITCH;
    public static int OBSTACLE_HEIGHT;
    public static int OBSTACLE_GAP;
    public static int OBSTACLE_DISTANCE;
    public static int OBSTACLE_SPEED;
    public static int WALL_WIDTH;

    public static void init(){
        settings = Gdx.app.getPreferences("multiframe_settings");
        PLAYER_SWITCH = settings.getInteger(EnumDualFrameSettings.PLAYER_SWITCH.getId(), EnumDualFrameSettings.PLAYER_SWITCH.getDefaultValue());
        PLAYER_SPEED = settings.getInteger(EnumDualFrameSettings.PLAYER_SPEED.getId(), EnumDualFrameSettings.PLAYER_SPEED.getDefaultValue());
        PLAYER_Y = settings.getInteger(EnumDualFrameSettings.PLAYER_Y.getId(), EnumDualFrameSettings.PLAYER_Y.getDefaultValue());
        OBSTACLE_SWITCH = settings.getInteger(EnumDualFrameSettings.OBSTACLE_SWITCH.getId(), EnumDualFrameSettings.OBSTACLE_SWITCH.getDefaultValue());
        OBSTACLE_HEIGHT = settings.getInteger(EnumDualFrameSettings.OBSTACLE_HEIGHT.getId(), EnumDualFrameSettings.OBSTACLE_HEIGHT.getDefaultValue());
        OBSTACLE_GAP = settings.getInteger(EnumDualFrameSettings.OBSTACLE_GAP.getId(), EnumDualFrameSettings.OBSTACLE_GAP.getDefaultValue());
        OBSTACLE_DISTANCE = settings.getInteger(EnumDualFrameSettings.OBSTACLE_DISTANCE.getId(), EnumDualFrameSettings.OBSTACLE_DISTANCE.getDefaultValue());
        OBSTACLE_SPEED = settings.getInteger(EnumDualFrameSettings.OBSTACLE_SPEED.getId(), EnumDualFrameSettings.OBSTACLE_SPEED.getDefaultValue());
        WALL_WIDTH = settings.getInteger(EnumDualFrameSettings.WALL_WIDTH.getId(), EnumDualFrameSettings.WALL_WIDTH.getDefaultValue());
    }

    public static void set(EnumDualFrameSettings setting, int value, OptionsScreen screen){
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
            case OBSTACLE_SWITCH:
                OBSTACLE_SWITCH = value;
                break;
            case OBSTACLE_HEIGHT:
                OBSTACLE_HEIGHT = value;
                break;
            case OBSTACLE_GAP:
                OBSTACLE_GAP = value;
                int wallWidth = EnumDualFrameSettings.WALL_WIDTH.recalculate();
                if(wallWidth != 0)
                    screen.setOption(EnumDualFrameSettings.WALL_WIDTH, wallWidth);
                break;
            case OBSTACLE_DISTANCE:
                OBSTACLE_DISTANCE = value;
                break;
            case OBSTACLE_SPEED:
                OBSTACLE_SPEED = value;
                break;
            case WALL_WIDTH:
                WALL_WIDTH = value;
                int obstacleGap = EnumDualFrameSettings.OBSTACLE_GAP.recalculate();
                if(obstacleGap != 0)
                    screen.setOption(EnumDualFrameSettings.OBSTACLE_GAP, obstacleGap);
                break;
        }
        settings.putInteger(setting.getId(), value);
        settings.flush();
    }

    public static int get(EnumDualFrameSettings setting){
        switch(setting){
            case PLAYER_SWITCH:
                return PLAYER_SWITCH;
            case PLAYER_SPEED:
                return PLAYER_SPEED;
            case PLAYER_Y:
                return PLAYER_Y;
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