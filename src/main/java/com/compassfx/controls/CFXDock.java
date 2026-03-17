package com.compassfx.controls;

import com.compassfx.enums.DockPosition;
import com.compassfx.models.CFXDockItem;
import javafx.animation.ScaleTransition;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * CompassFX Dock - macOS/Linux style dock with magnification animations
 */
public class CFXDock extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-dock";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-dock.css";

    // Properties
    private final ObjectProperty<ObservableList<CFXDockItem>> items;
    private final ObjectProperty<DockPosition> position;
    private final DoubleProperty iconSize;
    private final DoubleProperty magnification;
    private final DoubleProperty spacing;
    private final BooleanProperty showLabels;
    private final BooleanProperty animated;

    // UI Components
    private HBox horizontalContainer;
    private VBox verticalContainer;
    private Pane activeContainer;

    // Animation settings
    private static final double MAGNIFICATION_FACTOR = 1.6;
    private static final double NEIGHBOR_SCALE = 1.3;
    private static final int ANIMATION_DURATION = 200;

    public CFXDock() {
        this.items = new SimpleObjectProperty<>(this, "items", FXCollections.observableArrayList());
        this.position = new SimpleObjectProperty<>(this, "position", DockPosition.BOTTOM);
        this.iconSize = new SimpleDoubleProperty(this, "iconSize", 56);
        this.magnification = new SimpleDoubleProperty(this, "magnification", MAGNIFICATION_FACTOR);
        this.spacing = new SimpleDoubleProperty(this, "spacing", 8);
        this.showLabels = new SimpleBooleanProperty(this, "showLabels", true);
        this.animated = new SimpleBooleanProperty(this, "animated", true);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Ensure dock has minimum size
        setMinSize(100, 100);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        setupContainers();
        setupListeners();
        rebuildDock();
    }

    private void setupContainers() {
        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.CENTER);
        horizontalContainer.getStyleClass().add("dock-container");
        horizontalContainer.setMinHeight(80);
        horizontalContainer.setPadding(new Insets(10));

        verticalContainer = new VBox();
        verticalContainer.setAlignment(Pos.CENTER);
        verticalContainer.getStyleClass().add("dock-container");
        verticalContainer.setMinWidth(80);
        verticalContainer.setPadding(new Insets(10));
    }

    private void setupListeners() {
        items.get().addListener((ListChangeListener<CFXDockItem>) c -> {
            rebuildDock();
        });

        position.addListener((obs, old, newVal) -> {
            rebuildDock();
        });

        iconSize.addListener((obs, old, newVal) -> {
            rebuildDock();
        });

        spacing.addListener((obs, old, newVal) -> {
            if (activeContainer instanceof HBox) {
                ((HBox) activeContainer).setSpacing(newVal.doubleValue());
            } else if (activeContainer instanceof VBox) {
                ((VBox) activeContainer).setSpacing(newVal.doubleValue());
            }
        });
    }

    private void rebuildDock() {
        getChildren().clear();

        if (items.get().isEmpty()) {
            return;
        }

        DockPosition pos = position.get();

        if (pos == DockPosition.BOTTOM || pos == DockPosition.TOP) {
            buildHorizontalDock();
        } else {
            buildVerticalDock();
        }
    }

    private void buildHorizontalDock() {
        horizontalContainer.getChildren().clear();
        horizontalContainer.setSpacing(spacing.get());

        for (int i = 0; i < items.get().size(); i++) {
            CFXDockItem item = items.get().get(i);
            StackPane itemNode = createDockItemNode(item, i);
            horizontalContainer.getChildren().add(itemNode);
        }

        activeContainer = horizontalContainer;
        getChildren().add(horizontalContainer);
    }

    private void buildVerticalDock() {
        verticalContainer.getChildren().clear();
        verticalContainer.setSpacing(spacing.get());

        for (int i = 0; i < items.get().size(); i++) {
            CFXDockItem item = items.get().get(i);
            StackPane itemNode = createDockItemNode(item, i);
            verticalContainer.getChildren().add(itemNode);
        }

        activeContainer = verticalContainer;
        getChildren().add(verticalContainer);
    }

    private StackPane createDockItemNode(CFXDockItem item, int index) {
        StackPane container = new StackPane();
        container.getStyleClass().add("dock-item");
        container.setCursor(Cursor.HAND);

        // Icon container
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(iconSize.get(), iconSize.get());
        iconContainer.setMinSize(iconSize.get(), iconSize.get());
        iconContainer.setMaxSize(iconSize.get(), iconSize.get());
        iconContainer.getStyleClass().add("dock-icon-container");

        // Icon
        Node icon = item.getIcon();
        if (icon != null) {
            iconContainer.getChildren().add(icon);
        }

        // Badge
        if (item.isBadge() || item.getBadgeCount() > 0) {
            StackPane badge = new StackPane();
            badge.getStyleClass().add("dock-badge");
            badge.setPrefSize(20, 20);
            badge.setMinSize(20, 20);
            badge.setMaxSize(20, 20);

            if (item.getBadgeCount() > 0) {
                Label badgeLabel = new Label(String.valueOf(item.getBadgeCount()));
                badgeLabel.getStyleClass().add("dock-badge-label");
                badge.getChildren().add(badgeLabel);
            }

            StackPane.setAlignment(badge, Pos.TOP_RIGHT);
            container.getChildren().add(badge);
        }

        container.getChildren().add(iconContainer);

        // Tooltip
        if (item.getLabel() != null && !item.getLabel().isEmpty()) {
            Tooltip tooltip = new Tooltip(item.getLabel());
            tooltip.setShowDelay(Duration.millis(500));
            Tooltip.install(container, tooltip);
        }

        // Click handler
        container.setOnMouseClicked(e -> {
            if (item.getOnAction() != null) {
                item.getOnAction().run();
            }
        });

        // Magnification on hover
        setupMagnification(container, iconContainer, index);

        // Bounce animation on click
        container.setOnMousePressed(e -> {
            if (animated.get()) {
                bounceAnimation(iconContainer);
            }
        });

        return container;
    }

    private void setupMagnification(StackPane container, StackPane iconContainer, int index) {
        container.setOnMouseEntered(e -> {
            if (!animated.get()) return;

            // Magnify this item
            animateScale(iconContainer, magnification.get());

            // Scale neighbors
            scaleNeighbors(index, NEIGHBOR_SCALE);
        });

        container.setOnMouseExited(e -> {
            if (!animated.get()) return;

            // Reset this item
            animateScale(iconContainer, 1.0);

            // Reset neighbors
            scaleNeighbors(index, 1.0);
        });
    }

    private void scaleNeighbors(int index, double scale) {
        // Scale left/top neighbor
        if (index > 0) {
            Node leftNeighbor = activeContainer.getChildren().get(index - 1);
            if (leftNeighbor instanceof StackPane) {
                StackPane sp = (StackPane) leftNeighbor;
                if (!sp.getChildren().isEmpty() && sp.getChildren().get(0) instanceof StackPane) {
                    animateScale((StackPane) sp.getChildren().get(0), scale);
                }
            }
        }

        // Scale right/bottom neighbor
        if (index < activeContainer.getChildren().size() - 1) {
            Node rightNeighbor = activeContainer.getChildren().get(index + 1);
            if (rightNeighbor instanceof StackPane) {
                StackPane sp = (StackPane) rightNeighbor;
                if (!sp.getChildren().isEmpty() && sp.getChildren().get(0) instanceof StackPane) {
                    animateScale((StackPane) sp.getChildren().get(0), scale);
                }
            }
        }
    }

    private void animateScale(StackPane node, double targetScale) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(ANIMATION_DURATION), node);
        scale.setToX(targetScale);
        scale.setToY(targetScale);
        scale.play();
    }

    private void bounceAnimation(StackPane node) {
        // Quick bounce effect
        ScaleTransition down = new ScaleTransition(Duration.millis(100), node);
        down.setToX(0.9);
        down.setToY(0.9);

        ScaleTransition up = new ScaleTransition(Duration.millis(150), node);
        up.setToX(magnification.get());
        up.setToY(magnification.get());

        down.setOnFinished(e -> up.play());
        down.play();
    }

    // Public methods
    public void addItem(CFXDockItem item) {
        items.get().add(item);
    }

    public void removeItem(CFXDockItem item) {
        items.get().remove(item);
    }

    public void clearItems() {
        items.get().clear();
    }

    // Getters and Setters
    public ObservableList<CFXDockItem> getItems() { return items.get(); }
    public void setItems(ObservableList<CFXDockItem> items) { this.items.set(items); }
    public ObjectProperty<ObservableList<CFXDockItem>> itemsProperty() { return items; }

    public DockPosition getPosition() { return position.get(); }
    public void setPosition(DockPosition position) { this.position.set(position); }
    public ObjectProperty<DockPosition> positionProperty() { return position; }

    public double getIconSize() { return iconSize.get(); }
    public void setIconSize(double iconSize) { this.iconSize.set(iconSize); }
    public DoubleProperty iconSizeProperty() { return iconSize; }

    public double getMagnification() { return magnification.get(); }
    public void setMagnification(double magnification) { this.magnification.set(magnification); }
    public DoubleProperty magnificationProperty() { return magnification; }

    public double getSpacing() { return spacing.get(); }
    public void setSpacing(double spacing) { this.spacing.set(spacing); }
    public DoubleProperty spacingProperty() { return spacing; }

    public boolean isShowLabels() { return showLabels.get(); }
    public void setShowLabels(boolean showLabels) { this.showLabels.set(showLabels); }
    public BooleanProperty showLabelsProperty() { return showLabels; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }
}