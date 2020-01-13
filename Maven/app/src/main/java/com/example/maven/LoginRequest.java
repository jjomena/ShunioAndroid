package com.example.maven;

import android.view.textclassifier.TextLanguage;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //private static final String LOGIN_REQUEST_URL="http://192.168.1.125/login/LoginDF.php";
    private static final String LOGIN_REQUEST_URL="http://192.168.1.7/login/ShunioMobile.php";
    private Map<String,String> params;

    public LoginRequest(String user,String pwd,Response.Listener<String> listener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("user",user);
        params.put("pwd",pwd);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
