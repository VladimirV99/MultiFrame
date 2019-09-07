package net.vladimir.multiframe.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import net.vladimir.multiframe.entity.EntityPlayer;

import java.util.LinkedList;

public class PlayerEffectStreak extends PlayerEffect{

    private LinkedList<Vector2> path;
    private int streakLength;
    private Vector2 streakVelocity;

    public PlayerEffectStreak(EntityPlayer player, int streakLength, Vector2 streakVelocity) {
        super(player);
        this.streakLength = streakLength;
        this.streakVelocity = streakVelocity;

        path = new LinkedList<Vector2>();
    }

    @Override
    public void update(float delta) {
        if(path.size() >= streakLength)
            path.removeFirst();
        for(Vector2 position : path) {
            position.add(streakVelocity.x * delta, streakVelocity.y * delta);
//            position.add(0, 300*delta);
        }
        path.addLast(player.getPosition());
    }

    @Override
    public void render(SpriteBatch batch, float delta, int offsetX, int offsetY) {
        Color currentColor = batch.getColor().cpy();
        float opacity = 0.5f - path.size()*0.05f;
        for(Vector2 position : path) {
            batch.setColor(currentColor.r, currentColor.g, currentColor.b, opacity);
            batch.draw(player.getTexture(), position.x+offsetX, position.y+offsetY, player.getWidth(), player.getHeight());
            opacity += 0.05f;
        }
        batch.setColor(currentColor);
    }

}
