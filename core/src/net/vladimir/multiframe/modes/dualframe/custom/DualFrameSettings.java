package net.vladimir.multiframe.modes.dualframe.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.vladimir.multiframe.modes.dualframe.DualFrameData;
import net.vladimir.multiframe.references.References;

public class DualFrameSettings {

    private static Preferences settings;

    private static DualFrameData currentData;
    private static DualFrameData newData;

    public static DualFrameData mediumModeData;

    public static void init(){
        settings = Gdx.app.getPreferences("multiframe_settings");
        currentData = new DualFrameData();
        currentData.playerSwitch = settings.getInteger(EnumDualFrameSettings.PLAYER_SWITCH.getId(), EnumDualFrameSettings.PLAYER_SWITCH.getDefaultValue());
        currentData.obstacleSpeed = settings.getInteger(EnumDualFrameSettings.PLAYER_SPEED.getId(), EnumDualFrameSettings.PLAYER_SPEED.getDefaultValue());
        currentData.playerY = settings.getInteger(EnumDualFrameSettings.PLAYER_Y.getId(), EnumDualFrameSettings.PLAYER_Y.getDefaultValue());
        currentData.playerSize = 50;
        currentData.obstacleSwitch = settings.getInteger(EnumDualFrameSettings.OBSTACLE_SWITCH.getId(), EnumDualFrameSettings.OBSTACLE_SWITCH.getDefaultValue());
        currentData.obstacleHeight = settings.getInteger(EnumDualFrameSettings.OBSTACLE_HEIGHT.getId(), EnumDualFrameSettings.OBSTACLE_HEIGHT.getDefaultValue());
        currentData.obstacleGap = settings.getInteger(EnumDualFrameSettings.OBSTACLE_GAP.getId(), EnumDualFrameSettings.OBSTACLE_GAP.getDefaultValue());
        currentData.obstacleDistance = settings.getInteger(EnumDualFrameSettings.OBSTACLE_DISTANCE.getId(), EnumDualFrameSettings.OBSTACLE_DISTANCE.getDefaultValue());
        currentData.obstacleSpeed = settings.getInteger(EnumDualFrameSettings.OBSTACLE_SPEED.getId(), EnumDualFrameSettings.OBSTACLE_SPEED.getDefaultValue());
        currentData.wallWidth = settings.getInteger(EnumDualFrameSettings.WALL_WIDTH.getId(), EnumDualFrameSettings.WALL_WIDTH.getDefaultValue());

        newData = new DualFrameData();
        newData.copy(currentData);

        mediumModeData = new DualFrameData(1, 800, References.SCREEN_HEIGHT/2-25, 50, -1, 100, 150, 300, 300, 20);
    }

    public static void set(EnumDualFrameSettings setting, int value){
        switch(setting){
            case PLAYER_SWITCH:
                newData.playerSwitch = value;
                break;
            case PLAYER_SPEED:
                newData.playerSpeed = value;
                break;
            case PLAYER_Y:
                newData.playerY = value;
                break;
            case OBSTACLE_SWITCH:
                newData.obstacleSwitch = value;
                break;
            case OBSTACLE_HEIGHT:
                newData.obstacleHeight = value;
                break;
            case OBSTACLE_GAP:
                newData.obstacleGap = value;
                break;
            case OBSTACLE_DISTANCE:
                newData.obstacleDistance = value;
                break;
            case OBSTACLE_SPEED:
                newData.obstacleSpeed = value;
                break;
            case WALL_WIDTH:
                newData.wallWidth = value;
                break;
        }
    }

    public static void save() {
        currentData.copy(newData);
        settings.putInteger(EnumDualFrameSettings.PLAYER_SWITCH.getId(), currentData.playerSwitch);
        settings.putInteger(EnumDualFrameSettings.PLAYER_SPEED.getId(), currentData.playerSpeed);
        settings.putInteger(EnumDualFrameSettings.PLAYER_Y.getId(), currentData.playerY);
        settings.putInteger(EnumDualFrameSettings.OBSTACLE_SWITCH.getId(), currentData.obstacleSwitch);
        settings.putInteger(EnumDualFrameSettings.OBSTACLE_HEIGHT.getId(), currentData.obstacleHeight);
        settings.putInteger(EnumDualFrameSettings.OBSTACLE_GAP.getId(), currentData.obstacleGap);
        settings.putInteger(EnumDualFrameSettings.OBSTACLE_DISTANCE.getId(), currentData.obstacleDistance);
        settings.putInteger(EnumDualFrameSettings.OBSTACLE_SPEED.getId(), currentData.obstacleSpeed);
        settings.putInteger(EnumDualFrameSettings.WALL_WIDTH.getId(), currentData.wallWidth);
        settings.flush();
    }

    public static void discard() {
        newData.copy(currentData);
    }

    public static int get(EnumDualFrameSettings setting){
        switch(setting){
            case PLAYER_SWITCH:
                return newData.playerSwitch;
            case PLAYER_SPEED:
                return newData.playerSpeed;
            case PLAYER_Y:
                return newData.playerY;
            case OBSTACLE_SWITCH:
                return newData.obstacleSwitch;
            case OBSTACLE_HEIGHT:
                return newData.obstacleHeight;
            case OBSTACLE_GAP:
                return newData.obstacleGap;
            case OBSTACLE_DISTANCE:
                return newData.obstacleDistance;
            case OBSTACLE_SPEED:
                return newData.obstacleSpeed;
            case WALL_WIDTH:
                return newData.wallWidth;
        }
        return 0;
    }

    public static DualFrameData getCurrentData() {
        return currentData;
    }

}