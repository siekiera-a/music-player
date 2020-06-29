package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SchemeController implements Initializable {

    boolean isMute;
    boolean isFavourite;
    boolean isRepeat;
    int prev_volume;

    private final Store store = App.getStore();

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
    public Button exitButton;
    public Button shuffleButton;
    public Button prevButton;
    public Button playButton;
    public Button nextButton;
    public Button repeatButton;
    public Button volumeButton;
    public Button heartButton;
    public Button roomButton;
    public Button settingsButton;

    /**
     * Initialize icons of buttons
     */
    public void initIcons() {
        playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 30, 30, true, true)));
        prevButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/previous.png"), 30, 30, true, true)));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/next.png"), 30, 30, true, true)));
        repeatButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/repeat.png"), 30, 30, true, true)));
        shuffleButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/shuffle.png"), 30, 30, true, true)));
        volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/audio.png"), 30, 30, true, true)));
        homeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/home.png"), 30, 30, true, true)));
        playlistButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/playlist.png"), 30, 30, true, true)));
        queueButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/duration.png"), 30, 30, true, true)));
        statisticsButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/graph.png"), 30, 30, true, true)));
        roomButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/user.png"), 30, 30, true, true)));
        settingsButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/settings.png"), 30, 30, true, true)));
        exitButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/exit.png"), 30, 30, true, true)));
        heartButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 30, 30, true, true)));
    }

    /**
     * Update time
     * @param duration
     */
    private void updateTime(Duration duration) {
        updateTimeUI((int) duration.toSeconds());
    }

    /**
     * Helper method to initialize
     */
    public void init() {
        changePlayIcon(store.isPlayed());
        store.subscribeTimeChange(this::updateTime);
        store.subscribeSceneChange(this::changePlayIcon);
        store.subscribeTitleChange(this::setSongTitle);
        store.subscribeAudioLoaded(this::initDurationSlider);

        initIcons();
        initVolumeSlider();
    }

    /**
     * Initialize volume slider
     */
    public void initVolumeSlider() {
        volumeSlider.setValue(store.getVolume());
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeValue.setText(String.valueOf(store.getVolume()));

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            store.changeVolume(newValue.intValue());
            volumeValue.setText(String.valueOf(store.getVolume()));
            if (isMute && newValue.intValue() > 0) {
                isMute = false;
                volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/audio.png"), 30, 30, true, true)));
            } else if (!isMute && newValue.intValue() == 0) {
                isMute = true;
                volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/mute.png"), 30, 30, true, true)));
            }
        });
    }

    /**
     * Initialize duration slider
     * @param duration
     */
    public void initDurationSlider(Duration duration) {
        songSlider.setMax((int) (duration.toSeconds()));
        songSlider.setMin(0);
        end_time.setText(convertTime((int) (duration.toSeconds())));
        actual_time.setText(convertTime(0));

        songSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // @TODO implements
        });
    }

    /**
     * Update time in UI
     * @param newTime
     */
    public void updateTimeUI(int newTime) {
        actual_time.setText(convertTime(newTime));
        songSlider.setValue(newTime);
    }

    /**
     * Convert time from seconds to minutes and seconds
     * @param time converted time
     * @return
     */
    public static String convertTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    /**
     * Load new view of application
     * @param fxml
     */
    private void loadFXML(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
            mainContent.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Default initialize method
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::switchToMain);
        playlistButton.setOnAction(this::switchToPlaylist);
        queueButton.setOnAction(this::switchToQueue);
        statisticsButton.setOnAction(this::switchToStatistics);
        exitButton.setOnAction(this::exit);
        shuffleButton.setOnAction(this::shuffle);
        prevButton.setOnAction(this::prev);
        playButton.setOnAction(this::play);
        nextButton.setOnAction(this::next);
        repeatButton.setOnAction(this::repeat);
        heartButton.setOnAction(this::addToFavourite);
        volumeButton.setOnAction(this::volume);
        roomButton.setOnAction(this::switchToRoom);
        settingsButton.setOnAction(this::switchToSettings);

        loadFXML("main");
        init();
    }

    /**
     * Switch view to main view
     * @param event
     */
    @FXML
    private void switchToMain(ActionEvent event) {
        loadFXML("main");
    }

    /**
     * Switch view to playlist view
     * @param event
     */
    @FXML
    private void switchToPlaylist(ActionEvent event) {
        loadFXML("playlist");
    }

    /**
     * Switch view to queue view
     * @param event
     */
    @FXML
    private void switchToQueue(ActionEvent event) {
        loadFXML("queue");
    }

    /**
     * Switch view to statistics view
     * @param event
     */
    @FXML
    private void switchToStatistics(ActionEvent event) {
        loadFXML("statistics");
    }

    /**
     * Exit application
     * @param event
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) mainContent.getScene().getWindow();
        stage.close();
        App.getStore().release();
    }

    /**
     * Switch view to room view
     * @param event
     */
    @FXML
    private void switchToRoom(ActionEvent event) {
        loadFXML("room");
    }

    /**
     * Switch view to settings view
     * @param event
     */
    @FXML
    private void switchToSettings(ActionEvent event) {
        loadFXML("settings");
    }

    /**
     * Shuffle
     * @param event
     */
    @FXML
    private void shuffle(ActionEvent event) {
        store.shuffle();
    }

    /**
     * Go back to previous song
     * @param event
     */
    @FXML
    private void prev(ActionEvent event) {
        store.previous();
    }

    /**
     * Play or pause song
     */

    @FXML
    private void play(ActionEvent event) {
        store.playPause();
        changePlayIcon(store.isPlayed());
    }

    /**
     * Change icon play - pause
     * @param isPlayed
     */
    private void changePlayIcon(boolean isPlayed) {
        if (isPlayed) {
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/pause.png"), 30, 30, true, true)));
        } else {
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 30, 30, true, true)));
        }
    }

    /**
     * Change volume icon and value in UI
     * @param event
     */
    @FXML
    private void volume(ActionEvent event) {
        isMute = !isMute;
        if (isMute) {
            prev_volume = store.getVolume();
            store.changeVolume(0);
            volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/mute.png"), 30, 30, true, true)));
        } else {
            store.changeVolume(prev_volume);
            volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/audio.png"), 30, 30, true, true)));
        }
        volumeValue.setText(String.valueOf(store.getVolume()));
        volumeSlider.setValue(store.getVolume());
    }

    /**
     * Go to next song
     * @param event
     */
    @FXML
    private void next(ActionEvent event) {
        store.next();
    }

    /**
     * Repeat current song
     * @param event
     */
    @FXML
    private void repeat(ActionEvent event) {
        isRepeat = !isRepeat;
        if (isRepeat) {
            repeatButton.setStyle("-fx-opacity: 0.4; -fx-border-width: 0; -fx-background-color: white;");
            store.repeat();
        } else {
            repeatButton.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
        }
    }

    /**
     * Add song to the favourite playlist
     * @param event
     */
    @FXML
    private void addToFavourite(ActionEvent event) {
        isFavourite = !isFavourite;
        if (isFavourite) {
            heartButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart1.png"), 30, 30, true, true)));
        } else {
            heartButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 30, 30, true, true)));
        }
    }

    /**
     * Set title od songs
     * @param title
     */
    @FXML
    private void setSongTitle(String title) {
        songTitle.setText(title);
    }
}
