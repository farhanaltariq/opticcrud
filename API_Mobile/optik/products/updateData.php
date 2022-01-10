<?php
     include('../koneksi.php');
      
     $id       = $_POST['id']; 
     $model    = $_POST['model']; 
     $price    = $_POST['price'];
      
     if(!empty($id)){
      
         $sql = "UPDATE product set model = '$model', price = '$price'  WHERE id='$id' ";
         $query = mysqli_query($conn,$sql);
      
         if(mysqli_affected_rows($conn) > 0){
             $data['status'] = true;
             $data['result'] = "Berhasil";
         }else{
             $data['status'] = false;
             $data['result'] = "Gagal";
         }
      
     }else{
         $data['status'] = false;
         $data['result'] = "Gagal, id tidak boleh kosong!";
     }
     print_r(json_encode($data));
     ?>