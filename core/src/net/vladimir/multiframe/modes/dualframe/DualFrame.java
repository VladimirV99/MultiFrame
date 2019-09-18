package net.vladimir.multiframe.modes.dualframe;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.assets.RegionNames;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.frame.Frame;
import net.vladimir.multiframe.frame.FrameOrchestrator;

public class DualFrame extends Frame {

    private DualFrameData data;

    private TextureRegion selectorTexture;
    private NinePatch wallLeftTexture;
    private NinePatch wallRightTexture;

    public DualFrame(int id, int x, int y, int width, int height, AssetManager assetManager, DualFrameData data) {
        super(id, x, y, width, height);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        this.selectorTexture = gameplayAtlas.findRegion(RegionNames.SELECTOR);
        this.wallLeftTexture = new NinePatch(gameplayAtlas.findRegion(RegionNames.WALL_LEFT), 2, 2, 2, 2);
        this.wallRightTexture = new NinePatch(gameplayAtlas.findRegion(RegionNames.WALL_RIGHT), 2, 2, 2, 2);
        this.data = data;
    }

    @Override
    public void init(FrameOrchestrator orchestrator) {
        super.init(orchestrator);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        wallLeftTexture.draw(batch, getX()-padding.getLeft()-1, getY()-padding.getBottom()-1, data.wallWidth+padding.getLeft()+1, getHeight()+padding.getBottom()+padding.getTop()+2);
        wallRightTexture.draw(batch, getX()+getWidth()-data.wallWidth, getY()-padding.getBottom()-1, data.wallWidth+padding.getRight()+1, getHeight()+padding.getBottom()+padding.getTop()+2);
        if(isInFocus()) {
            batch.draw(selectorTexture, getX(), getY()+getHeight()+padding.getTop()-selectorTexture.getRegionHeight(), getWidth(), selectorTexture.getRegionHeight());
        }
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getType()) {
            case MOVE_PLAYER:
            case CLIP_PLAYER:
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
