module com.example.snakegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakegame to javafx.fxml;
    exports com.example.snakegame;
    exports com.example.snakegame.controller;
    opens com.example.snakegame.controller to javafx.fxml;
}