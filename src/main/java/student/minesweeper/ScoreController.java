package student.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ScoreController {

    public static DataBaseController dataBaseController;
    public static Stage stage;

    @FXML
    private TextField name;

    @FXML
    void saveScoreBeginner() throws SQLException {
        System.out.println("Name saved! Your name is " + name.getText());
        dataBaseController.setNewHighScore(name.getText());
        stage.close();
    }

}
