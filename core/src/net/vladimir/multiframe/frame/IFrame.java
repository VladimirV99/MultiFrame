package net.vladimir.multiframe.frame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.entity.EntityObstaclePair;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;

public interface IFrame {

    void update(float delta);

    void render(SpriteBatch batch, float delta);

    void setFocus(boolean flag);

    void addPlayer(EntityPlayer player);

    void addObstacle(EntityObstaclePair obstacle);

    void removeObstacle(EntityObstaclePair obstacle);

    void onEvent(Event event);

    int getId();

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    FrameOrchestrator getOrchestrator();

}
