module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.desktop;

    opens app to javafx.fxml;
    exports app;
}