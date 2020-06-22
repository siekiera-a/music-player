package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
//        if (stage.isIconified()){
//            stage.initStyle(StageStyle.TRANSPARENT);
//            stage.setAlwaysOnTop(true);
//            stage.setScene(new Scene(loadFXML("overlay"),300,300));
//        }
//        else{
//            stage.setScene(new Scene(loadFXML("scheme"), 640, 480));
//        }
        stage.setScene(new Scene(loadFXML("scheme"), 640, 480));
        stage.setTitle("Odtwarzacz muzyki");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource( fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}