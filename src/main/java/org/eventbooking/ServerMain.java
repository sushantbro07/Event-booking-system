package org.eventbooking;

public class ServerMain {
    public static void main(String[] args) {
        ServerData db = new ServerData();
        ServerSock serverSock = new ServerSock(db);
        serverSock.start();
    }
}