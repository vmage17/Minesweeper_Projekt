module com.student.minesweeper.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;


    opens student.minesweeper to javafx.fxml;
    exports student.minesweeper;
}