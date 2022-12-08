package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.model.user;
import com.example.oucare.services.DepartmentService;
import com.example.oucare.services.DoctorService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DoctorController implements Initializable {
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    public ComboBox<String> cbSex;
    @FXML
    public TableColumn<user, Integer> colId;
    @FXML
    public TableColumn<user, String> colName;
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
    public Button btnReset;
    @FXML
    public Button btnSave;
    @FXML
    public Button btnDep;
    @FXML
    public ComboBox<department> filterByDepartment;
    @FXML
    public TextField txtId;
    @FXML
    private TableView<user> tbDoctor;


    @FXML
    private Button clear;
    int user_id;
    int role_id;
    int check = 0;
    @FXML
    private TabPane tb;
    @FXML
    private DatePicker datePicker;
    List<user> doctors = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        loadComboboxSex(this.cbSex);

        initForm();
        try {
            loadTableView();
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

    }

    public void loadTableView() throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
    }

//    public void loadComboboxSex(ComboBox<String> comboBox) {
//        List<String> sex = new ArrayList<>();
//        sex.add("Nam");
//        sex.add("Nữ");
//        comboBox.setItems(FXCollections.observableList(sex));
//    }

    public void loadComboboxDepartment(ComboBox<department> comboBox) throws SQLException {
        DepartmentService departmentService = new DepartmentService();
        List<department> departments = departmentService.getDepartments();
        comboBox.setItems(FXCollections.observableList(departments));
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
        String id = txtId.getText();
        doctor.setName(txtName.getText());
        doctor.setAddress(txtAddress.getText());
        doctor.setEmail(txtEmail.getText());
        doctor.setBirthday(Date.valueOf(dpBirthday.getValue()));
        String text = this.cbSex.getSelectionModel().getSelectedItem();
        int sex;
        if (Objects.equals(text, "Nữ")) {
            sex = 2;
        } else {
            sex = 1;
        }
        doctor.setSex(sex);
        doctor.setId_role(2);
        doctor.setId_department(cbDepartment.getSelectionModel().getSelectedItem().getId());
        doctor.setPassword("");
        doctor.setPhone(txtPhone.getText());
        if (id.isEmpty()) {
            doctor.setId((int) (Math.random() * 1000));
            doctorService.saveDoctor(doctor);
        } else {
            doctor.setId(Integer.parseInt(id));
            doctorService.updateDoctor(Integer.parseInt(id), doctor);
        }
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
        this.txtId.setText(String.valueOf(id));
        this.dpBirthday.setValue(date);
        this.txtName.setText(doctorDB.getName());
        this.txtEmail.setText(doctorDB.getEmail());
        this.txtAddress.setText(doctorDB.getAddress());
        this.txtPhone.setText(doctorDB.getPhone());

        System.out.println(doctorDB);
    }
}
