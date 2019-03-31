package com.example.pavel.village.Find;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Model.Product;
import com.example.pavel.village.Remote.IMyAPI;
import com.example.pavel.village.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterList {
    private IListProduct view;
    private static final String TAG = "MyLogi";

    public PresenterList(IListProduct view) {
        this.view = view;
    }

    //void getData()
   /*{
        view.showProgress();
        IMyAPI mService = Common.getAPI();
        final String name = view.getName();
        Call<List<Product>> call = mService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                    view.onAddSuccess("Success");
                }
                else
                {
                    view.onAddError("Error"+response.message());
                    Log.i(TAG,"Error"+response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onAddError("Your error"+t.getMessage());
                Log.i(TAG,"Errorik"+t.getMessage());
            }
        });
      /*  mService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                    view.onAddSuccess("Success");
                }
                else
                {
                    view.onAddError("Error"+response.message());
                    Log.i(TAG,"Error"+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                view.hideProgress();
                view.onAddError("Your error"+t.getMessage());
                Log.i(TAG,"Errorik"+t.getMessage());
            }
        });*/

}
