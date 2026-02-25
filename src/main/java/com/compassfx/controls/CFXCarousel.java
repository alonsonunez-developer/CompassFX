package com.compassfx.controls;

import com.compassfx.enums.CarouselIndicatorStyle;
import com.compassfx.enums.CarouselTransition;
import com.compassfx.models.CarouselItem;
import com.compassfx.skins.CFXCarouselSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Duration;

/**
 * CompassFX Carousel - A Material Design inspired carousel component
 * Supports text, images, and custom content
 */
public class CFXCarousel extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-carousel";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-carousel.css";

    // Properties
    private final ObservableList<CarouselItem> items;
    private final IntegerProperty currentIndex;
    private final ObjectProperty<CarouselTransition> transition;
    private final ObjectProperty<CarouselIndicatorStyle> indicatorStyle;
    private final BooleanProperty autoPlay;
    private final ObjectProperty<Duration> autoPlayDelay;
    private final BooleanProperty loop;
    private final BooleanProperty showNavigation;
    private final BooleanProperty showIndicators;
    private final DoubleProperty itemWidth;
    private final DoubleProperty itemHeight;

    // Event handlers
    private ObjectProperty<EventHandler<CarouselEvent>> onSlideChange;

    public CFXCarousel() {
        this.items = FXCollections.observableArrayList();
        this.currentIndex = new SimpleIntegerProperty(this, "currentIndex", 0);
        this.transition = new SimpleObjectProperty<>(this, "transition", CarouselTransition.SLIDE);
        this.indicatorStyle = new SimpleObjectProperty<>(this, "indicatorStyle", CarouselIndicatorStyle.DOTS);
        this.autoPlay = new SimpleBooleanProperty(this, "autoPlay", false);
        this.autoPlayDelay = new SimpleObjectProperty<>(this, "autoPlayDelay", Duration.seconds(3));
        this.loop = new SimpleBooleanProperty(this, "loop", true);
        this.showNavigation = new SimpleBooleanProperty(this, "showNavigation", true);
        this.showIndicators = new SimpleBooleanProperty(this, "showIndicators", true);
        this.itemWidth = new SimpleDoubleProperty(this, "itemWidth", 600);
        this.itemHeight = new SimpleDoubleProperty(this, "itemHeight", 400);
        this.onSlideChange = new SimpleObjectProperty<>(this, "onSlideChange", null);

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
        transition.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Fire event when index changes
        currentIndex.addListener((obs, oldVal, newVal) -> {
            if (onSlideChange.get() != null && newVal.intValue() >= 0 && newVal.intValue() < items.size()) {
                CarouselEvent event = new CarouselEvent(items.get(newVal.intValue()), newVal.intValue());
                onSlideChange.get().handle(event);
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(transition.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXCarouselSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Navigation methods
    public void next() {
        if (canGoNext()) {
            currentIndex.set(currentIndex.get() + 1);
        } else if (loop.get() && !items.isEmpty()) {
            currentIndex.set(0);
        }
    }

    public void previous() {
        if (canGoPrevious()) {
            currentIndex.set(currentIndex.get() - 1);
        } else if (loop.get() && !items.isEmpty()) {
            currentIndex.set(items.size() - 1);
        }
    }

    public void goToSlide(int index) {
        if (index >= 0 && index < items.size()) {
            currentIndex.set(index);
        }
    }

    public boolean canGoNext() {
        return currentIndex.get() < items.size() - 1;
    }

    public boolean canGoPrevious() {
        return currentIndex.get() > 0;
    }

    // Helper methods
    public void addItem(CarouselItem item) {
        items.add(item);
    }

    public void addTextSlide(String title, String description) {
        items.add(CarouselItem.createTextSlide(title, description));
    }

    // Getters and Setters
    public ObservableList<CarouselItem> getItems() { return items; }

    public int getCurrentIndex() { return currentIndex.get(); }
    public void setCurrentIndex(int currentIndex) { this.currentIndex.set(currentIndex); }
    public IntegerProperty currentIndexProperty() { return currentIndex; }

    public CarouselTransition getTransition() { return transition.get(); }
    public void setTransition(CarouselTransition transition) { this.transition.set(transition); }
    public ObjectProperty<CarouselTransition> transitionProperty() { return transition; }

    public CarouselIndicatorStyle getIndicatorStyle() { return indicatorStyle.get(); }
    public void setIndicatorStyle(CarouselIndicatorStyle indicatorStyle) {
        this.indicatorStyle.set(indicatorStyle);
    }
    public ObjectProperty<CarouselIndicatorStyle> indicatorStyleProperty() { return indicatorStyle; }

    public boolean isAutoPlay() { return autoPlay.get(); }
    public void setAutoPlay(boolean autoPlay) { this.autoPlay.set(autoPlay); }
    public BooleanProperty autoPlayProperty() { return autoPlay; }

    public Duration getAutoPlayDelay() { return autoPlayDelay.get(); }
    public void setAutoPlayDelay(Duration autoPlayDelay) { this.autoPlayDelay.set(autoPlayDelay); }
    public ObjectProperty<Duration> autoPlayDelayProperty() { return autoPlayDelay; }

    public boolean isLoop() { return loop.get(); }
    public void setLoop(boolean loop) { this.loop.set(loop); }
    public BooleanProperty loopProperty() { return loop; }

    public boolean isShowNavigation() { return showNavigation.get(); }
    public void setShowNavigation(boolean showNavigation) { this.showNavigation.set(showNavigation); }
    public BooleanProperty showNavigationProperty() { return showNavigation; }

    public boolean isShowIndicators() { return showIndicators.get(); }
    public void setShowIndicators(boolean showIndicators) { this.showIndicators.set(showIndicators); }
    public BooleanProperty showIndicatorsProperty() { return showIndicators; }

    public double getItemWidth() { return itemWidth.get(); }
    public void setItemWidth(double itemWidth) { this.itemWidth.set(itemWidth); }
    public DoubleProperty itemWidthProperty() { return itemWidth; }

    public double getItemHeight() { return itemHeight.get(); }
    public void setItemHeight(double itemHeight) { this.itemHeight.set(itemHeight); }
    public DoubleProperty itemHeightProperty() { return itemHeight; }

    public EventHandler<CarouselEvent> getOnSlideChange() { return onSlideChange.get(); }
    public void setOnSlideChange(EventHandler<CarouselEvent> handler) { this.onSlideChange.set(handler); }
    public ObjectProperty<EventHandler<CarouselEvent>> onSlideChangeProperty() { return onSlideChange; }

    // Carousel Event Class
    public static class CarouselEvent extends ActionEvent {
        private final CarouselItem item;
        private final int index;

        public CarouselEvent(CarouselItem item, int index) {
            super();
            this.item = item;
            this.index = index;
        }

        public CarouselItem getItem() { return item; }
        public int getIndex() { return index; }
    }
}
