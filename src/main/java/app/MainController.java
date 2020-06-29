package app;

import app.playlist.Playlist;
import app.playlist.Song;
import app.settings.Settings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManagerSong listManager;

    @FXML
    public ListView<String> mainView;
    public TextArea mainSearch;

    private final Settings settings = App.getStore().getSettings();

    /**
     * Default initialize method
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManagerSong(mainView, mainSearch);
        // wyświetlenie konkretnej listy na ekranie

        try (var files = Files.walk(settings.getMusicDirectory(), 1)) {
            ArrayList<Song> songs = new ArrayList<>();
            for (Path p : files.filter(Files::isRegularFile).collect(Collectors.toList())) {
                try {
                    Song song = new Song(p.toString());
                    songs.add(song);
                } catch (IllegalArgumentException e) {
                }
            }
            listManager.setPlaylist(new Playlist("collection", songs));
        } catch (Exception e) {
        }

    }
}
