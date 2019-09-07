package net.vladimir.multiframe.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.vladimir.multiframe.references.References;

public class Background {

    private TextureRegion texture;
    private Direction direction;
    private int speed;

    private int x;
    private int y;

    private final int screentTop = References.SCREEN_HEIGHT / 2;
    private final int screenBottom = -References.SCREEN_HEIGHT / 2;
    private final int screenLeft = -References.SCREEN_WIDTH / 2;
    private final int screenRight = References.SCREEN_WIDTH / 2;

    public Background(TextureRegion texture, Direction direction, int speed) {
        this.texture = texture;
        this.direction = direction;
        this.speed = speed;

        this.x = screenLeft;
        this.y = screenBottom;
    }

    public void update(float delta) {
        switch (direction) {
            case UP:
                y += speed * delta;
                if(y >= screentTop)
                    y -= References.SCREEN_HEIGHT;
                break;
            case DOWN:
                y -= speed * delta;
                if(y <= screenBottom - References.SCREEN_HEIGHT)
                    y += References.SCREEN_HEIGHT;
                break;
            case LEFT:
                x -= speed * delta;
                if(x <= screenLeft - References.SCREEN_WIDTH)
                    x -= References.SCREEN_WIDTH;
                break;
            case RIGHT:
                x += speed * delta;
                if(x >= screenRight)
                    x -= References.SCREEN_WIDTH;
                break;
        }
    }

    public void render(SpriteBatch batch, float delta) {
        switch (direction) {
            case UP:
                batch.draw(texture, screenLeft, y);
                batch.draw(texture, screenLeft, y-texture.getRegionHeight());
                break;
            case DOWN:
                batch.draw(texture, screenLeft, y);
                batch.draw(texture, screenLeft, y+texture.getRegionHeight());
                break;
            case LEFT:
                batch.draw(texture, x, screenBottom);
                batch.draw(texture, x+texture.getRegionWidth(), screenBottom);
                break;
            case RIGHT:
                batch.draw(texture, x, screenBottom);
                batch.draw(texture, x-texture.getRegionWidth(), screenBottom);
                break;
        }
    }

    public enum Direction {

        UP, DOWN, LEFT, RIGHT

    }

}
