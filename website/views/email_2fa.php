<?php
session_start();

if (!isset($_SESSION['user_id'])) {
    header("Location: login.php");
    exit();
}

$verificationCode = rand(100000, 999999);
$_SESSION['email_verification_code'] = $verificationCode;
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email 2FA Page</title>
</head>
<body>
    <h1>Email 2FA Page</h1>
    <p>An email with a verification code has been sent to your registered email address.</p>

    <form action="verify_email_2fa.php" method="POST">
        <label for="verification_code">Enter Verification Code:</label>
        <input type="text" id="verification_code" name="verification_code" required>
        <button type="submit" name="submit">Verify</button>
    </form>
</body>
</html>
