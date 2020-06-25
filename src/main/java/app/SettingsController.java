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

    //TODO: trzeba ustawić domyślną lokalizację i zapisać zmienioną na dysku
    String location = "";
    String password = "";

    @FXML
    public CheckBox startAppCB;
    public Button chooseLocation;
    public Button passwordButton;
    public TextField locationPlaylist;
    public TextField passwordField;

    // pobieranie aktualnej lokalizacji playlist
    public String getLocation() {
        location = locationPlaylist.getText();
        return location;
    }

    // metoda zmieniająca lokalizacje playlist przy naciśnięciu przycisku
    public void chooseLocation(ActionEvent actionEvent) {
        Stage stage = (Stage) chooseLocation.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        setLocation(file.toString());
    }

    // metoda ustawiająca lokalizacje playlist
    public void setLocation(String location) {
        this.location = location;
        locationPlaylist.setText(location);
    }


    //metoda ustawiająca, że aplikacja ma zostać uruchomiona przy starcie systemu
    public void startApp() {
        startAppCB.isSelected();
    }

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startApp();
        chooseLocation.setOnAction(this::chooseLocation);
        passwordButton.setOnAction(this::setPasswordButton);
    }


    private void setPasswordButton(ActionEvent actionEvent) {
        password = passwordField.getText();
    }
}
