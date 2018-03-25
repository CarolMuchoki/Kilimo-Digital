package com.example.carol.kilimodigital2;

/**
 * Created by Carol on 3/25/2018.
 */

public class Market_Model {
    public String crop_name;
    public String price;
    public String image;

    public Market_Model(String crop_name, String price, String image) {
        this.crop_name = crop_name;
        this.price = price;
        this.image = image;

    }

    public Market_Model() {
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String description) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}

