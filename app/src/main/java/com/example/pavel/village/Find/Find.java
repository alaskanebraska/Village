package com.example.pavel.village.Find;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;


import com.example.pavel.village.Adapters.SearchAdapter;
import com.example.pavel.village.Common.ItemClickListenner;
import com.example.pavel.village.Model.ProductItem;
import com.example.pavel.village.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Find extends AppCompatActivity {
    private SearchAdapter adapter;
    private List<ProductItem> exampleList;
    private ItemClickListenner itemClickListenner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.search);
        setSupportActionBar(toolbar);
        fillExampleList();
        setUpRecyclerView();





       /* btn_find = findViewById(R.id.btn_find_product);
        editText = findViewById(R.id.ed_product);

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                Intent intent = new Intent(Find.this,ListProduct.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });*/

    }


    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new ProductItem("Strawberry", "Ten",R.drawable.a));
        exampleList.add(new ProductItem("Banana", "Ten",R.drawable.b));
        exampleList.add(new ProductItem("Potatoes", "Ten",R.drawable.c));
        exampleList.add(new ProductItem("Tomatoes", "Ten",R.drawable.d));
        exampleList.add(new ProductItem("Onion", "Ten",R.drawable.e));
        exampleList.add(new ProductItem("Watermelon", "Ten",R.drawable.f));
        exampleList.add(new ProductItem("Milk", "Ten",R.drawable.b));
        exampleList.add(new ProductItem("Oil", "Ten",R.drawable.a));
        exampleList.add(new ProductItem("Meat", "Ten",R.drawable.c));
        exampleList.add(new ProductItem("Cucumber", "Ten",R.drawable.a));
        exampleList.add(new ProductItem("Raspberry", "Ten",R.drawable.f));
        itemClickListenner = ((view,position)->
        {
            String name_product = exampleList.get(position).getTitle();
            Toasty.info(this,name_product,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Find.this,ListProduct.class);
            intent.putExtra("name",name_product);
            startActivity(intent);
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new SearchAdapter(this,exampleList,itemClickListenner);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        itemClickListenner = ((view,position)->
        {
            String name_product = exampleList.get(position).getTitle();
            Toasty.info(this,name_product,Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


}
