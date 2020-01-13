package com.example.maven;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Captura de botón cerrar
        Button btnCerrar= ( Button ) findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,MainActivity.class);
                AboutActivity.this.startActivity(intent);
            }
        });
    }
}
