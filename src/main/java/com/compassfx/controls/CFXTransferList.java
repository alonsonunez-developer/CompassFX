package com.compassfx.controls;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * CompassFX TransferList - Transfer items between two lists
 * Perfect for selecting multiple items, assigning permissions, etc.
 */
public class CFXTransferList<T> extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "cfx-transfer-list";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-transfer-list.css";

    // Properties
    private final StringProperty availableTitle;
    private final StringProperty selectedTitle;
    private final StringProperty searchPrompt;
    private final BooleanProperty searchable;
    private final BooleanProperty showCounts;
    private final IntegerProperty listHeight;

    // Dynamic title properties (for counts)
    private final StringProperty availableTitleWithCount;
    private final StringProperty selectedTitleWithCount;

    // Data
    private final ObjectProperty<ObservableList<T>> availableItems;
    private final ObjectProperty<ObservableList<T>> selectedItems;

    // UI Components
    private final VBox availableContainer;
    private final VBox selectedContainer;
    private final Label availableLabel;
    private final Label selectedLabel;
    private final TextField availableSearch;
    private final TextField selectedSearch;
    private final ListView<T> availableList;
    private final ListView<T> selectedList;
    private final VBox buttonsContainer;

    public CFXTransferList() {
        this.availableTitle = new SimpleStringProperty(this, "availableTitle", "Available");
        this.selectedTitle = new SimpleStringProperty(this, "selectedTitle", "Selected");
        this.searchPrompt = new SimpleStringProperty(this, "searchPrompt", "Search...");
        this.searchable = new SimpleBooleanProperty(this, "searchable", true);
        this.showCounts = new SimpleBooleanProperty(this, "showCounts", true);
        this.listHeight = new SimpleIntegerProperty(this, "listHeight", 400);

        // Dynamic titles with counts
        this.availableTitleWithCount = new SimpleStringProperty(this, "availableTitleWithCount", "Available");
        this.selectedTitleWithCount = new SimpleStringProperty(this, "selectedTitleWithCount", "Selected");

        this.availableItems = new SimpleObjectProperty<>(this, "availableItems",
                FXCollections.observableArrayList());
        this.selectedItems = new SimpleObjectProperty<>(this, "selectedItems",
                FXCollections.observableArrayList());

        // Create containers
        availableContainer = new VBox(8);
        selectedContainer = new VBox(8);
        availableLabel = new Label();
        selectedLabel = new Label();
        availableSearch = new TextField();
        selectedSearch = new TextField();
        availableList = new ListView<>();
        selectedList = new ListView<>();
        buttonsContainer = new VBox(10);

        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setSpacing(20);
        setPadding(new Insets(20));

        // Load stylesheet
        try {
            java.net.URL cssUrl = getClass().getResource(STYLESHEET);
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        setupAvailableList();
        setupSelectedList();
        setupButtons();
        setupLayout();
        setupListeners();
        updateCounts();
    }

    private void setupAvailableList() {
        availableContainer.getStyleClass().add("transfer-list-container");

        availableLabel.getStyleClass().add("transfer-list-title");
        availableLabel.textProperty().bind(availableTitleWithCount);  // Bind to dynamic property

        availableSearch.getStyleClass().add("transfer-search");
        availableSearch.promptTextProperty().bind(searchPrompt);
        availableSearch.setVisible(searchable.get());
        availableSearch.setManaged(searchable.get());

        availableList.getStyleClass().add("transfer-list-view");
        // IMPORTANT: Don't set items here - they will be set via property
        availableList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableList.prefHeightProperty().bind(listHeight);

        availableContainer.getChildren().addAll(availableLabel, availableSearch, availableList);
    }

    private void setupSelectedList() {
        selectedContainer.getStyleClass().add("transfer-list-container");

        selectedLabel.getStyleClass().add("transfer-list-title");
        selectedLabel.textProperty().bind(selectedTitleWithCount);  // Bind to dynamic property

        selectedSearch.getStyleClass().add("transfer-search");
        selectedSearch.promptTextProperty().bind(searchPrompt);
        selectedSearch.setVisible(searchable.get());
        selectedSearch.setManaged(searchable.get());

        selectedList.getStyleClass().add("transfer-list-view");
        // IMPORTANT: Don't set items here - they will be set via property
        selectedList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedList.prefHeightProperty().bind(listHeight);

        selectedContainer.getChildren().addAll(selectedLabel, selectedSearch, selectedList);
    }

    private void setupButtons() {
        buttonsContainer.getStyleClass().add("transfer-buttons");
        buttonsContainer.setAlignment(Pos.CENTER);

        // Move all right button
        Button moveAllRightBtn = createButton("»", "Move all right");
        moveAllRightBtn.setOnAction(e -> moveAllToSelected());

        // Move selected right button
        Button moveRightBtn = createButton("›", "Move selected right");
        moveRightBtn.setOnAction(e -> moveToSelected());

        // Move selected left button
        Button moveLeftBtn = createButton("‹", "Move selected left");
        moveLeftBtn.setOnAction(e -> moveToAvailable());

        // Move all left button
        Button moveAllLeftBtn = createButton("«", "Move all left");
        moveAllLeftBtn.setOnAction(e -> moveAllToAvailable());

        buttonsContainer.getChildren().addAll(
                moveAllRightBtn,
                moveRightBtn,
                moveLeftBtn,
                moveAllLeftBtn
        );
    }

    private Button createButton(String text, String tooltip) {
        Button btn = new Button(text);
        btn.getStyleClass().add("transfer-button");
        btn.setTooltip(new Tooltip(tooltip));
        btn.setPrefWidth(50);
        btn.setPrefHeight(40);
        return btn;
    }

    private void setupLayout() {
        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);

        HBox.setHgrow(availableContainer, Priority.ALWAYS);
        HBox.setHgrow(selectedContainer, Priority.ALWAYS);

        mainLayout.getChildren().addAll(
                availableContainer,
                buttonsContainer,
                selectedContainer
        );

        getChildren().add(mainLayout);
    }

    private void setupListeners() {
        // Bind list views to their item properties
        availableList.setItems(availableItems.get());
        selectedList.setItems(selectedItems.get());

        // Listen for property changes (when entire list is replaced)
        availableItems.addListener((obs, old, newVal) -> {
            availableList.setItems(newVal);
            availableSearch.clear();

            // Re-attach listener to the new list
            if (newVal != null) {
                newVal.addListener((javafx.collections.ListChangeListener<T>) c -> {
                    updateCounts();
                });
            }
            updateCounts();
        });

        selectedItems.addListener((obs, old, newVal) -> {
            selectedList.setItems(newVal);
            selectedSearch.clear();

            // Re-attach listener to the new list
            if (newVal != null) {
                newVal.addListener((javafx.collections.ListChangeListener<T>) c -> {
                    updateCounts();
                });
            }
            updateCounts();
        });

        // Initial listeners for the current lists
        availableItems.get().addListener((javafx.collections.ListChangeListener<T>) c -> {
            updateCounts();
        });

        selectedItems.get().addListener((javafx.collections.ListChangeListener<T>) c -> {
            updateCounts();
        });

        // Search functionality
        availableSearch.textProperty().addListener((obs, old, newText) -> {
            filterList(availableList, availableItems.get(), newText);
        });

        selectedSearch.textProperty().addListener((obs, old, newText) -> {
            filterList(selectedList, selectedItems.get(), newText);
        });

        // Searchable property
        searchable.addListener((obs, old, newVal) -> {
            availableSearch.setVisible(newVal);
            availableSearch.setManaged(newVal);
            selectedSearch.setVisible(newVal);
            selectedSearch.setManaged(newVal);
        });

        // Title changes should update counts
        availableTitle.addListener((obs, old, newVal) -> updateCounts());
        selectedTitle.addListener((obs, old, newVal) -> updateCounts());
        showCounts.addListener((obs, old, newVal) -> updateCounts());

        // Double click to transfer
        availableList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                T selected = availableList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    moveItemToSelected(selected);
                }
            }
        });

        selectedList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                T selected = selectedList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    moveItemToAvailable(selected);
                }
            }
        });
    }

    private void filterList(ListView<T> listView, ObservableList<T> originalItems, String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            listView.setItems(originalItems);
            return;
        }

        ObservableList<T> filtered = FXCollections.observableArrayList();
        String search = searchText.toLowerCase();

        for (T item : originalItems) {
            if (item.toString().toLowerCase().contains(search)) {
                filtered.add(item);
            }
        }

        listView.setItems(filtered);
    }

    private void updateCounts() {
        if (showCounts.get()) {
            availableTitleWithCount.set(availableTitle.get() + " (" + availableItems.get().size() + ")");
            selectedTitleWithCount.set(selectedTitle.get() + " (" + selectedItems.get().size() + ")");
        } else {
            availableTitleWithCount.set(availableTitle.get());
            selectedTitleWithCount.set(selectedTitle.get());
        }
    }

    // Transfer methods
    private void moveToSelected() {
        ObservableList<T> selected = FXCollections.observableArrayList(
                availableList.getSelectionModel().getSelectedItems()
        );

        for (T item : selected) {
            moveItemToSelected(item);
        }

        availableList.getSelectionModel().clearSelection();
    }

    private void moveToAvailable() {
        ObservableList<T> selected = FXCollections.observableArrayList(
                selectedList.getSelectionModel().getSelectedItems()
        );

        for (T item : selected) {
            moveItemToAvailable(item);
        }

        selectedList.getSelectionModel().clearSelection();
    }

    private void moveAllToSelected() {
        ObservableList<T> all = FXCollections.observableArrayList(availableItems.get());

        for (T item : all) {
            moveItemToSelected(item);
        }
    }

    private void moveAllToAvailable() {
        ObservableList<T> all = FXCollections.observableArrayList(selectedItems.get());

        for (T item : all) {
            moveItemToAvailable(item);
        }
    }

    private void moveItemToSelected(T item) {
        if (availableItems.get().remove(item)) {
            selectedItems.get().add(item);

            // Refilter lists if search is active
            refreshFilteredLists();
        }
    }

    private void moveItemToAvailable(T item) {
        if (selectedItems.get().remove(item)) {
            availableItems.get().add(item);

            // Refilter lists if search is active
            refreshFilteredLists();
        }
    }

    private void refreshFilteredLists() {
        // Refresh available list if search is active
        String availableSearchText = availableSearch.getText();
        if (availableSearchText != null && !availableSearchText.isEmpty()) {
            filterList(availableList, availableItems.get(), availableSearchText);
        }

        // Refresh selected list if search is active
        String selectedSearchText = selectedSearch.getText();
        if (selectedSearchText != null && !selectedSearchText.isEmpty()) {
            filterList(selectedList, selectedItems.get(), selectedSearchText);
        }
    }

    // Getters and Setters
    public String getAvailableTitle() { return availableTitle.get(); }
    public void setAvailableTitle(String title) { this.availableTitle.set(title); }
    public StringProperty availableTitleProperty() { return availableTitle; }

    public String getSelectedTitle() { return selectedTitle.get(); }
    public void setSelectedTitle(String title) { this.selectedTitle.set(title); }
    public StringProperty selectedTitleProperty() { return selectedTitle; }

    public String getSearchPrompt() { return searchPrompt.get(); }
    public void setSearchPrompt(String prompt) { this.searchPrompt.set(prompt); }
    public StringProperty searchPromptProperty() { return searchPrompt; }

    public boolean isSearchable() { return searchable.get(); }
    public void setSearchable(boolean searchable) { this.searchable.set(searchable); }
    public BooleanProperty searchableProperty() { return searchable; }

    public boolean isShowCounts() { return showCounts.get(); }
    public void setShowCounts(boolean showCounts) { this.showCounts.set(showCounts); }
    public BooleanProperty showCountsProperty() { return showCounts; }

    public int getListHeight() { return listHeight.get(); }
    public void setListHeight(int height) { this.listHeight.set(height); }
    public IntegerProperty listHeightProperty() { return listHeight; }

    public ObservableList<T> getAvailableItems() { return availableItems.get(); }
    public void setAvailableItems(ObservableList<T> items) { this.availableItems.set(items); }
    public ObjectProperty<ObservableList<T>> availableItemsProperty() { return availableItems; }

    public ObservableList<T> getSelectedItems() { return selectedItems.get(); }
    public void setSelectedItems(ObservableList<T> items) { this.selectedItems.set(items); }
    public ObjectProperty<ObservableList<T>> selectedItemsProperty() { return selectedItems; }
}