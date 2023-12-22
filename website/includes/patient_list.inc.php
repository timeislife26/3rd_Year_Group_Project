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
          // echo "\tInsurance: " . $entry['Insurance'] . "\n";
          // echo "\tDoctor: " . $entry['Doctor'] . "\n";
          // if ($entry['medicalRecords'] === 'yes') {
            foreach ($secondResult as $entry) {
              if (strcasecmp(trim($entry['userId']), $targetID) === 0) {
                echo "\tAge: " . $entry['Age'] . "\n";
                echo "\tAllergy: " . $entry['Allergy'] . "\n";
                echo "\tAnxiety: " . $entry['Anxiety'] . "\n";
                echo "\tChest Pain: " . $entry['Chest_pain'] . "\n";
                echo "\tChronic Disease: " . $entry['Chronic_Disease'] . "\n";
                echo "\tFatigue: " . $entry['Fatigue'] . "\n";
                echo "\tHbA1c Level: " . $entry['HbA1c_level'] . "\n";
                echo "\tSmoking: " . $entry['Smoking'] . "\n";
                echo "\tSwallowing Difficulty: " . $entry['Swallowing_Difficulty'] . "\n";
                echo "\tWheezing: " . $entry['Wheezing'] . "\n";
                echo "\tYellow Fingers: " . $entry['Yellow_Fingers'] . "\n";
                echo "\tBlood Glucose Level: " . $entry['blood_glucose_level'] . "\n";
                echo "\tBody Mass Index: " . $entry['bmi'] . "\n";
                echo "\tNumber of Major Vessels Coloured by Flourosopy: " . $entry['ca'] . "\n";
                echo "\tSerum Cholestoral: " . $entry['chol'] . "\n";
                //echo "\tChest Pain: " . $entry['cp'] . "\n";
                echo "\tExercise Induced Angina: " . $entry['exang'] . "\n";
                echo "\tFasting Blood Sugar: " . $entry['fbs'] . "\n";
                echo "\tGender: " . $entry['gender'] . "\n";
                echo "\tHeart Disease: " . $entry['heart_disease'] . "\n";
                echo "\tHypertension: " . $entry['hypertension'] . "\n";
                echo "\tOldpeak: " . $entry['oldpeak'] . "\n";
                echo "\tResting Electocardiographic Results: " . $entry['restecg'] . "\n";
                echo "\tSlope of Peak Exercise ST segment: " . $entry['slope'] . "\n";
                echo "\tThal: " . $entry['thal'] . "\n";
                echo "\tMaximum Heart Rate Achieved: " . $entry['thalach'] . "\n";
                echo "\tResting Blood Pressure: " . $entry['trewstbps'] . "\n";
              }
            }
          //}
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
      $secondTable = "MedicalRecords";
      $targetFullName = $_POST['selectedPatient'];

      $result = $database->getReference($refTable)->getValue();
      $secondResult = $database->getReference($secondTable)->getValue();

      if ($result) {
          foreach ($result as $entry) {
              if (strcasecmp(trim($entry['name']), $targetFullName) === 0) {
                  $targetID = $entry['userId'];
                  // Match found, prepare the data for export
                  $exportData =  "Full Name: " . $entry['name'] . "\n";
                  $exportData .= "Email: " . $entry['email'] . "\n";
                  // $exportData .= "\tInsurance: " . $entry['Insurance'] . "\n";
                  // $exportData .= "\tDoctor: " . $entry['Doctor'] . "\n";
                  // if ($entry['medicalRecords'] === 'yes') {
                    foreach ($secondResult as $entry) {
                      if (strcasecmp(trim($entry['userId']), $targetID) === 0) {
                        $exportData .= "\tAge: " . $entry['Age'] . "\n";
                        $exportData .= "\tAllergy: " . $entry['Allergy'] . "\n";
                        $exportData .= "\tAnxiety: " . $entry['Anxiety'] . "\n";
                        $exportData .= "\tChest Pain: " . $entry['Chest_pain'] . "\n";
                        $exportData .= "\tChronic Disease: " . $entry['Chronic_Disease'] . "\n";
                        $exportData .= "\tFatigue: " . $entry['Fatigue'] . "\n";
                        $exportData .= "\tHbA1c Level: " . $entry['HbA1c_level'] . "\n";
                        $exportData .= "\tSmoking: " . $entry['Smoking'] . "\n";
                        $exportData .= "\tSwallowing Difficulty: " . $entry['Swallowing_Difficulty'] . "\n";
                        $exportData .= "\tWheezing: " . $entry['Wheezing'] . "\n";
                        $exportData .= "\tYellow Fingers: " . $entry['Yellow_Fingers'] . "\n";
                        $exportData .= "\tBlood Glucose Level: " . $entry['blood_glucose_level'] . "\n";
                        $exportData .= "\tBody Mass Index: " . $entry['bmi'] . "\n";
                        $exportData .= "\tNumber of Major Vessels Coloured by Flourosopy: " . $entry['ca'] . "\n";
                        $exportData .= "\tSerum Cholestoral: " . $entry['chol'] . "\n";
                        // $exportData .= "\tChest Pain: " . $entry['cp'] . "\n";
                        $exportData .= "\tExercise Induced Angina: " . $entry['exang'] . "\n";
                        $exportData .= "\tFasting Blood Sugar: " . $entry['fbs'] . "\n";
                        $exportData .= "\tGender: " . $entry['gender'] . "\n";
                        $exportData .= "\tHeart Disease: " . $entry['heart_disease'] . "\n";
                        $exportData .= "\tHypertension: " . $entry['hypertension'] . "\n";
                        $exportData .= "\tST Depression Induced by Exercise Relative to Rest: " . $entry['oldpeak'] . "\n";
                        $exportData .= "\tResting Electocardiographic Results: " . $entry['restecg'] . "\n";
                        $exportData .= "\tSlope of Peak Exercise ST segment: " . $entry['slope'] . "\n";
                        $exportData .= "\tThal: " . $entry['thal'] . "\n";
                        $exportData .= "\tMaximum Heart Rate Achieved: " . $entry['thalach'] . "\n";
                        $exportData .= "\tResting Blood Pressure: " . $entry['trewstbps'] . "\n";
                      }
                    }
                  //}
                  
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
