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
          echo "\tInsurance: " . $entry['insuranceName'] . "\n";
          echo "\tInsurance Policy Number: " . $entry['insurancePolicyNum'] . "\n";
          echo "\tInsurance Telephone Number: " . $entry['insuranceTelNum'] . "\n";
          // echo "\tDoctor: " . $entry['Doctor'] . "\n";
          // if ($entry['medicalRecords'] === 'yes') {
            foreach ($secondResult as $entry) {
              if (isset($entry['userId']) && strcasecmp(trim($entry['userId']), $targetID) === 0) {
                // Age - int
                echo "\tAge: " . $entry['Age'] . "\n";
                // Allergy - boolean
                if (($entry['Allergy'] === true)) {
                  echo "\tAllergy: True\n";
                } else {
                  echo "\tAllergy: False\n";
                }
                // Anxiety - boolean
                if (($entry['Anxiety'] === true)) {
                  echo "\tAnxiety: True\n";
                } else {
                  echo "\tAnxiety: False\n";
                }
                // Chest_pain - boolean
                if (($entry['Chest_pain'] === true)) {
                  echo "\tChest Pain: True\n";
                } else {
                  echo "\tChest Pain: False\n";
                }
                // Chronic_Disease - boolean
                if (($entry['Chronic_Disease'] === true)) {
                  echo "\tChronic Disease: True\n";
                } else {
                  echo "\tChronic Disease: False\n";
                }
                // Fatigue - boolean
                if (($entry['Fatigue'] === true)) {
                  echo "\tFatigue: True\n";
                } else {
                  echo "\tFatigue: False\n";
                }
                // HbA1c_level - float
                echo "\tHbA1c Level: " . $entry['HbA1c_level'] . "\n";
                // Smoking - boolean
                if (($entry['Smoking'] === true)) {
                  echo "\tSmoking: True\n";
                } else {
                  echo "\tSmoking: False\n";
                }
                // Swallowing_Difficulty - boolean
                if (($entry['Swallowing_Difficulty'] === true)) {
                  echo "\tSwallowing Difficulty: True\n";
                } else {
                  echo "\tSwallowing Difficulty: False\n";
                }
                // Wheezing - boolean
                if (($entry['Wheezing'] === true)) {
                  echo "\tWheezing: True\n";
                } else {
                  echo "\tWheezing: False\n";
                }
                // Yellow_Fingers - boolean
                if (($entry['Yellow_Fingers'] === true)) {
                  echo "\tYellow Fingers: True\n";
                } else {
                  echo "\tYellow Fingers: False\n";
                }
                // blood_glucose_level - int
                echo "\tBlood Glucose Level: " . $entry['blood_glucose_level'] . "\n";
                // bmi - float
                echo "\tBody Mass Index: " . $entry['bmi'] . "\n";
                // ca - int (0 to 3)
                echo "\tNumber of Major Vessels Coloured by Flourosopy: " . $entry['ca'] . "\n";
                // chol - int
                echo "\tSerum Cholestoral: " . $entry['chol'] . "\n";
                // cp - int (0 to 3)
                //echo "\tChest Pain: " . $entry['cp'] . "\n";
                // exang - boolean
                if (($entry['exang'] === true)) {
                  echo "\tExercise Induced Angina: True\n";
                } else {
                  echo "\tExercise Induced Angina: False\n";
                }
                // fbs - int
                echo "\tFasting Blood Sugar: " . $entry['fbs'] . "\n";
                // gender - boolean
                if (($entry['gender'] === true)) {
                  echo "\tGender: Male\n";
                } else {
                  echo "\tGender: Female\n";
                }
                // heart_disease - boolean
                if (($entry['heart_disease'] === true)) {
                  echo "\tHeart Disease: True\n";
                } else {
                  echo "\tHeart Disease: False\n";
                }
                // hypertension - boolean
                if (($entry['hypertension'] === true)) {
                  echo "\tHypertension: True\n";
                } else {
                  echo "\tHypertension: False\n";
                }
                // oldpeak - int
                echo "\tST depression induced by exercise: " . $entry['oldpeak'] . "\n";
                // restecg - int (0 to 2)
                echo "\tResting Electocardiographic Results: " . $entry['restecg'] . "\n";
                // slope - int (0 to 2)
                echo "\tSlope of Peak Exercise ST segment: " . $entry['slope'] . "\n";
                // thal - int (0 to 3)
                echo "\tThal: " . $entry['thal'] . "\n";
                // thalach - int
                echo "\tMaximum Heart Rate Achieved: " . $entry['thalach'] . "\n";
                // trewstbps - int
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
                  $exportData = "\n\tFull Name: " . $entry['name'] . "\n";
                  $exportData .= "\tEmail: " . $entry['email'] . "\n";
                  $exportData .= "\tInsurance: " . $entry['insuranceName'] . "\n";
                  $exportData .= "\tInsurance Policy Number: " . $entry['insurancePolicyNum'] . "\n";
                  $exportData .= "\tInsurance Telephone Number: " . $entry['insuranceTelNum'] . "\n";
                  // echo "\tDoctor: " . $entry['Doctor'] . "\n";
                  // if ($entry['medicalRecords'] === 'yes') {
                    foreach ($secondResult as $entry) {
                      if (strcasecmp(trim($entry['userId']), $targetID) === 0) {
                        // Age - int
                        $exportData .= "\tAge: " . $entry['Age'] . "\n";
                        // Allergy - boolean
                        if (($entry['Allergy'] === true)) {
                          $exportData .= "\tAllergy: True\n";
                        } else {
                          $exportData .= "\tAllergy: False\n";
                        }
                        // Anxiety - boolean
                        if (($entry['Anxiety'] === true)) {
                          $exportData .= "\tAnxiety: True\n";
                        } else {
                          $exportData .= "\tAnxiety: False\n";
                        }
                        // Chest_pain - boolean
                        if (($entry['Chest_pain'] === true)) {
                          $exportData .= "\tChest Pain: True\n";
                        } else {
                          $exportData .= "\tChest Pain: False\n";
                        }
                        // Chronic_Disease - boolean
                        if (($entry['Chronic_Disease'] === true)) {
                          $exportData .= "\tChronic Disease: True\n";
                        } else {
                          $exportData .= "\tChronic Disease: False\n";
                        }
                        // Fatigue - boolean
                        if (($entry['Fatigue'] === true)) {
                          $exportData .= "\tFatigue: True\n";
                        } else {
                          $exportData .= "\tFatigue: False\n";
                        }
                        // HbA1c_level - float
                        $exportData .= "\tHbA1c Level: " . $entry['HbA1c_level'] . "\n";
                        // Smoking - boolean
                        if (($entry['Smoking'] === true)) {
                          $exportData .= "\tSmoking: True\n";
                        } else {
                          $exportData .= "\tSmoking: False\n";
                        }
                        // Swallowing_Difficulty - boolean
                        if (($entry['Swallowing_Difficulty'] === true)) {
                          $exportData .= "\tSwallowing Difficulty: True\n";
                        } else {
                          $exportData .= "\tSwallowing Difficulty: False\n";
                        }
                        // Wheezing - boolean
                        if (($entry['Wheezing'] === true)) {
                          $exportData .= "\tWheezing: True\n";
                        } else {
                          $exportData .= "\tWheezing: False\n";
                        }
                        // Yellow_Fingers - boolean
                        if (($entry['Yellow_Fingers'] === true)) {
                          $exportData .= "\tYellow Fingers: True\n";
                        } else {
                          $exportData .= "\tYellow Fingers: False\n";
                        }
                        // blood_glucose_level - int
                        $exportData .= "\tBlood Glucose Level: " . $entry['blood_glucose_level'] . "\n";
                        // bmi - float
                        $exportData .= "\tBody Mass Index: " . $entry['bmi'] . "\n";
                        // ca - int (0 to 3)
                        $exportData .= "\tNumber of Major Vessels Coloured by Flourosopy: " . $entry['ca'] . "\n";
                        // chol - int
                        $exportData .= "\tSerum Cholestoral: " . $entry['chol'] . "\n";
                        // cp - int (0 to 3)
                        //$exportData .= "\tChest Pain: " . $entry['cp'] . "\n";
                        // exang - boolean
                        if (($entry['exang'] === true)) {
                          $exportData .= "\tExercise Induced Angina: True\n";
                        } else {
                          $exportData .= "\tExercise Induced Angina: False\n";
                        }
                        // fbs - int
                        $exportData .= "\tFasting Blood Sugar: " . $entry['fbs'] . "\n";
                        // gender - boolean
                        if (($entry['gender'] === true)) {
                          $exportData .= "\tGender: Male\n";
                        } else {
                          $exportData .= "\tGender: Female\n";
                        }
                        // heart_disease - boolean
                        if (($entry['heart_disease'] === true)) {
                          $exportData .= "\tHeart Disease: True\n";
                        } else {
                          $exportData .= "\tHeart Disease: False\n";
                        }
                        // hypertension - boolean
                        if (($entry['hypertension'] === true)) {
                          $exportData .= "\tHypertension: True\n";
                        } else {
                          $exportData .= "\tHypertension: False\n";
                        }
                        // oldpeak - int
                        $exportData .= "\tST depression induced by exercise: " . $entry['oldpeak'] . "\n";
                        // restecg - int (0 to 2)
                        $exportData .= "\tResting Electocardiographic Results: " . $entry['restecg'] . "\n";
                        // slope - int (0 to 2)
                        $exportData .= "\tSlope of Peak Exercise ST segment: " . $entry['slope'] . "\n";
                        // thal - int (0 to 3)
                        $exportData .= "\tThal: " . $entry['thal'] . "\n";
                        // thalach - int
                        $exportData .= "\tMaximum Heart Rate Achieved: " . $entry['thalach'] . "\n";
                        // trewstbps - int
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
