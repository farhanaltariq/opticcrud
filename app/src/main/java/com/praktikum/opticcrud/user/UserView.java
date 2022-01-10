package com.praktikum.opticcrud.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UserView extends AppCompatActivity {


    ListView lv;
    RequestQueue mqueue;
    ArrayList<HashMap<String,String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view);

        arrayList=new ArrayList<>();
        lv = findViewById(R.id.listview);
        mqueue = Volley.newRequestQueue(this);

        FloatingActionButton cart = findViewById(R.id.cartButton);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        cart.setOnClickListener(view -> {

            Intent viewOrder = new Intent(this, ViewOrder.class);
            viewOrder.putExtra("username", username);
            startActivity(viewOrder);
        });

        String url = Url.getProduct;
        // TODO: Handle error
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try{
                JSONArray jsonArray = response.getJSONArray("result");

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    String id = data.getString("id");
                    String price = data.getString("price");
                    String model = data.getString("model");

                    HashMap<String,String> fetchData = new HashMap<>();
                    fetchData.put("id", id);
                    fetchData.put("price", price);
                    fetchData.put("model", model);
                    arrayList.add(fetchData);


                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.listview_product, new String[]{"model", "price"}, new int[]{R.id.model, R.id.price});
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener((parent, view, position, id1) -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserView.this);
                        builder.setMessage("Buy this item ? ").setPositiveButton("Yes", (dialogInterface, i1) -> {
                            Intent addOrder = new Intent(UserView.this, AddOrder.class);
                            addOrder.putExtra("id", arrayList.get(position).get("id"));
                            addOrder.putExtra("name", username);
                            addOrder.putExtra("model", arrayList.get(position).get("model"));
                            addOrder.putExtra("price", arrayList.get(position).get("price"));

                            startActivity(addOrder);
                        }).setNegativeButton("No", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                    });

                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        mqueue.add(jsonObjectRequest);
    }

}