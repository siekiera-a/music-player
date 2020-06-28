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

    //TODO: trzeba ustawić domyślną lokalizację i hasło oraz zapisać zmienione dane na dysku
    String locationPlaylists = System.getProperty("user.music");
    String locationSongs = System.getProperty("user.music");
    String password = "";

    @FXML
    public CheckBox startAppCB;

    public Button chooseLocation;
    public Button passwordButton;
    public Button chooseLocationSongs;

    public TextField locationPlaylist;
    public TextField locationSong;
    public TextField passwordField;

    // pobieranie aktualnej lokalizacji playlist
    public String getLocationPlaylist() {
        locationPlaylists = locationPlaylist.getText();
        return locationPlaylists;
    }

    public String getLocationSongs() {
        locationSongs = locationSong.getText();
        return locationSongs;
    }

    // metoda zmieniająca lokalizacje playlist przy naciśnięciu przycisku
    public void chooseLocationPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) chooseLocation.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        setLocationPlaylist(file.toString());
    }

    public void chooseLocationSongs(ActionEvent actionEvent) {
        Stage stage = (Stage) chooseLocationSongs.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        setLocationSongs(file.toString());
    }

    // metoda ustawiająca lokalizacje playlist
    public void setLocationPlaylist(String location) {
        this.locationPlaylists = location;
        locationPlaylist.setText(location);
    }

    public void setLocationSongs(String location) {
        this.locationSongs = location;
        locationSong.setText(location);
    }


    //metoda ustawiająca, że aplikacja ma zostać uruchomiona przy starcie systemu
    public void startApp() {
        startAppCB.isSelected();
    }

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startApp();
        chooseLocation.setOnAction(this::chooseLocationPlaylist);
        chooseLocationSongs.setOnAction(this::chooseLocationSongs);
        passwordButton.setOnAction(this::setPasswordButton);
    }

    private void setPasswordButton(ActionEvent actionEvent) {
        password = passwordField.getText();
    }
}
