package com.example.oucare;

import com.example.oucare.model.department;
import com.example.oucare.model.user;
import com.example.oucare.services.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class BookingController  implements Initializable {

    Integer idphongban = 0;
    @FXML
    private ComboBox<department> departmentComboBox;
    @FXML
    private ComboBox<user> userComboBox;
    @FXML
    private DatePicker datePicker;
//    @FXML
//    private ComboBox<time> time_comboBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DepartmentService Dps = new DepartmentService();
        DoctorService Dc = new DoctorService();
        try {
            this.departmentComboBox.setItems(FXCollections.observableList(Dps.getDepartment()));
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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(idphongban);

            }

        });
//        userComboBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                userComboBox.setItems(FXCollections.observableList(Dc.getDoctor(idphongban)));
//            }
//        });
    }
    public void BookingButton(ActionEvent event) throws SQLException, IOException {

    }
}
