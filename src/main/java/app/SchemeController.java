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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SchemeController implements Initializable {

    private String title = "Tytuł piosenki";
    boolean isPaused = false;
    boolean isFavourite = false;
    int atime = 74;
    int etime = 230;
    //MediaPlayer mediaPlayer;
    //Media media;

    @FXML
    public Slider volumeSlider;
    public Slider songSlider;
    //private MediaView mediaView;
    public Label actual_time;
    public Label end_time;
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
    public FontAwesomeIcon heart_icon;
    public FontAwesomeIcon play_icon;
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

        loadFXML("main");
        setSongTitle();
        songSlider.setValue(atime);
        songSlider.setMax(etime);
        songSlider.setMin(0);
        actual_time.setText(getAtime());
        end_time.setText(getEtime());

        songSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setAtime(newValue.intValue());
            actual_time.setText(getAtime());
        });
    }

    public String getAtime() {
        return atime/60 +  ":" + (atime%60);
    }

    public void setAtime(int atime) {
        this.atime = atime;
    }

    public String getEtime() {
        return etime/60 + ":" + (etime%60);
    }

    public void setEtime(int etime) {
        this.etime = etime;
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
        //System.out.println("play");
        isPaused = !isPaused;
        if (isPaused)
            play_icon.setGlyphName("PAUSE");
        else
            play_icon.setGlyphName("PLAY");
        //mediaPlayer.play();
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
        isFavourite = !isFavourite;
        if(isFavourite){
            heart_icon.setFill(Color.RED);
        }
        else
        {
            heart_icon.setFill(Color.BLACK);
        }
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
