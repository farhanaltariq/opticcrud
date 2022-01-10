<?php
     
     include('../koneksi.php');
      
     $id            = isset($_POST['id'])          ? $_POST['id']           : '';
     $user          = isset($_POST['user'])        ? $_POST['user']         : '';
     $product_id    = isset($_POST['product_id'])  ? $_POST['product_id']   : '';
     $min_kiri      = isset($_POST['min_kiri'])    ? $_POST['min_kiri']     : '';
     $min_kanan     = isset($_POST['min_kanan'])   ? $_POST['min_kanan']    : '';
     $silinder      = isset($_POST['silinder'])    ? $_POST['silinder']     : '';
     $total         = isset($_POST['total'])       ? $_POST['total']        : '';
      
      
         $sqlCheck = "SELECT COUNT(*) FROM product WHERE id='$id'";
         $queryCheck = mysqli_query($conn,$sqlCheck);
         $hasilCheck = mysqli_fetch_array($queryCheck);
         if($hasilCheck[0] == 0){
             $sql = "INSERT INTO `orders` (`id`, `user`, `product_id`, `min_kiri`, `min_kanan`, `silinder`, `total`) 
                     VALUES ('$id', '$user', '$product_id', '$min_kiri', '$min_kanan', '$silinder', '$total'); ";
      
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
             $data['result'] = "Gagal, id Sudah Ada";
         }
      
     print_r(json_encode($data));
      
?>