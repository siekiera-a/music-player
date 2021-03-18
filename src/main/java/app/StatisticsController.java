package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    // manager pomagający przy listach ( tworzenie list, szukanie w listach, itd )
    ListManager listManager;
    // zmienna odpowiedzialna za index w ComboBox
    int index;

    @FXML
    public ListView<String> statisticsView;
    public TextArea statisticsSearch;
    public ComboBox<String> chooseStatistics;

    // metoda inicjalizacji
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = new ArrayList<>();
        //nowe statystyki
        list.add(0, "Najczęściej odtwarzane");
        list.add(1, "Najrzadziej odtwarzane");
        list.add(2, "Dzisiaj odtwarzane");
        list.add(3, "Najczęściej odtwarzane w ostatnim tygodniu");

        ObservableList<String> observableList = FXCollections.observableList(list);
        chooseStatistics.getItems().clear();
        chooseStatistics.setItems(observableList);

        listManager = new ListManager(statisticsView, statisticsSearch);

        // ustawienie początkowej statystyki
        chooseStatistics.setValue(chooseStatistics.getItems().get(0));

        // wyświetlenie konkretnej listy na ekranie dla początkowej statystyki
        listManager.mostPlayed();

        chooseStatistics.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            index = observableList.indexOf(newValue);
            //System.out.println(index);
            //System.out.println(newValue);
            if (index == -1) {
                return;
            }
            if (index == 0) {
                // wyświetlenie konkretnej listy na ekranie dla pierwszej statystyki
                listManager.mostPlayed();
            } else if (index == 1) {
                // wyświetlenie konkretnej listy na ekranie dla drugiej statystyki
                listManager.leastPlayed();
            } else if (index == 2) {
                // wyświetlenie konkretnej listy na ekranie dla trzeciej statystyki
                listManager.currentDaySongs();
            } else if (index == 3){
                listManager.lastWeekSongs();
            }


        });
    }
}
