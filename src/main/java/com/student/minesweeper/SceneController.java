package com.student.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    public static final double SCALE = 1;

    public static Stage stage = new Stage();
    private Scene scene;
    private Parent root;
    private static Grid grid;

    private static String difficulty = "beginner";

    @FXML
    private GridPane gridPane;
    @FXML
    private Label bombsLeft;
    @FXML
    private ImageView smileImageView;
    @FXML
    private Label timeElapsed;

    public SceneController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("initialized with ");
        System.out.println(difficulty.toUpperCase());
        if (grid == null)
            System.out.println("grid is null again!! wtf");
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

        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/" + difficulty + ".fxml"))));
        stage = (Stage)smileImageView.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setScene(ActionEvent event) throws IOException {
        System.out.println("Scene set");
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/" + difficulty + ".fxml"))));
        MenuItem menuItem = (MenuItem)event.getTarget();
        stage = (Stage)menuItem.getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setDifficulty(String difficulty) {
        SceneController.difficulty = difficulty;
    }

    @FXML
    public void setCurrentDifficulty(ActionEvent event) throws IOException {
        System.out.println("Setting " + difficulty);
        switch (difficulty) {
            case "beginner" -> setBeginnerDifficulty(event);
            case "intermediate" -> setIntermediateDifficulty(event);
            case "expert" -> setExpertDifficulty(event);
        }
    }

    @FXML
    public void setBeginnerDifficulty(ActionEvent event) throws IOException {
        System.out.println("Beginner selected");
        setDifficulty("beginner");

        grid = new Grid(BEGINNER_HEIGHT, BEGINNER_WIDTH, BEGINNER_BOMBS);
        grid.setNumbers();

        setScene(event);
    }

    @FXML
    public void setIntermediateDifficulty(ActionEvent event) throws IOException {
        System.out.println("Intermediate selected");
        setDifficulty("intermediate");

        grid = new Grid(INTERMEDIATE_HEIGHT, INTERMEDIATE_WIDTH, INTERMEDIATE_BOMBS);
        grid.setNumbers();

        setScene(event);
    }

    @FXML
    public void setExpertDifficulty(ActionEvent event) throws IOException {
        System.out.println("Expert selected");
        setDifficulty("expert");

        grid = new Grid(EXPERT_HEIGHT, EXPERT_WIDTH, EXPERT_BOMBS);
        grid.setNumbers();

        setScene(event);
    }

    @FXML
    public void exit(ActionEvent event){
        System.out.println("Closing game");
        stage.close();
    }

    @FXML
    private void mouseEntered(MouseEvent event) {

        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode != gridPane) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);

            //System.out.println("Mouse clicked cell: (" + colIndex + ", " + rowIndex + ")");

            if (event.getButton() == MouseButton.PRIMARY)
                grid.open(gridPane, colIndex, rowIndex);
            else if (event.getButton() == MouseButton.SECONDARY)
                grid.flag(gridPane, colIndex, rowIndex);
        }

        /*
        Node source = (Node)e.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
        grid.open(colIndex, rowIndex);
        if (e. getButton() == MouseButton.PRIMARY)
            grid.open(colIndex, rowIndex);
        else if (e.getButton() == MouseButton.SECONDARY)
            grid.flag(colIndex, rowIndex);
         */
    }

    //public void setScene(Pane root) {stage.setScene(createContent());}
    /*
    private Parent createContent() {
        Pane root = new Pane();

        root.setPrefSize(10,10);//HEIGHT, WIDTH);

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbours(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }

        return root;
    }*/
}
