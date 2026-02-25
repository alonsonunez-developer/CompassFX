package com.compassfx.utils;

import javafx.scene.Node;

public class StylesheetHelper {

    public static void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }

    public static void removeStyleClass(Node node, String styleClass) {
        node.getStyleClass().remove(styleClass);
    }

    public static void toggleStyleClass(Node node, String styleClass) {
        if (node.getStyleClass().contains(styleClass)) {
            removeStyleClass(node, styleClass);
        } else {
            addStyleClass(node, styleClass);
        }
    }

    public static boolean hasStyleClass(Node node, String styleClass) {
        return node.getStyleClass().contains(styleClass);
    }
}