package net.vladimir.multiframe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.screen.MenuScreen;

public class MultiFrame extends Game {

	private AssetManager assetManager;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();

		Settings.init();
		DualFrameSettings.init();

		setScreen(new MenuScreen(this));
	}

	private void loadAssets() {
		assetManager.load(AssetDescriptors.UI_SKIN);
		assetManager.load(AssetDescriptors.UI_FONT);
		assetManager.load(AssetDescriptors.PLAYER);
		assetManager.load(AssetDescriptors.OBSTACLE);
		assetManager.load(AssetDescriptors.SELECTOR);
		assetManager.load(AssetDescriptors.WALL);

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

}
