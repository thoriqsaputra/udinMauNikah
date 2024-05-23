module org.awaludin.udinmaunikah {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires json.simple;

    opens org.awaludin.udinmaunikah to javafx.fxml;
    exports org.awaludin.udinmaunikah;
}