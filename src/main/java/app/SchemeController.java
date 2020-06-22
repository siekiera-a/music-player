package app;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SchemeController implements Initializable {

    private String title = "Tytuł piosenki";
    boolean isPaused = false;
    boolean isMute = false;
    boolean isFavourite = false;
    int actual_volume = 100;
    int prev_volume;
    int actualTime = 74; //w sekundach
    int endTime = 230; //w sekundach

    @FXML
    public Slider volumeSlider;
    public Slider songSlider;

    public Label volumeValue;
    public Label actual_time;
    public Label end_time;
    public Label songTitle;

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
    public Button devicesButton;
    public Button roomButton;
    public Button settingsButton;

    public FontAwesomeIcon heart_icon;
    public FontAwesomeIcon play_icon;
    public FontAwesomeIcon volume_icon;

    public void init() {
        setSongTitle();
        manageSongSlider();
        manageVolumeSlider();
    }

    public void manageVolumeSlider() {
        volumeSlider.setValue(actual_volume);
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeValue.setText(getActual_volume());

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setActual_volume(newValue.intValue());
            volumeValue.setText(getActual_volume());
            if (isMute && newValue.intValue() > 0) {
                isMute = false;
                volume_icon.setGlyphName("VOLUME_UP");
            } else if (!isMute && newValue.intValue() == 0) {
                isMute = true;
                volume_icon.setGlyphName("VOLUME_OFF");
            }
        });
    }

    public void manageSongSlider() {
        songSlider.setValue(actualTime);
        songSlider.setMax(endTime);
        songSlider.setMin(0);
        actual_time.setText(getActualTime());
        end_time.setText(getEndTime());

        songSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setActualTime(newValue.intValue());
            actual_time.setText(getActualTime());
        });
    }

    public String getActual_volume() {
        return String.valueOf(actual_volume);
    }

    public void setActual_volume(int actual_volume) {
        this.actual_volume = actual_volume;
    }

    public String getActualTime() {
        return actualTime / 60 + ":" + (actualTime % 60);
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public String getEndTime() {
        return endTime / 60 + ":" + (endTime % 60);
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
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
        heartButton.setOnAction(this::addToFavourite);
        devicesButton.setOnAction(this::changeOutputDevice);
        volumeButton.setOnAction(this::volume);
        roomButton.setOnAction(this::switchToRoom);
        settingsButton.setOnAction(this::switchToSettings);

        loadFXML("main");
        init();
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

    @FXML
    private void switchToRoom(ActionEvent event) {
        loadFXML("room");
    }

    @FXML
    private void switchToSettings(ActionEvent event) {
        loadFXML("settings");
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
        isPaused = !isPaused;
        if (isPaused)
            play_icon.setGlyphName("PAUSE");
        else
            play_icon.setGlyphName("PLAY");
    }

    @FXML
    private void volume(ActionEvent event) {
        isMute = !isMute;
        if (isMute) {
            prev_volume = actual_volume;
            actual_volume = 0;
            volume_icon.setGlyphName("VOLUME_OFF");
        } else {
            actual_volume = prev_volume;
            volume_icon.setGlyphName("VOLUME_UP");
        }
        volumeValue.setText(getActual_volume());
        volumeSlider.setValue(actual_volume);
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
    private void addToFavourite(ActionEvent event) {
        isFavourite = !isFavourite;
        if (isFavourite) {
            heart_icon.setFill(Color.RED);
        } else {
            heart_icon.setFill(Color.BLACK);
        }
    }

    @FXML
    private void changeOutputDevice(ActionEvent event) {
        System.out.println("devices");
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
