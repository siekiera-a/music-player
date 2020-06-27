package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class OverlayController implements Initializable {

    //zmienna odpowiedzialna za tytuł piosenki
    private String title = "Tytuł piosenki";
    //zmienna sprawdzająca czy piosenka jest zapausowana
    boolean isPaused = false;
    //zmienna przechowująca aktualny czas piosenki
    int actualTime = 74; //w sekundach
    //zmienna przechowująca czas całkowity piosenki
    int endTime = 230; //w sekundach

    //elementy widoczne w programie
    @FXML
    public Label songTitle;
    public Label actual_time;
    public Label end_time;

    public Button playButton;
    public Button prevButton;
    public Button nextButton;

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

        playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 20, 20, true, true)));
        prevButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/previous.png"), 20, 20, true, true)));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/next.png"), 20, 20, true, true)));

        actual_time.setText(convertTime(actualTime));
        end_time.setText(convertTime(endTime));
        setSongTitle();

    }

    /**
     * Time conversion from seconds to minutes and seconds
     *
     * @param time in seconds
     * @return convert time
     */
    public String convertTime(int time) {
        int minutes = (time % 60);
        String formatted = String.format("%02d", minutes);
        if (minutes < 10) {
            return time / 60 + ":" + formatted;
        } else {
            return time / 60 + ":" + minutes;
        }
    }

    /**
     * Go back to the previous song
     *
     * @param event
     */
    @FXML
    private void prev(ActionEvent event) {
        System.out.println("prev");
    }

    /**
     * Play and pause actual song
     *
     * @param event
     */
    @FXML
    private void play(ActionEvent event) {
        isPaused = !isPaused;
        if (isPaused) {
            System.out.println("pause");
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/pause.png"), 20, 20, true, true)));
        } else {
            System.out.println("play");
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 20, 20, true, true)));
        }
    }

    /**
     * Go to the next song
     *
     * @param event
     */
    @FXML
    private void next(ActionEvent event) {
        System.out.println("next");
    }


    /**
     * Change title of song label
     */
    @FXML
    private void setSongTitle() {
        songTitle.setText(title);
    }
}
