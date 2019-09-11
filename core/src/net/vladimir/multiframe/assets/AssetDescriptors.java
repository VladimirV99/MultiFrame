package net.vladimir.multiframe.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<Skin> UI_SKIN = new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS = new AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);

    private AssetDescriptors() {

    }

}
