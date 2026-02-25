// ============================================
// CFXTabs.java - Tabs Control
// src/main/java/com/compassfx/controls/CFXTabs.java
// ============================================
package com.compassfx.controls;

import com.compassfx.enums.TabPosition;
import com.compassfx.enums.TabVariant;
import com.compassfx.models.Tab;
import com.compassfx.skins.CFXTabsSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Tabs - A Material Design inspired tabs component
 */
public class CFXTabs extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-tabs";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-tabs.css";

    // Properties
    private final ObservableList<Tab> tabs;
    private final IntegerProperty selectedIndex;
    private final ObjectProperty<Tab> selectedTab;
    private final ObjectProperty<TabPosition> tabPosition;
    private final ObjectProperty<TabVariant> variant;
    private final BooleanProperty animated;
    private final BooleanProperty showTabBar;

    // Event handlers
    private ObjectProperty<EventHandler<TabEvent>> onTabChange;
    private ObjectProperty<EventHandler<TabEvent>> onTabClose;

    public CFXTabs() {
        this.tabs = FXCollections.observableArrayList();
        this.selectedIndex = new SimpleIntegerProperty(this, "selectedIndex", 0);
        this.selectedTab = new SimpleObjectProperty<>(this, "selectedTab", null);
        this.tabPosition = new SimpleObjectProperty<>(this, "tabPosition", TabPosition.TOP);
        this.variant = new SimpleObjectProperty<>(this, "variant", TabVariant.STANDARD);
        this.animated = new SimpleBooleanProperty(this, "animated", true);
        this.showTabBar = new SimpleBooleanProperty(this, "showTabBar", true);
        this.onTabChange = new SimpleObjectProperty<>(this, "onTabChange", null);
        this.onTabClose = new SimpleObjectProperty<>(this, "onTabClose", null);

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
        tabPosition.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Sync selectedTab with selectedIndex
        selectedIndex.addListener((obs, old, newVal) -> {
            if (newVal.intValue() >= 0 && newVal.intValue() < tabs.size()) {
                selectedTab.set(tabs.get(newVal.intValue()));

                if (onTabChange.get() != null) {
                    TabEvent event = new TabEvent(selectedTab.get(), newVal.intValue());
                    onTabChange.get().handle(event);
                }
            }
        });

        // Update selectedIndex when tabs change
        tabs.addListener((javafx.collections.ListChangeListener.Change<? extends Tab> c) -> {
            if (selectedIndex.get() >= tabs.size()) {
                selectedIndex.set(Math.max(0, tabs.size() - 1));
            }
            if (!tabs.isEmpty() && selectedTab.get() == null) {
                selectedIndex.set(0);
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(tabPosition.get().getStyleClass());
        getStyleClass().add(variant.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXTabsSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addTab(Tab tab) {
        tabs.add(tab);
    }

    public void addTab(String text) {
        tabs.add(new Tab(text));
    }

    public void addTab(String text, javafx.scene.Node content) {
        tabs.add(new Tab(text, content));
    }

    public void removeTab(Tab tab) {
        int index = tabs.indexOf(tab);
        if (index >= 0) {
            if (onTabClose.get() != null) {
                TabEvent event = new TabEvent(tab, index);
                onTabClose.get().handle(event);
            }
            tabs.remove(tab);
        }
    }

    public void selectTab(int index) {
        if (index >= 0 && index < tabs.size()) {
            selectedIndex.set(index);
        }
    }

    public void selectTab(Tab tab) {
        int index = tabs.indexOf(tab);
        if (index >= 0) {
            selectedIndex.set(index);
        }
    }

    // Getters and Setters
    public ObservableList<Tab> getTabs() { return tabs; }

    public int getSelectedIndex() { return selectedIndex.get(); }
    public void setSelectedIndex(int selectedIndex) { this.selectedIndex.set(selectedIndex); }
    public IntegerProperty selectedIndexProperty() { return selectedIndex; }

    public Tab getSelectedTab() { return selectedTab.get(); }
    public void setSelectedTab(Tab selectedTab) {
        int index = tabs.indexOf(selectedTab);
        if (index >= 0) {
            selectedIndex.set(index);
        }
    }
    public ObjectProperty<Tab> selectedTabProperty() { return selectedTab; }

    public TabPosition getTabPosition() { return tabPosition.get(); }
    public void setTabPosition(TabPosition tabPosition) { this.tabPosition.set(tabPosition); }
    public ObjectProperty<TabPosition> tabPositionProperty() { return tabPosition; }

    public TabVariant getVariant() { return variant.get(); }
    public void setVariant(TabVariant variant) { this.variant.set(variant); }
    public ObjectProperty<TabVariant> variantProperty() { return variant; }

    public boolean isAnimated() { return animated.get(); }
    public void setAnimated(boolean animated) { this.animated.set(animated); }
    public BooleanProperty animatedProperty() { return animated; }

    public boolean isShowTabBar() { return showTabBar.get(); }
    public void setShowTabBar(boolean showTabBar) { this.showTabBar.set(showTabBar); }
    public BooleanProperty showTabBarProperty() { return showTabBar; }

    public EventHandler<TabEvent> getOnTabChange() { return onTabChange.get(); }
    public void setOnTabChange(EventHandler<TabEvent> handler) { this.onTabChange.set(handler); }
    public ObjectProperty<EventHandler<TabEvent>> onTabChangeProperty() { return onTabChange; }

    public EventHandler<TabEvent> getOnTabClose() { return onTabClose.get(); }
    public void setOnTabClose(EventHandler<TabEvent> handler) { this.onTabClose.set(handler); }
    public ObjectProperty<EventHandler<TabEvent>> onTabCloseProperty() { return onTabClose; }

    // Tab Event Class
    public static class TabEvent extends ActionEvent {
        private final Tab tab;
        private final int index;

        public TabEvent(Tab tab, int index) {
            super();
            this.tab = tab;
            this.index = index;
        }

        public Tab getTab() { return tab; }
        public int getIndex() { return index; }
    }
}