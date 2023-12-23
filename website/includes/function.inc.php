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

    if ($result > 0) {
        foreach ($result as $key => $value) {
            if ($value["email"] === $email) {
                return $value;
            }
        }

        return false;
    } else {
        return false;
    }
    /*
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
    }*/
}

function createUser($auth,$database,$name, $imc, $email, $password){
    $hashedPwd = password_hash($password, PASSWORD_DEFAULT);
    $userProperties = [
        'fullName'=>$name,
        'emailVerified'=> false,
        'imc'=>$imc,
        'email'=>$email,
        'password'=>$password,
    ];
    
    $refTable = "DoctorUsers";
    $newUser = $auth->createUser($userProperties);
    if($newUser){
        session_start();
        loginUser($auth,$database,$email,$password, false);
        $postData = [
            'fID'=>$_SESSION['verified_uid'],
            'fullName'=>$name,
            'emailVerified'=> false,
            'imc'=>$imc,
        ];
        $postRef = $database->getReference($refTable)->push($postData);
        header("location: ../views/menu.php");
        exit();
    }
    else{
        header("location: ../views/index.php?error=failedconnection");
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

function loginUser($auth,$database, $email, $password, $redirect){
    /*$emailExists = emailExists($database, $email);
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
    */
    
    try {
        //$user = $auth->getUserByEmail($email);
        
        $signInResult = $auth->signInWithEmailAndPassword($email, $password);
        $idToken = $signInResult->idToken();
        

        try {
            $verifiedIdToken = $auth->verifyIdToken($idToken);
            $uid = $verifiedIdToken->claims()->get('sub');

            session_start();
            $_SESSION['verified_uid'] = $uid;
            $_SESSION['idToken'] = $idToken;
            $value = getUserData($uid, $database);
            $_SESSION['fID'] = $value['fID'];
            $_SESSION['fullName'] = $value['fullName'];
            $_SESSION['imc'] = $value['imc'];
            if ($redirect){
                header("location: ../views/menu.php");
                exit();
            }

        } catch (\Throwable $e) { 
            echo 'The token is invalid: '.$e->getMessage();
        }
    } catch (\Kreait\Firebase\Exception\Auth\UserNotFound | Kreait\Firebase\Auth\SignIn\FailedToSignIn $e) {
        header("location: ../views/login.php?error=wronglogin");
        exit();
    }

}
function getUserData($uid, $database){
    $refTable = "DoctorUsers";
    $result = $database->getReference($refTable)->getValue();

    if($result > 0){
        foreach ($result as $key => $value)
        if ($value["fID"] === $uid){
            return $value;
        }
        return false;
    }
    else{
        return false;
    }
}
