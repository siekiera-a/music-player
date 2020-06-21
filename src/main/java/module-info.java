module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;

    opens app to javafx.fxml;
    exports app;
}