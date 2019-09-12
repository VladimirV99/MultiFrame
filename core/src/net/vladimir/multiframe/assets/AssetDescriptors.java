package net.vladimir.multiframe.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<Skin> UI_SKIN = new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS = new AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
    public static final AssetDescriptor<Sound> SOUND_PLAYER_EXPLODE = new AssetDescriptor<Sound>(AssetPaths.SOUND_PLAYER_EXPLODE, Sound.class);
    public static final AssetDescriptor<Sound> SOUND_PLAYER_SCORE = new AssetDescriptor<Sound>(AssetPaths.SOUND_PLAYER_SCORE, Sound.class);

    private AssetDescriptors() {

    }

}
