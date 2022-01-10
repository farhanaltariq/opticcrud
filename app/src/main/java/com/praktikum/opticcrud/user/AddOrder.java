package com.praktikum.opticcrud.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import java.util.HashMap;
import java.util.Map;

public class AddOrder extends AppCompatActivity {
    EditText min_kiri, min_kanan, silinder, total;
    TextView name, model, price;
    Button insert;
    String idProd, uname, mdl, prc;

    public static final String url = Url.createOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order);

        Intent parsed = getIntent();
        idProd = parsed.getStringExtra("id");
        uname = parsed.getStringExtra("name");
        mdl = parsed.getStringExtra("model");
        prc = parsed.getStringExtra("price");

        name = findViewById(R.id.name); name.setText(uname);
        model = findViewById(R.id.model); model.setText(mdl);
        price = findViewById(R.id.price); price.setText(prc);

        min_kiri = findViewById(R.id.minus_left);
        min_kanan = findViewById(R.id.minus_right);
        silinder = findViewById(R.id.cylinder);
        total = findViewById(R.id.total);

        insert = findViewById(R.id.button);
        insert.setOnClickListener(view-> inputData());
    }
    void inputData(){
        String Tuser = uname;
        String Tprodid = idProd;
        String Tprice = prc;
        String Tmodel = mdl;

        String Tmin_kiri = min_kiri.getText().toString();
        String Tmin_kanan = min_kanan.getText().toString();
        String Tsilinder = silinder.getText().toString();
        String Ttotal = total.getText().toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Log.d("Params", response);
        }, Throwable::printStackTrace)
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("user", Tuser);
                params.put("price", Tprice);
                params.put("model", Tmodel);
                params.put("product_id", Tprodid);
                params.put("min_kiri", Tmin_kiri);
                params.put("min_kanan", Tmin_kanan);
                params.put("silinder", Tsilinder);
                params.put("total", Ttotal);
                Log.d("Params", String.valueOf(params));

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);

        Toast.makeText(AddOrder.this, "Order added", Toast.LENGTH_SHORT).show();
        finish();
    }
}