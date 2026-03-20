package com.compassfx.demo;

import com.compassfx.CompassFX;
import com.compassfx.controls.*;
import com.compassfx.enums.ButtonVariant;
import com.compassfx.enums.FileTreeItemType;
import com.compassfx.models.FileTreeItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FileTreeDemo {

    private Label selectedLabel;
    private Label eventLog;

    public void showDemo(Label title, VBox root) {
        // ====================================
        // Project Structure Tree
        // ====================================
        Label projectLabel = new Label("Project Structure");
        projectLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        CFXFileTree projectTree = new CFXFileTree();
        projectTree.setPrefHeight(500);
        projectTree.setRoot(createProjectStructure());
        projectTree.setShowRoot(true);

        // Event handlers
        projectTree.setOnItemSelected(item -> {
            selectedLabel.setText("Selected: " + item.getName());
        });

        projectTree.setOnItemDoubleClicked(item -> {
            eventLog.setText("Double-clicked: " + item.getName());
        });

        // ====================================
        // Controls
        // ====================================
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);

        CFXButton expandAll = new CFXButton("Expand All");
        expandAll.setVariant(ButtonVariant.OUTLINED);
        expandAll.setOnAction(e -> projectTree.expandAll());

        CFXButton collapseAll = new CFXButton("Collapse All");
        collapseAll.setVariant(ButtonVariant.OUTLINED);
        collapseAll.setOnAction(e -> projectTree.collapseAll());

        CFXButton toggleRoot = new CFXButton("Toggle Root");
        toggleRoot.setVariant(ButtonVariant.TEXT);
        toggleRoot.setOnAction(e -> {
            projectTree.setShowRoot(!projectTree.isShowRoot());
        });

        controls.getChildren().addAll(expandAll, collapseAll, toggleRoot);

        // ====================================
        // Status
        // ====================================
        VBox statusBox = new VBox(10);
        statusBox.setPadding(new Insets(15));
        statusBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E0E0E0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 4px; " +
                        "-fx-background-radius: 4px;"
        );

        selectedLabel = new Label("Selected: None");
        selectedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 500;");

        eventLog = new Label("Event log: waiting...");
        eventLog.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        statusBox.getChildren().addAll(selectedLabel, eventLog);

        // ====================================
        // Side by side comparison
        // ====================================
        Label comparisonLabel = new Label("Different File Systems");
        comparisonLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600;");

        HBox treesBox = new HBox(20);
        treesBox.setAlignment(Pos.TOP_CENTER);

        // Documents tree
        VBox docsBox = new VBox(10);
        Label docsLabel = new Label("Documents");
        docsLabel.setStyle("-fx-font-weight: 600;");
        CFXFileTree docsTree = new CFXFileTree();
        docsTree.setPrefHeight(300);
        docsTree.setPrefWidth(300);
        docsTree.setRoot(createDocumentsStructure());
        docsTree.setShowRoot(false);
        docsBox.getChildren().addAll(docsLabel, docsTree);

        // Media tree
        VBox mediaBox = new VBox(10);
        Label mediaLabel = new Label("Media Library");
        mediaLabel.setStyle("-fx-font-weight: 600;");
        CFXFileTree mediaTree = new CFXFileTree();
        mediaTree.setPrefHeight(300);
        mediaTree.setPrefWidth(300);
        mediaTree.setRoot(createMediaStructure());
        mediaTree.setShowRoot(false);
        mediaBox.getChildren().addAll(mediaLabel, mediaTree);

        treesBox.getChildren().addAll(docsBox, mediaBox);

        // Info
        Label info = new Label("💡 Click to select, double-click for action");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-padding: 10px; " +
                "-fx-background-color: #E3F2FD; -fx-background-radius: 8px;");

        root.getChildren().addAll(
                title,
                info,
                new Separator(),
                projectLabel,
                projectTree,
                controls,
                statusBox,
                new Separator(),
                comparisonLabel,
                treesBox
        );
    }


    private FileTreeItem createProjectStructure() {
        FileTreeItem root = new FileTreeItem("my-awesome-app", FileTreeItemType.FOLDER);

        // src folder
        FileTreeItem src = new FileTreeItem("src", FileTreeItemType.FOLDER);

        FileTreeItem main = new FileTreeItem("main", FileTreeItemType.FOLDER);
        FileTreeItem java = new FileTreeItem("java", FileTreeItemType.FOLDER);
        FileTreeItem com = new FileTreeItem("com", FileTreeItemType.FOLDER);
        FileTreeItem app = new FileTreeItem("app", FileTreeItemType.FOLDER);

        app.addChild(new FileTreeItem("Main.java", FileTreeItemType.FILE_JAVA));
        app.addChild(new FileTreeItem("Controller.java", FileTreeItemType.FILE_JAVA));
        app.addChild(new FileTreeItem("Service.java", FileTreeItemType.FILE_JAVA));

        FileTreeItem models = new FileTreeItem("models", FileTreeItemType.FOLDER);
        models.addChild(new FileTreeItem("User.java", FileTreeItemType.FILE_JAVA));
        models.addChild(new FileTreeItem("Product.java", FileTreeItemType.FILE_JAVA));
        app.addChild(models);

        com.addChild(app);
        java.addChild(com);

        FileTreeItem resources = new FileTreeItem("resources", FileTreeItemType.FOLDER);
        resources.addChild(new FileTreeItem("application.properties", FileTreeItemType.FILE_TEXT));
        resources.addChild(new FileTreeItem("styles.css", FileTreeItemType.FILE_CSS));
        resources.addChild(new FileTreeItem("config.json", FileTreeItemType.FILE_JSON));

        main.addChild(java);
        main.addChild(resources);

        FileTreeItem test = new FileTreeItem("test", FileTreeItemType.FOLDER);
        FileTreeItem testJava = new FileTreeItem("java", FileTreeItemType.FOLDER);
        testJava.addChild(new FileTreeItem("MainTest.java", FileTreeItemType.FILE_JAVA));
        testJava.addChild(new FileTreeItem("ServiceTest.java", FileTreeItemType.FILE_JAVA));
        test.addChild(testJava);

        src.addChild(main);
        src.addChild(test);

        // Other project files
        root.addChild(src);
        root.addChild(new FileTreeItem("pom.xml", FileTreeItemType.FILE_XML));
        root.addChild(new FileTreeItem("README.md", FileTreeItemType.FILE_MARKDOWN));
        root.addChild(new FileTreeItem("package.json", FileTreeItemType.FILE_JSON));
        root.addChild(new FileTreeItem(".gitignore", FileTreeItemType.FILE_TEXT));

        return root;
    }

    private FileTreeItem createDocumentsStructure() {
        FileTreeItem root = new FileTreeItem("Documents", FileTreeItemType.FOLDER);

        FileTreeItem work = new FileTreeItem("Work", FileTreeItemType.FOLDER);
        work.addChild(new FileTreeItem("Presentation.pdf", FileTreeItemType.FILE_PDF));
        work.addChild(new FileTreeItem("Report_Q4.pdf", FileTreeItemType.FILE_PDF));
        work.addChild(new FileTreeItem("Notes.txt", FileTreeItemType.FILE_TEXT));

        FileTreeItem personal = new FileTreeItem("Personal", FileTreeItemType.FOLDER);
        personal.addChild(new FileTreeItem("Resume.pdf", FileTreeItemType.FILE_PDF));
        personal.addChild(new FileTreeItem("Letter.txt", FileTreeItemType.FILE_TEXT));

        FileTreeItem projects = new FileTreeItem("Projects", FileTreeItemType.FOLDER);
        projects.addChild(new FileTreeItem("Website.html", FileTreeItemType.FILE_HTML));
        projects.addChild(new FileTreeItem("styles.css", FileTreeItemType.FILE_CSS));
        projects.addChild(new FileTreeItem("script.js", FileTreeItemType.FILE_JAVASCRIPT));

        root.addChild(work);
        root.addChild(personal);
        root.addChild(projects);

        return root;
    }

    private FileTreeItem createMediaStructure() {
        FileTreeItem root = new FileTreeItem("Media", FileTreeItemType.FOLDER);

        FileTreeItem photos = new FileTreeItem("Photos", FileTreeItemType.FOLDER);
        FileTreeItem vacation = new FileTreeItem("Vacation 2024", FileTreeItemType.FOLDER);
        vacation.addChild(new FileTreeItem("beach.jpg", FileTreeItemType.FILE_IMAGE));
        vacation.addChild(new FileTreeItem("sunset.jpg", FileTreeItemType.FILE_IMAGE));
        vacation.addChild(new FileTreeItem("family.jpg", FileTreeItemType.FILE_IMAGE));
        photos.addChild(vacation);
        photos.addChild(new FileTreeItem("profile.png", FileTreeItemType.FILE_IMAGE));

        FileTreeItem videos = new FileTreeItem("Videos", FileTreeItemType.FOLDER);
        videos.addChild(new FileTreeItem("tutorial.mp4", FileTreeItemType.FILE_VIDEO));
        videos.addChild(new FileTreeItem("presentation.mp4", FileTreeItemType.FILE_VIDEO));

        FileTreeItem music = new FileTreeItem("Music", FileTreeItemType.FOLDER);
        FileTreeItem playlist = new FileTreeItem("Favorites", FileTreeItemType.FOLDER);
        playlist.addChild(new FileTreeItem("song1.mp3", FileTreeItemType.FILE_AUDIO));
        playlist.addChild(new FileTreeItem("song2.mp3", FileTreeItemType.FILE_AUDIO));
        playlist.addChild(new FileTreeItem("song3.mp3", FileTreeItemType.FILE_AUDIO));
        music.addChild(playlist);

        FileTreeItem archives = new FileTreeItem("Archives", FileTreeItemType.FOLDER);
        archives.addChild(new FileTreeItem("backup.zip", FileTreeItemType.FILE_ZIP));
        archives.addChild(new FileTreeItem("old_photos.zip", FileTreeItemType.FILE_ZIP));

        root.addChild(photos);
        root.addChild(videos);
        root.addChild(music);
        root.addChild(archives);

        return root;
    }

}