package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class QueueController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManager listManager;

    @FXML
    public ListView<String> queueView;
    public TextArea queueSearch;

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManager(queueView, queueSearch);
        // wyświetlenie konkretnej listy na ekranie
        listManager.setMostPlayedList();
    }
}
