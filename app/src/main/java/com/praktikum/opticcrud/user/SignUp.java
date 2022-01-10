package com.praktikum.opticcrud.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praktikum.opticcrud.R;
import com.praktikum.opticcrud.utils.Url;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends Activity {
    EditText usernameField, nameField, passwordField, repasswordField;
    Button signup, reset;
    String username, password, name, repassword;
    String url = Url.getUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        signup = findViewById(R.id.signup);
        reset = findViewById(R.id.reset);

        usernameField = findViewById(R.id.username);
        nameField = findViewById(R.id.name);
        passwordField = findViewById(R.id.password);
        repasswordField = findViewById(R.id.repassword);
        username = password = name = repassword = "";
        signup.setOnClickListener(view -> signMeUp());

        reset.setOnClickListener(view -> {
            usernameField.setText("");
            nameField.setText("");
            passwordField.setText("");
            repasswordField.setText("");
        });
    }
    void signMeUp() {
        username = usernameField.getText().toString();
        name = nameField.getText().toString();
        password = passwordField.getText().toString();
        repassword = repasswordField.getText().toString();
        if (username.equals("") || name.equals("") || password.equals("")) {
            Toast.makeText(SignUp.this, "All field must be filled", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(repassword)) {
            Toast.makeText(SignUp.this, "Password field mismatch", Toast.LENGTH_SHORT).show();
        } else {
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                if (response.equals("Signup Success, please login"))
                    finish();
            }, error -> Toast.makeText(SignUp.this, "Username already used", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("username", username);
                    params.put("name", name);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest);
        }
    }
}
