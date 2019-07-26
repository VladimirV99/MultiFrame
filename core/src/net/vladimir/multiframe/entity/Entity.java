package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    protected Texture texture;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Entity(Texture texture, Vector2 position, Vector2 size) {
        this(texture, (int)position.x, (int)position.y, (int)size.x, (int)size.y);
    }

    public Entity(Texture texture, int x, int y, int width, int height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch, float delta) {
        batch.draw(texture, x, y, width, height);
    }

    public void setPosition(Vector2 position) {
        this.x = (int)position.x;
        this.y = (int)position.y;
    }

    public void setSize(Vector2 size) {
        this.width = (int)size.x;
        this.height = (int)size.y;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}