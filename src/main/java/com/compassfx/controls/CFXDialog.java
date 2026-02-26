package com.compassfx.controls;

import com.compassfx.enums.DialogSize;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * CompassFX Dialog - A Material Design inspired dialog/modal component
 * Supports customizable header, content, and actions with backdrop overlay
 */
public class CFXDialog {

    private static final String STYLESHEET = "/com/compassfx/controls/cfx-dialog.css";

    // Properties
    private final StringProperty title;
    private final ObjectProperty<Node> content;
    private final ObjectProperty<DialogSize> size;
    private final BooleanProperty closeOnBackdropClick;
    private final BooleanProperty showCloseButton;
    private final ObjectProperty<Node> headerGraphic;

    // UI Components
    private Stage dialogStage;
    private StackPane backdrop;
    private VBox dialogContainer;
    private HBox headerBox;
    private Label titleLabel;
    private Button closeButton;
    private StackPane contentContainer;
    private HBox actionBox;

    // Callback
    private Runnable onClose;

    /**
     * Creates a CFXDialog
     */
    public CFXDialog() {
        this.title = new SimpleStringProperty(this, "title", "");
        this.content = new SimpleObjectProperty<>(this, "content", null);
        this.size = new SimpleObjectProperty<>(this, "size", DialogSize.MEDIUM);
        this.closeOnBackdropClick = new SimpleBooleanProperty(this, "closeOnBackdropClick", true);
        this.showCloseButton = new SimpleBooleanProperty(this, "showCloseButton", true);
        this.headerGraphic = new SimpleObjectProperty<>(this, "headerGraphic", null);

        initializeComponents();
    }

    /**
     * Initialize dialog components
     */
    private void initializeComponents() {
        // Create backdrop
        backdrop = new StackPane();
        backdrop.getStyleClass().add("cfx-dialog-backdrop");
        backdrop.setAlignment(Pos.CENTER);

        // Create dialog container
        dialogContainer = new VBox();
        dialogContainer.getStyleClass().add("cfx-dialog-container");
        dialogContainer.setMaxWidth(size.get().getWidth());
        dialogContainer.setMinHeight(200);

        // Create header
        headerBox = new HBox(10);
        headerBox.getStyleClass().add("cfx-dialog-header");
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(20, 24, 16, 24));

        titleLabel = new Label();
        titleLabel.getStyleClass().add("cfx-dialog-title");
        titleLabel.textProperty().bind(title);

        closeButton = new Button("×");
        closeButton.getStyleClass().add("cfx-dialog-close-button");
        closeButton.setOnAction(e -> close());
        closeButton.managedProperty().bind(showCloseButton);
        closeButton.visibleProperty().bind(showCloseButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerBox.getChildren().addAll(titleLabel, spacer, closeButton);

        // Create content container
        contentContainer = new StackPane();
        contentContainer.getStyleClass().add("cfx-dialog-content");
        contentContainer.setPadding(new Insets(16, 24, 24, 24));
        VBox.setVgrow(contentContainer, Priority.ALWAYS);

        // Create action box
        actionBox = new HBox(10);
        actionBox.getStyleClass().add("cfx-dialog-actions");
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setPadding(new Insets(16, 24, 20, 24));
        actionBox.managedProperty().bind(actionBox.visibleProperty());

        // Assemble dialog
        dialogContainer.getChildren().addAll(headerBox, contentContainer, actionBox);
        backdrop.getChildren().add(dialogContainer);

        // Backdrop click handler
        backdrop.setOnMouseClicked(e -> {
            if (e.getTarget() == backdrop && closeOnBackdropClick.get()) {
                close();
            }
        });

        // Listen to property changes
        content.addListener((obs, oldVal, newVal) -> {
            contentContainer.getChildren().clear();
            if (newVal != null) {
                contentContainer.getChildren().add(newVal);
            }
        });

        size.addListener((obs, oldVal, newVal) -> {
            dialogContainer.setMaxWidth(newVal.getWidth());
        });

        headerGraphic.addListener((obs, oldVal, newVal) -> {
            headerBox.getChildren().remove(oldVal);
            if (newVal != null) {
                headerBox.getChildren().add(0, newVal);
            }
        });
    }

