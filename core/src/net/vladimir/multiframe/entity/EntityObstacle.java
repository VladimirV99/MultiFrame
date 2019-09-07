package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public abstract class EntityObstacle extends Entity implements Pool.Poolable {

    protected boolean passed;
    private boolean dead;

    public EntityObstacle(TextureRegion texture) {
        super(texture);
        passed = false;
        dead = false;
    }

    public abstract boolean checkCollision(EntityPlayer player);

    public void setDead() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public void reset() {
        passed = false;
        dead = false;
    }

}
