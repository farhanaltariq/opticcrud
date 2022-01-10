<?php 
    include('../koneksi.php');
    
    $username = $_GET['username'];
    $password = $_GET['password'];

    $sql = "SELECT * FROM user WHERE username = '$username' AND password = '$password';";
     
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