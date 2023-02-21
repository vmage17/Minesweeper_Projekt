package com.student.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneController implements Initializable{

    public static final int BEGINNER_HEIGHT = 8;
    public static final int BEGINNER_WIDTH = 8;
    public static final int BEGINNER_BOMBS = 10;
    public static final int INTERMEDIATE_HEIGHT = 16;
    public static final int INTERMEDIATE_WIDTH = 16;
    public static final int INTERMEDIATE_BOMBS = 40;
    public static final int EXPERT_HEIGHT = 16;
    public static final int EXPERT_WIDTH = 30;
    public static final int EXPERT_BOMBS = 99;

    public static Stage stage = new Stage();
    public static Parent root;
    private Scene scene;
    private static Grid grid;

    private static String difficulty = "beginner";

    public static Image concerned = new Image("file:src/main/resources/assets/face_concerned.png");
    public static Image cool = new Image("file:src/main/resources/assets/face_cool.png");
    public static Image smile = new Image("file:src/main/resources/assets/face_smile.png");
    public static Image dead = new Image("file:src/main/resources/assets/face_dead.png");

    @FXML
    private GridPane gridPane;
    @FXML
    public Label bombsLeft;
    @FXML
    public ImageView smileImageView;
    @FXML
    public Label timeElapsed;

    public SceneController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("initialized with " + difficulty.toUpperCase());
        //if (grid == null) System.out.println("grid is null again!! wtf");
        //scene = new Scene(root);
        //stage.setScene(scene);
        //stage.show();

        grid = new Grid(BEGINNER_HEIGHT, BEGINNER_WIDTH, BEGINNER_BOMBS);
        grid.setNumbers();

        bombsLeft.setText("0" + BEGINNER_BOMBS);
    }

    @FXML
    public void onClick() throws IOException {
        System.out.println("smile clicked");
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        // use existing window here, don't create a new one:
        File file = fileChooser.showOpenDialog(Defaultview.getScene().getWindow());
        if (file != null) {
            Defaultview.setImage(new Image(file.toURI().toString()));
        }*/

        grid = new Grid(BEGINNER_HEIGHT, BEGINNER_WIDTH, BEGINNER_BOMBS);
        grid.setNumbers();

        bombsLeft.setText("0" + BEGINNER_BOMBS);

        //stage = (Stage)smileImageView.getScene().getWindow();
        setScene();

    }

    public void setScene() throws IOException {
        System.out.println("Scene set");
        root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/" + difficulty + ".fxml"))));
        //MenuItem menuItem = (MenuItem)event.getTarget();
        //stage = (Stage)menuItem.getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setDifficulty(String difficulty) {
        SceneController.difficulty = difficulty;
    }

    @FXML
    public void setCurrentDifficulty() throws IOException {
        System.out.println("Setting " + difficulty);
        switch (difficulty) {
            case "beginner" -> setBeginnerDifficulty();
            case "intermediate" -> setIntermediateDifficulty();
            case "expert" -> setExpertDifficulty();
        }
    }

    @FXML
    public void setBeginnerDifficulty() throws IOException {
        System.out.println("Beginner selected");
        setDifficulty("beginner");

        grid = new Grid(BEGINNER_HEIGHT, BEGINNER_WIDTH, BEGINNER_BOMBS);
        grid.setNumbers();

        bombsLeft.setText("0" + BEGINNER_BOMBS);

        setScene();
    }

    @FXML
    public void setIntermediateDifficulty() throws IOException {
        System.out.println("Intermediate selected");
        setDifficulty("intermediate");

        grid = new Grid(INTERMEDIATE_HEIGHT, INTERMEDIATE_WIDTH, INTERMEDIATE_BOMBS);
        grid.setNumbers();

        bombsLeft.setText("0" + INTERMEDIATE_BOMBS);

        setScene();
    }

    @FXML
    public void setExpertDifficulty() throws IOException {
        System.out.println("Expert selected");
        setDifficulty("expert");

        grid = new Grid(EXPERT_HEIGHT, EXPERT_WIDTH, EXPERT_BOMBS);
        grid.setNumbers();

        bombsLeft.setText("0" + EXPERT_BOMBS);

        setScene();
    }

    @FXML
    public void exit(){
        System.out.println("Closing game");
        stage.close();
    }

    @FXML
    private void mouseEntered(MouseEvent event) {

        if(grid.isGameLost() || grid.isGameWon())
            return;

        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode != gridPane) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);

            //System.out.println("Mouse clicked cell: (" + colIndex + ", " + rowIndex + ")");

            if (event.getButton() == MouseButton.PRIMARY) {
                int indicator = grid.open(gridPane, colIndex, rowIndex, bombsLeft);
                if (indicator == -1) {
                    smileImageView.setImage(dead);
                }
                if (indicator == 1) {
                    smileImageView.setImage(cool);
                    // create new window for player to input name and then add that name to the database
                }
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                grid.flag(gridPane, colIndex, rowIndex, bombsLeft);
            }
        }
    }

    @FXML
    private void setConcerned(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && !grid.isGameWon() && !grid.isGameLost())
            smileImageView.setImage(concerned);
    }

    @FXML
    private void setNormal(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && !grid.isGameWon() && !grid.isGameLost())
            smileImageView.setImage(smile);
    }
}
