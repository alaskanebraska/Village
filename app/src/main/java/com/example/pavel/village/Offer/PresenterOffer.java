package com.example.pavel.village.Offer;


import android.util.Log;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Remote.IMyAPI;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterOffer {
    private IOffer view;
    private static final String TAG = "MyLogg";
    public PresenterOffer(IOffer view) {
        this.view = view;
    }

    void offerProduct()
    {
        final int user_id = view.getIntValues().get(0);
        final String nameOfProd = view.getEditText().get(0);
        final String decript = view.getEditText().get(1);
        final int price = view.getIntValues().get(1);
        final String date_created = view.getEditText().get(2);
        final String photo_product = view.getPhoto();
        view.showProgress();
        IMyAPI mService = Common.getAPI();
        mService.offerProduct(user_id,nameOfProd,decript,price,date_created,photo_product)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        ApiResponse result = response.body();
                        view.hideProgress();

                        if(result.isError())
                        {
                            view.onAddError(result.getError_msg());
                            Log.i(TAG,result.getError_msg());
                        }
                        else
                        {
                            view.onAddSuccess("Удачно опубликовали");
                            view.onHome();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        view.hideProgress();
                        view.onAddError("ERRORRRRRRRR"+t.getMessage());
                        Log.i(TAG,"ERRORRRRRRRR"+t.getMessage());
                    }
                });
    }

    void sentPhoto(String photo_product)
    {
        view.showProgress();
        IMyAPI mService = Common.getAPI();
        mService.photoProduct(photo_product).enqueue(new Callback<ApiResponse>() {
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
                    view.onAddSuccess("Удачная публикация фото");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                view.hideProgress();
                view.onAddError("Неудачная публикация фото"+t.getMessage());
            }
        });
    }

}
