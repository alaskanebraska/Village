package com.example.pavel.village.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pavel.village.Find.Find;
import com.example.pavel.village.Offer.Offer;
import com.example.pavel.village.R;

public class PurchaseFragment extends Fragment implements View.OnClickListener {

    Button btn_offer;
    Button btn_find;
    public PurchaseFragment() {
    }

    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchase, container, false);
        btn_offer = view.findViewById(R.id.btn_offer);
        btn_find = view.findViewById(R.id.btn_find);
        btn_offer.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_offer:
                startActivity(new Intent(getActivity(),Offer.class));
                break;
            case R.id.btn_find:
                startActivity(new Intent(getActivity(),Find.class));
                break;
        }
    }
}
