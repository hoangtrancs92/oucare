package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.model.ticket;
import com.example.oucare.model.user;
import com.example.oucare.services.TicketService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorController implements Initializable {
    @FXML
    private TableView<ticket> ticketTableView;
    @FXML private Button clear;
    int user_id;
    int role_id;
    int check = 0;
    @FXML private TabPane tb;
    @FXML private DatePicker datePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TicketService ts = new TicketService();
        this.loadTableView();
        this.tb.setOnMouseClicked((event) -> {
            if(check == 0){
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                // Step 2
                user u = (user) stage.getUserData();
                // Step 3
                user_id = u.getId();
                role_id = u.getId_role();
                System.err.println(user_id);
                System.err.println(role_id);
                check++;
                try {
                    this.loadDataTable(null, user_id,ts);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            this.changeDataLoading(ts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.clearValue(ts);
    }
    public void changeDataLoading(TicketService ts) throws SQLException {
        this.datePicker.valueProperty().addListener((evt) -> {

            try {
                this.loadDataTable(this.datePicker, user_id,ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void loadTableView(){
        TableColumn column1 = new TableColumn("Mã vé");
        column1.setCellValueFactory(new PropertyValueFactory("id"));
        column1.setPrefWidth(120);
        column1.setStyle("-fx-alignment: CENTER");
        TableColumn column2 = new TableColumn("Ngày khám");
        column2.setCellValueFactory(new PropertyValueFactory("date_start"));
        column2.setPrefWidth(270);
        column2.setStyle("-fx-alignment: CENTER");
        TableColumn column3 = new TableColumn("Thời gian");
        column3.setCellValueFactory(new PropertyValueFactory("time_start"));
        column3.setPrefWidth(130);
        column3.setStyle("-fx-alignment: CENTER");
        TableColumn column5 = new TableColumn("Tên Khách hàng");
        column5.setCellValueFactory(new PropertyValueFactory("customer_name"));
        column5.setPrefWidth(280);
        column5.setStyle("-fx-alignment: CENTER");
        this.ticketTableView.getColumns().addAll(column1,column2,column3,column5);
    }
    public void loadDataTable(DatePicker chooseDay, int user_id, TicketService ts) throws SQLException {
        this.ticketTableView.setItems(FXCollections.observableList(ts.getTicketsForDoctor(chooseDay, user_id)));
    }
    public void clearValue(TicketService ts){
        this.clear.setOnAction((evt) -> {
            this.datePicker.setValue(null);
            try {
                this.loadDataTable(null, user_id,ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
