package com.praktikum.opticcrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.praktikum.opticcrud.admin.AdminView;
import com.praktikum.opticcrud.user.SignUp;
import com.praktikum.opticcrud.user.UserView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private EditText username, Password;
    private String uname, password;
    private String URL = "http://192.168.1.5:4443/code/API_Mobile/optik/login/getData.php";
    private RequestQueue mqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = password = "";
        username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);

        mqueue = Volley.newRequestQueue(this);
        login.setOnClickListener(view -> {
            uname = username.getText().toString();
            password = Password.getText().toString();
            if(uname.equals("") || password.equals("")) {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                URL = "http://192.168.1.5:4443/code/API_Mobile/optik/login/getData.php?username=" + uname + "&password=" + password;
                login();
            }
        });
        signup.setOnClickListener(view -> {
            Intent signmeup = new Intent(MainActivity.this, SignUp.class);
            startActivity(signmeup);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        username.setText("");
        Password.setText("");
    }

    void login(){
        // TODO: Handle error
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try{
                    JSONArray jsonArray = response.getJSONArray("result");
                    JSONObject data = jsonArray.getJSONObject(0);
                    String level = data.getString("level");

                    if (level.equals("admin")) {
                        Intent adminView = new Intent(MainActivity.this, AdminView.class);
                        startActivity(adminView);
                    } else if (level.equals("user")) {
                        Intent userView = new Intent(getApplicationContext(), UserView.class);
                        userView.putExtra("username", data.getString("username"));
                        startActivity(userView);
                    }
            } catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
            }
        }, Throwable::printStackTrace);
        mqueue.add(jsonObjectRequest);
    }

}