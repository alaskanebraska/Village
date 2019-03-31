package com.example.pavel.village.Model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("name_product")
    private String name_product;
    @SerializedName("photo_product")
    private String photo_product;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;

    public Product(int id, int user_id, String name_product,  String description, int price,String photo_product, String name, String photo) {
        this.id = id;
        this.user_id = user_id;
        this.name_product = name_product;
        this.description = description;
        this.price = price;
        this.photo_product = photo_product;
        this.name = name;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getPhoto_product() {
        return photo_product;
    }

    public void setPhoto_product(String photo_product) {
        this.photo_product = photo_product;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
