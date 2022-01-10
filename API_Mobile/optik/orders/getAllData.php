<?php 
    include('../koneksi.php');

    $sql = "SELECT orders.id, orders.user, user.name, product.model, product.price, orders.min_kiri, orders.min_kanan, orders.silinder, orders.total FROM orders LEFT JOIN product ON orders.product_id = product.id JOIN user ON orders.user = user.username;";
     
    $query = mysqli_query($conn,$sql);
     
    if(mysqli_num_rows($query) > 0){
        while($row = mysqli_fetch_object($query)){
            $data['status'] = true;
            $data['result'][] = $row;
     
            // $data2 = respond(true, $row);
        }
    }else{
        $data['status'] = false;
        $data['result'][] = "Data not Found";
    }
     
    print_r(json_encode($data));
     
     
    ?>