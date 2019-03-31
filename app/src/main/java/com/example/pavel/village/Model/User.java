package com.example.pavel.village.Model;

public class User {
    private String id;
    private String name;
    private String email;
    private String photo;
    private String created_at;
    private String updated_at = "";

    public User() {
    }

    public User(String id, String name, String email,String photo, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String email, String created_at, String updated_at) {
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
