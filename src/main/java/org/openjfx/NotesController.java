package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NotesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button save;

    @FXML
    private ImageView notes;

    @FXML
    private TextArea text;

    @FXML
    private ImageView back;

    @FXML
    void initialize() {
        DatabaseHandler db = new DatabaseHandler();
        ResultSet resultSet1 = null;
        String select = "SELECT text FROM notes WHERE ulogin='"+PrimaryController.activateUser+"'";
        try {
            PreparedStatement prSt1 = db.getDbConnection().prepareStatement(select);
            resultSet1 = prSt1.executeQuery();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            resultSet1.next();
            text.setText(resultSet1.getString(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        back.setOnMouseClicked(even -> {
            try {
                App.openNewScene("tertiary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        save.setOnAction(even ->{
            DatabaseHandler db1 = new DatabaseHandler();
            String old="";
            ResultSet resSet = null;
            String s = text.getText();
            String select1 = "SELECT text FROM notes WHERE ulogin='"+PrimaryController.activateUser+"'";
            try {
                PreparedStatement prSt1 = db1.getDbConnection().prepareStatement(select1);
                resSet = prSt1.executeQuery();

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            try {
                resSet.next();
                old = resSet.getString(1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String SQL = "UPDATE notes SET text=? WHERE text=?";
            try {
                PreparedStatement prst = db.getDbConnection().prepareStatement(SQL);
                prst.setString(1, s);
                prst.setString(2, old);
                prst.executeUpdate();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            ResultSet resultSet = null;
            try {
                PreparedStatement prSt1 = db.getDbConnection().prepareStatement(select);
                resultSet = prSt1.executeQuery();

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            //text.clear();
            try {
                resultSet.next();
                text.setText(resultSet.getString(1));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

}
