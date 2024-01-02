<?php
require_once '../php-firebase/dbcon.php';

if (isset($_GET["fillID"])) {
    $selectedUserID = $_GET["fillID"];
    $userInfo = existingPatientInfo($selectedUserID, $database);

    header('Content-Type: application/json');

    // If userInfo is not false, retrieve the entire row based on the key
    if ($userInfo !== false) {
        $refTable = "MedicalRecords";
        $result = $database->getReference($refTable)->getValue();
        $userData = $result[$userInfo]; // Retrieve the row based on the key

        echo json_encode(["userData" => $userData]);
    } else {
        echo json_encode(["error" => "User not found"]);
    }

    exit();
}

function existingPatientInfo($id, $database) {
    $refTable = "MedicalRecords";
    $result = $database->getReference($refTable)->getValue();
    foreach ($result as $key => $row) {
        if ($row["userID"] === $id) {
            return $key; // Found the ID, return the key
        }
    }
    return false;
}
?>
