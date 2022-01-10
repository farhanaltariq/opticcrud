package com.praktikum.opticcrud.admin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends Activity {
    String url = Url.createProduct;
    Button addData;
    EditText model, price;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        addData = findViewById(R.id.button);
        model = findViewById(R.id.model);
        price = findViewById(R.id.price);

        addData.setOnClickListener(view -> inputData());
    }
    void inputData(){
        String modl = model.getText().toString();
        String prc = price.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(AddProduct.this, "Success adding data", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("model", modl);
                params.put("price", prc);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}
