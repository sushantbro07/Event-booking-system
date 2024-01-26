package org.eventbooking;

public class ClientMain {
    public static void main(String[] args) {
        ClientSock sock = new ClientSock();
        ClientGUI gui = new ClientGUI(sock);
        gui.show();
        gui.listen();
    }
} 