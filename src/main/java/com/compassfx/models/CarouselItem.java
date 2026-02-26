package com.compassfx.models;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * Represents a single item in a carousel
 */
public class CarouselItem {

    private final StringProperty title;
    private final StringProperty description;
    private final ObjectProperty<Node> content;
    private final ObjectProperty<Image> image;
    private final StringProperty imageUrl;
    private final ObjectProperty<Object> userData;

    public CarouselItem() {
        this(null);
    }

    public CarouselItem(String title) {
        this(title, null);
    }

    public CarouselItem(String title, String description) {
        this.title = new SimpleStringProperty(this, "title", title);
        this.description = new SimpleStringProperty(this, "description", description);
        this.content = new SimpleObjectProperty<>(this, "content", null);
        this.image = new SimpleObjectProperty<>(this, "image", null);
        this.imageUrl = new SimpleStringProperty(this, "imageUrl", null);
        this.userData = new SimpleObjectProperty<>(this, "userData", null);
    }

    // Static factory methods
    public static CarouselItem createWithImage(Image image) {
        CarouselItem item = new CarouselItem();
        item.setImage(image);
        return item;
    }

    public static CarouselItem createWithImageUrl(String imageUrl) {
        CarouselItem item = new CarouselItem();
        item.setImageUrl(imageUrl);
        return item;
    }

    public static CarouselItem createWithContent(Node content) {
        CarouselItem item = new CarouselItem();
        item.setContent(content);
        return item;
    }

    public static CarouselItem createTextSlide(String title, String description) {
        return new CarouselItem(title, description);
    }

    // Title
    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public StringProperty titleProperty() { return title; }

    // Description
    public String getDescription() { return description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public StringProperty descriptionProperty() { return description; }

    // Content (custom node)
    public Node getContent() { return content.get(); }
    public void setContent(Node content) { this.content.set(content); }
    public ObjectProperty<Node> contentProperty() { return content; }

    // Image
    public Image getImage() { return image.get(); }
    public void setImage(Image image) { this.image.set(image); }
    public ObjectProperty<Image> imageProperty() { return image; }

    // Image URL
    public String getImageUrl() { return imageUrl.get(); }
    public void setImageUrl(String imageUrl) { this.imageUrl.set(imageUrl); }
    public StringProperty imageUrlProperty() { return imageUrl; }

    // User Data
    public Object getUserData() { return userData.get(); }
    public void setUserData(Object userData) { this.userData.set(userData); }
    public ObjectProperty<Object> userDataProperty() { return userData; }

    @Override
    public String toString() {
        return title.get() != null ? title.get() : "Carousel Item";
    }
}
