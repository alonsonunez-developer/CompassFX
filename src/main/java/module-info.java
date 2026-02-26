module com.compassfx.demo {
    requires javafx.graphics;
    requires javafx.controls;

    opens com.compassfx.controls to javafx.graphics, javafx.fxml;

    exports com.compassfx.demo;
    exports com.compassfx.controls;
}