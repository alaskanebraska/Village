package com.example.pavel.village.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pavel.village.Common.ItemClickListenner;
import com.example.pavel.village.Model.Product;
import com.example.pavel.village.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecycleViewHolder>  {

    Context context;
    private List<Product> products;
    ItemClickListenner itemClickListenner;

    public ProductAdapter(Context context, List<Product> products, ItemClickListenner itemClickListenner) {
        this.context = context;
        this.products = products;
        this.itemClickListenner = itemClickListenner;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product,parent,false);
        return new RecycleViewHolder(view,itemClickListenner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Product product = products.get(position);
       // adapter = new ProductAdapter(context,products,itemClickListenner);
        holder.name.setText(product.getName_product());
       // holder.ck_face.setImageBitmap(StringToBitMap(product.getPhoto()));
        holder.product_card.setImageBitmap(StringToBitMap(product.getPhoto_product()));
        holder.btn_price.setText(String.valueOf(product.getPrice()));
        Picasso.get().load(product.getPhoto()).error(R.drawable.ic_account).into(holder.ck_face);
        Picasso.get().load(product.getPhoto_product()).error(R.drawable.ic_error_outline_white_48dp).into(holder.product_card);
    }

    @Override
    public int getItemCount() {
        //if(products==null) return 0;
        return products.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        ImageView product_card;
        Button btn_price;
        CircleImageView ck_face;
        ItemClickListenner itemClickListenner;

        public RecycleViewHolder(View itemView, ItemClickListenner itemClickListenner)
        {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_face);
            product_card = itemView.findViewById(R.id.product_card);
            btn_price = itemView.findViewById(R.id.btn_price);
            ck_face = itemView.findViewById(R.id.profile_image);
            this.itemClickListenner = itemClickListenner;
            product_card.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListenner.click(view,getAdapterPosition());
        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
