package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.model.ticket;
import com.example.oucare.model.time;
import com.example.oucare.model.user;
import com.example.oucare.services.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
public class BookingController  implements Initializable {

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
            System.err.println(user);
        }
        else
            System.err.println("dell");
        return user;
    }
    TicketService tks = new TicketService();
    Integer idphongban = 0;
    LocalDateTime now = LocalDateTime.now();
    @FXML
    private ComboBox<department> departmentComboBox;
    @FXML
    private ComboBox<user> userComboBox;
    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker datePicker1;
    @FXML
    private ComboBox<time> time_comboBox;
    @FXML
    private ComboBox<department> departmentListBox;
    @FXML private TextField name;
    @FXML private TitledPane tb;
    @FXML private TitledPane list;
    @FXML private TableView<ticket> ticketTableView;
    @FXML private Button bookingBtn;
    int user_id;
    int role_id;
    int check = 0;
    LocalDate choose_date = LocalDate.now();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DepartmentService Dps = new DepartmentService();
        DoctorService Dc = new DoctorService();
        TicketService tks = new TicketService();
        TimeService ts = new TimeService();
        try {
            datePicker.setDisable(true);
            bookingBtn.setDisable(true);
            this.departmentComboBox.setItems(FXCollections.observableList(Dps.getDepartment()));
            this.departmentListBox.setItems(FXCollections.observableList(Dps.getDepartment()));
            this.userComboBox.setItems(FXCollections.observableList(Dc.getDoctor(idphongban)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        departmentComboBox.setButtonCell(new ListCell<department>(){
            @Override
            protected void updateItem(department item, boolean btl){
                super.updateItem(item, btl);
                if(item != null) {
                    setText(item.toString());
                    idphongban = item.getId();
                    try {
                        userComboBox.setItems(FXCollections.observableList(Dc.getDoctor(idphongban)));
                        bookingBtn.setDisable(true);
                        userComboBox.setDisable(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        userComboBox.valueProperty().addListener((evt)->{
            datePicker.setDisable(false);
            if (datePicker.getValue() != null){
                bookingBtn.setDisable(false);
            }
        });
        datePicker.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                LocalDate date = datePicker.getValue();
                if (date.isAfter(ChronoLocalDate.from(now)) == true ){
                    choose_date = date;
                    try {
                        time_comboBox.setItems(FXCollections.observableList(ts.getTime()) );
                        time_comboBox.setDisable(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    datePicker.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Bạn không thể chọn ngày nhỏ hơn ngày hiện tại");
                    alert.setHeaderText("Lỗi thời gian");
                    alert.show();
                    bookingBtn.setDisable(true);
                }
                System.err.println(time_comboBox.getValue());
                if(time_comboBox.getValue() == null)
                    bookingBtn.setDisable(true);
            }
        });
        this.tb.setOnMouseClicked((event) -> {
            if(check == 0){
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                // Step 2
                user u = (user) stage.getUserData();
                // Step 3
                user_id = u.getId();
                role_id = u.getId_role();
                check++;
                try {
                    this.loadDataTable(null, user_id, tks);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        this.list.setOnMouseClicked((event) -> {
            if(check == 0){
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                // Step 2
                user u = (user) stage.getUserData();
                // Step 3
                user_id = u.getId();
                role_id = u.getId_role();
                check++;
                try {
                    this.loadDataTable(null, user_id, tks);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        time_comboBox.valueProperty().addListener((evt)->{
            try {
                if(time_comboBox.getValue() != null){
                    if(tks.checkQuantityTicket(time_comboBox.getValue().getTime(), userComboBox.getValue().getId(), datePicker.getValue().toString()) == false){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setContentText("Giờ này đã được đặt");
                        alert.setHeaderText("Vui lòng chọn giờ khác");
                        alert.show();
                        time_comboBox.getSelectionModel().clearSelection();
                        bookingBtn.setDisable(true);
                    }
                    else
                        bookingBtn.setDisable(false);
                }
                else
                    bookingBtn.setDisable(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        departmentListBox.valueProperty().addListener((evt)->{
            int dp_id = 0;
            TicketService tks_2 = new TicketService();
            if(this.departmentListBox.getValue() != null){
                dp_id = this.departmentListBox.getValue().getId();
            }
            try {
                ticketTableView.setItems(FXCollections.observableList(tks_2.getTicketByDepartment(user_id, dp_id)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        this.loadTableView();
    }
    public void loadDataTable(DatePicker chooseDay, int user_id, TicketService ts) throws SQLException {
        this.ticketTableView.setItems(FXCollections.observableList(ts.getTicketsForUser(chooseDay, user_id)));
    }
    public void BookingButton(ActionEvent event) throws SQLException, IOException {
        ticket t = new ticket(String.valueOf(choose_date), String.valueOf(time_comboBox.getValue().getTime()), user_id, userComboBox.getValue().getId());
        TicketService tcS = new TicketService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Xác nhân");
        alert.setContentText("Bạn có chắc chắn đặt lịch ?");
        alert.setHeaderText("Xác nhận đặt lịch");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            tcS.addTickets(t);
            departmentComboBox.getSelectionModel().clearSelection();
            userComboBox.getSelectionModel().clearSelection();
            time_comboBox.getSelectionModel().clearSelection();
            datePicker.setValue(null);
            datePicker.setDisable(true);
            bookingBtn.setDisable(true);
            time_comboBox.setDisable(true);
            userComboBox.setDisable(true);
            try {
                this.loadDataTable(null, user_id, tks);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
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
        TableColumn column5 = new TableColumn("Khoa khám");
        column5.setCellValueFactory(new PropertyValueFactory("department_name"));
        column5.setPrefWidth(150);
        column5.setStyle("-fx-alignment: CENTER");
        this.ticketTableView.getColumns().addAll(column1,column2,column3,column4,column5);
    }

}
