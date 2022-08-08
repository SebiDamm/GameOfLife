module com.example.gameoflife {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameoflife to javafx.fxml;
    exports com.example.gameoflife;
}