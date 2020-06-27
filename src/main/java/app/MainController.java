package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManager listManager;

    @FXML
    public ListView<String> mainView;
    public TextArea mainSearch;

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManager(mainView, mainSearch);
        // wyświetlenie konkretnej listy na ekranie
        listManager.setMostPlayedList();
    }
}
