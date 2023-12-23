<?php
require_once '../php-firebase/dbcon.php';

function validateVerificationCode($email, $verificationCode, $database)
{
    $reference = $database->getReference('users/' . $email . '/verificationCode');
    $storedVerificationCode = $reference->getValue();

    if ($storedVerificationCode !== null && $verificationCode === $storedVerificationCode) {
        $database->getReference('users/' . $email . '/verified')->set(true);
        header("Location: ../views/success.php");
        exit();
    } else {
        header("Location: ../views/email_2fa.php?error=invalid_code");
        exit();
    }
}

if ($_POST['csrf_token'] !== $_SESSION['csrf_token']) {
    header("Location: ../views/email_2fa.php?error=csrf_error");
    exit();
}

$email = $_POST['email'];
$verificationCode = $_POST['verification_code'];

validateVerificationCode($email, $verificationCode, $database);

header("Location: ../views/email_2fa.php?error=generic_error");
exit();
?>
