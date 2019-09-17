package net.vladimir.multiframe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.assets.RegionNames;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.screen.MenuScreen;
import net.vladimir.multiframe.background.MenuBackground;

public class MultiFrame extends Game {

	private AssetManager assetManager;
	private SpriteBatch batch;
	private MenuBackground background;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();

		background = new MenuBackground(
				assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS).findRegion(RegionNames.BACKGROUND_SQUARES),
				0.1f, 0.05f, 0.2f, 0.08f
		);

		Settings.init();
		DualFrameSettings.init();

		setScreen(new MenuScreen(this));
	}

	private void loadAssets() {
		assetManager.load(AssetDescriptors.UI_SKIN);
		assetManager.load(AssetDescriptors.GAMEPLAY_ATLAS);
		assetManager.load(AssetDescriptors.SOUND_PLAYER_EXPLODE);
		assetManager.load(AssetDescriptors.SOUND_PLAYER_SCORE);

		assetManager.finishLoading();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		assetManager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public MenuBackground getBackground() {
		return background;
	}

}
