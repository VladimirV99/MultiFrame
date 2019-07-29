package net.vladimir.multiframe.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;
import net.vladimir.multiframe.frame.IFrame;
import net.vladimir.multiframe.references.Settings;

public class EntityObstacle implements Pool.Poolable {

    private EntityObstaclePart left;
    private EntityObstaclePart right;

    private IFrame frame;

    private boolean passed;
    private boolean dead;

    public EntityObstacle(AssetManager assetManager){
        left = new EntityObstaclePart(assetManager);
        right = new EntityObstaclePart(assetManager);

        passed = false;
        dead = false;
    }

    public void init(IFrame frame, int y) {
        this.frame = frame;
        this.passed = false;
        setPosition(y);
    }

    private void setPosition(int y){
        float mid = MathUtils.random(Settings.OBSTACLE_GAP/2, frame.getWidth()-2*Settings.WALL_WIDTH-Settings.OBSTACLE_GAP/2);

        left.setPosition(new Vector2(Settings.WALL_WIDTH, y));
        left.setSize(new Vector2(mid-Settings.OBSTACLE_GAP/2, Settings.OBSTACLE_HEIGHT));

        right.setPosition(new Vector2(Settings.WALL_WIDTH+mid+Settings.OBSTACLE_GAP/2, y));
        right.setSize(new Vector2(frame.getWidth()-mid-Settings.OBSTACLE_GAP/2, Settings.OBSTACLE_HEIGHT));

        passed = false;
    }

    public void update(float delta){
        left.update(delta);
        right.update(delta);

        if(right.getPosition().y>= frame.getHeight()){
            setDead();
            return;
        }

        if(right.getPosition().y>frame.getHeight()/2+Settings.PLAYER_Y-5 && !passed){
            passed = true;
            frame.getOrchestrator().getHandler().handle(new Event(EventType.OBSTACLE_PASS, 0));
        }
    }

    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY){
        left.render(batch, delta, offsetX, offsetY);
        right.render(batch, delta, offsetX, offsetY);
    }

    public boolean intersects(Rectangle rect){
        return rect.overlaps(left.getBounds()) || rect.overlaps(right.getBounds());
    }

    public int getY() {
        return right.getY();
    }

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

    private class EntityObstaclePart extends Entity {
        public EntityObstaclePart(AssetManager assetManager) {
            super(assetManager.get(AssetDescriptors.OBSTACLE));
        }

        @Override
        public void update(float delta) {
            addY((int)(Settings.OBSTACLE_SPEED * delta));
        }
    }
}