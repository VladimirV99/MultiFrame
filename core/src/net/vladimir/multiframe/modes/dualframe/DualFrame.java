package net.vladimir.multiframe.modes.dualframe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.frame.Frame;
import net.vladimir.multiframe.frame.FrameOrchestrator;

public class DualFrame extends Frame {

    private DualFrameData data;

    private TextureRegion selectorTexture;
    private TextureRegion wallTexture;

    public DualFrame(int id, int x, int y, int width, int height, TextureRegion selectorTexture, TextureRegion wallTexture, DualFrameData data) {
        super(id, x, y, width, height);
        this.selectorTexture = selectorTexture;
        this.wallTexture = wallTexture;
        this.data = data;
    }

    @Override
    public void init(FrameOrchestrator orchestrator) {
        super.init(orchestrator);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        batch.draw(wallTexture, getX(), getY(), data.wallWidth, getHeight());
        batch.draw(wallTexture, getX()+getWidth()-data.wallWidth, getY(), data.wallWidth, getHeight());
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
