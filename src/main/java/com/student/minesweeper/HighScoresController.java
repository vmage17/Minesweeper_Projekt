package com.student.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HighScoresController  implements Initializable {

    @FXML
    private Label nameLabel1;

    @FXML
    private Label nameLabel10;

    @FXML
    private Label nameLabel2;

    @FXML
    private Label nameLabel3;

    @FXML
    private Label nameLabel4;

    @FXML
    private Label nameLabel5;

    @FXML
    private Label nameLabel6;

    @FXML
    private Label nameLabel7;

    @FXML
    private Label nameLabel8;

    @FXML
    private Label nameLabel9;

    @FXML
    private Label timeLabel1;

    @FXML
    private Label timeLabel10;

    @FXML
    private Label timeLabel2;

    @FXML
    private Label timeLabel3;

    @FXML
    private Label timeLabel4;

    @FXML
    private Label timeLabel5;

    @FXML
    private Label timeLabel6;

    @FXML
    private Label timeLabel7;

    @FXML
    private Label timeLabel8;

    @FXML
    private Label timeLabel9;

    @FXML
    private Label difficultyLabel;

    public static DataBaseController dataBaseController;
    public static Stage stage;
    private static String difficulty;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        difficultyLabel.setText("for " + difficulty.substring(0, 1).toUpperCase()
                + difficulty.substring(1) + " Difficulty");

        List<Label> list = new ArrayList<>();
        list.add(timeLabel1);
        list.add(nameLabel1);
        list.add(timeLabel2);
        list.add(nameLabel2);
        list.add(timeLabel3);
        list.add(nameLabel3);
        list.add(timeLabel4);
        list.add(nameLabel4);
        list.add(timeLabel5);
        list.add(nameLabel5);
        list.add(timeLabel6);
        list.add(nameLabel6);
        list.add(timeLabel7);
        list.add(nameLabel7);
        list.add(timeLabel8);
        list.add(nameLabel8);
        list.add(timeLabel9);
        list.add(nameLabel9);
        list.add(timeLabel10);
        list.add(nameLabel10);

        try {
            dataBaseController.showScores(difficulty, list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDifficulty(String difficulty) {
        HighScoresController.difficulty = difficulty;
    }

    @FXML
    void resetScores() throws SQLException {
        dataBaseController.resetScores(difficulty);
    }

    @FXML
    void exit() {
        System.out.println("Closing High Scores Window...");
        stage.close();
    }


}
