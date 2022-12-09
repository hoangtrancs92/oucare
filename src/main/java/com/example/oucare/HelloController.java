package com.example.oucare;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.example.oucare.model.department;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.oucare.services.DepartmentService;
import com.example.oucare.model.department;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    ComboBox<department> departmentComboBox;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DepartmentService tm = new DepartmentService();
        try {
            this.departmentComboBox.setItems(FXCollections.observableList(tm.getDepartment()));
        } catch (SQLException e) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, (String) null);
        }
    }
}