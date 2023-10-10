<?php

if(isset($_POST["submit"])){
    
    $name = $_POST["name"];    
    $email = $_POST["email"];
    $imc = $_POST["IMC"];
    $password = $_POST["password"];
    $cpassword = $_POST["confirm-password"];

    require_once 'dbh.inc.php';
    require_once 'function.inc.php';


    if (emptyInputSignup($name,$email, $imc, $password,$cpassword) !== false){
        header("location: ../views/signup.php?error=emptyinput");
        exit();
    }
    if (invalidEmail($email) !== false){
        header("location: ../views/signup.php?error=invalidemail");
        exit();
    }
    if (passwordMatch($password,$cpassword) !== false){
        header("location: ../views/signup.php?error=passwordsdifferent");
        exit();
    }
    if (emailExists($conn,$email) !== false){
        header("location: ../views/signup.php?error=emailexists");
        exit();
    }

    createUser($conn,$name,$imc,$email,$password);

}
else{
    header("location: ../views/signup.php");
    exit();
}