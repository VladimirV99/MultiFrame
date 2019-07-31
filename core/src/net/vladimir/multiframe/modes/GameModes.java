package net.vladimir.multiframe.modes;

import net.vladimir.multiframe.frame.IFrameHandler;
import net.vladimir.multiframe.modes.dualframe.DualFrameHandler;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;
import net.vladimir.multiframe.references.References;

public enum GameModes {

    DUALFRAME_MEDIUM(0, "Dual Frame Medium", "Dual Frame Medium Description",
            new DualFrameHandler("dualframe_medium", 1, 800, References.SCREEN_HEIGHT/2-25, -1, 100, 150, 300, 300, 20)),
    DUALFRAME_CUSTOM(1, "Dual Frame Custom", "Dual Frame Custom Description",
            new DualFrameHandler("dualframe_custom", DualFrameSettings.PLAYER_SWITCH, DualFrameSettings.PLAYER_SPEED, DualFrameSettings.PLAYER_Y,
                    DualFrameSettings.OBSTACLE_SWITCH, DualFrameSettings.OBSTACLE_HEIGHT, DualFrameSettings.OBSTACLE_GAP, DualFrameSettings.OBSTACLE_DISTANCE,
                    DualFrameSettings.OBSTACLE_SPEED, DualFrameSettings.WALL_WIDTH));

    private int id;
    private String title;
    private String description;
    private IFrameHandler frameHandler;

    GameModes(int id, String title, String description, IFrameHandler frameHandler) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.frameHandler = frameHandler;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IFrameHandler getFrameHandler() {
        return frameHandler;
    }

}
