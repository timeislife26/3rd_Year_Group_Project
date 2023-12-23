<?php

require_once '../php-firebase/dbcon.php'; 
require_once 'function.inc.php';

if (isset($_GET['googleId']) && isset($_GET['googleEmail'])) {
    $googleId = $_GET['googleId'];
    $googleEmail = $_GET['googleEmail'];

    $existingUser = checkIfUserExists($database, $googleId, $googleEmail);

    if ($existingUser) {
        initiate2FA($database, $googleEmail);
    } else {
        header("Location: ../register.php?googleId=$googleId&googleEmail=$googleEmail");
        exit();
    }
}

if (isset($_POST["submit"])) {
    $email = $_POST["email"];
    $password = $_POST["password"];

    if (emptyInputLogin($email, $password) !== false) {
        header("location: ../views/login.php?error=emptyinput");
        exit();
    }

    loginUser($auth, $database, $email, $password, true);
} else {
    header("location: ../views/login.php");
    exit();
}

function initiate2FA($database, $email) {
    $verificationCode = mt_rand(100000, 999999);

    saveVerificationCode($database, $email, $verificationCode);

    sendVerificationCodeByEmail($email, $verificationCode);
    header("Location: ../views/2fa_verification.php?email=$email");
    exit();
}

function saveVerificationCode($database, $email, $verificationCode) {
    $database->query("UPDATE users SET verification_code = '$verificationCode' WHERE email = '$email'");
}

function sendVerificationCodeByEmail($email, $verificationCode) {
    $subject = 'Your Two-Factor Authentication Code';
    $message = "Your verification code is: $verificationCode";
    mail($email, $subject, $message);
}
?>
