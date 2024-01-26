package org.eventbooking;

import java.util.List;

public class EventListResponse implements java.io.Serializable {
    final List<Event> events;

    public EventListResponse(List<Event> events) {
        this.events = events;
    }
}
