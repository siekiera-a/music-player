package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class OverlayController implements Initializable {

    @FXML
    public Label songTitle;
    public Label actual_time;
    public Label end_time;

    public Button playButton;
    public Button prevButton;
    public Button nextButton;

    private final Store store = App.getStore();

    /**
     * Default initialize method
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playButton.setOnAction(this::play);
        nextButton.setOnAction(this::next);
        prevButton.setOnAction(this::prev);

        prevButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/previous.png"), 20, 20, true, true)));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/next.png"), 20, 20, true, true)));

        changePlayIcon(store.isPlayed());
        store.subscribeTimeChange(this::updateTime);
        store.subscribeSceneChange(this::changePlayIcon);
        store.subscribeTitleChange(this::setSongTitle);
        store.subscribeAudioLoaded(d -> {
            end_time.setText(SchemeController.convertTime((int) (d.toSeconds())));
            actual_time.setText(SchemeController.convertTime(0));
        });
    }

    /**
     * Update time
     *
     * @param duration
     */
    private void updateTime(Duration duration) {
        actual_time.setText(SchemeController.convertTime((int) duration.toSeconds()));
    }

    /**
     * Change icon play - pause
     *
     * @param isPlayed
     */
    public void changePlayIcon(boolean isPlayed) {
        if (isPlayed) {
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/pause.png"), 20, 20, true, true)));
        } else {
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 20, 20, true, true)));
        }
    }

    /**
     * Go back to previous song
     *
     * @param event
     */
    @FXML
    private void prev(ActionEvent event) {
        store.previous();
    }

    /**
     * Play or pause song
     *
     * @param event
     */
    @FXML
    private void play(ActionEvent event) {
        store.playPause();
        changePlayIcon(store.isPlayed());
    }

    /**
     * Go to next song
     *
     * @param event
     */
    @FXML
    private void next(ActionEvent event) {
        store.next();
    }


    /**
     * Set title of song
     *
     * @param title
     */
    @FXML
    private void setSongTitle(String title) {
        songTitle.setText(title);
    }
}
