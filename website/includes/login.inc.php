<?php

if (isset($_POST["submit"])){
    $email = $_POST["email"];
    $password = $_POST["password"];

    require_once '../php-firebase/dbcon.php';//'dbh.inc.php';
    require_once 'function.inc.php';

    if (emptyInputLogin($email,$password) !== false){
        header("location: ../views/login.php?error=emptyinput");
        exit();
    }
    loginUser($auth,$database, $email,$password, true);
}
else{
    header("location: ../views/login.php");
    exit();
}