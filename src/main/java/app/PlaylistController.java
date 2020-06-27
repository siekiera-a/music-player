package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManagerSong listManager;

    // zmienna odpowiedzialna za index w ComboBox
    int index;

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
        List<String> list = new ArrayList<String>();
        //nowe playlisty
        list.add(0, "Playlista 1");
        list.add(1, "Playlista 2");
        list.add(2, "Playlista 3");
        list.add(3, "Playlista 4");
        list.add(4, "Ulubione");

        ObservableList<String> observableList = FXCollections.observableList(list);
        choosePlaylists.getItems().clear();
        choosePlaylists.setItems(observableList);

        listManager = new ListManagerSong(playlistView, playlistSearch);

        // ustawienie początkowej statystyki
        choosePlaylists.setValue(choosePlaylists.getItems().get(0));

        // wyświetlenie konkretnej listy na ekranie dla początkowej statystyki
        listManager.setPlaylist1List();

        choosePlaylists.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            index = observableList.indexOf(newValue);
            //System.out.println(index);
            //System.out.println(newValue);
            if (index == -1) {
                return;
            }
            if (index == 0) {
                // wyświetlenie konkretnej listy na ekranie dla pierwszej playlisty
                listManager.setPlaylist1List();
            } else if (index == 1) {
                // wyświetlenie konkretnej listy na ekranie dla drugiej playlisty
                listManager.setPlaylist2List();
            } else if (index == 2) {
                // wyświetlenie konkretnej listy na ekranie dla trzeciej playlisty
                listManager.setPlaylist3List();
            } else if (index == 3) {
                // wyświetlenie konkretnej listy na ekranie dla trzeciej playlisty
                listManager.setPlaylist4List();
            } else if (index == 4) {
                // wyświetlenie konkretnej listy na ekranie dla ulubionych
                listManager.setFavouriteList();
            }

        });
    }

}
