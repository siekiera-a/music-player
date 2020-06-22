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

    int peopleCount = 0;
    String id;

    @FXML
    public Button joinButton;
    public Button streamButton;
    public Button disconnectButton;

    public HBox peopleHBox;
    public HBox joinHBox;

    public Label peopleLabel;

    public TextField idTextField;

    public String getId() {
        return id = idTextField.getText();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPeopleLabel(Label peopleLabel) {
        this.peopleLabel = peopleLabel;
        peopleLabel.setText(getPeopleCount());
    }

    public String getPeopleCount() {
        return String.valueOf(peopleCount);
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        joinButton.setOnAction(this::joinToStream);
        streamButton.setOnAction(this::stream);
        disconnectButton.setOnAction(this::disconnect);
        setPeopleLabel(peopleLabel);
    }

    @FXML
    private void stream(ActionEvent actionEvent) {
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(false);
        disconnectButton.setDisable(false);
    }

    @FXML
    private void joinToStream(ActionEvent actionEvent) {
        getId();
        //System.out.println(id);
        streamButton.setDisable(true);
        joinHBox.setDisable(true);
        peopleHBox.setDisable(true);
        disconnectButton.setDisable(false);
    }

    @FXML
    private void disconnect(ActionEvent actionEvent) {
        streamButton.setDisable(false);
        joinHBox.setDisable(false);
        peopleHBox.setDisable(true);
        disconnectButton.setDisable(true);

    }

}
