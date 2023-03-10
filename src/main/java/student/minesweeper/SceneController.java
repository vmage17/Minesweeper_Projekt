package student.minesweeper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneController implements Initializable{

    public static final int BEGINNER_HEIGHT = 8;
    public static final int BEGINNER_WIDTH = 8;
    public static final int BEGINNER_BOMBS = 10;
    public static final int INTERMEDIATE_HEIGHT = 16;
    public static final int INTERMEDIATE_WIDTH = 16;
    public static final int INTERMEDIATE_BOMBS = 40;
    public static final int EXPERT_HEIGHT = 30;
    public static final int EXPERT_WIDTH = 16;
    public static final int EXPERT_BOMBS = 99;

    public static Stage stage = new Stage();
    public static Parent root;
    public static DataBaseController dataBaseController;
    private static Grid grid;

    private static String difficulty = "beginner";

    private long startTime = 0;
    private Timeline timeline;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized with " + difficulty);

        switch (difficulty) {
            case "beginner" -> {
                grid = new Grid(BEGINNER_WIDTH, BEGINNER_HEIGHT, BEGINNER_BOMBS);
                bombsLeft.setText("0" + BEGINNER_BOMBS);
            }
            case "intermediate" -> {
                grid = new Grid(INTERMEDIATE_WIDTH, INTERMEDIATE_HEIGHT, INTERMEDIATE_BOMBS);
                bombsLeft.setText("0" + INTERMEDIATE_BOMBS);
            }
            case "expert" -> {
                grid = new Grid(EXPERT_WIDTH, EXPERT_HEIGHT, EXPERT_BOMBS);
                bombsLeft.setText("0" + EXPERT_BOMBS);
            }
        }

        grid.setNumbers();
    }

    @FXML
    public void onClick() throws IOException {
        System.out.println("Smile clicked");
        setScene();
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

        setScene();
    }

    @FXML
    public void setIntermediateDifficulty() throws IOException {
        System.out.println("Intermediate selected");
        setDifficulty("intermediate");

        setScene();
    }

    @FXML
    public void setExpertDifficulty() throws IOException {
        System.out.println("Expert selected");
        setDifficulty("expert");

        setScene();
    }

    public void setScene() throws IOException {
        System.out.println("Scene set");
        root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/fxml_files/" + difficulty + ".fxml"))));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void showBeginnerHighScores() throws IOException {
        setHighScoresScene("beginner");
    }

    @FXML
    public void showIntermediateHighScores() throws IOException {
        setHighScoresScene("intermediate");
    }

    @FXML
    public void showExpertHighScores() throws IOException {
        setHighScoresScene("expert");
    }

    public void setHighScoresScene(String difficulty) throws IOException {
        HighScoresController.setDifficulty(difficulty);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_files/high_scores.fxml"));
        Parent rootHighScores = fxmlLoader.load();
        HighScoresController.stage = new Stage();
        HighScoresController.stage.setTitle("Minesweeper");
        HighScoresController.stage.getIcons().add(new Image("file:src/main/resources/assets/bomb.jpg"));
        HighScoresController.stage.setScene(new Scene(rootHighScores));
        HighScoresController.stage.setResizable(false);
        HighScoresController.stage.show();
    }

    @FXML
    public void exit(){
        System.out.println("Closing game");
        dataBaseController.closeConnection();
        stage.close();
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

    @FXML
    private void mouseEntered(MouseEvent event) throws IOException, SQLException {

        if(grid.isGameLost() || grid.isGameWon())
            return;

        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode != gridPane) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);

            if (event.getButton() == MouseButton.PRIMARY) {
                if (startTime == 0)
                    setTimer();
                int indicator = grid.open(gridPane, colIndex, rowIndex, bombsLeft);
                if (indicator == -1) {
                    smileImageView.setImage(dead);
                    stopTimer();
                }
                if (indicator == 1) {
                    smileImageView.setImage(cool);
                    winSequence();
                }
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                grid.flag(gridPane, colIndex, rowIndex, bombsLeft);
            }
        }
    }

    private void winSequence() throws IOException, SQLException {
        int score = stopTimer();
        if (dataBaseController.checkIfNewHighScore(difficulty, ((float) score)/1000))
            showSaveScoreWindow();
    }

    private void showSaveScoreWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml_files/save_score_" + difficulty + ".fxml"));
        Parent rootSaveScore = fxmlLoader.load();
        ScoreController.stage = new Stage();
        ScoreController.stage.setTitle("Minesweeper");
        ScoreController.stage.getIcons().add(new Image("file:src/main/resources/assets/bomb.jpg"));
        ScoreController.stage.setScene(new Scene(rootSaveScore));
        ScoreController.stage.setResizable(false);
        ScoreController.stage.show();
    }

    public void setTimer() {
        startTime = System.currentTimeMillis();

        IntegerProperty counter = new SimpleIntegerProperty(0);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1000), new KeyValue(counter, 1000)));
        timeElapsed.textProperty().bind(Bindings.createStringBinding(() -> getTimeString(counter.get()), counter));
        timeline.play();
    }

    public String getTimeString(Integer time) {
        if (time < 10)
            return "00" + time;
        if (time < 100)
            return "0" + time;
        if (time < 1000)
            return Integer.toString(time);
        return "999";
    }

    public int stopTimer() {
        timeline.stop();
        long endTime = System.currentTimeMillis();
        int timeElapsed = (int)(endTime-startTime);
        startTime = 0;
        return timeElapsed;
    }
}
