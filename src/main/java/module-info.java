module com.example.coursework {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.coursework to javafx.fxml;
    opens com.example.coursework.vacation to javafx.fxml;
    opens com.example.coursework.scenemanager to javafx.fxml;

    exports com.example.coursework;
    exports com.example.coursework.vacation;
    exports com.example.coursework.scenemanager;
}