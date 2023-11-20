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
              // Match found, printing the entry data
              echo "\n\tEmail Verified: " . $entry['emailVerified'] . "\n";
              echo "\tFull Name: " . $entry['fullName'] . "\n";
              echo "\tfID: " . $entry['fID'] . "\n";
              echo "\tIMC: " . $entry['imc'] . "\n";
              break; // Exit loop when match is found
            }
          }
        } else {
            echo "No results found for the specified table.";
        }
    }
}

// Function that will make the request for the prediction
// (currently a copy of the Profile function, will be updated once the prediction is running)
function displayPatientPrediction($database, $patientList) {
  if (isset($_POST['generate'])) {
      $refTable = "DoctorUsers";
      $targetFullName = $_POST['selectedPatient'];
      
      $result = $database->getReference($refTable)->getValue();
      
      if ($result) {
        foreach ($result as $entry) {
          if (strcasecmp(trim($entry['fullName']), $targetFullName) === 0) {
            // Match found, printing the entry data
            echo "\n\tEmail Verified: " . $entry['emailVerified'] . "\n";
            echo "\tFull Name: " . $entry['fullName'] . "\n";
            echo "\tfID: " . $entry['fID'] . "\n";
            echo "\tIMC: " . $entry['imc'] . "\n";
            break; // Exit loop when match is found
          }
        }
      } else {
          echo "No results found for the specified table.";
      }
  }
}

// Function that will export the information of the patient into an txt file
function displayPatientExport($database, $patientList) {
  if (isset($_POST['export'])) {
      $refTable = "DoctorUsers";
      $targetFullName = $_POST['selectedPatient'];

      $result = $database->getReference($refTable)->getValue();

      if ($result) {
          foreach ($result as $entry) {
              if (strcasecmp(trim($entry['fullName']), $targetFullName) === 0) {
                  // Match found, prepare the data for export
                  $exportData = "Email Verified: " . $entry['emailVerified'] . "\n";
                  $exportData .= "Full Name: " . $entry['fullName'] . "\n";
                  $exportData .= "fID: " . $entry['fID'] . "\n";
                  $exportData .= "IMC: " . $entry['imc'] . "\n";

                  // Set the headers to trigger a download
                  header('Content-Type: application/octet-stream');
                  header('Content-Disposition: attachment; filename="patient_export.txt');
                  header('Pragma: no-cache');
                  header('Expires: 0');

                  // Making sure only the info we want is on the file
                  ob_end_clean();
                  
                  // Output the data
                  echo $exportData;

                  exit;
              }
          }
          echo "No matching patient found.";
      } else {
          echo "No results found for the specified table.";
      }
  }
}
?>
