<?php

function emptyInputSignup($name,$email,$imc,$password,$cpassword){
    $result;
    if (empty($name) || empty($email) || empty($imc) || empty($password) || empty($cpassword)){
        $result = true;
    }
    else{
        $result = false;
    }
    return $result;
}

function invalidEmail($email){
    $result;
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)){
        $result = true;
    }
    else {
        $result = false;
    }
    return $result;
}

function passwordMatch($password,$cpassword){
    $result;
    if ($password !== $cpassword) {
        $result = true;
    }
    else { 
        $result = false;
    }
    return $result;
}

function emailExists($conn,$email){
    $sql = "SELECT * FROM users WHERE EMAIL = ?;";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)){
        header("location: ../views/signup.html?error=stmtfailed");
        exit();
    }
    
    mysqli_stmt_bind_param($stmt, "s", $email);
    mysqli_stmt_execute($stmt);

    $resultData = mysqli_stmt_get_result($stmt);
    if ($row = mysqli_fetch_assoc($resultData)){
        return $row;
    }
    else{
        $result = false;
        return $result;
    }

    mysqli_stmt_close($stmt);
}

function createUser($conn,$name, $imc, $email, $password){
    $sql = "INSERT INTO users (full_Name, email, IMC_Num,  password) VALUES (?,?,?, ?);";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)){
        header("location: ../views/signup.html?error=stmtfailed");
        exit();
    }

    $hashedPwd = password_hash($password, PASSWORD_DEFAULT);
    
    mysqli_stmt_bind_param($stmt, "ssss", $name, $email, $imc, $hashedPwd);//change $password to hashedPWD to encrypt
    mysqli_stmt_execute($stmt);
    mysqli_stmt_close($stmt);
    header("location: ../views/index.php");
    exit();
}


function emptyInputLogin($email,$password){
    $result;
    if (empty($email) || empty($password)){
        $result = true;
    }
    else{
        $result = false;
    }
    return $result;
}

function loginUser($conn, $email, $password){
    $emailExists = emailExists($conn, $email);

    if ($emailExists === false){
        header("location: ../views/login.php?error=noemail");
        exit();
    }
    /*
    $dbPassword = $emailExists["password"];

    if ($dbPassword === $password){
        session_start();
        $_SESSION["id"] = $emailExists["id"];
        header("location: ../views/index.php?");
        exit();
    }
    else if ($dbPassword != $password){
        header("location: ../views/login.php?error=wronglogin");
        exit();
    }
    */
    //For Hashed passwords
    $pwdHashed = $emailExists["password"];
    $checkPwd = password_verify($password, $pwdHashed);

    if ($checkPwd === false){
        header("location: ../views/login.php?error=wrongpassword");
        exit();
    }
    else if ($checkPwd === true){
        session_start();
        $_SESSION["id"] = $emailExists["id"];
        header("location: ../views/index.php");
        exit();
    }
    
}