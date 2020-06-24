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

    // metoda inicjalizacji
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

    //pobieranie aktualnego czasu piosenki
    public String getActualTime() {
        return actualTime / 60 + ":" + (actualTime % 60);
    }

    //ustawienie aktualnego czasu piosenki
    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    //pobranie czasu całkowitego piosenki
    public String getEndTime() {
        return endTime / 60 + ":" + (endTime % 60);
    }

    //ustawienie czasu całkowitego piosenki
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    // metoda odpowiedzialna za przycisk previous
    @FXML
    private void prev(ActionEvent event) {
        System.out.println("prev");
    }

    // metoda odpowiedzialna za przycisk play/pause
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

    // metoda odpowiedzialna za przycisk next
    @FXML
    private void next(ActionEvent event) {
        System.out.println("next");
    }


    // metoda służąca ustawieniu tytułu piosenki
    @FXML
    private void setSongTitle() {
        songTitle.setText(title);
    }

    // funkcja pobierająca tytuł piosenki
    @FXML
    public Label getSongTitle() {
        return songTitle;
    }
}
