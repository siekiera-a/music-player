package app;

import app.playlist.Playlist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class QueueController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManagerSong listManager;
    private final Store store = App.getStore();
    @FXML
    public ListView<String> queueView;
    public TextArea queueSearch;

    /**
     * Default initialize method
     * Create a queue list
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManagerSong(queueView, queueSearch);
        // wyświetlenie konkretnej listy na ekranie
        store.subscribeQueueChange(listManager::setPlaylist);
        listManager.setPlaylist(new Playlist("Queue", store.getQueue()));
    }
}
