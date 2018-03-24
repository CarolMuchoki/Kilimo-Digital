package com.example.carol.kilimodigital2;

/**
 * Created by Simon.Wambua on 24/03/2018.
 */

public class Home_Model {
    public String title;
    public String description;
    public String image;
    public String url;


    public Home_Model(String title, String description, String image, String url) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.url = url;
    }

    public Home_Model() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
