package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SchemeController implements Initializable {

    //zmienna odpowiedzialna za tytuł piosenki
    private String title = "Tytuł piosenki";
    //zmienna sprawdzająca czy piosenka jest zapausowana
    boolean isPaused = false;
    //zmienna sprawdzająca czy dźwięk jest wyciszony
    boolean isMute = false;
    //zmienna sprawdzająca czy piosenka została dodana do ulubionych
    boolean isFavourite = false;
    // zmienna odpowiedzialna za początkowa glosnosc piosenki
    int actual_volume = 100;
    //zmienna przechowująca aktualna glosnosc - używana przy nacisnieciu przycisku glosnosci
    int prev_volume;
    //zmienna przechowująca aktualny czas piosenki
    int actualTime = 74; //w sekundach
    //zmienna przechowująca czas całkowity piosenki
    int endTime = 230; //w sekundach

    // elementy widoczne w programie
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

    // metoda wstawiania ikonek do przycisków
    public void setImage() {
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
        infoButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/info.png"), 30, 30, true, true)));
        exitButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/exit.png"), 30, 30, true, true)));
        heartButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 30, 30, true, true)));
        devicesButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/laptop.png"), 30, 30, true, true)));

    }

    // pomocnicza metoda do initialize
    public void init() {
        setImage();
        setSongTitle();
        manageSongSlider();
        manageVolumeSlider();
    }

    // metoda obsługująca pasek głościości
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

            } else if (!isMute && newValue.intValue() == 0) {
                isMute = true;
            }
        });
    }

    // metoda obsługująca pasek trwania piosenki
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

    //pobieranie aktualnej głośności
    public String getActual_volume() {
        return String.valueOf(actual_volume);
    }

    //ustawianie aktualnej głośności
    public void setActual_volume(int actual_volume) {
        this.actual_volume = actual_volume;
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

    // metoda wczytujący obraz środka aplikacji
    private void loadFXML(String fxml) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
            mainContent.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metoda inicjalizacji
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

    // metody służace do przełączania się między kartami
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

    // metoda odpowiedzialna za przycisk shuffle
    @FXML
    private void shuffle(ActionEvent event) {
        System.out.println("shuffle");
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
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/pause.png"), 30, 30, true, true)));
        } else {
            System.out.println("play");
            playButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/play.png"), 30, 30, true, true)));
        }
    }

    // metoda odpowiedzialna za przycisk volume/mute
    @FXML
    private void volume(ActionEvent event) {
        isMute = !isMute;
        if (isMute) {
            prev_volume = actual_volume;
            actual_volume = 0;
            System.out.println("mute");
            volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/mute.png"), 30, 30, true, true)));

        } else {
            actual_volume = prev_volume;
            System.out.println("volume up");
            volumeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/audio.png"), 30, 30, true, true)));
        }
        volumeValue.setText(getActual_volume());
        volumeSlider.setValue(actual_volume);
    }

    // metoda odpowiedzialna za przycisk next
    @FXML
    private void next(ActionEvent event) {
        System.out.println("next");
    }

    // metoda odpowiedzialna za przycisk repeat
    @FXML
    private void repeat(ActionEvent event) {
        System.out.println("repeat");
    }

    // metoda odpowiedzialna za przycisk heart, czyli dodawanie do  ulubionych
    @FXML
    private void addToFavourite(ActionEvent event) {
        isFavourite = !isFavourite;
        if (isFavourite) {
            System.out.println("red heart");
            heartButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart1.png"), 30, 30, true, true)));
        } else {
            System.out.println("black heart");
            heartButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 30, 30, true, true)));
        }
    }

    // metoda odpowiedzialna za przycisk device - czyli urządzenia wyjściowe
    @FXML
    private void changeOutputDevice(ActionEvent event) {
        System.out.println("devices");
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

    // metoda tworząca małe okienko z informacjami o programie
    @FXML
    private void showInfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("jakies informacje o programie");
        alert.showAndWait();
    }
}
