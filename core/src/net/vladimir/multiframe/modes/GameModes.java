package net.vladimir.multiframe.modes;

import net.vladimir.multiframe.frame.IFrameHandler;
import net.vladimir.multiframe.modes.dualframe.DualFrameHandler;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;

public enum GameModes {

    DUALFRAME_EASY(
            0,
            "Dual Frame Easy",
            "Controls switch every 10 points. Obstacles are slower and more distant.",
            new DualFrameHandler("dualframe_easy", DualFrameSettings.easyModeData)
    ),
    DUALFRAME_NORMAL(
            1,
            "Dual Frame Normal",
            "Controls switch every point. This is the default dual frame mode",
            new DualFrameHandler("dualframe_normal", DualFrameSettings.normalModeData)
    ),
    DUALFRAME_HARD(
            2,
            "Dual Frame Hard",
            "Controls switch randomly. Distance between obstacles is longer.",
            new DualFrameHandler("dualframe_hard", DualFrameSettings.hardModeData)
    ),
    DUALFRAME_CUSTOM(
            3,
            "Dual Frame Custom",
            "You customize the sizes and speeds of all objects",
            new DualFrameHandler("dualframe_custom", DualFrameSettings.getCurrentData())
    );

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
