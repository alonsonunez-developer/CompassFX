package com.compassfx.controls;

import com.compassfx.enums.BreadcrumbSeparator;
import com.compassfx.enums.BreadcrumbVariant;
import com.compassfx.models.BreadcrumbItem;
import com.compassfx.skins.CFXBreadcrumbSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Breadcrumb - A Material Design inspired breadcrumb navigation component
 */
public class CFXBreadcrumb extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-breadcrumb";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-breadcrumb.css";

    // Properties
    private final ObservableList<BreadcrumbItem> items;
    private final ObjectProperty<BreadcrumbVariant> variant;
    private final ObjectProperty<BreadcrumbSeparator> separator;
    private final BooleanProperty showHomeIcon;
    private final BooleanProperty interactive;
    private final IntegerProperty maxDisplayedItems;
    private final BooleanProperty animated;

    // Event handler for item clicks
    private ObjectProperty<EventHandler<BreadcrumbEvent>> onItemClick;

    public CFXBreadcrumb() {
        this.items = FXCollections.observableArrayList();
        this.variant = new SimpleObjectProperty<>(this, "variant", BreadcrumbVariant.STANDARD);
        this.separator = new SimpleObjectProperty<>(this, "separator", BreadcrumbSeparator.CHEVRON);
        this.showHomeIcon = new SimpleBooleanProperty(this, "showHomeIcon", false);
        this.interactive = new SimpleBooleanProperty(this, "interactive", true);
        this.maxDisplayedItems = new SimpleIntegerProperty(this, "maxDisplayedItems", -1);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.onItemClick = new SimpleObjectProperty<>(this, "onItemClick", null);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("INFO: Cargado stylesheet: " + STYLESHEET);
            } else {
                System.err.println("ERROR: No se pudo encontrar el recurso en: " + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
            e.printStackTrace();
        }

        updateStyleClasses();
        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(variant.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXBreadcrumbSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addItem(String text) {
        items.add(new BreadcrumbItem(text));
    }

    public void addItem(BreadcrumbItem item) {
        items.add(item);
    }

    public void setPath(String... pathParts) {
        items.clear();
        for (String part : pathParts) {
            items.add(new BreadcrumbItem(part));
        }
    }

    public void navigateToIndex(int index) {
        if (index >= 0 && index < items.size()) {
            // Fire event
            if (onItemClick.get() != null) {
                BreadcrumbEvent event = new BreadcrumbEvent(items.get(index), index);
                onItemClick.get().handle(event);
            }
        }
    }

    // Getters and Setters
    public ObservableList<BreadcrumbItem> getItems() { return items; }

    public BreadcrumbVariant getVariant() { return variant.get(); }
    public void setVariant(BreadcrumbVariant variant) { this.variant.set(variant); }
    public ObjectProperty<BreadcrumbVariant> variantProperty() { return variant; }

    public BreadcrumbSeparator getSeparator() { return separator.get(); }
    public void setSeparator(BreadcrumbSeparator separator) { this.separator.set(separator); }
    public ObjectProperty<BreadcrumbSeparator> separatorProperty() { return separator; }

    public boolean isShowHomeIcon() { return showHomeIcon.get(); }
    public void setShowHomeIcon(boolean showHomeIcon) { this.showHomeIcon.set(showHomeIcon); }
    public BooleanProperty showHomeIconProperty() { return showHomeIcon; }

    public boolean isInteractive() { return interactive.get(); }
    public void setInteractive(boolean interactive) { this.interactive.set(interactive); }
    public BooleanProperty interactiveProperty() { return interactive; }

    public int getMaxDisplayedItems() { return maxDisplayedItems.get(); }
    public void setMaxDisplayedItems(int maxDisplayedItems) { this.maxDisplayedItems.set(maxDisplayedItems); }
    public IntegerProperty maxDisplayedItemsProperty() { return maxDisplayedItems; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public EventHandler<BreadcrumbEvent> getOnItemClick() { return onItemClick.get(); }
    public void setOnItemClick(EventHandler<BreadcrumbEvent> handler) { this.onItemClick.set(handler); }
    public ObjectProperty<EventHandler<BreadcrumbEvent>> onItemClickProperty() { return onItemClick; }

    // Breadcrumb Event Class
    public static class BreadcrumbEvent extends ActionEvent {
        private final BreadcrumbItem item;
        private final int index;

        public BreadcrumbEvent(BreadcrumbItem item, int index) {
            super();
            this.item = item;
            this.index = index;
        }

        public BreadcrumbItem getItem() { return item; }
        public int getIndex() { return index; }
    }
}
