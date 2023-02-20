package com.student.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MinesweeperApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Stage myStage = SceneController.stage;
        myStage.setTitle("Minesweeper");
        Image icon  = new Image("file:src/main/resources/assets/bomb.jpg");
        myStage.getIcons().add(icon);

        //sceneController = new SceneController();
        //sceneController.setCurrentDifficulty();

        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/beginner.fxml"))));
        myStage.setScene(new Scene(root));
        myStage.setResizable(false);
        myStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}