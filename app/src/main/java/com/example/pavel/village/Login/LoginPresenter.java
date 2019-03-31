package com.example.pavel.village.Login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Common.LoginAndRegisterView;
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Other.SessionManager;
import com.example.pavel.village.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginAndRegisterView view;
    private static final String TAG = "MyAppik";


    public LoginPresenter(LoginAndRegisterView view) {
        this.view = view;
    }

    void authenticateUser(final String email, String password)
    {

        int loginCode = Common.isValidData(email,password);
        if(loginCode == 0){
            view.onAddError("Введите email");
            return;}
        else if(loginCode == 1){
            view.onAddError("Напишите действительный email");
            return;}
        else if(loginCode == 2){
            view.onAddError("Пароль слишком короткий, минимум 6 символов!");
            return;}
        else if(loginCode == 3){
            view.onAddError("Введите пароль!");
            return;}
        view.showProgress();
        IMyAPI mService = Common.getAPI();

        mService.loginUser(email,password)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        ApiResponse result = response.body();

                        view.hideProgress();
                        if(result.isError())
                        {
                            view.onAddError(result.getError_msg());
                        }
                        else
                        {

                            view.onAddSuccess("Удачная авторизация");
                            view.onSessia(result.getUser().getName(),result.getUser().getEmail(),result.getUser().getId());
                            view.onProfile();

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        view.hideProgress();
                        view.onAddError(t.getMessage());
                    }
                });
    }
}
