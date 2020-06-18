package app;

import javafx.fxml.FXML;

import java.io.IOException;

public class QueueController {

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void switchToPlaylist() throws IOException {
        App.setRoot("playlist");
    }

    @FXML
    private void switchToStatistics() throws IOException {
        App.setRoot("statistics");
    }
}
