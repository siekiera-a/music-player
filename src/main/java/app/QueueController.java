package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import app.playlist.Queue;
import app.playlist.Song;

import java.net.URL;
import java.util.ResourceBundle;

public class QueueController implements Initializable {

    ListManager listManager;

    @FXML
    public ListView<String> queueView;
    public TextArea queueSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManager(queueView, queueSearch);
        listManager.setTestList();
    }
}
