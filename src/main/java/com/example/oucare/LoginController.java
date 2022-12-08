package com.example.oucare;

import com.example.oucare.services.AES;
import com.example.oucare.model.user;
import com.example.oucare.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginController {
    @FXML
    private Label LoginMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;
    public void LoginButton(ActionEvent event) throws SQLException, IOException {
        final String secretKey = "12345678";

        user u ;
        UserService USs = new UserService();
         u = USs.getUserByEmail(txtUsername.getText());
        if(u.getEmail().equals(txtUsername.getText()) && u.getPassword().equals(AES.encrypt(passwordField.getText(), secretKey)) ){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("hello-view.fxml"));
            stage.setUserData(USs.getUserById(u.getId()));
            Parent userViewParent = loader.load();
            Scene scene = new Scene(userViewParent);
            AdminController controller = loader.getController();
            controller.getUser(USs.getUserById(u.getId()));
            if(u.getId_role() == 1)
            {
                fxmlLoader = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            } else if(u.getId_role() == 2) {
                fxmlLoader = FXMLLoader.load(getClass().getResource("doctor.fxml"));

            } else {
                fxmlLoader = FXMLLoader.load(getClass().getResource("customer.fxml"));
            }

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(u);
            scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi đăng nhập");
            alert.setHeaderText("Sai email hoặc mật khẩu");
            alert.show();
        }
    }

    //Chuyển đến trang đăng ký
    public void switchToRegister(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

}
