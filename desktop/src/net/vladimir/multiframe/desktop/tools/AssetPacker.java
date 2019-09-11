package net.vladimir.multiframe.desktop.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final String RAW_ASSETS_PATH = "desktop/assets/";
    private static final String ASSETS_PATH = "android/assets/";

    private static final String GAMEPLAY = "gameplay";
    private static final String UI = "ui";
    private static final String UI_OUTPUT = "uiskin";

    public static void main(String[] args) {
        TexturePacker.process(
                RAW_ASSETS_PATH + GAMEPLAY,
                ASSETS_PATH + GAMEPLAY,
                GAMEPLAY
        );

        TexturePacker.process(
                RAW_ASSETS_PATH + UI,
                ASSETS_PATH + UI,
                UI_OUTPUT
        );
    }

}
