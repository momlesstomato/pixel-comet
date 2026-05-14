package com.cometproject.server.game.landing.types;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes promo article behavior for the Comet subsystem.
 */
public class PromoArticle {
    private int id;
    private String title;
    private String message;
    private String buttonText;
    private String buttonLink;
    private String imagePath;

    /**
     * Creates a promo article instance for the Comet subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public PromoArticle(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.title = data.getString("title");
        this.message = data.getString("message");
        this.buttonText = data.getString("button_text");
        this.buttonLink = data.getString("button_link");
        this.imagePath = data.getString("image_path");
    }

    /**
     * Returns the id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the message for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the button text for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getButtonText() {
        return buttonText;
    }

    /**
     * Returns the button link for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getButtonLink() {
        return buttonLink;
    }

    /**
     * Returns the image path for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getImagePath() {
        return imagePath;
    }
}
