package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.services.DepartmentService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable {
    @FXML
    public TextField txtIdDep;
    @FXML
    public TextField txtNameDep;
    @FXML
    public TextField txtSearchDep;
    @FXML
    public TableColumn<department, Integer> colIdDep;
    @FXML
    public TableColumn<department, String> colNameDep;
    @FXML
    public Button btnReset;
    @FXML
    public Button btnSave;
    @FXML
    private TableView<department> tbDep;
    DepartmentService departmentService = new DepartmentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void saveDepartment(ActionEvent event) throws SQLException {
        department dep = new department();
        String id = txtIdDep.getText();
        dep.setName(txtNameDep.getText());

        if (departmentService.isExits(Integer.parseInt(id)) == false) {
            dep.setId(Integer.parseInt(id));
            departmentService.saveDep(dep);
        } else {
            dep.setId(Integer.parseInt(id));
            departmentService.updateDep(Integer.parseInt(id), dep);
        }
        loadData();
    }

    public void loadData() throws SQLException {
        colIdDep.setCellValueFactory(new PropertyValueFactory("id"));
        colNameDep.setCellValueFactory(new PropertyValueFactory("name"));
        this.tbDep.setItems(FXCollections.observableList(departmentService.getDepartments()));
    }

    public void reset() {
        txtIdDep.setText("");
        txtNameDep.setText("");
    }
}


