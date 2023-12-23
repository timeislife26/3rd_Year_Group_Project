<?php
session_start();

if (!isset($_SESSION['user_id'])) {
    header("Location: login.php");
    exit();
}

if (isset($_POST['submit'])) {
    $enteredCode = isset($_POST['verification_code']) ? htmlspecialchars($_POST['verification_code']) : '';
    $savedCode = isset($_SESSION['email_verification_code']) ? $_SESSION['email_verification_code'] : '';

    if (!empty($enteredCode) && password_verify($enteredCode, $savedCode)) {
        echo "Verification successful! You can proceed.";
        unset($_SESSION['email_verification_code']);
    } else {
        echo "Invalid verification code. Please try again.";
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify Email 2FA</title>
</head>
<body>
    <div>
    <h2>Email 2FA Verification</h2>
    <p>Please enter the verification code sent to your email address.</p>

    <form action="verify_email_2fa.php" method="POST">
        <label for="verification_code">Verification Code:</label>
        <input type="text" id="verification_code" name="verification_code" required>
        <button type="submit" name="submit">Verify</button>
    </form>
</div>
</body>
</html>
