package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.model.ticket;
import com.example.oucare.model.user;
import com.example.oucare.services.TicketService;
import com.example.oucare.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;


import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML private ComboBox<department> departmentChoiceBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField name;
    @FXML private TableView<ticket> ticketTableView;
    @FXML private Button clear;
    @FXML private TabPane tb;

    @FXML private Button btXoa;

    @FXML private TableView<user> userTableView;



    int user_id;
    int role_id;
    int check = 0;
    private LocalDate dp;

    user user = new user();

    public user getUser(user u) {
        user.setId(u.getId());
        user.setName(u.getName());
        user.setAddress(u.getAddress());
        user.setSex(u.getSex());
        user.setEmail(u.getEmail());
        user.setBirthday((Date) u.getBirthday());
        user.setPhone(u.getPhone());
        user.setId_role(u.getId_role());
        if(user.getId() != 0) {
        }
        else
            System.out.println("dell");
        return user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TicketService ts = new TicketService();
        this.loadTableView();
        try {
            this.departmentChoiceBox.setItems(FXCollections.observableList(ts.getDepartments()));
            this.loadDataTable(null, new department(0, "null"), null, ts);
            this.tb.setOnMouseClicked((event) -> {
                if (check == 0) {
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
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            this.changeDataLoading(ts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.clearValue(ts);


        //trien
        this.loadTableUser();
        UserService us = new UserService();
        try {
            this.loadTableDataUser(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    @FXML
    private void receiveData(MouseEvent event) {
        // Step 1

    }
    public void loadTableView(){

        TableColumn column1 = new TableColumn("Mã vé");
        column1.setCellValueFactory(new PropertyValueFactory("id"));
        column1.setPrefWidth(110);
        column1.setStyle("-fx-alignment: CENTER");
        TableColumn column2 = new TableColumn("Ngày khám");
        column2.setCellValueFactory(new PropertyValueFactory("date_start"));
        column2.setPrefWidth(250);
        column2.setStyle("-fx-alignment: CENTER");
        TableColumn column3 = new TableColumn("Thời gian");
        column3.setCellValueFactory(new PropertyValueFactory("time_start"));
        column3.setPrefWidth(110);
        column3.setStyle("-fx-alignment: CENTER");
        TableColumn column4 = new TableColumn("Tên Bác Sĩ");
        column4.setCellValueFactory(new PropertyValueFactory("doctor_name"));
        column4.setPrefWidth(220);
        column4.setStyle("-fx-alignment: CENTER");
        TableColumn column5 = new TableColumn("Tên Khách hàng");
        column5.setCellValueFactory(new PropertyValueFactory("customer_name"));
        column5.setPrefWidth(220);
        column5.setStyle("-fx-alignment: CENTER");
        this.ticketTableView.getColumns().addAll(column1,column2,column3,column4,column5);
    }
    public void changeDataLoading(TicketService ts) throws SQLException {
        this.datePicker.valueProperty().addListener((evt) -> {
            int dp_id = 0;
            if(this.departmentChoiceBox.getValue() != null){
                dp_id = this.departmentChoiceBox.getValue().getId();
            }
            department dp = new department(dp_id);
            try {
                this.loadDataTable(this.datePicker, dp, this.name.getText(),ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.departmentChoiceBox.valueProperty().addListener((evt)->{
            int dp_id = 0;
            if(this.departmentChoiceBox.getValue() != null){
                dp_id = this.departmentChoiceBox.getValue().getId();
            }
            department dp = new department(dp_id);
            System.err.println(dp.getId());
            System.err.println(this.datePicker.getValue());
            try {
                this.loadDataTable(this.datePicker, dp, this.name.getText(),ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.name.textProperty().addListener((evt)->{
            int dp_id = 0;
            if(this.departmentChoiceBox.getValue() != null){
                dp_id = this.departmentChoiceBox.getValue().getId();
            }
            department dp = new department(dp_id);
            try {
                this.loadDataTable(this.datePicker, dp, this.name.getText(),ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


//        try {
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
    public void loadDataTable(DatePicker chooseDay, department category, String name, TicketService ts) throws SQLException {
        this.ticketTableView.setItems(FXCollections.observableList(ts.getTickets(chooseDay, category, name)));
    }
    public void clearValue(TicketService ts) {
        this.clear.setOnAction((evt) -> {
            this.datePicker.setValue(null);
            this.departmentChoiceBox.setItems(FXCollections.observableList(ts.getDepartments()));
            this.name.setText("");
            try {
                this.loadDataTable(null, new department(0, "null"), null, ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }




    /////////////// Phan cua Trien //////////////////////

    public void loadTableUser(){
        TableColumn col1 = new TableColumn("Mã KH");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(100);
        col1.setStyle("-fx-alignment: CENTER");

        TableColumn col2 = new TableColumn("Tên Khách hàng");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(100);
        col2.setStyle("-fx-alignment: CENTER");

        TableColumn col3 = new TableColumn("Email");
        col3.setCellValueFactory(new PropertyValueFactory("email"));
        col3.setPrefWidth(100);
        col3.setStyle("-fx-alignment: CENTER");

        TableColumn col4 = new TableColumn("Số điện thoại");
        col4.setCellValueFactory(new PropertyValueFactory("phone"));
        col4.setPrefWidth(100);
        col4.setStyle("-fx-alignment: CENTER");

        TableColumn col5 = new TableColumn("Địa chỉ");
        col5.setCellValueFactory(new PropertyValueFactory("address"));
        col5.setPrefWidth(110);
        col5.setStyle("-fx-alignment: CENTER");

        TableColumn col6 = new TableColumn("Năm sinh");
        col6.setCellValueFactory(new PropertyValueFactory("birthday"));
        col6.setPrefWidth(90);
        col6.setStyle("-fx-alignment: CENTER");

        TableColumn col7 = new TableColumn("Giới tính");
        col7.setCellValueFactory(new PropertyValueFactory("sex"));
//        if( Integer.parseInt(col7.getText()) == 0){
//            col7.setText("Nam");
//        }
        col7.setPrefWidth(80);
        col7.setStyle("-fx-alignment: CENTER");




        this.userTableView.getColumns().addAll(col1,col2,col3,col4,col5,col6,col7);


    }

    public void loadTableDataUser(String kw) throws SQLException{
        UserService u = new UserService();

        this.userTableView.setItems(FXCollections.observableList(u.getUserByName(kw)));
    }

    /*public void add (ActionEvent event){
        user u = new user(txtName.getText(),
                txtEmail.getText());
        ObservableList<user> users = userTableView.getItems();
        users.add(u);
        userTableView.setItems(users);

    }*/

    public void delete(ActionEvent event){
    int selectedID = userTableView.getSelectionModel().getSelectedIndex();
    userTableView.getItems().remove(selectedID);

    }



}
