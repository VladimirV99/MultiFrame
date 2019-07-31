package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public abstract class EntityObstacle extends Entity implements Pool.Poolable {

    protected boolean passed;
    private boolean dead;

    public EntityObstacle(Texture texture) {
        super(texture);
        passed = false;
        dead = false;
    }

    public abstract boolean intersects(Rectangle rect);

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
