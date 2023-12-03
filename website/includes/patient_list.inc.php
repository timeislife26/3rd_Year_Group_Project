<?php

// Database connection
require_once '../php-firebase/dbcon.php'; //'dbh.inc.php';

// Query to fetch the list of names from DB
$refTable = "PatientUsers";

$result = $database->getReference($refTable)->getValue();

// Array to store names
$patientList = array();

// Getting names and storing them in array
foreach ($result as $key => $row)
    $patientList[] = $row["name"];

// Funtion to display information of the selected patient
function displayPatientProfile($database, $patientList) {
  if (isset($_POST['generate'])) {
    $refTable = "PatientUsers";
    $secondTable = "MedicalRecords";
    $targetFullName = $_POST['selectedPatient'];
    
    $result = $database->getReference($refTable)->getValue();
    $secondResult = $database->getReference($secondTable)->getValue();
    
    if ($result) {
      foreach ($result as $entry) {
        if (strcasecmp(trim($entry['name']), $targetFullName) === 0) {
          $targetID = $entry['userId'];
          // Match found, printing the entry data
          echo "\n\tFull Name: " . $entry['name'] . "\n";
          echo "\tEmail: " . $entry['email'] . "\n";
          echo "\tInsurance: " . $entry['Insurance'] . "\n";
          echo "\tDoctor: " . $entry['Doctor'] . "\n";
          if ($entry['medicalRecords'] === 'yes') {
            foreach ($secondResult as $entry) {
              if (strcasecmp(trim($entry['userId']), $targetID) === 0) {
                echo "\tAge: " . $entry['Age'] . "\n";
                echo "\tAllergy: " . $entry['Allergy'] . "\n";
                echo "\tAnxiety: " . $entry['Anxiety'] . "\n";
                /*
                needs to be filled with the rest, some entries appear to be duplicated in DB
                */
              }
            }
          }
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
    $refTable = "PatientUsers";
    $targetFullName = $_POST['selectedPatient'];
    
    $result = $database->getReference($refTable)->getValue();
    
    if ($result) {
      foreach ($result as $entry) {
        if (strcasecmp(trim($entry['name']), $targetFullName) === 0) {
          // Match found, printing the entry data
          echo "\n\tFull Name: " . $entry['name'] . "\n";
          echo "\tEmail: " . $entry['email'] . "\n";
          break; // Exit loop when match is found
        }
      }
    } else {
        echo "No results found for the specified table.";
    }
  }
}

// Function that will export the information of the patient into an txt file
// Basically similar to the first function, needs to be updated once the first is done
function displayPatientExport($database, $patientList) {
  if (isset($_POST['export'])) {
      $refTable = "PatientUsers";
      $targetFullName = $_POST['selectedPatient'];

      $result = $database->getReference($refTable)->getValue();

      if ($result) {
          foreach ($result as $entry) {
              if (strcasecmp(trim($entry['name']), $targetFullName) === 0) {
                  // Match found, prepare the data for export
                  $exportData =  "Full Name: " . $entry['name'] . "\n";
                  $exportData .= "Email: " . $entry['email'] . "\n";
                  
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
