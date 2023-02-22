module com.student.minesweeper.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;


    opens com.student.minesweeper to javafx.fxml;
    exports com.student.minesweeper;
}