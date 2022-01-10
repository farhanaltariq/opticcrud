<?php 
    include('../koneksi.php');
    
    $username = $_POST['username'];
    $password = $_POST['password'];
    $name     = $_POST['name'];
    $level    = 'user';

    $sql = "INSERT INTO user (username, name, password, level) VALUES ('$username', '$name', '$password', '$level');";

    $query = mysqli_query($conn,$sql);
     
    if($query){
        echo "Signup Success, please login";
    }else{
        echo "Username already used";
    }
    
     
    ?>