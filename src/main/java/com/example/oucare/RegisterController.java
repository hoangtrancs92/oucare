package com.example.oucare;

import com.example.oucare.services.UserService;
import com.example.oucare.model.user;
import com.example.oucare.services.AES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class RegisterController {
    @FXML
    TextField usernameField;
    @FXML TextField EmailField;
    @FXML TextField addressField;
    @FXML TextField phoneField;
    @FXML PasswordField passwordField;
    @FXML
    Button RegisterBtn;
    @FXML
    DatePicker txtBirthday;
    @FXML
    RadioButton rButton1;
    @FXML RadioButton rButton2;
    @FXML RadioButton rButton3;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent fxmlLoader;
    ToggleGroup tg = new ToggleGroup();

    //Chuyển đến trang đăng ký
    public void switchToLogin(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }
    public void buttonRegister (ActionEvent event) throws SQLException, ParseException, IOException {
        final String secretKey = "12345678";

        String name = usernameField.getText();
        String password = passwordField.getText();
        String email = EmailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        LocalDate birthday = txtBirthday.getValue();
        System.out.println(birthday);
        Date date = Date.valueOf(birthday);

        int sex;
        if(rButton1.isSelected()){ //Nam
            sex = 0;
        }else if(rButton2.isSelected()){ // Nữ
            sex = 1;
        }else sex = 3; // Khác
        password = AES.encrypt(password,secretKey);
        user u = new user(name,email,password,phone,address,date,sex);
        UserService userSer = new UserService();
        userSer.addUsers(u);
        fxmlLoader = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }
}
