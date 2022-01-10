<?php
     include('../koneksi.php');
      
     $id       = $_POST['id']; 
     $min_kiri = $_POST['min_kiri'];
     $min_kanan = $_POST['min_kanan'];
     $silinder  = $_POST['silinder'];
     $total     = $_POST['total'];
      
     if(!empty($id)){
      
         $sql = "UPDATE orders set min_kiri = '$min_kiri', min_kanan = '$min_kanan', silinder = '$silinder', total = '$total'  WHERE id='$id' ";
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