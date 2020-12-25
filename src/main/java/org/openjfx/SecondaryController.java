package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField login_field;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private ImageView backId;

    @FXML
    private Tooltip loginexist;


    @FXML
    void initialize() {
        SignUpButton.setOnAction(event -> {

            try {
                if(signUpNewUser())
                App.openNewScene("primary");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        backId.setOnMouseClicked(event ->{
            try {
                App.openNewScene("primary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean signUpNewUser() {
        User user = null;
        DatabaseHandler dbHandler = new DatabaseHandler();
        String firstName = signUpFirstName.getText().trim();
        String lastName = signUpLastName.getText().trim();
        String gender = "";
        String userName = login_field.getText().trim();
        String password = password_field.getText().trim();
        if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не все поля заполнены");
            alert.setContentText("Заполните все поля для регистрации.");
            alert.showAndWait();
        } else {
        if (signUpCheckBoxMale.isSelected() && signUpCheckBoxFemale.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверно указан пол");
            alert.setContentText("Выберите одно значение.");
            alert.showAndWait();
        } else if (!signUpCheckBoxMale.isSelected() && !signUpCheckBoxFemale.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не указан пол");
            alert.setContentText("Выберите одно значение.");
            alert.showAndWait();
        } else if (signUpCheckBoxMale.isSelected()) {
            gender = "Male";
            user = new User(firstName, lastName, gender, userName, password);
            if (dbHandler.singUpUser(user)) {
                return true;
            }
            return false;
        } else {
            gender = "Female";
            user = new User(firstName, lastName, gender, userName, password);
            if (dbHandler.singUpUser(user)) {
                return true;
            }
            return false;
        }}
        return false;
    }}

