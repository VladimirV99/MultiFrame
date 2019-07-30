package net.vladimir.multiframe.modes.dualframe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.frame.Frame;
import net.vladimir.multiframe.frame.FrameOrchestrator;
import net.vladimir.multiframe.references.Settings;

public class DualFrame extends Frame {

    private Texture selectorTexture;
    private Texture wallTexture;

    public DualFrame(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
    }

    @Override
    public void init(FrameOrchestrator orchestrator) {
        super.init(orchestrator);
        this.selectorTexture = orchestrator.getAssetManager().get(AssetDescriptors.SELECTOR);
        this.wallTexture = orchestrator.getAssetManager().get(AssetDescriptors.WALL);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        batch.draw(wallTexture, getX(), getY(), Settings.WALL_WIDTH, getHeight());
        batch.draw(wallTexture, getX()+getWidth()-Settings.WALL_WIDTH, getY(), Settings.WALL_WIDTH, getHeight());
        if(isInFocus()) {
            batch.draw(selectorTexture, getX(), getY()+getHeight()-20, getWidth(), 20);
        }
    }
}
