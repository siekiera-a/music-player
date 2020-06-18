package app;

import javafx.fxml.FXML;
import java.io.IOException;

public class MainController {
    @FXML
    private void switchToPlaylist() throws IOException {
        App.setRoot("playlist");
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
