package com.example.pavel.village.Common;

public interface LoginAndRegisterView {
    public void showProgress();
    public void hideProgress();
    public void onAddSuccess(String msg);
    public void onAddError(String msg);
    public void onProfile();
    public void onSessia(String name,String email,String id);
}
