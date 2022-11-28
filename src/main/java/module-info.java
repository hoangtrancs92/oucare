module com.example.oucare {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oucare to javafx.fxml;
    exports com.example.oucare;
}