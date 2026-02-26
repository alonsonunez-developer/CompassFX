package com.compassfx.controls;

import com.compassfx.enums.MenuVariant;
import com.compassfx.models.MenuItem;
import com.compassfx.skins.CFXMenuBarSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX MenuBar - A Material Design inspired menu bar component
 */
public class CFXMenuBar extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-menubar";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-menu.css";

    // Properties
    private final ObservableList<MenuItem> menus;
    private final ObjectProperty<MenuVariant> variant;
    private final BooleanProperty useSystemMenuBar;

    public CFXMenuBar() {
        this.menus = FXCollections.observableArrayList();
        this.variant = new SimpleObjectProperty<>(this, "variant", MenuVariant.STANDARD);
        this.useSystemMenuBar = new SimpleBooleanProperty(this, "useSystemMenuBar", false);

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
        return new CFXMenuBarSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addMenu(MenuItem menu) {
        menus.add(menu);
    }

    public void addMenu(String text) {
        menus.add(new MenuItem(text));
    }

    // Getters and Setters
    public ObservableList<MenuItem> getMenus() { return menus; }

    public MenuVariant getVariant() { return variant.get(); }
    public void setVariant(MenuVariant variant) { this.variant.set(variant); }
    public ObjectProperty<MenuVariant> variantProperty() { return variant; }

    public boolean isUseSystemMenuBar() { return useSystemMenuBar.get(); }
    public void setUseSystemMenuBar(boolean useSystemMenuBar) {
        this.useSystemMenuBar.set(useSystemMenuBar);
    }
    public BooleanProperty useSystemMenuBarProperty() { return useSystemMenuBar; }
}
