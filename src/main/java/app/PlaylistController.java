package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    ListManager listManager;

    @FXML
    public ListView<String> playlistView;
    public TextArea playlistSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManager(playlistView,playlistSearch);
        listManager.setTestList();
    }
}
