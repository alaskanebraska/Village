package com.example.pavel.village.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Other.SessionManager;
import com.example.pavel.village.R;
import com.example.pavel.village.Remote.IMyAPI;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements MesageView {
    TextInputEditText ed_phone,ed_email;
    TextView txt_getComments,txt_throwComments,txt_exit;
    ImageView img_edit1,img_edit2,img_name;
    FloatingActionButton fb;
    SessionManager sessionManager;
    String getId;
    PresenterMessageFragment presenter;
    ProgressDialog loadingBar;
    Toolbar toolbar;
    NestedScrollView nsv;
    Bitmap bitmap;
    private static final String TAG = "myLogs";

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        presenter = new PresenterMessageFragment(this);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ed_email = view.findViewById(R.id.edTextEmail);
        ed_phone = view.findViewById(R.id.edTextPhone);
        txt_getComments = view.findViewById(R.id.getComments);
        txt_throwComments = view.findViewById(R.id.giveComments);
        txt_exit = view.findViewById(R.id.text_exit);
        fb = view.findViewById(R.id.doPhoto);
        img_edit1 = view.findViewById(R.id.imageEdit1);
        img_edit2 = view.findViewById(R.id.imageEdit2);
        img_name = view.findViewById(R.id.name_of_image);
        //nsv = view.findViewById(R.id.nsv);
        loadingBar = new ProgressDialog(getActivity());


        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetail();

        getId = user.get(SessionManager.ID);
        presenter.getUserDetail(getId);



        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        img_edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String edit_email = ed_email.getText().toString().trim();
                presenter.editData();
            }
        });


        return  view;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            // CropImage.ActivityResult result = CropImage.getActivityResult(data);
            //Uri filepath = result.getUri();
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                img_name.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            presenter.uploadPhoto(getId,getStringImage(bitmap));
        }
    }


    public String getStringImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void success(String message) {
        Toasty.success(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String message) {
        Toasty.error(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setParametr(ArrayList<String> list) {
        toolbar.setTitle(list.get(0));
        //img_name.setContentDescription(list.get(0));
        ed_email.setText(list.get(1));
        //if(list.get(2)!=null){
        Picasso.get().load(list.get(2).isEmpty() ? null:list.get(2)).error(R.drawable.ic_account).into(img_name);
    }

    @Override
    public void onSessia(String name, String email, String id) {
        sessionManager.createSession(name,email,id);
    }

    @Override
    public ArrayList<String> getEditText() {
        String email = ed_email.getText().toString().trim();
        String id = getId;

        //String photo = getStringImage(bitmap);
        //img_name.getContentDescription();
        //String name = (String) toolbar.getTitle();
        String name = (String) img_name.getContentDescription();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(email);
        arrayList.add(id);
        arrayList.add(name);
        return arrayList;
    }

    @Override
    public ArrayList<String> getPhoto() {
        String id = getId;
        String photo = getStringImage(bitmap);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(id);
        arrayList.add(photo);
        return arrayList;
    }

    @Override
    public void showProgress() {
        loadingBar.setTitle("Загрузка");
        loadingBar.setMessage("Пожалуйста подождите...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();
    }

    @Override
    public void hideProgress() {
        loadingBar.dismiss();
    }

}
