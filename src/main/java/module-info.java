module com.compassfx.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.compassfx.controls to javafx.graphics, javafx.fxml;

    exports com.compassfx.demo;
    exports com.compassfx.controls;
}