package com.compassfx.controls;

import com.compassfx.enums.AccordionColor;
import com.compassfx.enums.AccordionVariant;
import com.compassfx.skins.CFXAccordionSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Accordion - A Material Design inspired accordion component
 */
public class CFXAccordion extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-accordion";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-accordion.css";

    // Properties
    private final ObjectProperty<AccordionVariant> variant;
    private final ObjectProperty<AccordionColor> color;
    private final BooleanProperty allowMultipleExpanded;
    private final BooleanProperty animated;
    private final ObservableList<CFXAccordionItem> items;
    private final ObjectProperty<CFXAccordionItem> expandedItem;

    public CFXAccordion() {
        this.variant = new SimpleObjectProperty<>(this, "variant", AccordionVariant.STANDARD);
        this.color = new SimpleObjectProperty<>(this, "color", AccordionColor.NEUTRAL);
        this.allowMultipleExpanded = new SimpleBooleanProperty(this, "allowMultipleExpanded", false);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.items = FXCollections.observableArrayList();
        this.expandedItem = new SimpleObjectProperty<>(this, "expandedItem", null);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet with error handling
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("INFO: Cargado stylesheet: " + STYLESHEET);
            } else {
                System.err.println("ERROR: No se pudo encontrar el recurso en: " + STYLESHEET);
                System.err.println("Verifica que esté en: src/main/resources" + STYLESHEET);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Excepción al cargar stylesheet: " + e.getMessage());
            e.printStackTrace();
        }

        updateStyleClasses();

        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        color.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Handle item expansion logic
        items.addListener((javafx.collections.ListChangeListener.Change<? extends CFXAccordionItem> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (CFXAccordionItem item : c.getAddedSubList()) {
                        item.expandedProperty().addListener((obs, oldVal, newVal) -> {
                            if (newVal && !allowMultipleExpanded.get()) {
                                collapseOtherItems(item);
                            }
                            if (newVal) {
                                expandedItem.set(item);
                            } else if (expandedItem.get() == item) {
                                expandedItem.set(null);
                            }
                        });
                    }
                }
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );

        getStyleClass().add(variant.get().getStyleClass());

        if (color.get() != AccordionColor.NEUTRAL) {
            getStyleClass().add(color.get().getStyleClass());
        }
    }

    private void collapseOtherItems(CFXAccordionItem itemToKeepExpanded) {
        for (CFXAccordionItem item : items) {
            if (item != itemToKeepExpanded && item.isExpanded()) {
                item.setExpanded(false);
            }
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXAccordionSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Public API methods
    public void addItem(CFXAccordionItem item) {
        items.add(item);
    }

    public void removeItem(CFXAccordionItem item) {
        items.remove(item);
    }

    public void expandAll() {
        if (allowMultipleExpanded.get()) {
            for (CFXAccordionItem item : items) {
                item.setExpanded(true);
            }
        }
    }

    public void collapseAll() {
        for (CFXAccordionItem item : items) {
            item.setExpanded(false);
        }
    }

    // Getters and Setters
    public AccordionVariant getVariant() { return variant.get(); }
    public void setVariant(AccordionVariant variant) { this.variant.set(variant); }
    public ObjectProperty<AccordionVariant> variantProperty() { return variant; }

    public AccordionColor getColor() { return color.get(); }
    public void setColor(AccordionColor color) { this.color.set(color); }
    public ObjectProperty<AccordionColor> colorProperty() { return color; }

    public boolean isAllowMultipleExpanded() { return allowMultipleExpanded.get(); }
    public void setAllowMultipleExpanded(boolean allowMultipleExpanded) {
        this.allowMultipleExpanded.set(allowMultipleExpanded);
    }
    public BooleanProperty allowMultipleExpandedProperty() { return allowMultipleExpanded; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public ObservableList<CFXAccordionItem> getItems() { return items; }

    public CFXAccordionItem getExpandedItem() { return expandedItem.get(); }
    public ReadOnlyObjectProperty<CFXAccordionItem> expandedItemProperty() { return expandedItem; }
}
