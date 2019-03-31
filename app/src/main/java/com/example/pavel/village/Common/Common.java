package com.example.pavel.village.Common;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.pavel.village.Model.User;
import com.example.pavel.village.Remote.IMyAPI;
import com.example.pavel.village.Remote.RetrofitClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {

    //public static final String BASE_URL = "http://192.168.0.101/myapi/";
    //public static final String BASE_URL = "http://192.168.1.100/myapi/";
    //public static final String BASE_URL = "http://192.168.0.103/myapi/";
    public static final String BASE_URL = "http://192.168.1.101/myapi/";

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IMyAPI.class);
    }

    public static int isValidData(String email, String password)
    {

        //0 check email is empty
        //1 check email is matches patterns
        //2 check password length > 6
        //3 check passwrod is empty


        if(TextUtils.isEmpty(email))
            return 0;
        else if (!isEmailValid(email))
            return 1;
        else if(password.length() < 6)
            return 2;
        else if (TextUtils.isEmpty(password))
            return 3;
        else return -1;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
