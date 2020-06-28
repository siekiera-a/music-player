package app;

import app.playlist.Playlist;
import app.playlist.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ListManagerSong {
    ObservableList<String> playlistData = FXCollections.observableArrayList();
    ObservableList<String> searchedData = FXCollections.observableArrayList();

    public ListView<String> playlistView;
    public TextArea playlistSearch;

    ListManagerSong(ListView<String> playlistView, TextArea playlistSearch) {
        this.playlistView = playlistView;
        this.playlistSearch = playlistSearch;

        playlistView.setCellFactory(x -> new CustomCellSongs(this));
        setSearchListener();
    }

    public void setList(ObservableList<String> newPlaylist) {
        playlistData = newPlaylist;
        playlistView.setItems(playlistData);
    }

    protected void setSearchListener() {
        playlistSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                playlistView.setItems(playlistData);
            } else {
                ObservableList<String> items = FXCollections.observableArrayList();
                for (String x : playlistData) {
                    if (x.contains(newValue)) {
                        items.add(x);
                    }
                }
                searchedData = items;
                playlistView.setItems(searchedData);
            }
        });
    }

    public void setPlaylist(Playlist playlist) {
        playlistData.clear();
        if (playlist != null) {
            playlist.getSongs().stream()
                    .map(Song::getTitle)
                    .forEach(playlistData::add);
        }

        playlistView.setItems(playlistData);
    }

    public void setQueueList() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 5; i < 100; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }


}