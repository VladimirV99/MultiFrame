package net.vladimir.multiframe.modes.dualframe.custom;

import net.vladimir.multiframe.references.References;

public enum EnumDualFrameSettings {

    PLAYER_SWITCH("playerSwitch", "Player Control Switch", 1, -1, 100, 1),
    PLAYER_SPEED("playerSpeed", "Player Speed", 800, 200, 1800, 50),
    PLAYER_Y("playerY", "Player Y Position", References.SCREEN_HEIGHT/2-25, 50, References.SCREEN_HEIGHT- 50, 25),
    OBSTACLE_SWITCH("obstacleSwitch", "Obstacle Side Switch", -1, -1, 100, 1),
    OBSTACLE_HEIGHT("obstacleHeight", "Obstacle Height", 100, 20, 500, 10),
    OBSTACLE_GAP("obstacleGap", "Obstacle Gap", 150, 60, References.SCREEN_WIDTH/4/*-2*DualFrameSettings.WALL_WIDTH*/, 10),
    OBSTACLE_DISTANCE("obstacleDistance", "Distance Between Obstacles", 300, 0, 1000, 50),
    OBSTACLE_SPEED("obstacleSpeed", "Obstacle Speed", 300, 10, 600, 10),
    WALL_WIDTH("wallWidth", "Wall Width", 20, 5, (References.SCREEN_WIDTH/8) /*2- DualFrameSettings.OBSTACLE_GAP)/2*/, 5);

    private String id;
    private String name;
    private int defaultValue;
    private int min;
    private int max;
    private int step;

    EnumDualFrameSettings(String id, String name, int defaultValue, int min, int max, int step){
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

}
