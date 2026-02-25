package com.compassfx.controls;

import com.compassfx.enums.SortOrder;
import com.compassfx.enums.TableDensity;
import com.compassfx.enums.TableSelectionMode;
import com.compassfx.models.TableColumn;
import com.compassfx.skins.CFXTableSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.ArrayList;
import java.util.List;

/**
 * CompassFX Table - A Material Design inspired data table component
 * @param <T> The type of data items
 */
public class CFXTable<T> extends Control {

    private static final String DEFAULT_STYLE_CLASS = "cfx-table";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-table.css";

    // Properties
    private final ObservableList<TableColumn<T>> columns;
    private final ObservableList<T> items;
    private final ObservableList<T> filteredItems;
    private final ObservableList<T> selectedItems;
    private final ObjectProperty<TableSelectionMode> selectionMode;
    private final ObjectProperty<TableDensity> density;
    private final BooleanProperty showHeader;
    private final BooleanProperty striped;
    private final BooleanProperty hoverable;
    private final BooleanProperty bordered;
    private final StringProperty emptyMessage;
    private final BooleanProperty showPagination;
    private final IntegerProperty itemsPerPage;
    private final IntegerProperty currentPage;

    // Event handlers
    private ObjectProperty<EventHandler<RowEvent<T>>> onRowClick;
    private ObjectProperty<EventHandler<RowEvent<T>>> onRowDoubleClick;
    private ObjectProperty<EventHandler<SelectionEvent<T>>> onSelectionChange;

    public CFXTable() {
        this.columns = FXCollections.observableArrayList();
        this.items = FXCollections.observableArrayList();
        this.filteredItems = FXCollections.observableArrayList();
        this.selectedItems = FXCollections.observableArrayList();
        this.selectionMode = new SimpleObjectProperty<>(this, "selectionMode", TableSelectionMode.SINGLE);
        this.density = new SimpleObjectProperty<>(this, "density", TableDensity.STANDARD);
        this.showHeader = new SimpleBooleanProperty(this, "showHeader", true);
        this.striped = new SimpleBooleanProperty(this, "striped", false);
        this.hoverable = new SimpleBooleanProperty(this, "hoverable", true);
        this.bordered = new SimpleBooleanProperty(this, "bordered", true);
        this.emptyMessage = new SimpleStringProperty(this, "emptyMessage", "No data available");
        this.showPagination = new SimpleBooleanProperty(this, "showPagination", false);
        this.itemsPerPage = new SimpleIntegerProperty(this, "itemsPerPage", 10);
        this.currentPage = new SimpleIntegerProperty(this, "currentPage", 0);
        this.onRowClick = new SimpleObjectProperty<>(this, "onRowClick", null);
        this.onRowDoubleClick = new SimpleObjectProperty<>(this, "onRowDoubleClick", null);
        this.onSelectionChange = new SimpleObjectProperty<>(this, "onSelectionChange", null);

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
        density.addListener((obs, oldVal, newVal) -> updateStyleClasses());

        // Update filtered items when items change
        items.addListener((javafx.collections.ListChangeListener.Change<? extends T> c) -> {
            updateFilteredItems();
        });

        // Reset to first page when items change
        filteredItems.addListener((javafx.collections.ListChangeListener.Change<? extends T> c) -> {
            if (currentPage.get() > 0) {
                currentPage.set(0);
            }
        });
    }

