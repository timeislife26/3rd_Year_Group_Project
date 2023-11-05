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

function emailExists($database,$email){
    $refTable = "DoctorUsers";
    $result = $database->getReference($refTable)->getValue();

    if($result > 0){
        foreach ($result as $key => $value)
        if ($value["email"] === $email){
            return $value;
        }
        return false;
    }
    else{
        return false;
    }
}

function createUser($database,$name, $imc, $email, $password){
    $hashedPwd = password_hash($password, PASSWORD_DEFAULT);
    $postData = [
        'fullName'=>$name,
        'imc'=>$imc,
        'email'=>$email,
        'password'=>$hashedPwd,
    ];
    
    $refTable = "DoctorUsers";
    $postRef = $database->getReference($refTable)->push($postData);
    $emailExists = emailExists($database, $email);
    if($postRef){
        session_start();
        $_SESSION['email'] = $postData['email'];
        $_SESSION['fullName'] = $postData['fullName'];
        $_SESSION['imc'] = $postData['imc'];
        header("location: ../views/menu.php");
        exit();
    }
    else{
        header("location: ../views/menu.php?error=failedconnection");
        exit();
    }
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

function loginUser($database, $email, $password){
    $emailExists = emailExists($database, $email);
    /*
    if ($emailExists === false){
        header("location: ../views/login.php?error=noemail");
        exit();
    }
    
    $dbPassword = $emailExists["Password"];
    $dbEmail = $emailExists["Email"];

    if ($dbPassword === $password){
        session_start();
        $_SESSION["id"] = $emailExists["id"];
        header("location: ../views/index.php?");
        exit();
    }
    else if ($dbPassword !== $password){
        header("location: ../views/login.php?error=$dbPassword");
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
        $_SESSION['id'] = $emailExists['id'];
        $_SESSION['email'] = $emailExists['email'];
        $_SESSION['fullName'] = $emailExists['fullName'];
        $_SESSION['imc'] = $emailExists['imc'];
        header("location: ../views/menu.php");
        exit();
    }
    
}