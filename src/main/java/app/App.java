package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage st;
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("scheme"), 640, 480);
        stage.setTitle("Odtwarzacz muzyki");
        stage.setScene(scene);
        st = new Stage();
        Scene scene2 = new Scene(loadFXML("overlay"), 160, 130);
        st.setAlwaysOnTop(true);
        st.setX(120);
        st.setY(20);
        st.setScene(scene2);
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if (st.isShowing()) {
                st.hide();
            } else {
                st.show();
            }
        });
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