package com.student.minesweeper;

import java.sql.*;

public class DataBaseController {

    float scoreToSet;
    String difficultyToSet;

    int beginnerID = 1;
    int intermediateID = 1;
    int expertID = 1;

    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:src/main/resources/HighScores.db";
    static Connection conn;
    static Statement stat;

    DataBaseController() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("No driver for JDBC");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Database connection problem");
            e.printStackTrace();
        }
        createTables();
        setIDs();
    }

    public void createTables() throws SQLException {
        String createBeginnerTable =
                "CREATE TABLE IF NOT EXISTS `Beginner` ("
                        + "`ID` integer PRIMARY KEY,"
                        + "`Name` varchar(30),"
                        + "`Score` real);";
        String createIntermediateTable =
                "CREATE TABLE IF NOT EXISTS `Intermediate` ("
                        + "`ID` integer PRIMARY KEY,"
                        + "`Name` varchar(30),"
                        + "`Score` real);";
        String createExpertTable =
                "CREATE TABLE IF NOT EXISTS `Expert` ("
                        + "`ID` integer PRIMARY KEY,"
                        + "`Name` varchar(30),"
                        + "`Score` real);";
        try {
            stat.execute(createBeginnerTable);
            stat.execute(createIntermediateTable);
            stat.execute(createExpertTable);
        } catch (SQLException e) {
            System.err.println("Error when creating tables");
            e.printStackTrace();
        }
    }

    private void setIDs() throws SQLException {

        System.out.println("Updating current IDs...");

        ResultSet result = stat.executeQuery("SELECT ID FROM Beginner ORDER BY ID DESC");
        if (result.next())
            beginnerID = result.getInt("ID") + 1;
        else
            beginnerID = 1;

        result = stat.executeQuery("SELECT ID FROM Intermediate ORDER BY ID DESC");
        if (result.next())
            intermediateID = result.getInt("ID") + 1;
        else
            intermediateID = 1;

        result = stat.executeQuery("SELECT ID FROM Expert ORDER BY ID DESC");
        if (result.next())
            expertID = result.getInt("ID") + 1;
        else
            expertID = 1;
    }

    public boolean checkIfNewHighScore(String difficulty, float score) throws SQLException {
        System.out.println("Checking if score: " + score + " in " + difficulty + " difficulty is a new high score...");

        String query = "SELECT Score FROM "
                + difficulty.substring(0, 1).toUpperCase()
                + difficulty.substring(1)
                + " ORDER BY Score;";

        ResultSet result = stat.executeQuery(query);
        if (result.next()) {
            float currentTopScore = result.getFloat("Score");
            System.out.println("Current top score is: " + currentTopScore);
            if (score < currentTopScore) {
                System.out.println("Setting new high score: " + score);
                scoreToSet = score;
                difficultyToSet = difficulty;
                return true;
            }
        } else {
            System.out.println("There is no current top score");
            System.out.println("Setting new high score: " + score);
            scoreToSet = score;
            difficultyToSet = difficulty;
            return true;
        }
        System.out.println("Your score was not good enough...");
        return false;
    }

    public void setNewHighScore(String name) throws SQLException {
        System.out.print("Setting new high score for " + name + " with score: ");
        System.out.println(scoreToSet + " in " + difficultyToSet + " difficulty!");

        String query = "INSERT INTO " + difficultyToSet.substring(0, 1).toUpperCase()
                + difficultyToSet.substring(1) + " VALUES (?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query);
        switch (difficultyToSet) {
            case "beginner" -> statement.setInt(1, beginnerID);
            case "intermediate" -> statement.setInt(1, intermediateID);
            case "expert" -> statement.setInt(1, expertID);
        }
        statement.setString(2, name);
        statement.setFloat(3, scoreToSet);

        statement.executeUpdate();

        switch (difficultyToSet) {
            case "beginner" -> beginnerID++;
            case "intermediate" -> intermediateID++;
            case "expert" -> expertID++;
        }
    }

    public void resetScores(String difficulty) throws SQLException {

        // Deleting all scores for chosen difficulty...
        String query = "DELETE FROM "
                + difficulty.substring(0, 1).toUpperCase()
                + difficulty.substring(1);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.executeUpdate();
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem closing a connection");
            e.printStackTrace();
        }
    }

}
