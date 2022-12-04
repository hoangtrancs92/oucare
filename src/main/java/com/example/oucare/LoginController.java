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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
         u = USs.getUserById(txtUsername.getText(), passwordField.getText());
        System.out.println(u.getEmail());
        System.out.println(txtUsername.getText());
        System.out.println(u.getPassword());
        System.out.println(AES.encrypt(passwordField.getText(), secretKey));
        if(u.getEmail().equals(txtUsername.getText()) && u.getPassword().equals(AES.encrypt(passwordField.getText(), secretKey)) ){
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
            System.out.println("sai sai");
        }
        System.out.println(USs.getUserById(txtUsername.getText(), passwordField.getText()));
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
