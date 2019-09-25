package net.vladimir.multiframe.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import net.vladimir.multiframe.entity.EntityPlayer;

import java.util.LinkedList;

public class PlayerEffectStreak extends PlayerEffect{

    private Pool<Vector2> pathPool;
    private LinkedList<Vector2> path;
    private final int streakLength;
    private Vector2 streakVelocity;

    public PlayerEffectStreak(EntityPlayer player, int streakLength, Vector2 streakVelocity) {
        super(player);
        this.streakLength = streakLength;
        this.streakVelocity = streakVelocity;

        this.path = new LinkedList<Vector2>();
        this.pathPool = new Pool<Vector2>() {
            @Override
            protected Vector2 newObject() {
                return Vector2.Zero.cpy();
            }
        };
    }

    @Override
    public void update(float delta) {
        for(Vector2 position : path) {
            position.add(streakVelocity.x * delta, streakVelocity.y * delta);
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        if(!player.isDead()) {
            if (path.size() >= streakLength) {
                pathPool.free(path.getFirst());
                path.removeFirst();
            }
            path.addLast(pathPool.obtain().set(player.getPosition()));

            Color currentColor = batch.getColor().cpy();
            float opacity = 0.5f - path.size() * 0.05f;
            for (Vector2 position : path) {
                batch.setColor(currentColor.r, currentColor.g, currentColor.b, opacity);
                batch.draw(player.getTexture(), position.x + offsetX, position.y + offsetY, player.getWidth(), player.getHeight());
                opacity += 0.05f;
            }
            batch.setColor(currentColor);
        }
    }

    @Override
    public void reset() {
        for(Vector2 position : path)
            pathPool.free(position);
        path.clear();
    }

}
