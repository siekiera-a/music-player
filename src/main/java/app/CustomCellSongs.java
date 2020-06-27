package app;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.stage.Popup;

public class CustomCellSongs extends ListCell<String> {
    //parametry, które będą ustawiały popup w dobrym miejscu
    double height = 200;
    double width = 800;

    HBox hbox = new HBox();
    Label label = new Label("empty");
    Pane pane = new Pane();
    Button button = new Button("...");
    String lastItem;
    boolean isFavourite = false;
    boolean inQueue = false;
    boolean isPlayNext = false;
    boolean inPlaylist = false;
    Button queue;
    Button favourite;
    Button playNext;
    Button addToPlaylist;

    public CustomCellSongs() {
        super();
        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(event -> {
            Popup popup = new Popup();

            queue = new Button("Dodaj do kolejki");
            queue.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
            favourite = new Button("Dodaj do ulubionych");
            favourite.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
            addToPlaylist = new Button("Dodaj do playlisty");
            addToPlaylist.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
            playNext = new Button("Zagraj jako następne");
            playNext.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");

            popup.setX(width);
            popup.setY(height);
            popup.setHeight(200);
            popup.setWidth(300);

            VBox layout= new VBox(5);
            layout.getChildren().addAll(queue, favourite, addToPlaylist,playNext);
            layout.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-width: 2; -fx-border-color: lightgray; -fx-opacity: 10");
            popup.getContent().add(layout);

            queue.setOnAction(this::addQueue);
            favourite.setOnAction(this::addFavourite);
            addToPlaylist.setOnAction(this::addToPlaylist);
            playNext.setOnAction(this::playNext);

            popup.setAutoHide(true);
            popup.show(App.getInstance());
        });
    }

    private void playNext(ActionEvent actionEvent) {
        isPlayNext = !isPlayNext;
        if(isPlayNext){
            System.out.println("play next");
            playNext.setStyle("-fx-opacity: 0.4; -fx-border-width: 0; -fx-background-color: white;");
        }
        else
        {
            System.out.println("not play next");
            playNext.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
        }
    }

    private void addToPlaylist(ActionEvent actionEvent) {
        inPlaylist = !inPlaylist;
        if (inPlaylist){
            System.out.println("in playlist");
            addToPlaylist.setStyle("-fx-opacity: 0.4; -fx-border-width: 0; -fx-background-color: white;");
        }
        else
        {
            System.out.println("not in playlist");
            addToPlaylist.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
        }
    }

    private void addFavourite(ActionEvent actionEvent) {
        isFavourite = !isFavourite;
        if (isFavourite){
            System.out.println("favourite");
            favourite.setStyle("-fx-opacity: 0.4; -fx-border-width: 0; -fx-background-color: white;");
        }
        else{
            System.out.println("not favourite");
            favourite.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
        }
    }

    private void addQueue(ActionEvent actionEvent) {
        inQueue = !inQueue;
        if (inQueue){
            System.out.println(" in queue");
            queue.setStyle("-fx-opacity: 0.4; -fx-border-width: 0; -fx-background-color: white;");
        }
        else
        {
            System.out.println("not in queue");
            queue.setStyle("-fx-opacity: 1; -fx-border-width: 0; -fx-background-color: white;");
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {
            lastItem = item;
            label.setText(item != null ? item : "<null>");
            setGraphic(hbox);
        }
    }
}