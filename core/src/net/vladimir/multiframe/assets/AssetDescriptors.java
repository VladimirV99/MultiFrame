package net.vladimir.multiframe.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> UI_FONT = new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);
    public static final AssetDescriptor<Skin> UI_SKIN = new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<Texture> PLAYER = new AssetDescriptor<Texture>(AssetPaths.PLAYER, Texture.class);
    public static final AssetDescriptor<Texture> OBSTACLE = new AssetDescriptor<Texture>(AssetPaths.OBSTACLE, Texture.class);
    public static final AssetDescriptor<Texture> SELECTOR = new AssetDescriptor<Texture>(AssetPaths.SELECTOR, Texture.class);
    public static final AssetDescriptor<Texture> WALL = new AssetDescriptor<Texture>(AssetPaths.WALL, Texture.class);

    private AssetDescriptors() {

    }

}
