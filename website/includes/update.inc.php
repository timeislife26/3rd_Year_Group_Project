<?php
    require_once '../php-firebase/dbcon.php';
if(isset($_POST["update"])){
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
    $Smoking = $_POST["Smoking"];
    $Yellow_Fingers = $_POST["Yellow_Fingers"];
    $Anxiety = $_POST["Anxiety"];
    $Chronic_Disease = $_POST["Chronic_Disease"];
    $Fatigue = $_POST["Fatigue"];
    $Allergy = $_POST["Allergy"];
    $Wheezing = $_POST["Wheezing"];
    $Swallowing_Difficulty = $_POST["Swallowing_Difficulty"];
    $Chest_pain = $_POST["Chest_pain"];
    $cp = $_POST["cp"];
    
    $postData = [
        'userID'=>$selectedUserID,
        'Age'=>$Age,
        'gender'=>$gender,
        'trewstbps'=>$trewstbps,
        'chol'=>$chol,
        'fbs'=>$fbs,
        'restecg'=>$restecg,
        'thalach'=>$thalach,
        'oldpeak'=>$oldpeak,
        'slope'=>$slope,
        'ca'=>$ca,
        'exang'=>$exang,
        'thal'=>$thal,
        'Smoking'=>$Smoking,
        'Yellow_Fingers'=>$Yellow_Fingers,
        'Anxiety'=> $Anxiety,
        'Chronic_Disease'=>$Chronic_Disease,
        'Fatigue'=>$Fatigue,
        'Allergy'=>$Allergy,
        'Wheezing'=>$Wheezing,
        'Swallowing_Difficulty'=>$Swallowing_Difficulty,
        'Chest_pain'=>$Chest_pain,
        'cp'=>$cp,
    ];


    $refTable = "MedicalRecords";
    $medKey = existingPatientInfo($selectedUserID, $database);
    if ($medKey != false){
        $refTable = "MedicalRecords/".$medKey;
        $database->getReference($refTable)->update($postData);
    }
    else{
        $postRef = $database->getReference($refTable)->push($postData);
    }
    header("location: ../views/update.php?Done");
    exit();
}
else{
    header("location: ../views/update.php?Error");
    exit();
}

function existingPatientInfo($id, $database){
    $refTable = "MedicalRecords";
    $result = $database->getReference($refTable)->getValue();
    foreach ($result as $key => $row){
        if ($row["userID"] === $id) {
            return $key; // Found the ID, return the key
        }
    }
    return false;
}
