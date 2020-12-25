package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.openjfx.animations.Shake;

public class PrimaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField login_field;

    @FXML
    private Button authSignButton;

    @FXML
    private Button loginSignUpButton;
    public static String activateUser = null;

    @FXML
    void initialize() {
        authSignButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();
            if(!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Login and Password is empty!");
            }});

        loginSignUpButton.setOnAction(event -> {
            try {
                App.openNewScene("secondary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void loginUser(String loginText, String loginPassword) throws IOException, SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        try{
            while(result.next()) {
                counter++;
            }
        }catch(SQLException e){
             e.printStackTrace();
            }

        if(counter!=0){
            activateUser = user.getUserName().hashCode() + "hash";
            App.openNewScene("tertiary");
            System.out.println("universal user-login (ulogin): " + activateUser);

        }
        else{
            Shake userLoginAnim = new Shake(login_field);
            Shake userPassAnim = new Shake(password_field);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
        }
        DatabaseHandler db = new DatabaseHandler();
        String insert = "INSERT INTO notes (text,ulogin) VALUES(?,?)";
        try {
            PreparedStatement prSt = db.getDbConnection().prepareStatement(insert);
            prSt.setString(1, "Запишите нужные заметки прямо здесь");
            prSt.setString(2, PrimaryController.activateUser);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Данный ulogin успешно авторизован");
        }
    }
}
