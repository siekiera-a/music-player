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

    private String title = "Tytuł piosenki";
    boolean isPaused = false;
    int actualTime = 74; //w sekundach
    int endTime = 230; //w sekundach

    @FXML
    public Label songTitle;
    public Label actual_time;
    public Label end_time;

    public Button playButton;
    public Button prevButton;
    public Button nextButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playButton.setOnAction(this::play);
        nextButton.setOnAction(this::next);
        prevButton.setOnAction(this::prev);

        playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 20, 20, true, true)));
        prevButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/previous.png"), 20, 20, true, true)));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/next.png"), 20, 20, true, true)));

        actual_time.setText(getActualTime());
        end_time.setText(getEndTime());
        setSongTitle();

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

    @FXML
    private void prev(ActionEvent event) {
        System.out.println("prev");
    }

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

    @FXML
    private void next(ActionEvent event) {
        System.out.println("next");
    }

    @FXML
    private void setSongTitle() {
        songTitle.setText(title);
    }
}
