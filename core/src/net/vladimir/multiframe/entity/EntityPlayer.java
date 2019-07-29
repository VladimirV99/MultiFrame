package net.vladimir.multiframe.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.Settings;

public class EntityPlayer extends Entity {

    private int minX;
    private int maxX;
    private int multiplier;
    private int dir;

    private int startX;

    public EntityPlayer(AssetManager assetManager, int x, int y, int width, int height, int multiplier, int minX, int maxX) {
        super(assetManager.get(AssetDescriptors.PLAYER), x, y, width, height);
        this.startX = x;
        this.minX = minX;
        this.maxX = maxX;
        this.multiplier = multiplier;
        this.dir = 0;
    }

    @Override
    public void update(float delta) {
        if(dir != 0) {
            int deltaX = (int)(multiplier * dir * Settings.PLAYER_SPEED * delta);
            setX(MathUtils.clamp(getX() + deltaX, minX, maxX));
        }
    }

    public void reset() {
        this.setX(startX);
    }

    public void setDirection(int dir) {
        this.dir = dir;
    }

    public void switchDirection(){
        multiplier *= -1;
    }

}
