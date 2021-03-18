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

    private final Store store = App.getStore();

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
        store.startStream();
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
        store.connect(idTextField.getText());
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(true);
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
        store.disconnect();
    }

}
