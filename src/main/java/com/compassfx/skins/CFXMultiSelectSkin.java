package com.compassfx.skins;

import com.compassfx.controls.CFXMultiSelect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;

/**
 * Skin for CFXMultiSelect
 * Shows chips for selected items and dropdown with checkboxes
 */
public class CFXMultiSelectSkin<T> extends SkinBase<CFXMultiSelect<T>> {

    private final VBox container;
    private final Label floatingLabel;
    private final StackPane fieldContainer;
    private final FlowPane chipsContainer;
    private final Label promptLabel;
    private final Region dropdownArrow;
    private final Popup popup;
    private final VBox popupContent;
    private final ScrollPane popupScroll;
    private final CheckBox selectAllCheckbox;

    public CFXMultiSelectSkin(CFXMultiSelect<T> control) {
        super(control);

        // Main container
        container = new VBox(4);
        container.getStyleClass().add("multiselect-container");

        // Floating label
        floatingLabel = new Label();
        floatingLabel.getStyleClass().add("multiselect-label");
        floatingLabel.textProperty().bind(control.labelProperty());
        floatingLabel.setVisible(false);
        floatingLabel.setManaged(false);

        // Field container (the main box)
        fieldContainer = new StackPane();
        fieldContainer.getStyleClass().add("multiselect-field");
        fieldContainer.setMinHeight(48);

        // Chips container (shows selected items)
        chipsContainer = new FlowPane(6, 6);
        chipsContainer.getStyleClass().add("chips-container");
        chipsContainer.setPadding(new Insets(8, 40, 8, 12));
        chipsContainer.setAlignment(Pos.CENTER_LEFT);

        // Prompt text
        promptLabel = new Label();
        promptLabel.getStyleClass().add("multiselect-prompt");
        promptLabel.textProperty().bind(control.promptTextProperty());
        StackPane.setAlignment(promptLabel, Pos.CENTER_LEFT);
        StackPane.setMargin(promptLabel, new Insets(0, 0, 0, 12));

        // Dropdown arrow
        dropdownArrow = createDropdownArrow();
        dropdownArrow.getStyleClass().add("dropdown-arrow");
        StackPane.setAlignment(dropdownArrow, Pos.CENTER_RIGHT);
        StackPane.setMargin(dropdownArrow, new Insets(0, 12, 0, 0));

        fieldContainer.getChildren().addAll(chipsContainer, promptLabel, dropdownArrow);

        // Popup (dropdown menu)
        popup = new Popup();
        popup.setAutoHide(true);

        popupContent = new VBox(4);
        popupContent.getStyleClass().add("multiselect-popup");
        popupContent.setPadding(new Insets(8));
        popupContent.setMinWidth(300);

        // Select All checkbox
        selectAllCheckbox = new CheckBox("Select All");
        selectAllCheckbox.getStyleClass().add("select-all-checkbox");
        selectAllCheckbox.setVisible(control.isSelectAllEnabled());
        selectAllCheckbox.setManaged(control.isSelectAllEnabled());

        popupScroll = new ScrollPane(popupContent);
        popupScroll.setFitToWidth(true);
        popupScroll.setMaxHeight(300);
        popupScroll.getStyleClass().add("multiselect-scroll");

        popup.getContent().add(popupScroll);

        container.getChildren().addAll(floatingLabel, fieldContainer);
        getChildren().add(container);

        // Setup interactions
        setupFieldClick();
        setupSelectAll();
        updateChips();
        updatePromptVisibility();

        // Listeners
        control.selectedItemsProperty().addListener((obs, old, newVal) -> {
            updateChips();
            updatePromptVisibility();
            updateSelectAllState();
        });

        control.itemsProperty().addListener((obs, old, newVal) -> {
            updatePopupItems();
            updateSelectAllState();
        });

        control.labelProperty().addListener((obs, old, newVal) -> {
            boolean hasLabel = newVal != null && !newVal.isEmpty();
            floatingLabel.setVisible(hasLabel);
            floatingLabel.setManaged(hasLabel);
        });

        control.selectAllEnabledProperty().addListener((obs, old, newVal) -> {
            selectAllCheckbox.setVisible(newVal);
            selectAllCheckbox.setManaged(newVal);
        });

        updatePopupItems();
    }

    private void setupFieldClick() {
        fieldContainer.setOnMouseClicked(e -> {
            if (popup.isShowing()) {
                popup.hide();
            } else {
                showPopup();
            }
        });
    }

