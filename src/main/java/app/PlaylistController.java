package app;

import app.playlist.Playlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PlaylistController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManagerSong listManager;

    private final Store store = App.getStore();

    @FXML
    public ListView<String> playlistView;
    public TextArea playlistSearch;
    public ComboBox<String> choosePlaylists;


    /**
     * Default initialize method
     * Create list of playlist with songs
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = store.getPlaylists()
            .stream()
            .map(Playlist::getName)
            .collect(Collectors.toList());

        ObservableList<String> observableList = FXCollections.observableList(list);
        choosePlaylists.getItems().clear();
        choosePlaylists.setItems(observableList);

        listManager = new ListManagerSong(playlistView, playlistSearch);
        // ustawienie początkowej statystyki
        if (choosePlaylists.getItems().size() > 0) {
            choosePlaylists.setValue(choosePlaylists.getItems().get(0));
        }

        choosePlaylists.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) ->
            listManager.setPlaylist(store.getPlaylist(newValue))
        );
    }

}
