package com.example.carol.kilimodigital2;

class ItemData {


    private String title, price, description, name, contact, category, image, uid;
    private long id;

    public ItemData() {

    }

    public ItemData(String uid, long id, String title, String price, String description, String name, String contact, String category) {
        this.title = title;
        this.id = id;
        this.price = price;
        this.description = description;
        this.name = name;
        this.contact = contact;
        this.category = category;
        this.uid = uid;


    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }





}
