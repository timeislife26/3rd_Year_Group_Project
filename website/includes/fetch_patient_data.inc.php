<?php
    error_reporting(E_ALL);
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

    // Function to populate the form
    function fetchPatientData($database, $patientList) {
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
                        
                        $formData = array(
                            'name' => $entry['name'],
                            'email' => $entry['email'],
                        );

                        foreach ($secondResult as $entry) {
                            if (isset($entry['userId']) && strcasecmp(trim($entry['userId']), $targetID) === 0) {
                                $formData['Age'] = $entry['Age'];
                                $formData['gender'] = $entry['gender'];
                                /*
                                $formData['trewstbps'] = $entry['trewstbps'];
                                $formData['chol'] = $entry['chol'];
                                $formData['fbs'] = $entry['fbs'];
                                $formData['restecg'] = $entry['restecg'];
                                $formData['thalach'] = $entry['thalach'];
                                $formData['oldpeak'] = $entry['oldpeak'];
                                $formData['slope'] = $entry['slope'];
                                $formData['ca'] = $entry['ca'];
                                $formData['bmi'] = $entry['bmi'];
                                $formData['HbA1c_level'] = $entry['HbA1c_level'];
                                $formData['blood_glucose_level'] = $entry['blood_glucose_level'];
                                $formData['exang'] = $entry['exang'];
                                $formData['thal'] = $entry['thal'];
                                $formData['Smoking'] = $entry['Smoking'];
                                $formData['Yellow_Fingers'] = $entry['Yellow_Fingers'];
                                //--$formData['Alcohol_Consuming'] = $entry['Alcohol_Consuming'];
                                //--$formData['Coughing'] = $entry['Coughing'];
                                //--$formData['Shortness_of_Breath'] = $entry['Shortness_of_Breath'];
                                $formData['Anxiety'] = $entry['Anxiety'];
                                $formData['Chronic_Disease'] = $entry['Chronic_Disease'];
                                $formData['Fatigue'] = $entry['Fatigue'];
                                $formData['Allergy'] = $entry['Allergy'];
                                $formData['Wheezing'] = $entry['Wheezing'];
                                $formData['Swallowing_Difficulty'] = $entry['Swallowing_Difficulty'];
                                $formData['Chest_pain'] = $entry['Chest_pain'];
                                //$formData['cp'] = $entry['cp'];
                                $formData['hypertension'] = $entry['hypertension'];
                                $formData['heart_disease'] = $entry['heart_disease'];
                                */
                            }
                        }
                        $dataToSend = json_encode($formData);
                        echo '<script>';
                        echo 'var dataToSend = ' . $dataToSend . ';';
                        echo 'fillForm(dataToSend)</script>';
                    }
                }
            }
        }
    }
?>
