package com.example.maven;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GraphicActivity extends AppCompatActivity {

    public static final String nombres = "names";

    TextView cajaBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        cajaBienvenido=(TextView) findViewById(R.id.textBinv);
        String usuario=getIntent().getStringExtra("names");
        cajaBienvenido.setText("!Bienvenido " + usuario +"!");


    }
}
