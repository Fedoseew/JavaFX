package org.openjfx;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException,SQLException{
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public boolean singUpUser(User user){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + ","+
                Const.USERS_GENDER + ","+ Const.USERS_USERNAME + "," + Const.USERS_PASSWORD+ ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,user.getFirstName());
            prSt.setString(2,user.getLastName());
            prSt.setString(3,user.getGender());
            prSt.setString(4,user.getUserName());
            prSt.setString(5,user.getPassword());
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пользователь с таким логином уже существует");
            alert.setContentText("Попробуйте ввести другой логин.");
            alert.showAndWait();
            return false;
        }
        return  true;
    }
    public ResultSet getUser(User user) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " =? AND " + Const.USERS_PASSWORD + " =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,user.getUserName());
            prSt.setString(2,user.getPassword());
            resSet = prSt.executeQuery();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void addContacts(Contacts contacts) {
        String insert = "INSERT INTO " + Const.CONTACTS_TABLE + "(" + Const.CONTACTS_NAME + "," +
                Const.CONTACTS_PHONE + "," + Const.CONTACTS_EMAIL + "," + Const.CONTACTS_USER + ")" + "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,contacts.getName());
            prSt.setString(2,contacts.getPhone());
            prSt.setString(3,contacts.getEmail());
            prSt.setString(4,PrimaryController.activateUser);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Контакт с такими данным уже существует");
            alert.setContentText("Убедитесь в правильности набора данных или проверьте список контактов");
            ((Stage) (alert.getDialogPane().getScene().getWindow())).getIcons().add(new Image("Male-Avtar.jpg"));
            ((Stage) (alert.getDialogPane().getScene().getWindow())).initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            System.out.println("Контакт с такими данным уже существует!");
        }
    }
}
