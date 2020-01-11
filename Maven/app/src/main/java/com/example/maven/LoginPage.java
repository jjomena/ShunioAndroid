package com.example.maven;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

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


public class LoginPage extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private RequestQueue rq;
    private EditText fieldUser, fieldPwd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login_page, container,false);
        View vista = inflater.inflate(R.layout.fragment_login_page,container,false);
        fieldUser=(EditText) vista.findViewById(R.id.textUser);
        fieldPwd=(EditText) vista.findViewById(R.id.txtPwd);
        Button btnConsult = (Button) vista.findViewById(R.id.btnIntro);
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

    private void newSesion() {
        String url = "http://192.168.0.11/login/sesion.php?user="+fieldUser.getText().toString()+
                "&pwd="+fieldPwd.getText().toString();
        Log.d("URLConexion",url);

        JsonRequest jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    private void nuevaSesion(){
        final String username=fieldUser.getText().toString();
        final String password=fieldPwd.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        String name = jsonResponse.getString("names");
                        Intent intencion = new Intent(getContext(),GraphicActivity.class);
                        //intencion.putExtra(GraphicActivity.nombres,userJson.getFirst_name());
                        intencion.putExtra(GraphicActivity.nombres,name);
                        startActivity(intencion);
                    }else{
                        Toast.makeText(getContext(),"No se encontro el usuario",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(username,password,responseListener);
        rq = Volley.newRequestQueue(getContext());
        rq.add(loginRequest);
    }




    @Override
    public void onResponse(JSONObject response) {
        //User userJson = new User();
        OtherUser usuario = new OtherUser();

        Toast.makeText(getContext(),"Se ha encontrado el usuario "+ fieldUser.getText().toString(), Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject= null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setUser(jsonObject.optString("user"));
            usuario.setPwd(jsonObject.optString("pwd"));
            usuario.setNames(jsonObject.optString("names"));
            //userJson.setFirst_name(jsonObject.optString("first_name"));
            //userJson.setLast_name(jsonObject.optString("last_name"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        Intent intencion = new Intent(getContext(),GraphicActivity.class);
        //intencion.putExtra(GraphicActivity.nombres,userJson.getFirst_name());
        intencion.putExtra(GraphicActivity.nombres,usuario.getNames());
        startActivity(intencion);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se encontro el usuario" + error.toString(),Toast.LENGTH_SHORT).show();
    }
}
