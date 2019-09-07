package net.vladimir.multiframe.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.entity.EntityPlayer;

public abstract class PlayerEffect {

    protected EntityPlayer player;

    public PlayerEffect(EntityPlayer player) {
        this.player = player;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch, float delta, int offsetX, int offsetY);

}
