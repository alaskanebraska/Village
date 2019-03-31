package com.example.pavel.village.Find;

import com.example.pavel.village.Model.Product;

import java.util.List;

public interface IListProduct {
    public void showProgress();
    public void hideProgress();
    public void onAddSuccess(String msg);
    public void onAddError(String msg);
    public void onGetResult(List<Product> products);
    public String getName();
}
