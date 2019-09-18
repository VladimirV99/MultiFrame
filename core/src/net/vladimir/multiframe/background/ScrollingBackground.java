package net.vladimir.multiframe.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.vladimir.multiframe.references.References;

public class ScrollingBackground implements IBackground{

    private TextureRegion texture;
    private Direction direction;
    private int speed;

    private int x;
    private int y;

    private int viewportX;
    private int viewportY;

    private int screenTop;
    private int screenBottom;
    private int screenLeft;
    private int screenRight;

    public ScrollingBackground(TextureRegion texture, Direction direction, int speed) {
        this.texture = texture;
        this.direction = direction;
        this.speed = speed;

        resize(References.SCREEN_WIDTH, References.SCREEN_HEIGHT);

        this.viewportX = screenLeft;
        this.viewportY = screenBottom;

        this.x = screenLeft;
        this.y = screenTop - texture.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        switch (direction) {
            case UP:
                y += speed * delta;
                if(y >= screenTop)
                    y -= texture.getRegionHeight();
                break;
            case DOWN:
                y -= speed * delta;
                if(y <= screenBottom - texture.getRegionHeight())
                    y += texture.getRegionHeight();
                break;
            case LEFT:
                x -= speed * delta;
                if(x <= screenLeft - texture.getRegionWidth())
                    x -= texture.getRegionWidth();
                break;
            case RIGHT:
                x += speed * delta;
                if(x >= screenRight)
                    x -= texture.getRegionWidth();
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        switch (direction) {
            case UP:
                for(int cy = y; cy >= screenBottom - texture.getRegionHeight(); cy -= texture.getRegionHeight()) {
                    batch.draw(texture, viewportX, cy);
                }
                break;
            case DOWN:
                for(int cy = y; cy <= screenTop; cy += texture.getRegionHeight()) {
                    batch.draw(texture, viewportX, cy);
                }
                break;
            case LEFT:
                for(int cx = x; cx <= screenRight - texture.getRegionWidth(); cx += texture.getRegionWidth()) {
                    batch.draw(texture, cx, viewportY);
                }
                break;
            case RIGHT:
                for(int cx = x; cx >= screenLeft; cx -= texture.getRegionWidth()) {
                    batch.draw(texture, cx, viewportY);
                }
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        this.screenTop = height / 2;
        this.screenBottom = -height / 2;
        this.screenLeft = -width / 2;
        this.screenRight = width / 2;
    }

    public enum Direction {

        UP, DOWN, LEFT, RIGHT

    }

}
