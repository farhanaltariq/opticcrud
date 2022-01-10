package com.praktikum.opticcrud.admin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class ManageUser extends Activity {
    ListView lv;
    RequestQueue mqueue;
    ArrayList<HashMap<String,String>> arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_user);

        arrayList=new ArrayList<>();
        lv = findViewById(R.id.listview);
        mqueue = Volley.newRequestQueue(this);

        String url = Url.getAllUser;
        // TODO: Handle error
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try{
                JSONArray jsonArray = response.getJSONArray("result");

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    String username = data.getString("username");
                    String name = data.getString("name");
                    String level = data.getString("level");

                    HashMap<String,String> fetchData = new HashMap<>();
                    fetchData.put("username", username);
                    fetchData.put("name", name);
                    fetchData.put("level", level);
                    arrayList.add(fetchData);

                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.listview_user, new String[]{"username", "name", "level"}, new int[]{R.id.usertitle, R.id.name, R.id.level});
                    lv.setAdapter(adapter);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        mqueue.add(jsonObjectRequest);
    }

}
