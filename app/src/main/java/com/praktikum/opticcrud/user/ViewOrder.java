package com.praktikum.opticcrud.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewOrder extends Activity {
    ListView lv;
    RequestQueue mqueue;
    ArrayList<HashMap<String,String>> arrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        arrayList=new ArrayList<>();
        lv = findViewById(R.id.listview);
        mqueue = Volley.newRequestQueue(this);

        String url = Url.getOrder + "?username=" +username;


        // TODO: Handle error
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try{
                JSONArray jsonArray = response.getJSONArray("result");

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    String id           = data.getString("id");
                    String uname        = data.getString("name");
                    String model        = data.getString("model");
                    String price        = data.getString("price");
                    String min_kiri     = data.getString("min_kiri");
                    String min_kanan    = data.getString("min_kanan");
                    String cylinder     = data.getString("silinder");
                    String total        = data.getString("total");

                    HashMap<String,String> fetchData = new HashMap<>();
                    fetchData.put("id", id);
                    fetchData.put("name", uname);
                    fetchData.put("user", username);
                    fetchData.put("model", model);
                    fetchData.put("price", price);
                    fetchData.put("min_kiri", min_kiri);
                    fetchData.put("min_kanan", min_kanan);
                    fetchData.put("silinder", cylinder);
                    fetchData.put("total", total);
                    arrayList.add(fetchData);

                    String[] insert = {"id", "user", "name", "model", "price", "min_kiri", "min_kanan", "silinder", "total"};
                    int[] pos = {R.id.id, R.id.username, R.id.name, R.id.model, R.id.price, R.id.minus_left, R.id.minus_right, R.id.cylinder, R.id.total};
                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.listview_order, insert, pos);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener((parent, view, position, idlv) -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewOrder.this);
                        builder.setTitle("Selection");
                        String[] selection = {"Edit", "Cancel Order"};

                        builder.setItems(selection, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    Intent editProduct = new Intent(ViewOrder.this, EditOrder.class);
                                    editProduct.putExtra("id", arrayList.get(position).get("id"));
                                    editProduct.putExtra("model", arrayList.get(position).get("model"));
                                    editProduct.putExtra("price", arrayList.get(position).get("price"));
                                    editProduct.putExtra("min_kiri", arrayList.get(position).get("min_kiri"));
                                    editProduct.putExtra("min_kanan", arrayList.get(position).get("min_kanan"));
                                    editProduct.putExtra("silinder", arrayList.get(position).get("silinder"));
                                    editProduct.putExtra("total", arrayList.get(position).get("total"));
                                    startActivity(editProduct);
                                    break;
                                case 1:
                                    cancelOrder(id);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void cancelOrder(String id){
        String url = Url.deleteOrder;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(ViewOrder.this, "Order Canceled", Toast.LENGTH_SHORT).show();
            finish();
        }, error -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show()){
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
