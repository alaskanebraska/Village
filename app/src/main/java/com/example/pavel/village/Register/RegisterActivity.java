package com.example.pavel.village.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.village.Common.Common;
import com.example.pavel.village.Common.LoginAndRegisterView;
import com.example.pavel.village.CurvedBottom.MainActivity;
import com.example.pavel.village.Fragments.ProfileFragment;
import com.example.pavel.village.Login.LoginActivity;
import com.example.pavel.village.R;
import com.example.pavel.village.Remote.IMyAPI;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements LoginAndRegisterView {

    TextView txt_login, txt_forgeting;
    EditText ed_email, ed_password,ed_name;
    Button btn_sign;
    IMyAPI mService;
    RegisterPresenter presenter;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //INIT Service
        mService = Common.getAPI();

        presenter = new RegisterPresenter(this);

        //INIT VIEW
        txt_login = findViewById(R.id.txt_login);
        btn_sign = findViewById(R.id.btn_signUp);
        ed_email = findViewById(R.id.ed_email_reg);
        ed_password = findViewById(R.id.ed_pwd_reg);
        ed_name = findViewById(R.id.ed_name);
        loadingBar = new ProgressDialog(this);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createUser(ed_name.getText().toString().trim(),ed_email.getText().toString(),ed_password.getText().toString().trim());

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
        Toasty.success(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddError(String msg) {
        Toasty.error(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfile() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSessia(String name, String email, String id) {

    }
}
