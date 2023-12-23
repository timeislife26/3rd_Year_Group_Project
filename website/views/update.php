<?php
include_once 'header.php';

require_once '../php-firebase/dbcon.php';

if (isset($_POST["update"])) {
    $name = $_POST["name"];
    $email = $_POST["email"];
    $age = $_POST["Age"];
    $gender = $_POST["gender"];
    $phone = $_POST["phone"];
    $address = $_POST["address"];
    $interests = $_POST["interests"];

    $userData = [
        'name' => $name,
        'email' => $email,
        'age' => $age,
        'gender' => $gender,
        'phone' => $phone,
        'address' => $address,
        'interests' => $interests,
    ];

    updateUserProfile($database, $email, $userData);

    $verificationCode = mt_rand(100000, 999999);
    saveVerificationCode($database, $email, $verificationCode);
    sendVerificationCodeByEmail($email, $verificationCode);

    header("Location: ../views/2fa_verification.php?email=$email");
    exit();
}
?>

<main>
    <div id="container">
        <form action="../includes/update.inc.php" method="POST">
            <h2>User Profile Update</h2>
            <label for="name">Name: <input type="text" name="name" required></label>
            <label for="email">Email: <input type="email" name="email" required></label>
            <label for="Age">Age: <input type="number" name="Age" required></label>
            <label for="gender">Gender: 
                <select name="gender">
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                </select>
            </label>
            <label for="phone">Phone: <input type="tel" name="phone"></label>
            <label for="address">Address: <input type="text" name="address"></label>
            <label for="interests">Interests: <input type="text" name="interests"></label>
            <!-- Add other user profile fields here -->

            <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
            <button type="submit" name="update">Update Profile</button>
        </form>
    </div>
</main>

<?php
include_once 'footer.php';
?>
