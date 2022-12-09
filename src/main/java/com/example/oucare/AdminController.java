package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.model.ticket;
import com.example.oucare.model.user;
import com.example.oucare.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class AdminController implements Initializable {
    @FXML
    private ComboBox<department> departmentChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField name;
    @FXML
    private TableView<ticket> ticketTableView;
    @FXML
    private Button clear;
    @FXML
    private TabPane tb;

    @FXML
    private Button btXoa;

    @FXML
    private TableView<user> userTableView;

    /////////////// Phong ban //////////////////
    @FXML
    private TextField txtIdDep;
    @FXML
    private TextField txtNameDep;
    @FXML
    private TextField txtSearchDep;
    @FXML
    private TableColumn<department, Integer> colIdDep;
    @FXML
    private TableColumn<department, String> colNameDep;
    @FXML
    private Button btnResetDep;
    @FXML
    private Button btnSaveDep;
    @FXML
    private TableView<department> tbDep;

    //////////////// Bac si ////////////
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private TableColumn<user, Integer> colId;
    @FXML
    private ComboBox<String> cbSex;
    @FXML
    private TableColumn<user, String> colName;
    @FXML
    public TableColumn<user, Integer> colSex;
    @FXML
    public TableColumn<user, Date> colBirthday;
    @FXML
    public TableColumn<user, String> colEmail;
    @FXML
    public TableColumn<user, String> colPhone;
    @FXML
    public TableColumn<user, String> colAddress;
    @FXML
    public TextField txtSearch;
    @FXML
    public TextField txtPhone;
    public TextField txtName;
    @FXML
    public TextField txtEmail;
    @FXML
    public DatePicker dpBirthday;
    @FXML
    public TextArea txtAddress;
    @FXML
    public ComboBox<department> cbDepartment;
    @FXML
    public Button btnResetDoc;
    @FXML
    public Button btnSaveDoc;
    @FXML
    public Button btnDep;
    @FXML
    public ComboBox<department> filterByDepartment;
    @FXML
    public TextField txtId;
    @FXML
    private TableView<user> tbDoctor;
    @FXML


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
        if (user.getId() != 0) {
        } else System.out.println("dell");
        return user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadGender();
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
        /**
         * Dat
         */
        initForm();
        try {
            loadTableViewDoctor();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DoctorService doctorService = new DoctorService();
        try {
            this.doctors = FXCollections.observableList(doctorService.getDoctors());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.tbDoctor.setItems((ObservableList<user>) this.doctors);

        try {
            loadComboboxDepartment(this.filterByDepartment);

            loadComboboxDepartment(this.cbDepartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.filterByDepartment.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue);

            try {
                filterDoctorByDepartment(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.txtSearch.textProperty().addListener((observableValue, s, t1) -> {
            try {
                searchDoctor(t1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        this.tbDoctor.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super user>) change -> {
            try {
                int id = change.getList().get(0).getId();
                System.out.println("id=" + id);
                updateDoctor(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            loadData();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    private void receiveData(MouseEvent event) {
        // Step 1

    }

    public void loadTableView() {

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
        this.ticketTableView.getColumns().addAll(column1, column2, column3, column4, column5);
    }

    public void changeDataLoading(TicketService ts) throws SQLException {
        this.datePicker.valueProperty().addListener((evt) -> {
            int dp_id = 0;
            if (this.departmentChoiceBox.getValue() != null) {
                dp_id = this.departmentChoiceBox.getValue().getId();
            }
            department dp = new department(dp_id);
            try {
                this.loadDataTable(this.datePicker, dp, this.name.getText(), ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.departmentChoiceBox.valueProperty().addListener((evt) -> {
            int dp_id = 0;
            if (this.departmentChoiceBox.getValue() != null) {
                dp_id = this.departmentChoiceBox.getValue().getId();
            }
            department dp = new department(dp_id);
            System.err.println(dp.getId());
            System.err.println(this.datePicker.getValue());
            try {
                this.loadDataTable(this.datePicker, dp, this.name.getText(), ts);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.name.textProperty().addListener((evt) -> {
            int dp_id = 0;
            if (this.departmentChoiceBox.getValue() != null) {
                dp_id = this.departmentChoiceBox.getValue().getId();
            }
            department dp = new department(dp_id);
            try {
                this.loadDataTable(this.datePicker, dp, this.name.getText(), ts);
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
    public void loadTableUser() {
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


        this.userTableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);


    }

    public void loadTableDataUser(String kw) throws SQLException {
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

    public void delete(ActionEvent event) {
        int selectedID = userTableView.getSelectionModel().getSelectedIndex();
        userTableView.getItems().remove(selectedID);

    }

    /**
     * Phan cua Dat: quan ly phong ban, quan ly bac si
     */
    //////////// QUAN LY PHONG BAN //////////////////////
    DepartmentService departmentService = new DepartmentService();

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
        this.tbDep.setItems(FXCollections.observableList(departmentService.getDepartment()));
    }

    public void reset() {
        txtIdDep.setText("");
        txtNameDep.setText("");
    }

    ///////////////////////// Quan ly bac si ////////////////////////
    List<user> doctors = new ArrayList<>();

    public void loadTableViewDoctor() throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
    }

    public void loadComboboxDepartment(ComboBox<department> comboBox) throws SQLException {
        DepartmentService departmentService = new DepartmentService();
        List<department> departments = departmentService.getDepartment();
        comboBox.setItems(FXCollections.observableList(departments));
    }

    public void loadGender() {
        ObservableList<String> listGender = FXCollections.observableArrayList();
        listGender.add("Nam");
        listGender.add("Nữ");
        listGender.add("Khác");
        cbSex.setItems(listGender);
    }

    public void filterDoctorByDepartment(department department) throws SQLException {
        DoctorService doctorService = new DoctorService();
        this.doctors = FXCollections.observableList(doctorService.getDoctors(department));
        this.tbDoctor.setItems((ObservableList<user>) this.doctors);
    }

    public void searchDoctor(String keyword) throws SQLException {
        DoctorService doctorService = new DoctorService();
        this.doctors = FXCollections.observableList(doctorService.getDoctors(keyword));
        this.tbDoctor.setItems((ObservableList<user>) this.doctors);
        System.out.println(keyword);
    }

    public void initForm() {
        cbDepartment.promptTextProperty().setValue("Thuộc phòng ban");
        txtName.textProperty().setValue("");
        dpBirthday.setValue(LocalDate.now());
        txtPhone.textProperty().setValue("");
        txtEmail.textProperty().setValue("");
        txtAddress.textProperty().setValue("");
    }

    public void resetForm(ActionEvent event) {
        initForm();
    }

    public void saveDoctor(ActionEvent event) throws SQLException {
        DoctorService doctorService = new DoctorService();
        user doctor = new user();
        String id;
        doctor.setName(txtName.getText());
        doctor.setAddress(txtAddress.getText());
        doctor.setEmail(txtEmail.getText());
        doctor.setBirthday(Date.valueOf(dpBirthday.getValue()));
        String text = this.cbSex.getSelectionModel().getSelectedItem();
        int sex;
        if (Objects.equals(text, "Nữ")) {
            sex = 1;
        } else if (Objects.equals(text, "Nam")) {
            sex = 0;
        } else {
            sex = 3;
        }
        doctor.setSex(sex);
        doctor.setId_role(2);
        doctor.setId_department(cbDepartment.getSelectionModel().getSelectedItem().getId());
        doctor.setPassword(AES.encrypt("123456", "12345678"));
        doctor.setPhone(txtPhone.getText());
        doctorService.saveDoctor(doctor);
        this.tbDoctor.setItems(FXCollections.observableList(doctorService.getDoctors()));

    }

    public void updateDoctor(int id) throws SQLException {
        DoctorService doctorService = new DoctorService();
        user doctorDB = doctorService.getDoctor(id);
        this.cbDepartment.getItems().forEach(department -> {
            if (department.getId() == doctorDB.getId_department()) {
                cbDepartment.getSelectionModel().select(department);
            }
        });
        this.cbSex.getItems().forEach(s -> {
            if (doctorDB.getSex() == 1) {
                System.out.println("sex= " + s);
                cbDepartment.getSelectionModel().select(1);
            } else {
                cbDepartment.getSelectionModel().select(2);
            }
        });

        LocalDate date = LocalDate.parse(doctorDB.getBirthday().toString());
        // this.txtId.setText(String.valueOf(id));
        this.dpBirthday.setValue(date);
        this.txtName.setText(doctorDB.getName());
        this.txtEmail.setText(doctorDB.getEmail());
        this.txtAddress.setText(doctorDB.getAddress());
        this.txtPhone.setText(doctorDB.getPhone());

        System.out.println(doctorDB);
    }
}