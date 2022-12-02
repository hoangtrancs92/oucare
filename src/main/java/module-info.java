module com.example.oucare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens com.example.oucare to javafx.fxml;
    exports com.example.oucare;
}