package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Popup;

public class CustomCell extends ListCell<String> {
    HBox hbox = new HBox();
    Label label = new Label("empty");
    Pane pane = new Pane();
    Button button = new Button("...");
    Popup popup = new Popup();
    String lastItem;
    boolean isFavourite = false;

    public CustomCell() {
        super();
        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createPopup();
            }
        });
    }

    public void createPopup(){

        Button queue = new Button();
        Button favourite = new Button();
        Button addToPlaylist = new Button();
        Button playNext = new Button();

        popup.getContent().add(queue);
        popup.getContent().add(favourite);
        popup.getContent().add(addToPlaylist);
        popup.getContent().add(playNext);

        queue.setOnAction(this::addQueue);
        favourite.setOnAction(this::addFavourite);
        addToPlaylist.setOnAction(this::addToPlaylist);
        playNext.setOnAction(this::playNext);
    }

    private void playNext(ActionEvent actionEvent) {
        System.out.println("playnext");
    }

    private void addToPlaylist(ActionEvent actionEvent) {
        System.out.println("playlist");
    }

    private void addFavourite(ActionEvent actionEvent) {
        isFavourite = !isFavourite;
        if (isFavourite)
            button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart1.png"), 20, 20, true, true)));
        else
            button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 20, 20, true, true)));
    }

    private void addQueue(ActionEvent actionEvent) {
        System.out.println("queue");
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
