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

    ListManager listManager;
    int index;

    @FXML
    public ListView<String> statisticsView;
    public TextArea statisticsSearch;
    public ComboBox<String> chooseStatistics;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = new ArrayList<String>();
        list.add(0, "Najczęściej odtwarzane");
        list.add(1, "Najrzadziej odtwarzane");
        list.add(2, "Ostatnio odtwarzane");
        ObservableList<String> observableList = FXCollections.observableList(list);
        chooseStatistics.getItems().clear();
        chooseStatistics.setItems(observableList);
        listManager = new ListManager(statisticsView, statisticsSearch);
        chooseStatistics.setValue(chooseStatistics.getItems().get(0));
        listManager.setTestList();

        chooseStatistics.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            index = observableList.indexOf(newValue);
            //System.out.println(index);
            //System.out.println(newValue);
            if (index == -1){
                return;
            }
            if (index == 0) {
                listManager.setTestList();
            } else if (index == 1) {
                listManager.setTest1List();
            } else if (index == 2) {
                listManager.setTest2List();
            }
        });
    }
}
