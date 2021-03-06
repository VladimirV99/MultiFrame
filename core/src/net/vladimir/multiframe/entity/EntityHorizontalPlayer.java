package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import net.vladimir.multiframe.event.Event;

public class EntityHorizontalPlayer extends EntityPlayer {

    private int multiplier;
    private int startMultiplier;

    public EntityHorizontalPlayer(TextureRegion texture, int x, int y, int width, int height, int speed, int multiplier, int minX, int maxX) {
        super(texture, x, y, width, height, speed, minX, maxX, y, y);
        this.multiplier = multiplier;
        this.startMultiplier = multiplier;
    }

    @Override
    public void update(float delta) {
        if(dirX != 0) {
            int deltaX = (int) (multiplier * dirX * speed * delta);
            setX(MathUtils.clamp(getX() + deltaX, minX, maxX));
        }
        this.updateEffects(delta);
    }

    @Override
    public boolean onEvent(Event event) {
        if(super.onEvent(event))
            return true;
        switch (event.getType()) {
            case MOVE_PLAYER:
                setDirection(event.getData(), 0);
                return true;
            case CLIP_PLAYER:
                addX(multiplier * event.getData());
                return true;
            case SWITCH_CONTROLS:
                switchDirection();
                return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        this.multiplier = startMultiplier;
    }

    private void switchDirection(){
        multiplier *= -1;
    }

}
