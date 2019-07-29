package net.vladimir.multiframe.event;

public class Event {

    private EventType type;
    private int data;

    public Event(EventType type, int data) {
        this.type = type;
        this.data = data;
    }

    public EventType getType() {
        return type;
    }

    public int getData() {
        return data;
    }

}
