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

    // zmienna odpowiedzialna za hasło komputera
    String password;

    @FXML
    public Button joinButton;
    public Button passwordButton;
    public Button streamButton;
    public Button disconnectButton;

    public HBox peopleHBox;
    public HBox passwordHBox;
    public HBox joinHBox;

    public Label peopleLabel;

    public TextField idTextField;
    public TextField passwordField;

    /**
     * Get password and put it in field
     *
     * @return
     */
    public String getPassword() {
        return password = passwordField.getText();
    }

    /**
     * Set password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get id of computer to join
     *
     * @return
     */
    public String getId() {
        return id = idTextField.getText();
    }

    /**
     * show in label how many people is on stream
     *
     * @param peopleLabel
     */
    public void setPeopleLabel(Label peopleLabel) {
        this.peopleLabel = peopleLabel;
        peopleLabel.setText(getPeopleCount());
    }

    /**
     * Get how many people is on stream
     *
     * @return
     */
    public String getPeopleCount() {
        return String.valueOf(peopleCount);
    }


    /**
     * Default initialize method
     *
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        joinButton.setOnAction(this::joinToStream);
        streamButton.setOnAction(this::stream);
        passwordButton.setOnAction(this::passwordToStream);
        disconnectButton.setOnAction(this::disconnect);
        setPeopleLabel(peopleLabel);
    }

    /**
     * Turn on stream function
     *
     * @param actionEvent
     */
    @FXML
    private void stream(ActionEvent actionEvent) {
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(false);
        disconnectButton.setDisable(false);
    }

    /**
     * Detect other computer id to join someone stream
     *
     * @param actionEvent
     */
    @FXML
    private void joinToStream(ActionEvent actionEvent) {
        getId();
        //System.out.println(id);
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(true);
        passwordHBox.setDisable(false);
        disconnectButton.setDisable(true);
    }

    /**
     * Check room password to join someone stream
     *
     * @param actionEvent
     */
    @FXML
    private void passwordToStream(ActionEvent actionEvent) {
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(true);
        passwordHBox.setDisable(true);
        disconnectButton.setDisable(false);
    }

    /**
     * Turn off stream or join to other stream
     *
     * @param actionEvent
     */
    @FXML
    private void disconnect(ActionEvent actionEvent) {
        streamButton.setDisable(false);
        joinHBox.setDisable(false);
        peopleHBox.setDisable(true);
        disconnectButton.setDisable(true);

    }

}
