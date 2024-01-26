package org.eventbooking;

public class Event implements java.io.Serializable {
    int id;
    String name;
    String location;
    String date;

    public Event(int id, String name, String location, String date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public Event(String name, String location, String date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }
}
