package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class PlaylistController {

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void switchToQueue() throws IOException {
        App.setRoot("queue");
    }

    @FXML
    private void switchToStatistics() throws IOException {
        App.setRoot("statistics");
    }
}
