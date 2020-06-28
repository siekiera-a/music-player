package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Stage stage2;
    private static Stage st;

    private static final Store store = new Store();

    public static Store getStore() {
        return store;
    }

    /**
     * Start method
     * Create stages
     *
     * @param stage
     * @throws Exception
     */

    @Override
    public void start(Stage stage) throws Exception {
        // ustawienie okna głównego aplikacji
        Scene scene = new Scene(loadFXML("scheme"), 640, 480);
        stage.setTitle("Odtwarzacz muzyki");
        stage.setScene(scene);
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        //ustawienie overlaya po minimalizacji okna głownego
        st = new Stage();
        Scene scene2 = new Scene(loadFXML("overlay"), 200, 100);
        st.setAlwaysOnTop(true);
        st.setX(15);
        st.setY(15);
        st.setScene(scene2);
        st.setResizable(false);
        scene2.setFill(Color.TRANSPARENT);
        st.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        stage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if (st.isShowing()) {
                st.hide();
            } else {
                st.show();
            }
            store.sceneChange();
        });
        stage2 = stage;
    }

    public static Stage getInstance() {
        return stage2;
    }

    /**
     * Load the application view
     *
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Run app
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}