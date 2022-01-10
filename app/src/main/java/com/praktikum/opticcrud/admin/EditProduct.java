package com.praktikum.opticcrud.admin;

import android.app.Activity;
import android.content.Intent;
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

public class EditProduct extends Activity {
    String url = Url.updateProduct, id;
    Button editData;
    EditText model, price;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        Intent getData = getIntent();
        id      = getData.getStringExtra("id");
        editData = findViewById(R.id.button);
        model   = findViewById(R.id.model); model.setText(getData.getStringExtra("model"));
        price   = findViewById(R.id.price); price.setText(getData.getStringExtra("price"));

        editData.setOnClickListener(view -> inputData());
    }

    void inputData(){
        String modl = model.getText().toString();
        String prc = price.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(EditProduct.this, "Data updated", Toast.LENGTH_SHORT).show();
            finish();
        }, error -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("model", modl);
                params.put("price", prc);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}
