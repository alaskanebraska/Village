package com.example.pavel.village.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pavel.village.Common.ItemClickListenner;
import com.example.pavel.village.Model.Product;
import com.example.pavel.village.Model.ProductItem;
import com.example.pavel.village.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecycleViewHolder> implements Filterable {
    Context context;
    private List<ProductItem> productList;
    private List<ProductItem> productListFiltered;
    ItemClickListenner itemClickListenner;

    public SearchAdapter(Context context, List<ProductItem> contactList, ItemClickListenner listener) {
        this.context = context;
        this.itemClickListenner = listener;
        this.productList = contactList;
        this.productListFiltered = contactList;
    }


    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_recycle,parent,false);
        return new SearchAdapter.RecycleViewHolder(view,itemClickListenner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        ProductItem product = productListFiltered.get(position);
        holder.txt1.setText(product.getTitle());
        holder.txt2.setText(product.getDescription());
        holder.circleImageView.setImageResource(product.getImage());
    }

    @Override
    public int getItemCount() {
        return productListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = productList;
                } else {
                    List<ProductItem> filteredList = new ArrayList<>();
                    for (ProductItem row : productList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getDescription().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<ProductItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }




    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CircleImageView circleImageView;
        TextView txt1,txt2;
        ItemClickListenner itemClickListenner;
        LinearLayout relative;

        public RecycleViewHolder(View itemView, ItemClickListenner itemClickListenner) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.list_imageView);
            txt1 = itemView.findViewById(R.id.list_title);
            txt2 = itemView.findViewById(R.id.list_description);
            relative = itemView.findViewById(R.id.relative);
            this.itemClickListenner = itemClickListenner;

            relative.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListenner.click(view,getAdapterPosition());
        }
    }
}
