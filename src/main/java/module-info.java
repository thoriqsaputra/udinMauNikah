module org.AwalUdin.udinmaunikah {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;

    opens org.AwalUdin.udinmaunikah to javafx.fxml;
    exports org.AwalUdin.udinmaunikah;
}