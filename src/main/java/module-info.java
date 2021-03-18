module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens app to javafx.fxml;
    exports app;
}