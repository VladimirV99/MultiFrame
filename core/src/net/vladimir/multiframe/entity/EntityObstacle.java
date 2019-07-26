package net.vladimir.multiframe.entity;

import com.badlogic.gdx.assets.AssetManager;

import net.vladimir.multiframe.assets.AssetDescriptors;
import net.vladimir.multiframe.references.Settings;

public class EntityObstacle extends Entity {

    public EntityObstacle(AssetManager assetManager) {
        super(assetManager.get(AssetDescriptors.OBSTACLE), 0, 0, 0, 0);
    }

    @Override
    public void update(float delta) {
        addY((int)(Settings.OBSTACLE_SPEED * delta));
    }

}