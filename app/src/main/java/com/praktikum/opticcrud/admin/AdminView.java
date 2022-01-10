package com.praktikum.opticcrud.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.praktikum.opticcrud.R;

public class AdminView extends Activity {
    Button mngOrder, mngProduct, mngUsers;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view);

        mngOrder = findViewById(R.id.manageOrders);
        mngProduct = findViewById(R.id.manageProduct);
        mngUsers = findViewById(R.id.manageUsers);

        mngOrder.setOnClickListener(view -> {
            Intent ordermgmt = new Intent(this, ManageOrder.class);
            startActivity(ordermgmt);
        });

        mngProduct.setOnClickListener(view -> {
            Intent productmgmt = new Intent(this, ManageProduct.class);
            startActivity(productmgmt);
        });

        mngUsers.setOnClickListener(view -> {
            Intent usermgmt = new Intent(this, ManageUser.class);
            startActivity(usermgmt);
        });
    }
}
