package net.vladimir.multiframe.frame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.entity.EntityPlayer;
import net.vladimir.multiframe.event.Event;

public interface IFrame {

    void init(FrameOrchestrator orchestrator);

    void update(float delta);

    void render(SpriteBatch batch, float delta);

    void reset();

    void setFocus(boolean flag);

    boolean isInFocus();

    void addPlayer(EntityPlayer player);

    void addObstacle(EntityObstacle obstacle);

    void removeObstacle(EntityObstacle obstacle);

    void removeObstacles();

    void onEvent(Event event);

    void setPadding(int left, int top, int right, int bottom);

    int getId();

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    FrameOrchestrator getOrchestrator();

}
