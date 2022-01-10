<?php
     
     include('../koneksi.php');
      
     $price    = $_POST['price']; 
     $model    = $_POST['model']; 
      
    $sql = "INSERT INTO product (model, price) VALUES('$model', '$price')";

    $query = mysqli_query($conn,$sql);
    // echo $sql;
    if($query){
        $data['status'] = true;
        $data['result'] = "Berhasil";
    }else{
        $data['status'] = false;
        $data['result'] = "Gagal";
    }
      
      
     print_r(json_encode($data));
      
?>