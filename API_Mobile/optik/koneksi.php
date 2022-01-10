<?php 
    $hostname = '127.0.0.1';
    $username = 'root';
    $password = '';
    $database = 'db_optik';
     
    $conn = mysqli_connect($hostname,$username,$password,$database);
    if(!$conn){
        echo "gagal";
    }
    header('Content-Type: application/json; charset=utf-8');
    ?>