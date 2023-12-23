<?php
require_once '../php-firebase/dbcon.php';

session_start();

function validate2FACode($email, $code, $database)
{
    $reference = $database->getReference('users/' . $email . '/verificationCode');
    $storedVerificationCode = $reference->getValue();

    if ($storedVerificationCode !== null && $code === $storedVerificationCode) {
        header("Location: ../views/protected_resources.php");
        exit();
    } else {
        header("Location: ../views/verify_2fa.php?error=invalid_code");
        exit();
    }
}

if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
    header("Location: ../views/verify_2fa.php?error=csrf_error");
    exit();
}

$email = $_POST['email'];
$code = $_POST['2fa_code'];

validate2FACode($email, $code, $database);

header("Location: ../views/verify_2fa.php?error=generic_error");
exit();
?>
