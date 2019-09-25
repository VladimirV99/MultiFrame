package net.vladimir.multiframe.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import net.vladimir.multiframe.effect.PlayerEffect;
import net.vladimir.multiframe.effect.PlayerEffectExplode;
import net.vladimir.multiframe.event.Event;
import net.vladimir.multiframe.event.EventType;

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

    private boolean dead;

    private List<PlayerEffect> effects;
    private PlayerEffect deathEffect;

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

        this.dead = false;

        this.effects = new ArrayList<PlayerEffect>();
        this.deathEffect = new PlayerEffectExplode(this, 30, 15, 0.2f);
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

    protected void updateEffects(float delta) {
        for(PlayerEffect effect : effects)
            effect.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        this.renderEffects(batch, delta, offsetX, offsetY);
        if(!dead)
            super.render(batch, delta, offsetX, offsetY);
        else
            deathEffect.render(batch, delta, offsetX, offsetY);
    }

    protected void renderEffects(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        for(PlayerEffect effect : effects)
            effect.render(batch, delta, offsetX, offsetY);
    }

    @Override
    public boolean onEvent(Event event) {
        if(event.getType() == EventType.DESTROY_PLAYER) {
            deathEffect.reset();
            this.dead = true;
            return true;
        }
        return false;
    }

    public void reset() {
        this.dead = false;
        this.setX(startX);
        this.setY(startY);
        this.resetEffects();
    }

    protected void resetEffects() {
        for(PlayerEffect effect : effects)
            effect.reset();
    }

    public void setDirection(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public boolean isDead() {
        return dead;
    }

}
