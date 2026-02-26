package com.compassfx.controls;

import com.compassfx.enums.BadgeColor;
import com.compassfx.enums.BadgePosition;
import com.compassfx.enums.BadgeVariant;
import com.compassfx.skins.CFXBadgeSkin;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * CompassFX Badge - A Material Design inspired badge component
 * Displays a badge (dot or number) on top of a child node
 */
public class CFXBadge extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-badge";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-badge.css";

    // Properties
    private final ObjectProperty<Node> content;
    private final StringProperty text;
    private final IntegerProperty value;
    private final IntegerProperty max;
    private final ObjectProperty<BadgeVariant> variant;
    private final ObjectProperty<BadgeColor> color;
    private final ObjectProperty<BadgePosition> position;
    private final BooleanProperty badgeVisible;
    private final BooleanProperty showZero;
    private final DoubleProperty offsetX;
    private final DoubleProperty offsetY;

    public CFXBadge() {
        this(null);
    }

    public CFXBadge(Node content) {
        this.content = new SimpleObjectProperty<>(this, "content", content);
        this.text = new SimpleStringProperty(this, "text", "");
        this.value = new SimpleIntegerProperty(this, "value", 0);
        this.max = new SimpleIntegerProperty(this, "max", 99);
        this.variant = new SimpleObjectProperty<>(this, "variant", BadgeVariant.STANDARD);
        this.color = new SimpleObjectProperty<>(this, "color", BadgeColor.ERROR);
        this.position = new SimpleObjectProperty<>(this, "position", BadgePosition.TOP_RIGHT);
        this.badgeVisible = new SimpleBooleanProperty(this, "badgeVisible", true);
        this.showZero = new SimpleBooleanProperty(this, "showZero", false);
        this.offsetX = new SimpleDoubleProperty(this, "offsetX", 0);
        this.offsetY = new SimpleDoubleProperty(this, "offsetY", 0);

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
        position.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Auto-hide badge when value is 0 and showZero is false
        value.addListener((obs, oldVal, newVal) -> {
            if (!showZero.get() && newVal.intValue() == 0) {
                badgeVisible.set(false);
            } else {
                badgeVisible.set(true);
            }
        });

        showZero.addListener((obs, oldVal, newVal) -> {
            if (!newVal && value.get() == 0) {
                badgeVisible.set(false);
            } else {
                badgeVisible.set(true);
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );

        getStyleClass().add(variant.get().getStyleClass());
        getStyleClass().add(color.get().getStyleClass());
        getStyleClass().add(position.get().getStyleClass());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXBadgeSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    /**
     * Get the badge display text
     * If text property is set, returns it
     * Otherwise returns the value (or value+ if exceeds max)
     */
    public String getBadgeText() {
        if (text.get() != null && !text.get().isEmpty()) {
            return text.get();
        }

        if (variant.get() == BadgeVariant.DOT) {
            return "";
        }

        int val = value.get();
        int maxVal = max.get();

        if (val > maxVal) {
            return maxVal + "+";
        }

        return String.valueOf(val);
    }

    // Getters and Setters
    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    public String getText() { return text.get(); }
    public void setText(String text) { this.text.set(text); }
    public StringProperty textProperty() { return text; }

    public int getValue() { return value.get(); }
    public void setValue(int value) { this.value.set(value); }
    public IntegerProperty valueProperty() { return value; }

    public int getMax() { return max.get(); }
    public void setMax(int max) { this.max.set(max); }
    public IntegerProperty maxProperty() { return max; }

    public BadgeVariant getVariant() { return variant.get(); }
    public void setVariant(BadgeVariant variant) { this.variant.set(variant); }
    public ObjectProperty<BadgeVariant> variantProperty() { return variant; }

    public BadgeColor getColor() { return color.get(); }
    public void setColor(BadgeColor color) { this.color.set(color); }
    public ObjectProperty<BadgeColor> colorProperty() { return color; }

    public BadgePosition getPosition() { return position.get(); }
    public void setPosition(BadgePosition position) { this.position.set(position); }
    public ObjectProperty<BadgePosition> positionProperty() { return position; }

    public boolean isBadgeVisible() { return badgeVisible.get(); }
    public void setBadgeVisible(boolean badgeVisible) { this.badgeVisible.set(badgeVisible); }
    public BooleanProperty badgeVisibleProperty() { return badgeVisible; }

    public boolean isShowZero() { return showZero.get(); }
    public void setShowZero(boolean showZero) { this.showZero.set(showZero); }
    public BooleanProperty showZeroProperty() { return showZero; }

    public double getOffsetX() { return offsetX.get(); }
    public void setOffsetX(double offsetX) { this.offsetX.set(offsetX); }
    public DoubleProperty offsetXProperty() { return offsetX; }

    public double getOffsetY() { return offsetY.get(); }
    public void setOffsetY(double offsetY) { this.offsetY.set(offsetY); }
    public DoubleProperty offsetYProperty() { return offsetY; }
}