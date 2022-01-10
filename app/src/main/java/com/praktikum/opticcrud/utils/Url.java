package com.praktikum.opticcrud.utils;

public class Url {
    public static final String url = "http://192.168.1.5:4443/code/API_Mobile/optik/";
    public static final String createProduct    = url + "products/addData.php";
    public static final String getProduct       = url + "products/getData.php";
    public static final String deleteProduct    = url + "products/deleteData.php";
    public static final String updateProduct    = url + "products/updateData.php";

    public static final String getAllOrder = url + "orders/getAllData.php";
    public static final String createOrder = url + "orders/addData.php";
    public static final String deleteOrder = url + "orders/deleteData.php";
    public static final String updateOrder = url + "orders/updateData.php";
    public static final String getOrder    = url + "orders/getData.php";

    public static final String getAllUser   = url + "login/getAllData.php";
    public static final String getUser      = url + "login/addData.php";
}
