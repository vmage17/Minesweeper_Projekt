module com.student.minesweeper.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.student.minesweeper to javafx.fxml;
    exports com.student.minesweeper;
}