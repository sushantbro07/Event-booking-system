package org.eventbooking;

public class EventAddMsg implements java.io.Serializable {
    public Event event;
    EventAddMsg(Event e) {
        this.event = e;
    }
}
