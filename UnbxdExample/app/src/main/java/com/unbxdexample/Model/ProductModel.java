package com.unbxdexample.Model;

import java.io.Serializable;

/**
 * Created by training on 09/03/16.
 */
public class ProductModel implements Serializable {
    private String title, description, imageURL, pID;
    private int price;
    private String autoSuggest;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getAutoSuggest() {
        return autoSuggest;
    }

    public void setAutoSuggest(String autoSuggest) {
        this.autoSuggest = autoSuggest;
    }
}
