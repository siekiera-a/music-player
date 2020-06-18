package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SchemeController implements Initializable {

    @FXML
    public BorderPane mainContent;
    public Button homeButton;
    public Button playlistButton;
    public Button queueButton;
    public Button statisticsButton;
    public Button infoButton;
    public Button exitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::switchToMain);
        playlistButton.setOnAction(this::switchToPlaylist);
        queueButton.setOnAction(this::switchToQueue);
        statisticsButton.setOnAction(this::switchToStatistics);
        infoButton.setOnAction(this::showInfo);
        exitButton.setOnAction(this::exit);
    }

    @FXML
    private void switchToMain(ActionEvent event) {
        loadFXML("main");
    }

    @FXML
    private void switchToPlaylist(ActionEvent event) {
        loadFXML("playlist");
    }

    @FXML
    private void switchToQueue(ActionEvent event) {
        loadFXML("queue");
    }

    @FXML
    private void switchToStatistics(ActionEvent event) {
        loadFXML("statistics");
    }

    @FXML
    private void showInfo(ActionEvent event) {
        
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) mainContent.getScene().getWindow();
        stage.close();
    }

    private void loadFXML(String fxml) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
            mainContent.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
