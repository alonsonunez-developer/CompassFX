package com.compassfx.controls;

import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.ButtonSize;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.skins.CFXButtonSkin;
import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;

import com.compassfx.enums.ButtonColor;
import com.compassfx.enums.ButtonSize;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.skins.CFXButtonSkin;
import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;

/**
 * CompassFX Button - A Material Design inspired button component
 */
public class CFXButton extends Button {

    private static final String DEFAULT_STYLE_CLASS = "cfx-button";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-button.css";

    // Properties
    private final ObjectProperty<ButtonVariant> variant;
    private final ObjectProperty<ButtonSize> size;
    private final ObjectProperty<ButtonColor> color;
    private final BooleanProperty fullWidth;
    private final BooleanProperty iconButton;
    private final BooleanProperty rippleEnabled;

    public CFXButton() {
        this("");
    }

    public CFXButton(String text) {
        super(text);

        this.variant = new SimpleObjectProperty<>(this, "variant", ButtonVariant.CONTAINED);
        this.size = new SimpleObjectProperty<>(this, "size", ButtonSize.MEDIUM);
        this.color = new SimpleObjectProperty<>(this, "color", ButtonColor.PRIMARY);
        this.fullWidth = new SimpleBooleanProperty(this, "fullWidth", false);
        this.iconButton = new SimpleBooleanProperty(this, "iconButton", false);
        this.rippleEnabled = new SimpleBooleanProperty(this, "rippleEnabled", true);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet with better error handling
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
        size.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        color.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        fullWidth.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        iconButton.addListener((obs, oldVal, newVal) -> updateStyleClasses());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("button")
        );

        getStyleClass().add(variant.get().getStyleClass());
        getStyleClass().add(size.get().getStyleClass());

        if (color.get() != ButtonColor.PRIMARY) {
            getStyleClass().add(color.get().getStyleClass());
        }

        if (fullWidth.get()) {
            getStyleClass().add("full-width");
        }

        if (iconButton.get()) {
            getStyleClass().add("icon");
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXButtonSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Getters and Setters
    public ButtonVariant getVariant() { return variant.get(); }
    public void setVariant(ButtonVariant variant) { this.variant.set(variant); }
    public ObjectProperty<ButtonVariant> variantProperty() { return variant; }

    public ButtonSize getSize() { return size.get(); }
    public void setSize(ButtonSize size) { this.size.set(size); }
    public ObjectProperty<ButtonSize> sizeProperty() { return size; }

    public ButtonColor getColor() { return color.get(); }
    public void setColor(ButtonColor color) { this.color.set(color); }
    public ObjectProperty<ButtonColor> colorProperty() { return color; }

    public boolean isFullWidth() { return fullWidth.get(); }
    public void setFullWidth(boolean fullWidth) { this.fullWidth.set(fullWidth); }
    public BooleanProperty fullWidthProperty() { return fullWidth; }

    public boolean isIconButton() { return iconButton.get(); }
    public void setIconButton(boolean iconButton) { this.iconButton.set(iconButton); }
    public BooleanProperty iconButtonProperty() { return iconButton; }

    public boolean isRippleEnabled() { return rippleEnabled.get(); }
    public void setRippleEnabled(boolean rippleEnabled) { this.rippleEnabled.set(rippleEnabled); }
    public BooleanProperty rippleEnabledProperty() { return rippleEnabled; }
}