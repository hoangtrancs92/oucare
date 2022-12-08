package com.example.oucare;

import com.example.oucare.services.UserService;
import com.example.oucare.model.user;
import com.example.oucare.services.AES;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
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
    private Pattern regexPattern;
    private Matcher regMatcher;
    LocalDateTime now = LocalDateTime.now();
    LocalDate choose_date = LocalDate.now();
    public Boolean validateEmailAddress(String emailAddress) {

        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher   = regexPattern.matcher(emailAddress);
        if(regMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkpassword(String password) {
        int passwordLength=8, upChars=0, lowChars=0;
        int special=0, digits=0;
        char ch;

        int total = password.length();
        if(total<passwordLength)
        {
            return false;
        }
        else
        {
            for(int i=0; i<total; i++)
            {
                ch = password.charAt(i);
                if(Character.isUpperCase(ch))
                    upChars = 1;
                else if(Character.isLowerCase(ch))
                    lowChars = 1;
                else if(Character.isDigit(ch))
                    digits = 1;
                else
                    special = 1;
            }
        }
        if(upChars==1 && lowChars==1 && digits==1 && special==1)
            return true;
        else
            System.out.println("\nThe Password is Weak.");
        return false;
    }
    //Chuyển đến trang đăng ký
    public void switchToLogin(ActionEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegisterBtn.setDisable(true);
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        rButton1.setSelected(true);
        txtBirthday.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                LocalDate date = txtBirthday.getValue();
                if (date.isBefore(ChronoLocalDate.from(now.minusYears(16))) == true ){
                    RegisterBtn.setDisable(false);
                    choose_date = date;
                }
                else{
                    txtBirthday.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Trên 16 tuổi mới được đăng ký tài khoản");
                    alert.setHeaderText("Vui lòng không đăng ký");
                    alert.show();
                }
            }
        });
    }
    public void buttonRegister (ActionEvent event) throws SQLException, ParseException, IOException {
        final String secretKey = "12345678";
        String password = "";
        UserService Uss = new UserService();
        String name = usernameField.getText();
        String email = EmailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        LocalDate birthday = txtBirthday.getValue();
        Date date = Date.valueOf(birthday);
        int sex ;

        if(rButton1.isSelected()){ //Nam
            sex = 0;
        }else if(rButton2.isSelected()){ // Nữ
            sex = 1;
        }else sex = 3; // Khác
        if(usernameField.getText() == "" || EmailField.getText() == "" || phoneField.getText() == "" || address == "" && date == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chưa nhập đầy đủ thông tin");
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.show();
        }
        if(usernameField.getText() != "" && EmailField.getText() != "" && phoneField.getText() != "" && address != null && date != null){
            if(validateEmailAddress(EmailField.getText()) == true){
                if(Uss.getEmailUser(EmailField.getText()) == null){
                    if(checkpassword( passwordField.getText()) == true){
                        password = passwordField.getText();
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
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Sai định dạng mật khẩu");
                        alert.setContentText("Mật khẩu phải dài trên 8 kí tự\nCó một kí tự hoa\nCó một kí tự thường\n có một số\n có một kí tự đặc biệt");
                        alert.setHeaderText("Sai");
                        alert.show();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Email đã được sử dụng");
                    alert.setHeaderText("Email đã được sử dụng");
                    alert.setContentText("Vui lòng chọn email khác");
                    alert.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sai định dạng email");
                alert.setContentText("Sai định dạng email. Vui lòng sửa lại");
                alert.show();
            }
        }


    }
}
