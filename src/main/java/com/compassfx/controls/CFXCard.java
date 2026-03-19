package com.compassfx.controls;

import com.compassfx.enums.CardElevation;
import com.compassfx.enums.CardVariant;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * CompassFX Card - A Material Design inspired card component
 * Supports header, content, footer, elevation levels, and flip animation
 */
public class CFXCard extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "cfx-card";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-card.css";

    // Properties
    private final ObjectProperty<CardElevation> elevation;
    private final ObjectProperty<CardVariant> variant;
    private final BooleanProperty hoverable;
    private final BooleanProperty darkMode;
    private final ObjectProperty<Node> header;
    private final ObjectProperty<Node> content;
    private final ObjectProperty<Node> footer;
    private final ObjectProperty<Node> media;

    // Containers
    private final VBox headerContainer;
    private final VBox mediaContainer;
    private final VBox contentContainer;
    private final VBox footerContainer;

    public CFXCard() {
        this.elevation = new SimpleObjectProperty<>(this, "elevation", CardElevation.MEDIUM);
        this.variant = new SimpleObjectProperty<>(this, "variant", CardVariant.FILLED);
        this.hoverable = new SimpleBooleanProperty(this, "hoverable", false);
        this.darkMode = new SimpleBooleanProperty(this, "darkMode", false);
        this.header = new SimpleObjectProperty<>(this, "header", null);
        this.content = new SimpleObjectProperty<>(this, "content", null);
        this.footer = new SimpleObjectProperty<>(this, "footer", null);
        this.media = new SimpleObjectProperty<>(this, "media", null);

        // Create containers
        this.headerContainer = new VBox();
        this.mediaContainer = new VBox();
        this.contentContainer = new VBox();
        this.footerContainer = new VBox();

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load CFXCard stylesheet: " + e.getMessage());
        }

        // Setup containers
        headerContainer.getStyleClass().add("cfx-card-header");
        headerContainer.managedProperty().bind(headerContainer.visibleProperty());

        mediaContainer.getStyleClass().add("cfx-card-media");
        mediaContainer.managedProperty().bind(mediaContainer.visibleProperty());
        mediaContainer.setPadding(Insets.EMPTY);

        contentContainer.getStyleClass().add("cfx-card-content");
        contentContainer.managedProperty().bind(contentContainer.visibleProperty());

        footerContainer.getStyleClass().add("cfx-card-footer");
        footerContainer.managedProperty().bind(footerContainer.visibleProperty());

        // Add containers to card
        getChildren().addAll(mediaContainer, headerContainer, contentContainer, footerContainer);

        updateStyleClasses();
        setupListeners();
    }

    private void setupListeners() {
        elevation.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        variant.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        hoverable.addListener((obs, oldVal, newVal) -> updateStyleClasses());
        darkMode.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        header.addListener((obs, oldVal, newVal) -> {
            headerContainer.getChildren().clear();
            if (newVal != null) {
                headerContainer.getChildren().add(newVal);
                headerContainer.setVisible(true);
            } else {
                headerContainer.setVisible(false);
            }
        });

        content.addListener((obs, oldVal, newVal) -> {
            contentContainer.getChildren().clear();
            if (newVal != null) {
                contentContainer.getChildren().add(newVal);
                contentContainer.setVisible(true);
            } else {
                contentContainer.setVisible(false);
            }
        });

        footer.addListener((obs, oldVal, newVal) -> {
            footerContainer.getChildren().clear();
            if (newVal != null) {
                footerContainer.getChildren().add(newVal);
                footerContainer.setVisible(true);
            } else {
                footerContainer.setVisible(false);
            }
        });

        media.addListener((obs, oldVal, newVal) -> {
            mediaContainer.getChildren().clear();
            if (newVal != null) {
                mediaContainer.getChildren().add(newVal);
                mediaContainer.setVisible(true);
            } else {
                mediaContainer.setVisible(false);
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS) &&
                        !styleClass.equals("vbox")
        );

        getStyleClass().add(elevation.get().getStyleClass());
        getStyleClass().add(variant.get().getStyleClass());

        if (hoverable.get()) {
            getStyleClass().add("hoverable");
        }

        if (darkMode.get()) {
            getStyleClass().add("dark");
        }
    }

    // Getters and Setters
    public CardElevation getElevation() { return elevation.get(); }
    public void setElevation(CardElevation elevation) { this.elevation.set(elevation); }
    public ObjectProperty<CardElevation> elevationProperty() { return elevation; }

    public CardVariant getVariant() { return variant.get(); }
    public void setVariant(CardVariant variant) { this.variant.set(variant); }
    public ObjectProperty<CardVariant> variantProperty() { return variant; }

    public boolean isHoverable() { return hoverable.get(); }
    public void setHoverable(boolean hoverable) { this.hoverable.set(hoverable); }
    public BooleanProperty hoverableProperty() { return hoverable; }

    public boolean isDarkMode() { return darkMode.get(); }
    public void setDarkMode(boolean darkMode) { this.darkMode.set(darkMode); }
    public BooleanProperty darkModeProperty() { return darkMode; }

    public Node getHeader() { return header.get(); }
    public void setHeader(Node header) { this.header.set(header); }
    public ObjectProperty<Node> headerProperty() { return header; }

    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    public Node getFooter() { return footer.get(); }
    public void setFooter(Node footer) { this.footer.set(footer); }
    public ObjectProperty<Node> footerProperty() { return footer; }

    public Node getMedia() { return media.get(); }
    public void setMedia(Node media) { this.media.set(media); }
    public ObjectProperty<Node> mediaProperty() { return media; }
}