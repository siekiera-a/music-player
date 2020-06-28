package app;

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

    /**
     * Search (filtered) elements in list
     */
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

    /**
     * Create first playlist
     */
    public void setPlaylist1List() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 0; i < 100; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /**
     * Create second playlist
     */
    public void setPlaylist2List() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 0; i < 80; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /**
     * Create third playlist
     */
    public void setPlaylist3List() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 0; i < 60; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /**
     * Create forth playlist
     */
    public void setPlaylist4List() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 0; i < 30; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /**
     * Create favourite playlist
     */
    public void setFavouriteList() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 0; i < 14; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /**
     * Create queue
     */
    public void setQueueList() {
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 5; i < 100; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /**
     * Create collection of songs
     */
    public void setCollection(){
        playlistData.clear();
        // tworzenie początkowej listy piosenek
        for (int i = 0; i < 1000; i++) {
            playlistData.add(String.valueOf(i));
        }
        playlistView.setItems(playlistData);
    }

    /*public void upWithIndex(int index) {

    }*/

    /*public void downWithIndex(int index) {

    }*/

}