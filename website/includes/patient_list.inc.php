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

/*
Age - int
gender - bool
cp (Chest Pain) - int (0 to 3)
trewstbps (resting blood pressure) - int 
chol(Serum Cholestoral in mg/dl) - int
fbs(fasting blood sugar > 120 mg/dl) - int
restecg(resting electocardiographic results - int (0 to 2)
thalach(maximum heart rate achieved) - int
exang(exercise induced angina) - bool
oldpeak(oldpeak = ST depression induced by exercise relative to rest) - float
slope (the slope of the peak exercise ST segment) - int (0 to 2)
ca(number of major vessels (0-3) colored by flourosopy) - int (0 to 3)
thal(thal: 0 = normal; 1 = fixed defect; 2 = reversable defect) - int (0 to 3)
Smoking - bool
Yellow_Fingers - bool
Anxiety - bool
Chronic_Disease - bool
Fatigue - bool
Allergy - bool
Wheezing - bool
Swallowing_Difficulty - bool
Chest_pain - bool
*/
?>
