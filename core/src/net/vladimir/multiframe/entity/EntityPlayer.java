package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public abstract class EntityPlayer extends Entity {

    protected int dirX;
    protected int dirY;

    protected int speed;

    protected int minX;
    protected int maxX;
    protected int minY;
    protected int maxY;

    private int startX;
    private int startY;

    public EntityPlayer(Texture texture, int x, int y, int width, int height, int speed, int minX, int maxX, int minY, int maxY) {
        super(texture, x, y, width, height);
        this.speed = speed;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        this.startX = x;
        this.startY = y;

        this.dirX = 0;
        this.dirY = 0;
    }

    @Override
    public void update(float delta) {
        int deltaX = 0;
        int deltaY = 0;
        if(dirX != 0)
            deltaX = (int)(dirX * speed * delta);
        if(dirY != 0)
            deltaY = (int)(dirY * speed * delta);
        add(MathUtils.clamp(getX() + deltaX, minX, maxX), MathUtils.clamp(getY() + deltaY, minY, maxY));
    }

    public void reset() {
        this.setX(startX);
        this.setY(startY);
    }

    public void setDirection(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

}
