package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.services.TicketService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLTicketController implements Initializable {
    @FXML private ChoiceBox<department> departmentChoiceBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TicketService ts = new TicketService();
        this.departmentChoiceBox.setItems(FXCollections.observableList(ts.getDepartments()));
    }
}
