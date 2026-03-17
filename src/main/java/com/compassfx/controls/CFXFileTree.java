package com.compassfx.controls;

import com.compassfx.models.FileTreeItem;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * CompassFX File Tree - A Material Design file/folder tree component
 * with dynamic icons based on file types
 */
public class CFXFileTree extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "cfx-file-tree";
    private static final String STYLESHEET = "/com/compassfx/controls/cfx-file-tree.css";

    // Properties
    private final ObjectProperty<FileTreeItem> root;
    private final BooleanProperty showRoot;
    private final DoubleProperty indentSize;
    private final ObjectProperty<FileTreeItem> selectedItem;

    // UI Components
    private final VBox contentContainer;
    private final ScrollPane scrollPane;

    // Callbacks
    private OnFileTreeItemHandler onItemSelected;
    private OnFileTreeItemHandler onItemDoubleClicked;

    // Functional interface for callbacks
    @FunctionalInterface
    public interface OnFileTreeItemHandler {
        void handle(FileTreeItem item);
    }

    public CFXFileTree() {
        this.root = new SimpleObjectProperty<>(this, "root", null);
        this.showRoot = new SimpleBooleanProperty(this, "showRoot", true);
        this.indentSize = new SimpleDoubleProperty(this, "indentSize", 20);
        this.selectedItem = new SimpleObjectProperty<>(this, "selectedItem", null);

        this.contentContainer = new VBox(2);
        this.scrollPane = new ScrollPane(contentContainer);

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
            System.err.println("ERROR: " + e.getMessage());
        }

        // Setup scroll pane
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.getStyleClass().add("file-tree-scroll");

        contentContainer.setPadding(new Insets(10));

        getChildren().add(scrollPane);

        setupListeners();
    }

    private void setupListeners() {
        root.addListener((obs, old, newRoot) -> {
            rebuildTree();
        });

        showRoot.addListener((obs, old, newVal) -> {
            rebuildTree();
        });
    }

    private void rebuildTree() {
        contentContainer.getChildren().clear();

        if (root.get() == null) {
            return;
        }

        if (showRoot.get()) {
            // Show root item
            VBox rootNode = createTreeNode(root.get(), 0);
            contentContainer.getChildren().add(rootNode);
        } else {
            // Show only children of root
            for (FileTreeItem child : root.get().getChildren()) {
                VBox childNode = createTreeNode(child, 0);
                contentContainer.getChildren().add(childNode);
            }
        }
    }

    private VBox createTreeNode(FileTreeItem item, int level) {
        VBox nodeContainer = new VBox(2);

        // Create the item row
        HBox itemRow = new HBox(8);
        itemRow.setAlignment(Pos.CENTER_LEFT);
        itemRow.setPadding(new Insets(6, 10, 6, level * indentSize.get() + 10));
        itemRow.getStyleClass().add("tree-item-row");

        // Expand/collapse icon (if folder with children)
        Region expandIcon = new Region();
        expandIcon.setPrefSize(16, 16);
        expandIcon.setMinSize(16, 16);
        expandIcon.setMaxSize(16, 16);

        if (item.isFolder() && item.hasChildren()) {
            expandIcon.getStyleClass().add("expand-icon");
            if (item.isExpanded()) {
                expandIcon.getStyleClass().add("expanded");
            }
            expandIcon.setStyle("-fx-cursor: hand;");
        } else {
            expandIcon.setStyle("-fx-opacity: 0;"); // Invisible placeholder
        }

        // File/folder icon
        Node icon = createIcon(item);

        // Name label
        Label nameLabel = new Label(item.getName());
        nameLabel.getStyleClass().add("tree-item-label");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        itemRow.getChildren().addAll(expandIcon, icon, nameLabel);

        // Click handlers
        itemRow.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                selectItem(item, itemRow);

                // Toggle expand on click if folder
                if (item.isFolder() && item.hasChildren()) {
                    item.setExpanded(!item.isExpanded());
                    rebuildTree();
                }

                if (onItemSelected != null) {
                    onItemSelected.handle(item);
                }
            } else if (e.getClickCount() == 2) {
                if (onItemDoubleClicked != null) {
                    onItemDoubleClicked.handle(item);
                }
            }
            e.consume();
        });

        nodeContainer.getChildren().add(itemRow);

        // Add children if expanded
        if (item.isExpanded() && item.hasChildren()) {
            for (FileTreeItem child : item.getChildren()) {
                VBox childNode = createTreeNode(child, level + 1);
                nodeContainer.getChildren().add(childNode);
            }
        }

        return nodeContainer;
    }

    private void selectItem(FileTreeItem item, HBox row) {
        // Clear previous selection
        contentContainer.getChildren().forEach(node -> {
            clearSelection(node);
        });

        // Set new selection
        selectedItem.set(item);
        row.getStyleClass().add("selected");
    }

    private void clearSelection(Node node) {
        if (node instanceof VBox) {
            VBox vbox = (VBox) node;
            for (Node child : vbox.getChildren()) {
                if (child instanceof HBox) {
                    child.getStyleClass().remove("selected");
                }
                clearSelection(child);
            }
        }
    }

    private Node createIcon(FileTreeItem item) {
        if (item.getCustomIcon() != null) {
            return item.getCustomIcon();
        }

        // Create SVG icon based on type
        SVGPath icon = new SVGPath();
        icon.getStyleClass().add("tree-item-icon");

        switch (item.getType()) {
            case FOLDER:
                icon.setContent("M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z");
                icon.setFill(Color.web("#FFA726"));
                break;

            case FILE_CODE:
            case FILE_JAVA:
            case FILE_JAVASCRIPT:
            case FILE_PYTHON:
                icon.setContent("M9.4 16.6L4.8 12l4.6-4.6L8 6l-6 6 6 6 1.4-1.4zm5.2 0l4.6-4.6-4.6-4.6L16 6l6 6-6 6-1.4-1.4z");
                icon.setFill(Color.web("#42A5F5"));
                break;

            case FILE_IMAGE:
                icon.setContent("M21 19V5c0-1.1-.9-2-2-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2zM8.5 13.5l2.5 3.01L14.5 12l4.5 6H5l3.5-4.5z");
                icon.setFill(Color.web("#66BB6A"));
                break;

            case FILE_VIDEO:
                icon.setContent("M17 10.5V7c0-.55-.45-1-1-1H4c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1v-3.5l4 4v-11l-4 4z");
                icon.setFill(Color.web("#EF5350"));
                break;

            case FILE_AUDIO:
                icon.setContent("M12 3v9.28c-.47-.17-.97-.28-1.5-.28C8.01 12 6 14.01 6 16.5S8.01 21 10.5 21c2.31 0 4.2-1.75 4.45-4H15V6h4V3h-7z");
                icon.setFill(Color.web("#AB47BC"));
                break;

            case FILE_PDF:
                icon.setContent("M20 2H8c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm-8.5 7.5c0 .83-.67 1.5-1.5 1.5H9v2H7.5V7H10c.83 0 1.5.67 1.5 1.5v1zm5 2c0 .83-.67 1.5-1.5 1.5h-2.5V7H15c.83 0 1.5.67 1.5 1.5v3zm4-3H19v1h1.5V11H19v2h-1.5V7h3v1.5zM9 9.5h1v-1H9v1zM4 6H2v14c0 1.1.9 2 2 2h14v-2H4V6zm10 5.5h1v-3h-1v3z");
                icon.setFill(Color.web("#E53935"));
                break;

            case FILE_ZIP:
                icon.setContent("M20 6h-8l-2-2H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2zm-2 6h-2v2h2v2h-2v2h-2v-2h2v-2h-2v-2h2v-2h-2V8h2v2h2v2z");
                icon.setFill(Color.web("#FFB74D"));
                break;

            case FILE_JSON:
            case FILE_XML:
                icon.setContent("M14.6 16.6l4.6-4.6-4.6-4.6L16 6l6 6-6 6-1.4-1.4zm-5.2 0L4.8 12l4.6-4.6L8 6l-6 6 6 6 1.4-1.4z");
                icon.setFill(Color.web("#FFA726"));
                break;

            case FILE_CSS:
                icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 5-5 1.41 1.41L7.83 12l3.58 3.59L10 17zm4 0l-1.41-1.41L16.17 12l-3.58-3.59L14 7l5 5-5 5z");
                icon.setFill(Color.web("#42A5F5"));
                break;

            case FILE_HTML:
                icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-.5 15v-2h1v2h-1zm0-4v-2h1v2h-1zm0-4V7h1v2h-1z");
                icon.setFill(Color.web("#FF7043"));
                break;

            case FILE_MARKDOWN:
                icon.setContent("M3 3h18v18H3V3zm15 6l-5 5-5-5h3V7h4v2h3z");
                icon.setFill(Color.web("#78909C"));
                break;

            case FILE_TEXT:
                icon.setContent("M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zm2 16H8v-2h8v2zm0-4H8v-2h8v2zm-3-5V3.5L18.5 9H13z");
                icon.setFill(Color.web("#90A4AE"));
                break;

            default: // FILE
                icon.setContent("M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zm2 16H8v-2h8v2zm0-4H8v-2h8v2zm-3-5V3.5L18.5 9H13z");
                icon.setFill(Color.web("#BDBDBD"));
                break;
        }

        icon.setScaleX(0.8);
        icon.setScaleY(0.8);

        return icon;
    }

    // Public methods
    public void expandAll() {
        if (root.get() != null) {
            expandNode(root.get());
            rebuildTree();
        }
    }

    public void collapseAll() {
        if (root.get() != null) {
            collapseNode(root.get());
            rebuildTree();
        }
    }

    private void expandNode(FileTreeItem item) {
        item.setExpanded(true);
        for (FileTreeItem child : item.getChildren()) {
            expandNode(child);
        }
    }

    private void collapseNode(FileTreeItem item) {
        item.setExpanded(false);
        for (FileTreeItem child : item.getChildren()) {
            collapseNode(child);
        }
    }

    // Getters and Setters
    public FileTreeItem getRoot() { return root.get(); }
    public void setRoot(FileTreeItem root) { this.root.set(root); }
    public ObjectProperty<FileTreeItem> rootProperty() { return root; }

    public boolean isShowRoot() { return showRoot.get(); }
    public void setShowRoot(boolean showRoot) { this.showRoot.set(showRoot); }
    public BooleanProperty showRootProperty() { return showRoot; }

    public double getIndentSize() { return indentSize.get(); }
    public void setIndentSize(double indentSize) { this.indentSize.set(indentSize); }
    public DoubleProperty indentSizeProperty() { return indentSize; }

    public FileTreeItem getSelectedItem() { return selectedItem.get(); }
    public ObjectProperty<FileTreeItem> selectedItemProperty() { return selectedItem; }

    public void setOnItemSelected(OnFileTreeItemHandler handler) {
        this.onItemSelected = handler;
    }

    public void setOnItemDoubleClicked(OnFileTreeItemHandler handler) {
        this.onItemDoubleClicked = handler;
    }
}