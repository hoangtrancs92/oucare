module com.example.oucare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires org.junit.jupiter.api;

    opens com.example.oucare to javafx.fxml;
    exports com.example.oucare;
    exports com.example.oucare.model;

}