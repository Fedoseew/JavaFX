package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TertiaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView events;

    @FXML
    private ImageView notes;

    @FXML
    private ImageView contacts;

    @FXML
    private Label tertiaryLabel;

    @FXML
    private ImageView backId1;

    @FXML
    void initialize() {
        contacts.setOnMouseClicked(event ->{
            try {
                App.openNewScene("contacts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        notes.setOnMouseClicked(event ->{
            try {
                App.openNewScene("notes");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        events.setOnMouseClicked(event ->{
            try {
                App.openNewScene("events");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backId1.setOnMouseClicked(event ->{
            try {
                App.openNewScene("primary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