    private void showPopup() {
        double x = fieldContainer.localToScreen(fieldContainer.getBoundsInLocal()).getMinX();
        double y = fieldContainer.localToScreen(fieldContainer.getBoundsInLocal()).getMaxY() + 4;

        popupContent.setPrefWidth(fieldContainer.getWidth());
        popup.show(fieldContainer, x, y);
    }

    private void setupSelectAll() {
        selectAllCheckbox.setOnAction(e -> {
            if (selectAllCheckbox.isSelected()) {
                getSkinnable().getSelectedItems().setAll(getSkinnable().getItems());
            } else {
                getSkinnable().getSelectedItems().clear();
            }
        });
    }

    private void updatePopupItems() {
        popupContent.getChildren().clear();

        if (getSkinnable().isSelectAllEnabled() && !getSkinnable().getItems().isEmpty()) {
            popupContent.getChildren().add(selectAllCheckbox);
            popupContent.getChildren().add(new Separator());
        }

        for (T item : getSkinnable().getItems()) {
            CheckBox checkbox = new CheckBox(item.toString());
            checkbox.getStyleClass().add("multiselect-item");
            checkbox.setSelected(getSkinnable().getSelectedItems().contains(item));

            checkbox.selectedProperty().addListener((obs, old, newVal) -> {
                if (newVal) {
                    if (!getSkinnable().getSelectedItems().contains(item)) {
                        getSkinnable().getSelectedItems().add(item);
                    }
                } else {
                    getSkinnable().getSelectedItems().remove(item);
                }
            });

            popupContent.getChildren().add(checkbox);
        }

        updateSelectAllState();
    }

    private void updateSelectAllState() {
        if (!getSkinnable().isSelectAllEnabled()) return;

        int totalItems = getSkinnable().getItems().size();
        int selectedCount = getSkinnable().getSelectedItems().size();

        if (selectedCount == 0) {
            selectAllCheckbox.setSelected(false);
            selectAllCheckbox.setIndeterminate(false);
        } else if (selectedCount == totalItems) {
            selectAllCheckbox.setSelected(true);
            selectAllCheckbox.setIndeterminate(false);
        } else {
            selectAllCheckbox.setIndeterminate(true);
        }
    }

    private void updateChips() {
        chipsContainer.getChildren().clear();

        int maxDisplay = getSkinnable().getMaxChipsDisplay();
        int selectedCount = getSkinnable().getSelectedItems().size();
        int displayCount = Math.min(maxDisplay, selectedCount);

        for (int i = 0; i < displayCount; i++) {
            T item = getSkinnable().getSelectedItems().get(i);
            HBox chip = createChip(item);
            chipsContainer.getChildren().add(chip);
        }

        // Show "+N more" if there are more items
        if (selectedCount > maxDisplay) {
            Label moreLabel = new Label("+" + (selectedCount - maxDisplay) + " more");
            moreLabel.getStyleClass().add("more-chip");
            chipsContainer.getChildren().add(moreLabel);
        }
    }

    private HBox createChip(T item) {
        HBox chip = new HBox(6);
        chip.getStyleClass().add("multiselect-chip");
        chip.setAlignment(Pos.CENTER);
        chip.setPadding(new Insets(4, 8, 4, 8));

        Label label = new Label(item.toString());
        label.getStyleClass().add("chip-label");

        Label closeBtn = new Label("×");
        closeBtn.getStyleClass().add("chip-close");
        closeBtn.setOnMouseClicked(e -> {
            getSkinnable().getSelectedItems().remove(item);
            e.consume();
        });

        chip.getChildren().addAll(label, closeBtn);
        return chip;
    }

    private void updatePromptVisibility() {
        boolean hasSelection = !getSkinnable().getSelectedItems().isEmpty();
        promptLabel.setVisible(!hasSelection);
        promptLabel.setManaged(!hasSelection);
    }

    private Region createDropdownArrow() {
        SVGPath arrow = new SVGPath();
        arrow.setContent("M 0 0 L 4 5 L 8 0 Z");
        arrow.setFill(Color.web("#757575"));

        Region region = new Region();
        region.setShape(arrow);
        region.setMinSize(8, 5);
        region.setPrefSize(8, 5);
        region.setMaxSize(8, 5);
        region.setStyle("-fx-background-color: #757575;");

        return region;
    }
}