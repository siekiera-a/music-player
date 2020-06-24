package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomController implements Initializable {

    // zmienna zliczająca ilość osob w streamie
    int peopleCount = 0;

    // zmienna odpowiedzialna za id komputera
    String id;

    @FXML
    public Button joinButton;
    public Button streamButton;
    public Button disconnectButton;

    public HBox peopleHBox;
    public HBox joinHBox;

    public Label peopleLabel;

    public TextField idTextField;


    // pobieranie id komputera
    public String getId() {
        return id = idTextField.getText();
    }

    //ustawianie id komputera
    public void setId(String id) {
        this.id = id;
    }

    // wyswietlenie liczby osob na streamie
    public void setPeopleLabel(Label peopleLabel) {
        this.peopleLabel = peopleLabel;
        peopleLabel.setText(getPeopleCount());
    }

    // pobranie liczby osob na streamie
    public String getPeopleCount() {
        return String.valueOf(peopleCount);
    }

    // ustawienie liczby osob na streamie
    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        joinButton.setOnAction(this::joinToStream);
        streamButton.setOnAction(this::stream);
        disconnectButton.setOnAction(this::disconnect);
        setPeopleLabel(peopleLabel);
    }

    // metoda odpowiedzialna za przycisk stream
    @FXML
    private void stream(ActionEvent actionEvent) {
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(false);
        disconnectButton.setDisable(false);
    }

    // metoda odpowiedzialna za przycisk dołącz
    @FXML
    private void joinToStream(ActionEvent actionEvent) {
        getId();
        //System.out.println(id);
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(true);
        disconnectButton.setDisable(false);
    }

    // metoda odpowiedzialna za przycisk rozłącz
    @FXML
    private void disconnect(ActionEvent actionEvent) {
        streamButton.setDisable(false);
        joinHBox.setDisable(false);
        peopleHBox.setDisable(true);
        disconnectButton.setDisable(true);

    }

}
