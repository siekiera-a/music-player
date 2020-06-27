package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    String locationPlaylists = System.getProperty("user.music");
    String locationSongs = System.getProperty("user.music");
    String password = "room";

    @FXML
    public CheckBox startAppCB;

    public Button chooseLocation;
    public Button passwordButton;
    public Button chooseLocationSongs;

    public TextField locationPlaylist;
    public TextField locationSong;
    public TextField passwordField;


    /**
     * Get location of playlists
     *
     * @return
     */
    public String getLocationPlaylist() {
        locationPlaylists = locationPlaylist.getText();
        return locationPlaylists;
    }

    /**
     * Get location of songs
     *
     * @return
     */
    public String getLocationSongs() {
        locationSongs = locationSong.getText();
        return locationSongs;
    }

    /**
     * Change location of playlist
     *
     * @param actionEvent
     */
    public void chooseLocationPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) chooseLocation.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        setLocationPlaylist(file.toString());
    }

    /**
     * Change location of songs
     *
     * @param actionEvent
     */
    public void chooseLocationSongs(ActionEvent actionEvent) {
        Stage stage = (Stage) chooseLocationSongs.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        setLocationSongs(file.toString());
    }

    /**
     * Set location of playlist and put in textfield
     *
     * @param location
     */
    public void setLocationPlaylist(String location) {
        this.locationPlaylists = location;
        locationPlaylist.setText(location);
    }

    /**
     * Set location of songs and put in textfield
     *
     * @param location
     */
    public void setLocationSongs(String location) {
        this.locationSongs = location;
        locationSong.setText(location);
    }

    /**
     * Application will run when system starts;
     */
    public void startApp() {
        startAppCB.isSelected();
    }

    /**
     * Default initialize method
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startApp();
        chooseLocation.setOnAction(this::chooseLocationPlaylist);
        chooseLocationSongs.setOnAction(this::chooseLocationSongs);
        passwordButton.setOnAction(this::setPasswordButton);
    }

    /**
     * Create a password to the room
     *
     * @param actionEvent
     */
    private void setPasswordButton(ActionEvent actionEvent) {
        password = passwordField.getText();
    }
}
