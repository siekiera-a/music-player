package app;

import app.settings.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    String locationPlaylists;
    String locationSongs;
    private final Settings settings = App.getStore().getSettings();

    public Button playlistButton;
    public Button songButton;

    public TextField playlistLocationField;
    public TextField songLocationField;


    /**
     * Change playlists search directory
     *
     * @param actionEvent
     */
    public void chooseLocationPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) playlistButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            setPlaylistLocation(file.toString());
        }
    }

    /**
     * Change songs search directory
     *
     * @param actionEvent
     */
    public void chooseLocationSongs(ActionEvent actionEvent) {
        Stage stage = (Stage) songButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            setSongsLocation(file.toString());
        }
    }

    /**
     * Change playlist's save directory and set in playlists localization text field
     *
     * @param directory where system will save playlists
     */
    public void setPlaylistLocation(String directory) {
        this.locationPlaylists = directory;
        settings.setPlaylistDirectory(Path.of(directory));
        playlistLocationField.setText(locationPlaylists);
    }

    /**
     * Change songs search directory and set in songs localization text field
     *
     * @param directory where system will search songs
     */
    public void setSongsLocation(String directory) {
        this.locationSongs = directory;
        settings.setMusicLocation(Paths.get(directory));
        songLocationField.setText(locationSongs);
    }

    /**
     * Set user's settings on screen in Text fields
     */
    public void showSettings() {
        songLocationField.setText(locationSongs);
        playlistLocationField.setText(locationPlaylists);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        locationSongs = settings.getMusicDirectory().toAbsolutePath().toString();
        locationPlaylists = settings.getPlaylistDirectory().toAbsolutePath().toString();
        showSettings();
        playlistButton.setOnAction(this::chooseLocationPlaylist);
        songButton.setOnAction(this::chooseLocationSongs);
    }
}
