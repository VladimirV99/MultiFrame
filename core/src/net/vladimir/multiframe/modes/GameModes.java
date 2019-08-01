package net.vladimir.multiframe.modes;

import net.vladimir.multiframe.frame.IFrameHandler;
import net.vladimir.multiframe.modes.dualframe.DualFrameHandler;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;

public enum GameModes {

    DUALFRAME_MEDIUM(0, "Dual Frame Medium", "Dual Frame Medium Description",
            new DualFrameHandler("dualframe_medium", DualFrameSettings.mediumModeData)),
    DUALFRAME_CUSTOM(1, "Dual Frame Custom", "Dual Frame Custom Description",
            new DualFrameHandler("dualframe_custom", DualFrameSettings.getCurrentData()));

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
