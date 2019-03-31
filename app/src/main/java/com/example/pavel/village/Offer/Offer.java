package com.example.pavel.village.Offer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.CurvedBottom.MainActivity;
import com.example.pavel.village.Other.SessionManager;
import com.example.pavel.village.R;
import com.example.pavel.village.Remote.IMyAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class Offer extends AppCompatActivity implements IOffer,View.OnClickListener {

    private static final int SELECT_PHOTO = 2;
    Bitmap bitmap;
    Intent intent;
    ImageView imageView;
    EditText name,price,description,date;
    Button btn_photo,btn_save;
    private DatePickerDialog dp;
    ProgressDialog loadingBar;
    IMyAPI mService;
    PresenterOffer presenterOffer;
    String getId,getPhoto;
    SessionManager sessionManager;
    Date dating;
    private static final String TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer);
        btn_photo = findViewById(R.id.btn_photo);
        btn_save =  findViewById(R.id.btn_save);
        name = findViewById(R.id.input_name);
        price = findViewById(R.id.input_price);
        description = findViewById(R.id.input_description);
        date = findViewById(R.id.input_date);
        imageView = findViewById(R.id.image);
        presenterOffer = new PresenterOffer(this);
        btn_photo.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        callDp();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        Log.i(TAG,getId);



        mService = Common.getAPI();
        loadingBar = new ProgressDialog(this);

    }


    public void callDp()
    {
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                dp = new DatePickerDialog(Offer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i2 + "-"
                                + (i1 + 1) + "-" + i);
                    }
                }, mYear, mMonth, mDay);
                dp.show();
            }
        });
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

    @Override
    public void onAddSuccess(String msg) {
        Toasty.success(Offer.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddError(String msg) {
        Toasty.error(Offer.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHome() {
        startActivity(new Intent(Offer.this,MainActivity.class));
    }

    @Override
    public ArrayList<String> getEditText() {
        String nameOfProduct = name.getText().toString().trim();
        String descript = description.getText().toString().trim();
        String date_created = date.getText().toString().trim();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(nameOfProduct);
        arrayList.add(descript);
        arrayList.add(date_created);
        return arrayList;
    }

    @Override
    public ArrayList<Integer> getIntValues() {
        int id = Integer.parseInt(getId);
        Log.i(TAG, String.valueOf(id));
        int pricing = Integer.parseInt(price.getText().toString().trim());
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(id);
        arrayList.add(pricing);
        return arrayList;
    }

    @Override
    public Date getData() {
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String str = date.getText().toString().trim();
        try {
            dating = dateFormat.parse(str);
            Log.i(TAG, String.valueOf(dating));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dating;
    }

    @Override
    public String getPhoto() {
        return getPhoto;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_save:
                presenterOffer.offerProduct();
                break;
            case R.id.btn_photo:
                chooseFile();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            getPhoto = getStringImage(bitmap);
           // presenterOffer.sentPhoto(getStringImage(bitmap));
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
}
