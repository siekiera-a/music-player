package app;

import app.ListManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    ListManager listManager;

    @FXML
    public ListView<String> mainView;
    public TextArea mainSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listManager = new ListManager(mainView, mainSearch);
        listManager.setTestList();
    }
}
