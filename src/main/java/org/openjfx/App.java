package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    public static Stage st;

    {
        st = new Stage();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        st.setScene(scene);
        st.setResizable(false);
        st.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void openNewScene(String window) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(window+".fxml"));
        scene = new Scene(loader.load());
        st.setScene(scene);

    }

    public static void main(String[] args) {
        launch();
    }

}