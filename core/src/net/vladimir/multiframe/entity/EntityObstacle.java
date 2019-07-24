package net.vladimir.multiframe.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.Settings;

public class EntityObstacle extends Entity {

    private Vector2 dest;

    public EntityObstacle(AssetManager assetManager, Vector2 direction) {
        super(assetManager.get(AssetDescriptors.OBSTACLE), null, direction);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dest.x, dest.y);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(texture, pos.x, pos.y, dest.x, dest.y);
    }

    public void setPos(Vector2 pos){
        this.pos = pos;
    }

    public void setDest(Vector2 dest){
        this.dest = dest;
    }

    @Override
    public void update() {
        pos.add(direction);
        setDirection(0, Settings.OBSTACLE_SPEED);
    }

}