package com.example.pavel.village.Offer;

import java.util.ArrayList;
import java.util.Date;

public interface IOffer {
    public void showProgress();
    public void hideProgress();
    public void onAddSuccess(String msg);
    public void onAddError(String msg);
    public void onHome();
    ArrayList<String> getEditText();
    ArrayList<Integer> getIntValues();
    Date getData();
    String getPhoto();
}