    /**
     * Show the dialog
     */
    public void show() {
        if (dialogStage != null && dialogStage.isShowing()) {
            return;
        }

        // Create stage
        dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        // Create scene
        Scene scene = new Scene(backdrop);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("WARNING: CFXDialog stylesheet not found: " + STYLESHEET);
        }

        dialogStage.setScene(scene);

        // Show with animation
        showWithAnimation();
    }

    /**
     * Show dialog with fade and scale animation
     */
    private void showWithAnimation() {
        // Initial state
        backdrop.setOpacity(0);
        dialogContainer.setScaleX(0.7);
        dialogContainer.setScaleY(0.7);

        dialogStage.show();

        // Backdrop fade in
        FadeTransition backdropFade = new FadeTransition(Duration.millis(200), backdrop);
        backdropFade.setFromValue(0);
        backdropFade.setToValue(1);

        // Dialog scale in
        ScaleTransition dialogScale = new ScaleTransition(Duration.millis(200), dialogContainer);
        dialogScale.setFromX(0.7);
        dialogScale.setFromY(0.7);
        dialogScale.setToX(1.0);
        dialogScale.setToY(1.0);

        backdropFade.play();
        dialogScale.play();
    }

    /**
     * Close the dialog
     */
    public void close() {
        if (dialogStage == null || !dialogStage.isShowing()) {
            return;
        }

        // Backdrop fade out
        FadeTransition backdropFade = new FadeTransition(Duration.millis(150), backdrop);
        backdropFade.setFromValue(1);
        backdropFade.setToValue(0);

        // Dialog scale out
        ScaleTransition dialogScale = new ScaleTransition(Duration.millis(150), dialogContainer);
        dialogScale.setFromX(1.0);
        dialogScale.setFromY(1.0);
        dialogScale.setToX(0.7);
        dialogScale.setToY(0.7);

        backdropFade.setOnFinished(e -> {
            dialogStage.close();
            if (onClose != null) {
                onClose.run();
            }
        });

        backdropFade.play();
        dialogScale.play();
    }

    /**
     * Add action buttons to the dialog
     * @param buttons Buttons to add to the action area
     */
    public void addActions(Node... buttons) {
        actionBox.getChildren().addAll(buttons);
        actionBox.setVisible(true);
    }

    /**
     * Clear all action buttons
     */
    public void clearActions() {
        actionBox.getChildren().clear();
        actionBox.setVisible(false);
    }

    /**
     * Set the callback to run when dialog closes
     * @param onClose Callback runnable
     */
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    // ===== Property Getters and Setters =====

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public Node getContent() {
        return content.get();
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public DialogSize getSize() {
        return size.get();
    }

    public void setSize(DialogSize size) {
        this.size.set(size);
    }

    public ObjectProperty<DialogSize> sizeProperty() {
        return size;
    }

    public boolean isCloseOnBackdropClick() {
        return closeOnBackdropClick.get();
    }

    public void setCloseOnBackdropClick(boolean closeOnBackdropClick) {
        this.closeOnBackdropClick.set(closeOnBackdropClick);
    }

    public BooleanProperty closeOnBackdropClickProperty() {
        return closeOnBackdropClick;
    }

    public boolean isShowCloseButton() {
        return showCloseButton.get();
    }

    public void setShowCloseButton(boolean showCloseButton) {
        this.showCloseButton.set(showCloseButton);
    }

    public BooleanProperty showCloseButtonProperty() {
        return showCloseButton;
    }

    public Node getHeaderGraphic() {
        return headerGraphic.get();
    }

    public void setHeaderGraphic(Node headerGraphic) {
        this.headerGraphic.set(headerGraphic);
    }

    public ObjectProperty<Node> headerGraphicProperty() {
        return headerGraphic;
    }
}
