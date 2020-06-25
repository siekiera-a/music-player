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

public class CustomCell extends ListCell<String> {
    HBox hbox = new HBox();
    Label label = new Label("empty");
    Pane pane = new Pane();
    Button button = new Button();
    String lastItem;
    boolean isFavourite = false;

    public CustomCell() {
        super();
        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isFavourite = !isFavourite;
                if (isFavourite)
                    button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart1.png"), 20, 20, true, true)));
                else
                    button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 20, 20, true, true)));
                //System.out.println(lastItem + " : " + event);
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/heart.png"), 20, 20, true, true)));
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
