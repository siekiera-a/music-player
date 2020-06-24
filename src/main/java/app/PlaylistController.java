package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManager listManager;

    @FXML
    public ListView<String> playlistView;
    public TextArea playlistSearch;

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManager(playlistView, playlistSearch);
        // wyświetlenie konkretnej listy na ekranie
        listManager.setTestList();
    }
}
