package com.example.pavel.village.Find;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.pavel.village.Adapters.ProductAdapter;
import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Common.ItemClickListenner;
import com.example.pavel.village.Login.LoginActivity;
import com.example.pavel.village.Model.Product;
import com.example.pavel.village.R;
import com.example.pavel.village.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProduct extends AppCompatActivity implements IListProduct {
    private PresenterList presenterList;
    private ProductAdapter productAdapter;
    private List<Product> productss;
    private List<Product> productList = new ArrayList<>();
    private ProgressDialog loadingBar;
    private ItemClickListenner itemClickListenner;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String name;
    private static final String TAG = "MyLogi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        loadingBar = new ProgressDialog(ListProduct.this);
        //productss = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

       // swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this,productList,itemClickListenner);
        productss = new ArrayList<>();


       // productAdapter.notifyDataSetChanged();
        //recyclerView.setAdapter(productAdapter);

        //method to load fruits
        getData();

        //presenterList = new PresenterList(this);
        //presenterList.getData();



       /* swipeRefreshLayout.setOnRefreshListener(
                //() -> presenterList.getData()
                () -> presenterList.getData()
        );*/

        itemClickListenner = ((view,position)->
        {
            String name_product = productss.get(position).getName_product();
            Toasty.info(this,name_product,Toast.LENGTH_SHORT).show();
        });
    }

    private void getData() {

        IMyAPI mService = Common.getAPI();
        String name = getName();
        mService.getProducts(name).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loadDataList(response.body());
                }
                else
                {
                    onAddError("Error"+response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                //view.hideProgress();
                onAddError("Your error"+t.getMessage());
               // Log.i(TAG,"Errorik"+t.getMessage());
            }
        });
    }

    @Override
    public void showProgress() {
        /*loadingBar.setTitle("Загрузка");
        loadingBar.setMessage("Пожалуйста подождите...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();*/
       // swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        //loadingBar.dismiss();
      //  swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAddSuccess(String msg) {
        Toasty.success(ListProduct.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddError(String msg) {
        Toasty.error(ListProduct.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetResult(List<Product> products) {
        productAdapter = new ProductAdapter(this,productss,itemClickListenner);
        productAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(productAdapter);
        productss = products;
    }

    @Override
    public String getName() {
        return name;
    }


    private void loadDataList(List<Product> productsList) {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        productAdapter = new ProductAdapter(this,productsList,itemClickListenner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(productAdapter);
    }

}
