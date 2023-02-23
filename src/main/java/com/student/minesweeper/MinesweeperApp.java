package com.student.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class MinesweeperApp extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        DataBaseController dataBaseController = new DataBaseController();

        //dataBaseController.resetScores("beginner");
        //dataBaseController.resetScores("intermediate");
        //dataBaseController.resetScores("expert");

        SceneController.dataBaseController = dataBaseController;
        HighScoresController.dataBaseController = dataBaseController;
        ScoreController.dataBaseController = dataBaseController;

        SceneController.stage.setTitle("Minesweeper");
        Image icon  = new Image("file:src/main/resources/assets/bomb.jpg");
        SceneController.stage.getIcons().add(icon);

        SceneController.root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/fxml_files/beginner.fxml"))));
        SceneController.stage.setScene(new Scene(SceneController.root));
        SceneController.stage.setResizable(false);
        SceneController.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}