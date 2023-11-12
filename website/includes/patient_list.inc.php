<?php

// Database connection
require_once '../php-firebase/dbcon.php'; //'dbh.inc.php';

// Query to fetch the list of names from DB
$refTable = "DoctorUsers";

$result = $database->getReference($refTable)->getValue();

// Array to store names
$patientList = array();

// Getting names and storing them in array
foreach ($result as $key => $row)
    $patientList[] = $row["fullName"];

// Funtion to display information of the selected patient
function displayPatientProfile($database, $patientList) {
    if (isset($_POST['generate'])) {
        $refTable = "DoctorUsers";
        $targetFullName = $_POST['selectedPatient'];
        
        $result = $database->getReference($refTable)->getValue();
        
        if ($result) {
          foreach ($result as $entry) {
            if (strcasecmp(trim($entry['fullName']), $targetFullName) === 0) {
              // Match found, you can print or work with the entry data
              echo "\n\tEmail Verified: " . $entry['emailVerified'] . "\n";
              echo "\tFull Name: " . $entry['fullName'] . "\n";
              echo "\tfID: " . $entry['fID'] . "\n";
              echo "\tIMC: " . $entry['imc'] . "\n";
              break; // You can exit the loop since you found the match
            }
          }
        } else {
          echo "No results found for the specified table.";
        }
    }
}
?>
