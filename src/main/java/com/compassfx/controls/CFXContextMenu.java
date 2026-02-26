package com.compassfx.controls;

import com.compassfx.models.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Popup;

/**
 * CompassFX Context Menu - A Material Design inspired context/popup menu
 */
public class CFXContextMenu extends Popup {

    private static final String DEFAULT_STYLE_CLASS = "cfx-context-menu";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-menu.css";

    private final ObservableList<MenuItem> items;
    private final javafx.scene.layout.VBox container;

    public CFXContextMenu() {
        this.items = FXCollections.observableArrayList();
        this.container = new javafx.scene.layout.VBox();
        container.getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                container.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
        }

        getContent().add(container);
        setAutoHide(true);

        // Listen for items changes
        items.addListener((javafx.collections.ListChangeListener.Change<? extends MenuItem> c) -> {
            updateMenu();
        });
    }

    private void updateMenu() {
        container.getChildren().clear();

        for (MenuItem item : items) {
            container.getChildren().add(createMenuItemNode(item));
        }
    }

    private Node createMenuItemNode(MenuItem item) {
        if (item.isSeparator()) {
            javafx.scene.control.Separator sep = new javafx.scene.control.Separator();
            sep.getStyleClass().add("menu-separator");
            return sep;
        }

        javafx.scene.layout.HBox itemBox = new javafx.scene.layout.HBox(10);
        itemBox.getStyleClass().add("context-menu-item");
        itemBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        itemBox.setPadding(new javafx.geometry.Insets(8, 16, 8, 16));

        if (item.getGraphic() != null) {
            itemBox.getChildren().add(item.getGraphic());
        }

        javafx.scene.control.Label label = new javafx.scene.control.Label();
        label.textProperty().bind(item.textProperty());
        javafx.scene.layout.HBox.setHgrow(label, javafx.scene.layout.Priority.ALWAYS);
        itemBox.getChildren().add(label);

        if (item.getAccelerator() != null) {
            javafx.scene.control.Label accelLabel = new javafx.scene.control.Label(
                    item.getAccelerator().getDisplayText()
            );
            accelLabel.getStyleClass().add("accelerator-label");
            itemBox.getChildren().add(accelLabel);
        }

        if (item.hasSubItems()) {
            javafx.scene.control.Label arrow = new javafx.scene.control.Label("›");
            arrow.getStyleClass().add("submenu-arrow");
            itemBox.getChildren().add(arrow);
        }

        itemBox.setCursor(javafx.scene.Cursor.HAND);
        itemBox.setOnMouseClicked(e -> {
            if (item.getOnAction() != null) {
                item.getOnAction().handle(new javafx.event.ActionEvent());
            }
            hide();
        });

        itemBox.setOnMouseEntered(e -> itemBox.getStyleClass().add("hover"));
        itemBox.setOnMouseExited(e -> itemBox.getStyleClass().remove("hover"));

        if (item.isDisabled()) {
            itemBox.setDisable(true);
            itemBox.setOpacity(0.5);
        }

        return itemBox;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void addItem(String text) {
        items.add(new MenuItem(text));
    }

    public ObservableList<MenuItem> getItems() {
        return items;
    }

    public void show(Node anchor, double screenX, double screenY) {
        super.show(anchor, screenX, screenY);
    }
}