    private void updateStyleClasses() {
        getStyleClass().removeIf(styleClass ->
                !styleClass.equals(DEFAULT_STYLE_CLASS)
        );
        getStyleClass().add(density.get().getStyleClass());

        if (striped.get()) {
            getStyleClass().add("striped");
        }
        if (hoverable.get()) {
            getStyleClass().add("hoverable");
        }
        if (bordered.get()) {
            getStyleClass().add("bordered");
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CFXTableSkin<>(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    // Helper methods
    public void addColumn(TableColumn<T> column) {
        columns.add(column);
    }

    public void addColumn(String header, String propertyName) {
        columns.add(new TableColumn<>(header, propertyName));
    }

    public void addColumn(String header, String propertyName, double width) {
        columns.add(new TableColumn<>(header, propertyName, width));
    }

    public void setData(List<T> data) {
        items.setAll(data);
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
        selectedItems.remove(item);
    }

    public void clearSelection() {
        selectedItems.clear();
        fireSelectionChange();
    }

    public void selectItem(T item) {
        if (selectionMode.get() == TableSelectionMode.NONE) {
            return;
        }

        if (selectionMode.get() == TableSelectionMode.SINGLE) {
            selectedItems.clear();
        }

        if (!selectedItems.contains(item)) {
            selectedItems.add(item);
            fireSelectionChange();
        }
    }

    public void deselectItem(T item) {
        if (selectedItems.remove(item)) {
            fireSelectionChange();
        }
    }

    public void toggleSelection(T item) {
        if (selectedItems.contains(item)) {
            deselectItem(item);
        } else {
            selectItem(item);
        }
    }

    public boolean isSelected(T item) {
        return selectedItems.contains(item);
    }

    public void sort(TableColumn<T> column, SortOrder order) {
        // Clear other column sorts
        for (TableColumn<T> col : columns) {
            if (col != column) {
                col.setSortOrder(SortOrder.NONE);
            }
        }

        column.setSortOrder(order);
        updateFilteredItems();
    }

    public void filter(String searchText) {
        updateFilteredItems(searchText);
    }

    private void updateFilteredItems() {
        updateFilteredItems(null);
    }

    private void updateFilteredItems(String searchText) {
        List<T> result = new ArrayList<>(items);

        // Apply search filter
        if (searchText != null && !searchText.trim().isEmpty()) {
            String search = searchText.toLowerCase();
            result.removeIf(item -> {
                for (TableColumn<T> column : columns) {
                    String value = getCellValue(item, column);
                    if (value != null && value.toLowerCase().contains(search)) {
                        return false;
                    }
                }
                return true;
            });
        }

        // Apply sorting
        for (TableColumn<T> column : columns) {
            if (column.getSortOrder() != SortOrder.NONE) {
                result.sort((a, b) -> {
                    if (column.getComparator() != null) {
                        int comparison = column.getComparator().compare(a, b);
                        return column.getSortOrder() == SortOrder.ASCENDING ? comparison : -comparison;
                    } else {
                        String valueA = getCellValue(a, column);
                        String valueB = getCellValue(b, column);
                        int comparison = compareStrings(valueA, valueB);
                        return column.getSortOrder() == SortOrder.ASCENDING ? comparison : -comparison;
                    }
                });
                break; // Only sort by first sorted column
            }
        }

        filteredItems.setAll(result);
    }

    private String getCellValue(T item, TableColumn<T> column) {
        if (column.getCellValueFactory() != null) {
            return column.getCellValueFactory().call(item);
        }

        // Default: try to get property value via reflection
        try {
            String propertyName = column.getPropertyName();
            java.lang.reflect.Method method = item.getClass().getMethod(
                    "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1)
            );
            Object value = method.invoke(item);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private int compareStrings(String a, String b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;

        // Try numeric comparison first
        try {
            double numA = Double.parseDouble(a);
            double numB = Double.parseDouble(b);
            return Double.compare(numA, numB);
        } catch (NumberFormatException e) {
            return a.compareToIgnoreCase(b);
        }
    }

    private void fireSelectionChange() {
        if (onSelectionChange.get() != null) {
            SelectionEvent<T> event = new SelectionEvent<>(new ArrayList<>(selectedItems));
            onSelectionChange.get().handle(event);
        }
    }

    public int getTotalPages() {
        if (!showPagination.get() || itemsPerPage.get() <= 0) {
            return 1;
        }
        return (int) Math.ceil((double) filteredItems.size() / itemsPerPage.get());
    }

    public List<T> getCurrentPageItems() {
        if (!showPagination.get()) {
            return new ArrayList<>(filteredItems);
        }

        int start = currentPage.get() * itemsPerPage.get();
        int end = Math.min(start + itemsPerPage.get(), filteredItems.size());

        if (start >= filteredItems.size()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(filteredItems.subList(start, end));
    }

    // Getters and Setters
    public ObservableList<TableColumn<T>> getColumns() { return columns; }
    public ObservableList<T> getItems() { return items; }
    public ObservableList<T> getFilteredItems() { return filteredItems; }
    public ObservableList<T> getSelectedItems() { return selectedItems; }

    public TableSelectionMode getSelectionMode() { return selectionMode.get(); }
    public void setSelectionMode(TableSelectionMode selectionMode) { this.selectionMode.set(selectionMode); }
    public ObjectProperty<TableSelectionMode> selectionModeProperty() { return selectionMode; }

    public TableDensity getDensity() { return density.get(); }
    public void setDensity(TableDensity density) { this.density.set(density); }
    public ObjectProperty<TableDensity> densityProperty() { return density; }

    public boolean isShowHeader() { return showHeader.get(); }
    public void setShowHeader(boolean showHeader) { this.showHeader.set(showHeader); }
    public BooleanProperty showHeaderProperty() { return showHeader; }

    public boolean isStriped() { return striped.get(); }
    public void setStriped(boolean striped) { this.striped.set(striped); updateStyleClasses(); }
    public BooleanProperty stripedProperty() { return striped; }

    public boolean isHoverable() { return hoverable.get(); }
    public void setHoverable(boolean hoverable) { this.hoverable.set(hoverable); updateStyleClasses(); }
    public BooleanProperty hoverableProperty() { return hoverable; }

    public boolean isBordered() { return bordered.get(); }
    public void setBordered(boolean bordered) { this.bordered.set(bordered); updateStyleClasses(); }
    public BooleanProperty borderedProperty() { return bordered; }

    public String getEmptyMessage() { return emptyMessage.get(); }
    public void setEmptyMessage(String emptyMessage) { this.emptyMessage.set(emptyMessage); }
    public StringProperty emptyMessageProperty() { return emptyMessage; }

    public boolean isShowPagination() { return showPagination.get(); }
    public void setShowPagination(boolean showPagination) { this.showPagination.set(showPagination); }
    public BooleanProperty showPaginationProperty() { return showPagination; }

    public int getItemsPerPage() { return itemsPerPage.get(); }
    public void setItemsPerPage(int itemsPerPage) { this.itemsPerPage.set(itemsPerPage); }
    public IntegerProperty itemsPerPageProperty() { return itemsPerPage; }

    public int getCurrentPage() { return currentPage.get(); }
    public void setCurrentPage(int currentPage) { this.currentPage.set(currentPage); }
    public IntegerProperty currentPageProperty() { return currentPage; }

    public EventHandler<RowEvent<T>> getOnRowClick() { return onRowClick.get(); }
    public void setOnRowClick(EventHandler<RowEvent<T>> handler) { this.onRowClick.set(handler); }
    public ObjectProperty<EventHandler<RowEvent<T>>> onRowClickProperty() { return onRowClick; }

    public EventHandler<RowEvent<T>> getOnRowDoubleClick() { return onRowDoubleClick.get(); }
    public void setOnRowDoubleClick(EventHandler<RowEvent<T>> handler) { this.onRowDoubleClick.set(handler); }
    public ObjectProperty<EventHandler<RowEvent<T>>> onRowDoubleClickProperty() { return onRowDoubleClick; }

    public EventHandler<SelectionEvent<T>> getOnSelectionChange() { return onSelectionChange.get(); }
    public void setOnSelectionChange(EventHandler<SelectionEvent<T>> handler) {
        this.onSelectionChange.set(handler);
    }
    public ObjectProperty<EventHandler<SelectionEvent<T>>> onSelectionChangeProperty() {
        return onSelectionChange;
    }

    // Event Classes
    public static class RowEvent<T> extends ActionEvent {
        private final T item;

        public RowEvent(T item) {
            super();
            this.item = item;
        }

        public T getItem() { return item; }
    }

    public static class SelectionEvent<T> extends ActionEvent {
        private final List<T> selectedItems;

        public SelectionEvent(List<T> selectedItems) {
            super();
            this.selectedItems = selectedItems;
        }

        public List<T> getSelectedItems() { return selectedItems; }
    }
}
