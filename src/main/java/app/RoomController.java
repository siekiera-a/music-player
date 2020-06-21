package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomController implements Initializable {

    int peopleCount = 0;
    String id;

    @FXML
    public Button joinButton;
    public Button streamButton;
    public Button disconnectButton;

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
    private void stream(ActionEvent actionEvent){
        System.out.println("Stream");
    }

    @FXML
    private void joinToStream(ActionEvent actionEvent){
        System.out.println("Join");
        getId();
        System.out.println(id);
    }

    @FXML
    private void disconnect(ActionEvent actionEvent){
        System.out.println("Disconnect");
    }

}
