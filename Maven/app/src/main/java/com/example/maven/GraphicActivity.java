package com.example.maven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GraphicActivity extends AppCompatActivity {

    TextView usuarioLog;
    private static final String URL_PRODUCTS = "http://192.168.1.7/MyApi/Api.php";

    //a list to store all the products
    private ArrayList<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            //viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            //Product thisItem = productList.get(position);
            Product thisItem = productList.get(position);
            Intent intent = new Intent(GraphicActivity.this,DetailActivity.class);
            intent.putExtra("image",thisItem.getImage());
            GraphicActivity.this.startActivity(intent);
            //Toast.makeText(GraphicActivity.this, "You Clicked: " + thisItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        //Recibir el nombre de Usuario para mostrar en txtName
        usuarioLog=(TextView) findViewById(R.id.txtName);
        Intent intent = getIntent();
        //String usuario=intent.getStringExtra("names");
        String usuario=intent.getStringExtra("first_name");
        usuarioLog.setText("Usuario: "+usuario);

        //Captura de botón about
        Button btnAbout= ( Button ) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphicActivity.this,AboutActivity.class);
                GraphicActivity.this.startActivity(intent);
            }
        });

        //Captura de botón cerrar
        Button btnCerrar= ( Button ) findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphicActivity.this,MainActivity.class);
                GraphicActivity.this.startActivity(intent);
            }
        });


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

        //ProductsAdapter recyclerViewAdapter = new ProductsAdapter(productList);
        ProductsAdapter recyclerViewAdapter = ProductsAdapter.getInstance();
        recyclerViewAdapter.setProductsAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(onItemClickListener);

    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("shortdesc"),
                                        product.getDouble("rating"),
                                        product.getDouble("price"),
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            //ProductsAdapter adapter = new ProductsAdapter(GraphicActivity.this, productList);
                            ProductsAdapter adapter = ProductsAdapter.getInstance();
                            adapter.setProductsAdapter(GraphicActivity.this,productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}

