package com.praktikum.opticcrud.admin;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ManageProduct extends AppCompatActivity {

    FloatingActionButton add;
    ListView lv;
    RequestQueue mqueue;
    ArrayList<HashMap<String,String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_product);

        arrayList=new ArrayList<>();
        lv = findViewById(R.id.listview);
        mqueue = Volley.newRequestQueue(this);
        add = findViewById(R.id.addButton);

        add.setOnClickListener(view -> {
            Intent addProduct = new Intent(this, AddProduct.class);
            startActivity(addProduct);
        });

        refreshList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void refreshList(){

        String url = Url.getProduct;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try{
                JSONArray jsonArray = response.getJSONArray("result");

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    String price = data.getString("price");
                    String model = data.getString("model");

                    HashMap<String,String> fetchData = new HashMap<>();
                    fetchData.put("id", data.getString("id"));
                    fetchData.put("price", price);
                    fetchData.put("model", model);
                    arrayList.add(fetchData);

                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.listview_product, new String[]{"model", "price"}, new int[]{R.id.model, R.id.price});
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener((parent, view, position, id) -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageProduct.this);
                        builder.setTitle("Selection");
                        String[] selection = {"Edit", "Delete"};

                        builder.setItems(selection, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    Intent editProduct = new Intent(ManageProduct.this, EditProduct.class);
                                    editProduct.putExtra("id", arrayList.get(position).get("id"));
                                    editProduct.putExtra("model", arrayList.get(position).get("model"));
                                    editProduct.putExtra("price", arrayList.get(position).get("price"));
                                    startActivity(editProduct);
                                    break;
                                case 1:
                                    deleteItem(arrayList.get(position).get("id"));
                                    break;
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    });

                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        mqueue.add(jsonObjectRequest);
    }
    private void deleteItem(String id){
        String url = Url.deleteProduct;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {}, Throwable::printStackTrace)
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);

        finish();
        startActivity(getIntent());
    }

}