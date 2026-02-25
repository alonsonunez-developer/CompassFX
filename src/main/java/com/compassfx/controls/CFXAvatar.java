// ============================================
// CFXAvatar.java - Avatar Control
// src/main/java/com/compassfx/controls/CFXAvatar.java
// ============================================
package com.compassfx.controls;

import com.compassfx.enums.AvatarShape;
import com.compassfx.enums.AvatarSize;
import com.compassfx.enums.AvatarVariant;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * CompassFX Avatar - A versatile avatar component
 * Supports images, initials, icons, and colors
 */
public class CFXAvatar extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-avatar";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-avatar.css";

    // Properties
    private final ObjectProperty<Image> image;
    private final StringProperty initials;
    private final ObjectProperty<AvatarSize> size;
    private final ObjectProperty<AvatarShape> shape;
    private final ObjectProperty<AvatarVariant> variant;
    private final ObjectProperty<Color> color;
    private final BooleanProperty showBorder;
    private final ObjectProperty<Color> borderColor;
    private final DoubleProperty borderWidth;
    private final BooleanProperty showStatus;
    private final ObjectProperty<Color> statusColor;

    // UI Components
    private final StackPane container;
    private final ImageView imageView;
    private final Text initialsText;
    private final Circle statusIndicator;

    public CFXAvatar() {
        this("");
    }

    public CFXAvatar(String initials) {
        this.image = new SimpleObjectProperty<>(this, "image", null);
        this.initials = new SimpleStringProperty(this, "initials", initials);
        this.size = new SimpleObjectProperty<>(this, "size", AvatarSize.MEDIUM);
        this.shape = new SimpleObjectProperty<>(this, "shape", AvatarShape.CIRCLE);
        this.variant = new SimpleObjectProperty<>(this, "variant", AvatarVariant.FILLED);
        this.color = new SimpleObjectProperty<>(this, "color", Color.web("#2196F3"));
        this.showBorder = new SimpleBooleanProperty(this, "showBorder", false);
        this.borderColor = new SimpleObjectProperty<>(this, "borderColor", Color.WHITE);
        this.borderWidth = new SimpleDoubleProperty(this, "borderWidth", 2);
        this.showStatus = new SimpleBooleanProperty(this, "showStatus", false);
        this.statusColor = new SimpleObjectProperty<>(this, "statusColor", Color.web("#4CAF50"));

        // Create container
        container = new StackPane();
        container.getStyleClass().add("avatar-container");

        // Create image view
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setVisible(false);

        // Create initials text
        initialsText = new Text();
        initialsText.setFill(Color.WHITE);
        initialsText.getStyleClass().add("avatar-initials");

        // Create status indicator
        statusIndicator = new Circle(5);
        statusIndicator.getStyleClass().add("status-indicator");
        statusIndicator.setVisible(false);
        StackPane.setAlignment(statusIndicator, Pos.BOTTOM_RIGHT);

        container.getChildren().addAll(imageView, initialsText);
        getChildren().addAll(container, statusIndicator);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.CENTER);

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
        updateSize();
        updateContent();
        updateShape();
        updateColors();
        updateBorder();
        updateStatus();

        // Listeners
        size.addListener((obs, old, newVal) -> {
            updateStyleClasses();
            updateSize();
        });
        shape.addListener((obs, old, newVal) -> {
            updateStyleClasses();
            updateShape();
        });
        variant.addListener((obs, old, newVal) -> {
            updateStyleClasses();
            updateColors();
        });
        image.addListener((obs, old, newVal) -> updateContent());
        initials.addListener((obs, old, newVal) -> updateContent());
        color.addListener((obs, old, newVal) -> updateColors());
        showBorder.addListener((obs, old, newVal) -> updateBorder());
        borderColor.addListener((obs, old, newVal) -> updateBorder());
        borderWidth.addListener((obs, old, newVal) -> updateBorder());
        showStatus.addListener((obs, old, newVal) -> updateStatus());
        statusColor.addListener((obs, old, newVal) -> updateStatus());
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(size.get().getStyleClass());
        getStyleClass().add(shape.get().getStyleClass());
        getStyleClass().add(variant.get().getStyleClass());
    }

    private void updateSize() {
        double avatarSize = size.get().getSize();
        container.setPrefSize(avatarSize, avatarSize);
        container.setMinSize(avatarSize, avatarSize);
        container.setMaxSize(avatarSize, avatarSize);

        imageView.setFitWidth(avatarSize);
        imageView.setFitHeight(avatarSize);

        // Update font size based on avatar size
        double fontSize = avatarSize * 0.4;
        initialsText.setFont(Font.font("System", FontWeight.BOLD, fontSize));

        // Update status indicator size
        double statusSize = avatarSize * 0.15;
        statusIndicator.setRadius(statusSize);
        statusIndicator.setTranslateX(avatarSize * 0.35);
        statusIndicator.setTranslateY(avatarSize * 0.35);
    }

    private void updateContent() {
        if (image.get() != null) {
            imageView.setImage(image.get());
            imageView.setVisible(true);
            initialsText.setVisible(false);
        } else {
            imageView.setVisible(false);
            initialsText.setVisible(true);
            initialsText.setText(getDisplayInitials());
        }
    }

    private String getDisplayInitials() {
        String text = initials.get();
        if (text == null || text.isEmpty()) {
            return "?";
        }

        // Get first 2 characters/words
        String[] parts = text.trim().split("\\s+");
        if (parts.length >= 2) {
            return (parts[0].charAt(0) + "" + parts[1].charAt(0)).toUpperCase();
        } else if (text.length() >= 2) {
            return text.substring(0, 2).toUpperCase();
        } else {
            return text.toUpperCase();
        }
    }

    private void updateShape() {
        container.setClip(null);

        double avatarSize = size.get().getSize();

        switch (shape.get()) {
            case CIRCLE:
                Circle circle = new Circle(avatarSize / 2);
                circle.setCenterX(avatarSize / 2);
                circle.setCenterY(avatarSize / 2);
                container.setClip(circle);
                break;
            case ROUNDED:
                Rectangle rounded = new Rectangle(avatarSize, avatarSize);
                rounded.setArcWidth(avatarSize * 0.25);
                rounded.setArcHeight(avatarSize * 0.25);
                container.setClip(rounded);
                break;
            case SQUARE:
                Rectangle square = new Rectangle(avatarSize, avatarSize);
                container.setClip(square);
                break;
        }
    }

    private void updateColors() {
        Color bgColor = color.get();

        switch (variant.get()) {
            case FILLED:
                container.setStyle(String.format(
                        "-fx-background-color: rgb(%d, %d, %d);",
                        (int)(bgColor.getRed() * 255),
                        (int)(bgColor.getGreen() * 255),
                        (int)(bgColor.getBlue() * 255)
                ));
                initialsText.setFill(Color.WHITE);
                break;
            case OUTLINED:
                container.setStyle(String.format(
                        "-fx-background-color: transparent; " +
                                "-fx-border-color: rgb(%d, %d, %d); " +
                                "-fx-border-width: 2px;",
                        (int)(bgColor.getRed() * 255),
                        (int)(bgColor.getGreen() * 255),
                        (int)(bgColor.getBlue() * 255)
                ));
                initialsText.setFill(bgColor);
                break;
            case LIGHT:
                container.setStyle(String.format(
                        "-fx-background-color: rgba(%d, %d, %d, 0.1);",
                        (int)(bgColor.getRed() * 255),
                        (int)(bgColor.getGreen() * 255),
                        (int)(bgColor.getBlue() * 255)
                ));
                initialsText.setFill(bgColor);
                break;
        }
    }

    private void updateBorder() {
        if (showBorder.get()) {
            Color border = borderColor.get();
            double width = borderWidth.get();

            String currentStyle = container.getStyle();
            String borderStyle = String.format(
                    "-fx-border-color: rgb(%d, %d, %d); -fx-border-width: %.1fpx;",
                    (int)(border.getRed() * 255),
                    (int)(border.getGreen() * 255),
                    (int)(border.getBlue() * 255),
                    width
            );

            if (!currentStyle.contains("-fx-border-color")) {
                container.setStyle(currentStyle + " " + borderStyle);
            }
        }
    }

    private void updateStatus() {
        statusIndicator.setVisible(showStatus.get());
        statusIndicator.setFill(statusColor.get());
    }

    // Getters and Setters
    public Image getImage() { return image.get(); }
    public void setImage(Image image) { this.image.set(image); }
    public ObjectProperty<Image> imageProperty() { return image; }

    public String getInitials() { return initials.get(); }
    public void setInitials(String initials) { this.initials.set(initials); }
    public StringProperty initialsProperty() { return initials; }

    public AvatarSize getAvatarSize() { return size.get(); }
    public void setAvatarSize(AvatarSize size) { this.size.set(size); }
    public ObjectProperty<AvatarSize> avatarSizeProperty() { return size; }

    public AvatarShape getAvatarShape() { return shape.get(); }
    public void setAvatarShape(AvatarShape shape) { this.shape.set(shape); }
    public ObjectProperty<AvatarShape> avatarShapeProperty() { return shape; }

    public AvatarVariant getVariant() { return variant.get(); }
    public void setVariant(AvatarVariant variant) { this.variant.set(variant); }
    public ObjectProperty<AvatarVariant> variantProperty() { return variant; }

    public Color getColor() { return color.get(); }
    public void setColor(Color color) { this.color.set(color); }
    public ObjectProperty<Color> colorProperty() { return color; }

    public boolean isShowBorder() { return showBorder.get(); }
    public void setShowBorder(boolean showBorder) { this.showBorder.set(showBorder); }
    public BooleanProperty showBorderProperty() { return showBorder; }

    public Color getBorderColor() { return borderColor.get(); }
    public void setBorderColor(Color borderColor) { this.borderColor.set(borderColor); }
    public ObjectProperty<Color> borderColorProperty() { return borderColor; }

    public double getBorderWidth() { return borderWidth.get(); }
    public void setBorderWidth(double borderWidth) { this.borderWidth.set(borderWidth); }
    public DoubleProperty borderWidthProperty() { return borderWidth; }

    public boolean isShowStatus() { return showStatus.get(); }
    public void setShowStatus(boolean showStatus) { this.showStatus.set(showStatus); }
    public BooleanProperty showStatusProperty() { return showStatus; }

    public Color getStatusColor() { return statusColor.get(); }
    public void setStatusColor(Color statusColor) { this.statusColor.set(statusColor); }
    public ObjectProperty<Color> statusColorProperty() { return statusColor; }
}