package com.compassfx.controls;

import com.compassfx.enums.CardElevation;
import javafx.scene.Node;

/**
 * Builder class for creating CFXCard instances
 */
public class CFXCardBuilder {

    private final CFXCard card;

    public CFXCardBuilder() {
        this.card = new CFXCard();
    }

    public CFXCardBuilder elevation(CardElevation elevation) {
        card.setElevation(elevation);
        return this;
    }

    public CFXCardBuilder hoverable(boolean hoverable) {
        card.setHoverable(hoverable);
        return this;
    }

    public CFXCardBuilder header(Node header) {
        card.setHeader(header);
        return this;
    }

    public CFXCardBuilder content(Node content) {
        card.setContent(content);
        return this;
    }

    public CFXCardBuilder footer(Node footer) {
        card.setFooter(footer);
        return this;
    }

    public CFXCardBuilder media(Node media) {
        card.setMedia(media);
        return this;
    }

    public CFXCardBuilder width(double width) {
        card.setPrefWidth(width);
        card.setMaxWidth(width);
        return this;
    }

    public CFXCardBuilder minWidth(double width) {
        card.setMinWidth(width);
        return this;
    }

    public CFXCardBuilder maxWidth(double width) {
        card.setMaxWidth(width);
        return this;
    }

    public CFXCard build() {
        return card;
    }
}