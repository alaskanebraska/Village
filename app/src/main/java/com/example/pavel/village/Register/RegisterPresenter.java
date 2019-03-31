package com.example.pavel.village.Register;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Common.LoginAndRegisterView;
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter  {
    private LoginAndRegisterView view;

    public RegisterPresenter(LoginAndRegisterView loginAndRegisterView) {
        this.view = loginAndRegisterView;
    }

     void createUser(String name,String email,String password)
    {

        int loginCode = Common.isValidData(email,password);
        if(TextUtils.isEmpty(name)){
            view.onAddError("Введите имя");
            return;}
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
        mService.registerUser(name,email,password)
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

                            view.onAddSuccess("Удачная регистрация");
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
