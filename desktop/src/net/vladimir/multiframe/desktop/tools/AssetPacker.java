package net.vladimir.multiframe.desktop.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final String RAW_ASSETS_PATH = "desktop/assets/";
    private static final String ASSETS_PATH = "android/assets/";

    private static final String GAMEPLAY_FILE = "gameplay";

    public static void main(String[] args) {
        TexturePacker.process(
                RAW_ASSETS_PATH + GAMEPLAY_FILE,
                ASSETS_PATH + GAMEPLAY_FILE,
                GAMEPLAY_FILE
        );
    }

}
