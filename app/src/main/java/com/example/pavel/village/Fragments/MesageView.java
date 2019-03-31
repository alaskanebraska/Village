package com.example.pavel.village.Fragments;

import android.graphics.Bitmap;

import java.util.ArrayList;

public interface MesageView {
    void success(String message);
    void error(String message);
    void setParametr(ArrayList<String> list);
    void onSessia(String name,String email,String id);
    ArrayList<String> getEditText();
    ArrayList<String> getPhoto();
    void showProgress();
    void hideProgress();
}
