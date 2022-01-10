package com.praktikum.opticcrud.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditOrder extends Activity {
    String url = Url.updateOrder, id;
    Button cancelButton, saveButton;
    TextView model, price;
    EditText min_left, min_right, cylinder, quantity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_order);
        Intent getData = getIntent();
        id      = getData.getStringExtra("id");
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        model   = findViewById(R.id.model); model.setText(getData.getStringExtra("model"));
        price   = findViewById(R.id.price); price.setText(getData.getStringExtra("price"));

        min_left = findViewById(R.id.minus_left); min_left.setText(getData.getStringExtra("min_kiri"));
        min_right = findViewById(R.id.minus_right); min_right.setText(getData.getStringExtra("min_kanan"));
        cylinder = findViewById(R.id.cylinder); cylinder.setText(getData.getStringExtra("silinder"));
        quantity = findViewById(R.id.total); quantity.setText(getData.getStringExtra("total"));

        saveButton.setOnClickListener(view -> updateData());
        cancelButton.setOnClickListener(view -> finish());
    }

    void updateData(){
        String min_l = min_left.getText().toString();
        String min_r = min_right.getText().toString();
        String cyl = cylinder.getText().toString();
        String qty = quantity.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Log.d("RES", response);
            Toast.makeText(EditOrder.this, "Data updated", Toast.LENGTH_SHORT).show();
            finish();
        }, error -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("min_kiri", min_l);
                params.put("min_kanan", min_r);
                params.put("silinder", cyl);
                params.put("total", qty);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}
