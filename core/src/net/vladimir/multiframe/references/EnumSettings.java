package net.vladimir.multiframe.references;

import com.badlogic.gdx.math.MathUtils;
import net.vladimir.multiframe.screen.OptionsScreen;

public enum EnumSettings {
    PLAYER_SWITCH("playerSwitch", "Player Control Switch", Settings.DEFAULT_PLAYER_SWITCH, -1, 100, 1),
    PLAYER_SPEED("playerSpeed", "Player Speed", Settings.DEFAULT_PLAYER_SPEED, 0, 2000, 50),
    PLAYER_Y("playerY", "Player Y Position", Settings.DEFAULT_PLAYER_Y, -Settings.SCREEN_HEIGHT/2, Settings.SCREEN_HEIGHT/2, 25),
    OBSTACLE_COUNT("obstacleCount", "Number of Obstacles", Settings.DEFAULT_OBSTACLE_COUNT, 0, 10, 1),
    OBSTACLE_SWITCH("obstacleSwitch", "Obstacle Side Switch", Settings.DEFAULT_OBSTACLE_SWITCH, -1, 100, 1),
    OBSTACLE_HEIGHT("obstacleHeight", "Obstacle Height", Settings.DEFAULT_OBSTACLE_HEIGHT, 20, 500, 10),
    OBSTACLE_GAP("obstacleGap", "Obstacle Gap", Settings.DEFAULT_OBSTACLE_GAP, 60, Settings.SCREEN_WIDTH/2-2*Settings.WALL_WIDTH, 10),
    OBSTACLE_DISTANCE("obstacleDistance", "Distance Between Obstacles", Settings.DEFAULT_OBSTACLE_DISTANCE, 0, 1000, 50),
    OBSTACLE_SPEED("obstacleSpeed", "Obstacle Speed", Settings.DEFAULT_OBSTACLE_SPEED, 10, 600, 10),
    WALL_WIDTH("wallWidth", "Wall Width", Settings.DEFAULT_WALL_WIDTH, 5, (Settings.SCREEN_WIDTH/2-Settings.OBSTACLE_GAP)/2, 5);

    private String id;
    private String name;
    private int defaultValue;
    private int min;
    private int max;
    private int step;

    private EnumSettings(String id, String name, int defaultValue, int min, int max, int step){
        this.id = id;
        this.name = name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getStep() {
        return step;
    }

    private void setMin(int min){
        this.min = min;
    }

    private void setMax(int max){
        this.max = max;
    }

    public Integer recalculate(){
        switch(this){
            case WALL_WIDTH:
                WALL_WIDTH.setMax((Settings.SCREEN_WIDTH/2-Settings.OBSTACLE_GAP)/2);
                if(Settings.WALL_WIDTH>WALL_WIDTH.getMax()) {
                    return WALL_WIDTH.getMax();
                }
                break;
            case OBSTACLE_GAP:
                OBSTACLE_GAP.setMax(Settings.SCREEN_WIDTH/2-2*Settings.WALL_WIDTH);
                if(Settings.OBSTACLE_GAP>OBSTACLE_GAP.getMax()) {
                    return OBSTACLE_GAP.getMax();
                }
                break;
            case OBSTACLE_COUNT:
                return MathUtils.ceil((float)(Settings.SCREEN_HEIGHT+Settings.OBSTACLE_HEIGHT)/(Settings.OBSTACLE_HEIGHT+Settings.OBSTACLE_DISTANCE));
            default:
                break;
        }
        return null;
    }

}
