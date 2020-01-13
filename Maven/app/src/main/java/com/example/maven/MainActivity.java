package com.example.maven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //private RequestQueue rq;
    //private JsonRequest jrq;
    private EditText fieldUser, fieldPwd;
    private Button btnConsult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fieldUser= ( EditText ) findViewById(R.id.textUser);
        fieldPwd= ( EditText )findViewById(R.id.txtPwd);
        btnConsult= ( Button ) findViewById(R.id.btnIngrear);

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username=fieldUser.getText().toString();
                final String password=fieldPwd.getText().toString();

                Response.Listener<String> responses = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");

                            if(success){
                                //String names=jsonResponse.getString("names");
                                String first_name=jsonResponse.getString("first_name");
                                Intent intent = new Intent(MainActivity.this,GraphicActivity.class);
                                //intent.putExtra("names",names);
                                intent.putExtra("first_name",first_name);
                                MainActivity.this.startActivity(intent);

                            }else{
                                Toast.makeText(MainActivity.this,"No se encontro el usuario",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,"Error de usuario" + e,Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username,password,responses);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);


            }
        });

    }
}
