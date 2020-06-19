package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SchemeController implements Initializable {

    private String title = "Tytuł piosenki";

    @FXML
    public BorderPane mainContent;
    public Button homeButton;
    public Button playlistButton;
    public Button queueButton;
    public Button statisticsButton;
    public Button infoButton;
    public Button exitButton;
    public Button shuffleButton;
    public Button prevButton;
    public Button playButton;
    public Button nextButton;
    public Button repeatButton;
    public Button volumeButton;
    public Button heartButton;
    public Label songTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::switchToMain);
        playlistButton.setOnAction(this::switchToPlaylist);
        queueButton.setOnAction(this::switchToQueue);
        statisticsButton.setOnAction(this::switchToStatistics);
        infoButton.setOnAction(this::showInfo);
        exitButton.setOnAction(this::exit);
        shuffleButton.setOnAction(this::shuffle);
        prevButton.setOnAction(this::prev);
        playButton.setOnAction(this::play);
        nextButton.setOnAction(this::next);
        repeatButton.setOnAction(this::repeat);
        volumeButton.setOnAction(this::volume);
        heartButton.setOnAction(this::addToFavourite);
        setSongTitle();
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

    @FXML
    private void shuffle(ActionEvent event) {
        System.out.println("shuffle");
    }

    @FXML
    private void prev(ActionEvent event) {
        System.out.println("prev");
    }

    @FXML
    private void play(ActionEvent event) {
        System.out.println("play");
    }

    @FXML
    private void next(ActionEvent event) {
        System.out.println("next");
    }

    @FXML
    private void repeat(ActionEvent event) {
        System.out.println("repeat");
    }

    @FXML
    private void volume(ActionEvent event) {
        System.out.println("volume");
    }

    @FXML
    private void addToFavourite(ActionEvent event) {
        System.out.println("heart");
    }

    @FXML
    private void setSongTitle() {
        songTitle.setText(title);
    }

    @FXML
    private void showInfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("jakies informacje o programie");
        alert.showAndWait();
    }
}
