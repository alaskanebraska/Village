package com.example.pavel.village.Fragments;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Other.SessionManager;
import com.example.pavel.village.Remote.IMyAPI;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterMessageFragment {
    private MesageView mesageView;
    //SessionManager sessionManager;

    public PresenterMessageFragment(MesageView mesageView)
    {
        this.mesageView = mesageView;
    }

    void getUserDetail(String id)
    {
        mesageView.showProgress();
        final IMyAPI mService = Common.getAPI();
        mService.detailUser(id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse result = response.body();
                mesageView.hideProgress();
                if(result.isError())
                {
                    mesageView.error("erroro"+result.getError_msg());
                }
                else
                {
                    mesageView.success("tip top");
                    String mName = result.getUser().getName();
                    String mEmail = result.getUser().getEmail();
                        String mPhoto = result.getUser().getPhoto();
                        ArrayList<String> list = new ArrayList();
                        list.add(mName);
                        list.add(mEmail);
                        list.add(mPhoto);
                        mesageView.setParametr(list);


                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mesageView.hideProgress();
                mesageView.error("not enter");
            }
        });
    }

     void editData()
    {
        final String email=mesageView.getEditText().get(0);
        final String id = mesageView.getEditText().get(1);
        final String name = mesageView.getEditText().get(2);
        if(email==null)
        {
            mesageView.error("Введите email!");
            return;
        }
        else if(!Common.isEmailValid(email))
        {
            mesageView.error("Введите действующий email!");
            return;
        }
        mesageView.showProgress();
        final IMyAPI mService = Common.getAPI();
        mService.editDetailUser(name,email,id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse result = response.body();
                mesageView.hideProgress();
                if(result.isError())
                {
                    mesageView.error("errorik"+result.getError_msg());
                }
                else
                {
                    mesageView.onSessia(email,name,id);
                    //sessionManager.createSession(name,email,id);
                    mesageView.success("tipi topi");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mesageView.hideProgress();
                mesageView.error("not enter");
            }
        });
    }
    void uploadPhoto(String id, String photo)
    {
        mesageView.showProgress();
        IMyAPI mService = Common.getAPI();
        mService.uploadPhoto(id,photo).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse result = response.body();
                mesageView.hideProgress();
                if(result.isError())
                {
                    mesageView.error("not upload"+result.getError_msg());
                }
                else
                {
                    mesageView.success("upload photo");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mesageView.hideProgress();
                mesageView.error("not upload");
            }
        });
    }

    public String getStringImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodedImage;
    }


}
