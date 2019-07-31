package net.vladimir.multiframe.modes.dualframe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.frame.Frame;
import net.vladimir.multiframe.frame.FrameOrchestrator;
import net.vladimir.multiframe.modes.dualframe.custom.DualFrameSettings;

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
        batch.draw(wallTexture, getX(), getY(), DualFrameSettings.WALL_WIDTH, getHeight());
        batch.draw(wallTexture, getX()+getWidth()- DualFrameSettings.WALL_WIDTH, getY(), DualFrameSettings.WALL_WIDTH, getHeight());
        if(isInFocus()) {
            batch.draw(selectorTexture, getX(), getY()+getHeight()-20, getWidth(), 20);
        }
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getType()) {
            case MOVE_PLAYER:
                for(EntityPlayer player : getPlayers()) {
                    player.onEvent(event);
                }
                break;
            case SWITCH_CONTROLS:
                setFocus(!isInFocus());
                for (EntityPlayer player : getPlayers()) {
                    player.onEvent(event);
                }
                break;
        }
    }

}
