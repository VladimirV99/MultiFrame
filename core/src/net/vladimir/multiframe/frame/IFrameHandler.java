package net.vladimir.multiframe.frame;

import com.badlogic.gdx.utils.Pool;

import net.vladimir.multiframe.entity.EntityObstacle;
import net.vladimir.multiframe.event.Event;

public interface IFrameHandler {

    void init(FrameOrchestrator orchestrator);

    Pool<EntityObstacle> createObstaclePool();

    void update();

    void reset();

    void handle(Event event);

    String getId();

}
