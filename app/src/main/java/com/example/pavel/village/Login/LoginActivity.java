package com.example.pavel.village.Login;

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
import com.example.pavel.village.Model.ApiResponse;
import com.example.pavel.village.Other.SessionManager;
import com.example.pavel.village.R;
import com.example.pavel.village.Register.RegisterActivity;
import com.example.pavel.village.Remote.IMyAPI;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements LoginAndRegisterView {
    TextView txt_register, txt_forgeting;
    EditText ed_email, ed_password;
    Button btn_login;
    IMyAPI mService;
    LoginPresenter loginPresenter;
    ProgressDialog loadingBar;
    SessionManager sessionManager;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //INIT Service
        mService = Common.getAPI();

        //INIT Presenter
        loginPresenter = new LoginPresenter(this);

        sessionManager = new SessionManager(this);
        if(sessionManager.isLoggin())
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        //INIT VIEW
        txt_forgeting = findViewById(R.id.txt_forget);
        txt_register = findViewById(R.id.textView2);
        btn_login = findViewById(R.id.button_signin);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_pwd);
        loadingBar = new ProgressDialog(this);

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.authenticateUser(ed_email.getText().toString().trim(),ed_password.getText().toString().trim());
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
        Toasty.success(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddError(String msg) {
        Toasty.error(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfile() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    public void onSessia(String name, String email, String id) {
        sessionManager.createSession(name,email,id);
    }

}
