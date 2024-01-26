package org.eventbooking;

import com.sun.source.tree.StatementTree;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerData {
    private Connection connection;

    final String dbName = "event_booking";
    final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
    final String username = "root";
    final String password = "";

    public ServerData() {
        try {
            this.connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
        } catch (SQLException e) {
            System.out.println("Error while making database");
        }
    }

    void addEvent(Event event) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO" +
                " events(name, location, date) " +
                " VALUES(?, ?, ?)"
        );
        stmt.setString(1, event.name);
        stmt.setString(2, event.location);
        stmt.setString(3, event.date);
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Event> getEvents() throws SQLException {
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM events");
        List<Event> products = new ArrayList<>();
        while (rs.next()) {
            Event prod = new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getString("date")
            );
            products.add(prod);
        }
        return products;
    }
}
