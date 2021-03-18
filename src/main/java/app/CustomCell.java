package app;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class CustomCell extends ListCell<String> {
    HBox hbox = new HBox();
    Label label = new Label("empty");
    Pane pane = new Pane();
    String lastItem;

    public CustomCell() {
        super();
        hbox.getChildren().addAll(label, pane);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    /**
     * Update element
     *
     * @param item
     * @param empty
     */
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
