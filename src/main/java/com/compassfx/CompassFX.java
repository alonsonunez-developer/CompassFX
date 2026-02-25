package com.compassfx;

import javafx.scene.Scene;
import java.net.URL;

public class CompassFX {

    private static final String TOKENS_CSS = "/com/compassfx/themes/tokens.css";
    private static final String LIGHT_THEME_CSS = "/com/compassfx/themes/material-light.css";
    private static final String DARK_THEME_CSS = "/com/compassfx/themes/material-dark.css";

    public static void applyLightTheme(Scene scene) {
        scene.getStylesheets().clear();

        // Try to load tokens.css
        URL tokensUrl = CompassFX.class.getResource(TOKENS_CSS);
        if (tokensUrl != null) {
            scene.getStylesheets().add(tokensUrl.toExternalForm());
        } else {
            System.out.println("INFO: tokens.css not found, skipping...");
        }

        // Try to load material-light.css
        URL lightUrl = CompassFX.class.getResource(LIGHT_THEME_CSS);
        if (lightUrl != null) {
            scene.getStylesheets().add(lightUrl.toExternalForm());
        } else {
            System.out.println("INFO: material-light.css not found, skipping...");
        }
    }

    public static void applyDarkTheme(Scene scene) {
        scene.getStylesheets().clear();

        URL tokensUrl = CompassFX.class.getResource(TOKENS_CSS);
        if (tokensUrl != null) {
            scene.getStylesheets().add(tokensUrl.toExternalForm());
        }

        URL lightUrl = CompassFX.class.getResource(LIGHT_THEME_CSS);
        if (lightUrl != null) {
            scene.getStylesheets().add(lightUrl.toExternalForm());
        }

        URL darkUrl = CompassFX.class.getResource(DARK_THEME_CSS);
        if (darkUrl != null) {
            scene.getStylesheets().add(darkUrl.toExternalForm());
            scene.getRoot().getStyleClass().add("dark-theme");
        } else {
            System.out.println("INFO: material-dark.css not found, skipping...");
        }
    }

    public static String getVersion() {
        return "1.0.0";
    }
}