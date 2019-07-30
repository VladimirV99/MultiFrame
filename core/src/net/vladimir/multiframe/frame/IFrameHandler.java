package net.vladimir.multiframe.frame;

import net.vladimir.multiframe.event.Event;

public interface IFrameHandler {

    void init(FrameOrchestrator orchestrator);

    void update();

    void reset();

    void handle(Event event);

}
