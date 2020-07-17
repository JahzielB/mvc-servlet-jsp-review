package com.codeup.reviewlister;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.jdbc.Driver;

public class QuotesDao {
    private Connection connection = null;

    public QuotesDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }

    public List<Quote> all() {
        List<Quote> quotes = new ArrayList<>();
        String query = "SELECT * FROM quotes";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                quotes.add(
                    new Quote(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("quote")
                    )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quotes;

    }

    public Quote random() {
        List<Quote> quotes = all();

        int randomIndex = (int) Math.floor(Math.random() * quotes.size());

        return quotes.get(randomIndex);
    }

    public void create(String userAuthor, String userQuote) {
        String query = "INSERT INTO quotes (author, quote)" + " values (?, ?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, userAuthor);
            prepStmt.setString(2, userQuote);
            prepStmt.executeUpdate();
            prepStmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
