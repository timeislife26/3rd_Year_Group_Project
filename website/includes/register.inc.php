<?php

if(isset($_POST["submit"])){
    
    $name = $_POST["name"];    
    $email = $_POST["email"];
    $imc = $_POST["IMC"];
    $password = $_POST["password"];
    $cpassword = $_POST["confirm-password"];
    $pNumber = $_POST["phone"];
    $Address = $_POST["address"];
    $DOB = $_POST["dob"];
    $gender = $_POST["gender"];

    require_once '../php-firebase/dbcon.php';//'dbh.inc.php';
    require_once 'function.inc.php';

    /*
    if (emptyInputSignup($name,$email, $imc, $password,$cpassword) !== false){
        header("location: ../views/register.php?error=emptyinput");
        exit();
    }
    if (invalidEmail($email) !== false){
        header("location: ../views/register.php?error=invalidemail");
        exit();
    }
    if (passwordMatch($password,$cpassword) !== false){
        header("location: ../views/register.php?error=passwordsdifferent");
        exit();
    }
    if (emailExists($database,$email) !== false){
        header("location: ../views/register.php?error=emailexists");
        exit();
    }*/
    if (ValidPassword($password)){

    createUser($auth,$database,$name,$imc,$email,$password, $DOB, $gender, $Address, $pNumber);
    }
    else {
        header("location: ../views/register.php?error=invalidpassword");
        exit();
    }

}
else{
    header("location: ../views/register.php");
    exit();
}

function ValidPassword($password){
    if(strlen($password) >= 8 && preg_match('/[!@#\$%^&*()_+|~\-={}\[\]:;"\'<>,.?\/]/', $password) && preg_match('/[0-9]/', $password) && preg_match('/[A-Z]/', $password) && preg_match('/[a-z]/', $password)){
        return TRUE;
    }
    else {
    return FALSE;
    }
    
}