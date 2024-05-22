module org.awaludin.udinmaunikah {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.awaludin.udinmaunikah to javafx.fxml;
    exports org.awaludin.udinmaunikah;
}