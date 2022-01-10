package com.praktikum.opticcrud.admin;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ManageOrder extends AppCompatActivity {


    ListView lv;
    RequestQueue mqueue;
    ArrayList<HashMap<String,String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_order);

        arrayList=new ArrayList<>();
        lv = findViewById(R.id.listview);
        mqueue = Volley.newRequestQueue(this);

        String url = Url.getAllOrder;
        // TODO: Handle error
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            try{
                JSONArray jsonArray = response.getJSONArray("result");

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    String id           = data.getString("id");
                    String username     = data.getString("user");
                    String name         = data.getString("name");
                    String model        = data.getString("model");
                    String price        = data.getString("price");
                    String min_kiri     = data.getString("min_kiri");
                    String min_kanan    = data.getString("min_kanan");
                    String cylinder     = data.getString("silinder");
                    String total        = data.getString("total");

                    HashMap<String,String> fetchData = new HashMap<>();
                    fetchData.put("id", id);
                    fetchData.put("user", username);
                    fetchData.put("name", name);
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

                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        mqueue.add(jsonObjectRequest);
    }

}