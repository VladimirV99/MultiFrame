package net.vladimir.multiframe.effect;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.entity.EntityPlayer;

public class PlayerEffectGlow extends PlayerEffect {

    private int radius;
    private Texture texture;

    public PlayerEffectGlow(EntityPlayer player, int radius) {
        super(player);
        this.radius = radius;

        int size = 2 * radius;

        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);

        pixmap.setColor(0f, 0f, 0f, 0f);
        pixmap.fill();
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(1f, 1f, 1f, 0.1f);
        pixmap.fillCircle(radius, radius, radius);

        this.texture = new Texture(pixmap);

        pixmap.dispose();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        batch.draw(texture, offsetX+player.getX()+player.getWidth()/2-radius, offsetY+player.getY()+player.getHeight()/2-radius);
    }

    @Override
    public void reset() {

    }

}
