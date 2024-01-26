package org.eventbooking;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientGUI {
    ClientSock sock;
    JPanel eventList = new JPanel(new GridLayout(0, 1, 10, 10));

    public ClientGUI(ClientSock sock) {
        this.sock = sock;
    }

    void show() {
        Dimension frameSize = new Dimension(1000, 700);
        JFrame frame = new JFrame("Event booking system");
        frame.setSize(frameSize);
        frame.setMinimumSize(frameSize);

        JPanel container = new JPanel(new BorderLayout());

        int fieldSize = 15;
        JPanel controls = new JPanel();
        JTextField name = new JTextField(fieldSize);
        JTextField location = new JTextField(fieldSize);
        JTextField date = new JTextField("YYYY-MM-DD", fieldSize);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                this.sock.outputStream.writeObject(new EventAddMsg(
                        new Event(
                                name.getText(),
                                location.getText(),
                                date.getText()
                        )
                ));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton listEventsButton = new JButton("List Events");
        listEventsButton.addActionListener(e -> {
            try {
                this.sock.outputStream.writeObject(new EventListRequest());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controls.add(new JLabel("Name"));
        controls.add(name);
        controls.add(new JLabel("Location"));
        controls.add(location);
        controls.add(new JLabel("Date"));
        controls.add(date);

        controls.add(addButton);
        controls.add(listEventsButton);

        container.add(controls, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(this.eventList);
        container.add(scrollPane, BorderLayout.CENTER);

        frame.add(container);
        frame.setVisible(true);
    }

    void setEvents(List<Event> events) {
        this.eventList.removeAll();
        for (Event e : events) {
            System.out.println("Adding event " + e);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Name: " + e.name));
            panel.add(new JLabel("Location: " + e.location));
            panel.add(new JLabel("Date: " + e.date));
            this.eventList.add(panel);
        }
        this.eventList.revalidate();
        this.eventList.repaint();
    }

    void listen() {
        System.out.println("Listening for server messages");
        while (true) {
            try {
                Object res = this.sock.inputStream.readObject();
                System.out.println(res);

                if (res instanceof EventListResponse msg) {
                    setEvents(msg.events);
                }
            }
            catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error while reading server messages");
            }
        }
    }
}