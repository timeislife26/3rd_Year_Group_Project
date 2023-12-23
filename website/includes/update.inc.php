<?php
require_once '../php-firebase/dbcon.php';

if (isset($_POST["update"])) {
    $selectedUserID = $_POST["selectedUserID"];
    $Age = $_POST["Age"];
    $gender = $_POST["gender"];
    $trewstbps = $_POST["trewstbps"];
    $chol = $_POST["chol"];
    $fbs = $_POST["fbs"];
    $restecg = $_POST["restecg"];
    $thalach = $_POST["thalach"];
    $oldpeak = $_POST["oldpeak"];
    $slope = $_POST["slope"];
    $ca = $_POST["ca"];
    $exang = $_POST["exang"];
    $thal = $_POST["thal"];
    $Smoking_History = $_POST["Smoking_history"];
    $Smoking = $_POST["smokingStatus"];
    $Yellow_Fingers = $_POST["Yellow_Fingers"];
    $Anxiety = $_POST["Anxiety"];
    $Chronic_Disease = $_POST["Chronic_Disease"];
    $Fatigue = $_POST["Fatigue"];
    $Allergy = $_POST["Allergy"];
    $Wheezing = $_POST["Wheezing"];
    $Swallowing_Difficulty = $_POST["Swallowing_Difficulty"];
    $Chest_pain = $_POST["Chest_pain"];
    $cp = $_POST["cp"];
    $alcoholC = $_POST["Alcohol_Consuming"];
    $coughing = $_POST["Coughing"];
    $SOB = $_POST["Shortness_of_Breath"];
    $hypertension = $_POST["hypertension"];
    $heart_disease = $_POST["heart_disease"];
    $bmi = $_POST["bmi"];
    $HbA1c_level = $_POST["HbA1c_level"];
    $blood_glucose_level = $_POST["blood_glucose_level"];

    $postData = [
        'userID' => $selectedUserID,
        'Age' => $Age,
        'gender' => $gender,
        'trewstbps' => $trewstbps,
        'chol' => $chol,
        'fbs' => $fbs,
        'restecg' => $restecg,
        'thalach' => $thalach,
        'oldpeak' => $oldpeak,
        'slope' => $slope,
        'ca' => $ca,
        'exang' => $exang,
        'thal' => $thal,
        'Smoking_history' => $Smoking_History,
        'Smoking' => $Smoking,
        'Yellow_Fingers' => $Yellow_Fingers,
        'Anxiety' => $Anxiety,
        'Chronic_Disease' => $Chronic_Disease,
        'Fatigue' => $Fatigue,
        'Allergy' => $Allergy,
        'Wheezing' => $Wheezing,
        'Swallowing_Difficulty' => $Swallowing_Difficulty,
        'Chest_pain' => $Chest_pain,
        'cp' => $cp,
        'Alcohol_Consuming' => $alcoholC,
        'Coughing' => $coughing,
        'Shortness_of_Breath' => $SOB,
        'hypertension' => $hypertension,
        'heart_disease' => $heart_disease,
        'bmi' => $bmi,
        'HbA1c_level' => $HbA1c_level,
        'blood_glucose_level' => $blood_glucose_level,
    ];

    $refTable = "MedicalRecords";
    $medKey = existingPatientInfo($selectedUserID, $database);

    if ($medKey != false) {
        $refTable = "MedicalRecords/" . $medKey;
        $database->getReference($refTable)->update($postData);
    } else {
        $postRef = $database->getReference($refTable)->push($postData);
    }

    header("Location: ../views/2fa_verification.php?email=$selectedUserID");
    exit();
} else {
    header("Location: ../views/update.php?Error");
    exit();
}

function existingPatientInfo($id, $database)
{
    $refTable = "MedicalRecords";
    $result = $database->getReference($refTable)->getValue();
    
    foreach ($result as $key => $row) {
        if ($row["userID"] === $id) {
            return $key;
        }
    }
    
    return false;
}
?>
