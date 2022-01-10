<?php
    include_once("../koneksi.php");

    $id = $_POST['id'];
      
         $sql = "DELETE FROM orders WHERE id='$id' ";
      
         $query = mysqli_query($conn,$sql);
      if($query){
         $data['status'] = true;
         $data['result'] = 'Berhasil';
     }else{
         $data['status'] = false;
         $data['result'] = 'Gagal';
     }
      
     print_r(json_encode($data));
?>