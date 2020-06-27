package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Stage stage2;

    @Override
    public void start(Stage stage) throws Exception {
        // ustawienie okna głównego aplikacji
        Scene scene = new Scene(loadFXML("scheme"), 640, 480);
        stage.setTitle("Odtwarzacz muzyki");
        stage.setScene(scene);
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        //ustawienie overlaya po minimalizacji okna głownego
        Stage st = new Stage();
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
        stage2 = stage;
    }

    public static Stage getInstance() {
        return stage2;
    }

    // metoda ładująca odpowiedni wygląd aplikacji
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource( fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // uruchomienie aplikacji
    public static void main(String[] args) {
        launch(args);
    }
}