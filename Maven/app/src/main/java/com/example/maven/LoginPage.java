package com.example.maven;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginPage extends Fragment {

    private RequestQueue rq;
    private JsonRequest jrq;
    private EditText fieldUser, fieldPwd;
    private Button btnConsult;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_login_page,container,false);
        fieldUser = vista.findViewById(R.id.textUser);
        fieldPwd = vista.findViewById(R.id.txtPwd);
        btnConsult = (Button) vista.findViewById(R.id.btnIngrear);
        rq = Volley.newRequestQueue(getContext());

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                nuevaSesion();
                //newSesion();
            }
        });

        return vista;
    }

   public void nuevaSesion(){
        final String names=fieldUser.getText().toString();
        final String pwd=fieldPwd.getText().toString();

        Response.Listener<String> responses = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");

                    if(success){
                        String names=jsonResponse.getString("names");
                        Intent intent = new Intent(getContext(),GraphicActivity.class);
                        intent.putExtra("names",names);
                        //intent.putExtra(GraphicActivity.nombres,names);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getContext(),"No se encontro el usuario",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"No se encontro el usuario" + e,Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        };

        LoginRequest loginRequest = new LoginRequest(names,pwd,responses);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(loginRequest);
   }
}
