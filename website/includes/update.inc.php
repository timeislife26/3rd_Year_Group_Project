<?php
require_once '../php-firebase/dbcon.php';

echo $_POST["userID"];

if (isset($_POST["update"])) {
    $selectedUserID = $_POST["selectedUserID"];
    $Age = (int)$_POST["Age"];
    $gender = ($_POST["gender"] === "True");
    $trewstbps = (int)$_POST["trewstbps"];
    $chol = (int)$_POST["chol"];
    $fbs = ($_POST["fbs"] === "True");
    $restecg = (int)$_POST["restecg"];
    $thalach = (int)$_POST["thalach"];
    $oldpeak = (string)$_POST["oldpeak"];
    $slope = (int)$_POST["slope"];
    $ca = (int)$_POST["ca"];
    $exang = ($_POST["exang"] === "True");
    $thal = (int)$_POST["thal"];
    $Smoking_History = (int)$_POST["Smoking_history"];
    $Smoking = ($_POST["smokingStatus"] === "True");
    $Yellow_Fingers = ($_POST["Yellow_Fingers"] === "True");
    $Anxiety = ($_POST["Anxiety"] === "True");
    $Chronic_Disease = ($_POST["Chronic_Disease"] === "True");
    $Fatigue = ($_POST["Fatigue"] === "True");
    $Allergy = ($_POST["Allergy"] === "True");
    $Wheezing = ($_POST["Wheezing"] === "True");
    $Swallowing_Difficulty = ($_POST["Swallowing_Difficulty"] === "True");
    $Chest_pain = ($_POST["Chest_pain"] === "True");
    $cp = (int)$_POST["cp"];
    if (!$Chest_pain){
        $cp = 0;
    }
    $alcoholC = ($_POST["Alcohol_Consuming"] === "True");
    $coughing = ($_POST["Coughing"] === "True");
    $SOB = ($_POST["Shortness_of_Breath"] === "True");
    $hypertension = ($_POST["hypertension"] === "True");
    $heart_disease = ($_POST["heart_disease"] === "True");
    $bmi = $_POST["bmi"];
    $HbA1c_level = $_POST["HbA1c_level"];
    $blood_glucose_level = (int)$_POST["blood_glucose_level"];

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
    echo $selectedUserID;
    header("location: ../views/update.php?updated");
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
