package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import net.vladimir.multiframe.effect.PlayerEffect;

import java.util.ArrayList;
import java.util.List;

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

    private List<PlayerEffect> effects;

    public EntityPlayer(TextureRegion texture, int x, int y, int width, int height, int speed, int minX, int maxX, int minY, int maxY) {
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

        this.effects = new ArrayList<PlayerEffect>();
    }

    public void addEffect(PlayerEffect effect) {
        this.effects.add(effect);
    }

    @Override
    public void update(float delta) {
        int deltaX = 0;
        int deltaY = 0;
        if(dirX != 0)
            deltaX = (int)(dirX * speed * delta);
        if(dirY != 0)
            deltaY = (int)(dirY * speed * delta);
        setPosition(MathUtils.clamp(getX() + deltaX, minX, maxX), MathUtils.clamp(getY() + deltaY, minY, maxY));

        this.updateEffects(delta);
    }

    public void updateEffects(float delta) {
        for(PlayerEffect effect : effects)
            effect.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        this.renderEffects(batch, delta, offsetX, offsetY);
        super.render(batch, delta, offsetX, offsetY);
    }

    public void renderEffects(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        for(PlayerEffect effect : effects)
            effect.render(batch, delta, offsetX, offsetY);
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
