package com.compassfx.skins;

import com.compassfx.controls.CFXCarousel;
import com.compassfx.enums.CarouselIndicatorStyle;
import com.compassfx.enums.CarouselTransition;
import com.compassfx.models.CarouselItem;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class CFXCarouselSkin extends SkinBase<CFXCarousel> {

    private final CFXCarousel carousel;
    private final StackPane mainContainer;
    private final StackPane slideContainer;
    private final HBox navigationContainer;
    private final HBox indicatorsContainer;
    private final Button prevButton;
    private final Button nextButton;

    private Timeline autoPlayTimeline;
    private StackPane currentSlidePane;

    public CFXCarouselSkin(CFXCarousel carousel) {
        super(carousel);
        this.carousel = carousel;

        // Main container
        mainContainer = new StackPane();
        mainContainer.getStyleClass().add("carousel-main-container");
        mainContainer.setPrefSize(carousel.getItemWidth(), carousel.getItemHeight());

        // Slide container
        slideContainer = new StackPane();
        slideContainer.getStyleClass().add("carousel-slide-container");
        slideContainer.setPrefSize(carousel.getItemWidth(), carousel.getItemHeight());

        // Navigation buttons
        prevButton = new Button("❮");
        prevButton.getStyleClass().add("carousel-nav-button");
        prevButton.getStyleClass().add("prev");
        prevButton.setOnAction(e -> {
            carousel.previous();
            resetAutoPlay();
        });

        nextButton = new Button("❯");
        nextButton.getStyleClass().add("carousel-nav-button");
        nextButton.getStyleClass().add("next");
        nextButton.setOnAction(e -> {
            carousel.next();
            resetAutoPlay();
        });

        navigationContainer = new HBox();
        navigationContainer.getStyleClass().add("carousel-navigation");
        navigationContainer.setMouseTransparent(true);

        StackPane.setAlignment(prevButton, Pos.CENTER_LEFT);
        StackPane.setMargin(prevButton, new Insets(0, 0, 0, 20));

        StackPane.setAlignment(nextButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(nextButton, new Insets(0, 20, 0, 0));

        // Indicators
        indicatorsContainer = new HBox(10);
        indicatorsContainer.getStyleClass().add("carousel-indicators");
        indicatorsContainer.setAlignment(Pos.CENTER);

        StackPane.setAlignment(indicatorsContainer, Pos.BOTTOM_CENTER);
        StackPane.setMargin(indicatorsContainer, new Insets(0, 0, 20, 0));

        // Assemble
        mainContainer.getChildren().add(slideContainer);

        if (carousel.isShowNavigation()) {
            mainContainer.getChildren().addAll(prevButton, nextButton);
        }

        if (carousel.isShowIndicators()) {
            mainContainer.getChildren().add(indicatorsContainer);
        }

        getChildren().add(mainContainer);

        // Listen for changes
        carousel.getItems().addListener((ListChangeListener.Change<? extends CarouselItem> c) -> {
            updateIndicators();
            if (carousel.getCurrentIndex() >= carousel.getItems().size()) {
                carousel.setCurrentIndex(Math.max(0, carousel.getItems().size() - 1));
            }
        });

        carousel.currentIndexProperty().addListener((obs, old, newVal) -> {
            updateSlide(old.intValue(), newVal.intValue());
            updateIndicators();
        });

        carousel.showNavigationProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                if (!mainContainer.getChildren().contains(prevButton)) {
                    mainContainer.getChildren().addAll(prevButton, nextButton);
                }
            } else {
                mainContainer.getChildren().removeAll(prevButton, nextButton);
            }
        });

        carousel.showIndicatorsProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                if (!mainContainer.getChildren().contains(indicatorsContainer)) {
                    mainContainer.getChildren().add(indicatorsContainer);
                }
            } else {
                mainContainer.getChildren().remove(indicatorsContainer);
            }
        });

        carousel.autoPlayProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                startAutoPlay();
            } else {
                stopAutoPlay();
            }
        });

        carousel.indicatorStyleProperty().addListener((obs, old, newVal) -> updateIndicators());

        // Initialize
        if (!carousel.getItems().isEmpty()) {
            updateSlide(-1, carousel.getCurrentIndex());
        }
        updateIndicators();

        if (carousel.isAutoPlay()) {
            startAutoPlay();
        }
    }

    private void updateSlide(int oldIndex, int newIndex) {
        if (newIndex < 0 || newIndex >= carousel.getItems().size()) {
            return;
        }

        CarouselItem item = carousel.getItems().get(newIndex);
        StackPane newSlidePane = createSlidePane(item);

        if (currentSlidePane == null) {
            // First slide
            slideContainer.getChildren().clear();
            slideContainer.getChildren().add(newSlidePane);
            currentSlidePane = newSlidePane;
        } else {
            // Transition between slides
            StackPane oldSlidePane = currentSlidePane;

            slideContainer.getChildren().add(newSlidePane);

            switch (carousel.getTransition()) {
                case SLIDE:
                    animateSlide(oldSlidePane, newSlidePane, newIndex > oldIndex);
                    break;
                case FADE:
                    animateFade(oldSlidePane, newSlidePane);
                    break;
                case SCALE:
                    animateScale(oldSlidePane, newSlidePane);
                    break;
                case NONE:
                default:
                    slideContainer.getChildren().remove(oldSlidePane);
                    break;
            }

            currentSlidePane = newSlidePane;
        }
    }

    private StackPane createSlidePane(CarouselItem item) {
        StackPane pane = new StackPane();
        pane.getStyleClass().add("carousel-slide");
        pane.setPrefSize(carousel.getItemWidth(), carousel.getItemHeight());

        if (item.getContent() != null) {
            // Custom content
            pane.getChildren().add(item.getContent());
        } else if (item.getImage() != null) {
            // Image slide
            ImageView imageView = new ImageView(item.getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(carousel.getItemWidth());
            imageView.setFitHeight(carousel.getItemHeight());

            VBox imageContainer = new VBox(imageView);
            imageContainer.setAlignment(Pos.CENTER);
            pane.getChildren().add(imageContainer);

            // Add caption if exists
            if (item.getTitle() != null || item.getDescription() != null) {
                VBox caption = createTextCaption(item);
                StackPane.setAlignment(caption, Pos.BOTTOM_CENTER);
                StackPane.setMargin(caption, new Insets(0, 0, 60, 0));
                pane.getChildren().add(caption);
            }
        } else if (item.getImageUrl() != null) {
            // Image from URL
            ImageView imageView = new ImageView(item.getImageUrl());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(carousel.getItemWidth());
            imageView.setFitHeight(carousel.getItemHeight());

            VBox imageContainer = new VBox(imageView);
            imageContainer.setAlignment(Pos.CENTER);
            pane.getChildren().add(imageContainer);

            if (item.getTitle() != null || item.getDescription() != null) {
                VBox caption = createTextCaption(item);
                StackPane.setAlignment(caption, Pos.BOTTOM_CENTER);
                StackPane.setMargin(caption, new Insets(0, 0, 60, 0));
                pane.getChildren().add(caption);
            }
        } else {
            // Text slide
            VBox textContent = createTextContent(item);
            pane.getChildren().add(textContent);
        }

        return pane;
    }

    private VBox createTextContent(CarouselItem item) {
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.getStyleClass().add("slide-text-content");
        content.setMaxWidth(500);

        if (item.getTitle() != null) {
            Label titleLabel = new Label(item.getTitle());
            titleLabel.getStyleClass().add("slide-title");
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
            titleLabel.setWrapText(true);
            titleLabel.setTextAlignment(TextAlignment.CENTER);
            content.getChildren().add(titleLabel);
        }

        if (item.getDescription() != null) {
            Label descLabel = new Label(item.getDescription());
            descLabel.getStyleClass().add("slide-description");
            descLabel.setFont(Font.font("System", 18));
            descLabel.setWrapText(true);
            descLabel.setTextAlignment(TextAlignment.CENTER);
            content.getChildren().add(descLabel);
        }

        return content;
    }

    private VBox createTextCaption(CarouselItem item) {
        VBox caption = new VBox(5);
        caption.setAlignment(Pos.CENTER);
        caption.setPadding(new Insets(15, 30, 15, 30));
        caption.getStyleClass().add("slide-caption");
        caption.setMaxWidth(500);

        if (item.getTitle() != null) {
            Label titleLabel = new Label(item.getTitle());
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
            titleLabel.setWrapText(true);
            titleLabel.setTextAlignment(TextAlignment.CENTER);
            caption.getChildren().add(titleLabel);
        }

        if (item.getDescription() != null) {
            Label descLabel = new Label(item.getDescription());
            descLabel.setFont(Font.font("System", 14));
            descLabel.setWrapText(true);
            descLabel.setTextAlignment(TextAlignment.CENTER);
            caption.getChildren().add(descLabel);
        }

        return caption;
    }

    private void animateSlide(StackPane oldPane, StackPane newPane, boolean forward) {
        double width = carousel.getItemWidth();

        newPane.setTranslateX(forward ? width : -width);

        TranslateTransition oldTransition = new TranslateTransition(Duration.millis(400), oldPane);
        oldTransition.setToX(forward ? -width : width);

        TranslateTransition newTransition = new TranslateTransition(Duration.millis(400), newPane);
        newTransition.setToX(0);

        ParallelTransition parallel = new ParallelTransition(oldTransition, newTransition);
        parallel.setOnFinished(e -> {
            slideContainer.getChildren().remove(oldPane);
            newPane.setTranslateX(0);
        });
        parallel.play();
    }

    private void animateFade(StackPane oldPane, StackPane newPane) {
        newPane.setOpacity(0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), oldPane);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newPane);
        fadeIn.setToValue(1);

        SequentialTransition sequence = new SequentialTransition(fadeOut, fadeIn);
        sequence.setOnFinished(e -> slideContainer.getChildren().remove(oldPane));
        sequence.play();
    }

    private void animateScale(StackPane oldPane, StackPane newPane) {
        newPane.setScaleX(0.8);
        newPane.setScaleY(0.8);
        newPane.setOpacity(0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), oldPane);
        scaleOut.setToX(0.8);
        scaleOut.setToY(0.8);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), oldPane);
        fadeOut.setToValue(0);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), newPane);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newPane);
        fadeIn.setToValue(1.0);

        ParallelTransition outTransition = new ParallelTransition(scaleOut, fadeOut);
        ParallelTransition inTransition = new ParallelTransition(scaleIn, fadeIn);

        SequentialTransition sequence = new SequentialTransition(outTransition, inTransition);
        sequence.setOnFinished(e -> slideContainer.getChildren().remove(oldPane));
        sequence.play();
    }

    private void updateIndicators() {
        indicatorsContainer.getChildren().clear();

        if (carousel.getIndicatorStyle() == CarouselIndicatorStyle.NONE) {
            return;
        }

        for (int i = 0; i < carousel.getItems().size(); i++) {
            final int index = i;

            switch (carousel.getIndicatorStyle()) {
                case DOTS:
                    Circle dot = new Circle(5);
                    dot.getStyleClass().add("carousel-indicator-dot");
                    if (i == carousel.getCurrentIndex()) {
                        dot.getStyleClass().add("active");
                    }
                    dot.setCursor(Cursor.HAND);
                    dot.setOnMouseClicked(e -> {
                        carousel.goToSlide(index);
                        resetAutoPlay();
                    });
                    indicatorsContainer.getChildren().add(dot);
                    break;

                case LINES:
                    Region line = new Region();
                    line.getStyleClass().add("carousel-indicator-line");
                    line.setPrefSize(30, 3);
                    if (i == carousel.getCurrentIndex()) {
                        line.getStyleClass().add("active");
                        line.setPrefWidth(50);
                    }
                    line.setCursor(Cursor.HAND);
                    line.setOnMouseClicked(e -> {
                        carousel.goToSlide(index);
                        resetAutoPlay();
                    });
                    indicatorsContainer.getChildren().add(line);
                    break;

                case NUMBERS:
                    Label number = new Label(String.valueOf(i + 1));
                    number.getStyleClass().add("carousel-indicator-number");
                    if (i == carousel.getCurrentIndex()) {
                        number.getStyleClass().add("active");
                    }
                    number.setCursor(Cursor.HAND);
                    number.setOnMouseClicked(e -> {
                        carousel.goToSlide(index);
                        resetAutoPlay();
                    });
                    indicatorsContainer.getChildren().add(number);
                    break;
            }
        }
    }

    private void startAutoPlay() {
        stopAutoPlay();

        autoPlayTimeline = new Timeline(new KeyFrame(carousel.getAutoPlayDelay(), e -> {
            carousel.next();
        }));
        autoPlayTimeline.setCycleCount(Timeline.INDEFINITE);
        autoPlayTimeline.play();
    }

    private void stopAutoPlay() {
        if (autoPlayTimeline != null) {
            autoPlayTimeline.stop();
            autoPlayTimeline = null;
        }
    }

    private void resetAutoPlay() {
        if (carousel.isAutoPlay()) {
            startAutoPlay();
        }
    }

    @Override
    public void dispose() {
        stopAutoPlay();
        super.dispose();
    }
}
