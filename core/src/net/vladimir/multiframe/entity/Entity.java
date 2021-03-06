package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import net.vladimir.multiframe.event.Event;

public abstract class Entity {

    private TextureRegion texture;
    private int x;
    private int y;
    private int width;
    private int height;

    private Rectangle bounds;

    public Entity(TextureRegion texture, Vector2 position, Vector2 size) {
        this(texture, (int)position.x, (int)position.y, (int)size.x, (int)size.y);
    }

    public Entity(TextureRegion texture) {
        this(texture, 0, 0, 0, 0);
    }

    public Entity(TextureRegion texture, int x, int y, int width, int height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        batch.draw(texture, offsetX+x, offsetY+y, width, height);
    }

    public boolean onEvent(Event event) {
        return false;
    }

    public void setX(int x) {
        this.x = x;
        this.bounds.x = this.x;
    }

    public void setY(int y) {
        this.y = y;
        this.bounds.y = this.y;
    }

    public void add(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
        this.bounds.x = this.x;
        this.bounds.y = this.y;
    }

    public void addX(int deltaX) {
        add(deltaX, 0);
    }

    public void addY(int deltaY) {
        add(0, deltaY);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds.x = this.x;
        this.bounds.y = this.y;
    }

    public void setPosition(Vector2 position) {
        this.x = (int)position.x;
        this.y = (int)position.y;
        this.bounds.x = this.x;
        this.bounds.y = this.y;
    }

    public void setSize(Vector2 size) {
        this.width = (int)size.x;
        this.height = (int)size.y;
        this.bounds.width = this.width;
        this.bounds.height = this.height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public TextureRegion getTexture() {
        return texture;
    }

}