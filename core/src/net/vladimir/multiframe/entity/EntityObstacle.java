package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.vladimir.multiframe.references.Settings;
import net.vladimir.multiframe.references.TextureManager;

public class EntityObstacle extends Entity {

    private Vector2 dest;

    public EntityObstacle(Vector2 direction) {
        super(TextureManager.OBSTACLE, null, direction);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dest.x, dest.y);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(TextureManager.OBSTACLE, pos.x, pos.y, dest.x, dest.y);
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