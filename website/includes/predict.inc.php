<?php
    require_once '../php-firebase/dbcon.php';
    if(isset($_POST["generate"])){
        $userID = $_POST["selectedUserID"];

        $medKey = existingPatientInfo($userID, $database);
        if ($medKey != false){
            $refTable = "MedicalRecords";
            $result = $database->getReference($refTable)->getChild($medKey)->getValue();
            $Age = $result["Age"];
            $Allergy = $result["Allergy"];
            $Anxiety = $result["Anxiety"];
            $Chest_pain = $result["Chest_pain"];
            $Chronic_Disease = $result["Chronic_Disease"];
            $Fatigue = $result["Fatigue"];
            $Smoking = $result["Smoking"];
            $Swallowing_Difficulty = $result["Swallowing_Difficulty"];
            $Wheezing = $result["Wheezing"];
            $Yellow_Fingers = $result["Yellow_Fingers"];
            $ca = $result["ca"];
            $chol = $result["chol"];
            $cp = $result["cp"];
            $exang = $result["exang"];
            $fbs = $result["fbs"];
            $gender = $result["gender"];
            $oldpeak = $result["oldpeak"];
            $restecg = $result["restecg"];
            $slope = $result["slope"];
            $thal = $result["thal"];
            $thalach = $result["thalach"];
            $trewstbps = $result["trewstbps"];
            if ($Allergy === "True"){
                $Allergy = 1;
            }
            else{
                $Allergy = 0;
            }
            if ($Anxiety === "True"){
                $Anxiety = 1;
            }
            else{
                $Anxiety = 0;
            }
            if ($Chest_pain === "True"){
                $Chest_pain = 1;
            }
            else{
                $Chest_pain = 0;
            }
            if ($Chronic_Disease === "True"){
                $Chronic_Disease = 1;
            }
            else{
                $Chronic_Disease = 0;
            }
            if ($Fatigue === "True"){
                $Fatigue = 1;
            }
            else{
                $Fatigue = 0;
            }
            if ($Smoking === "True"){
                $Smoking = 1;
            }
            else {
                $Smoking = 0;
            }
            if ($Swallowing_Difficulty === "True"){
                $Swallowing_Difficulty = 1;
            }
            else{
                $Swallowing_Difficulty = 0;
            }
            if ($Wheezing === "True"){
                $Wheezing = 1;
            }
            else {
                $Wheezing = 0;
            }
            if ($Yellow_Fingers === "True"){
                $Yellow_Fingers = 1;
            }
            else {
                $Yellow_Fingers = 0;
            }
            if ($exang === "True"){
                $exang = 1;
            }
            else{
                $exang = 0;
            }
            if ($gender === "True"){
                $gender = 1;
            }
            else{
                $gender = 0;
            }
            $Heart_Disease_data = [[$Age, $ca, $chol, $cp, $exang, $fbs, $oldpeak, $restecg, $gender, $slope, $thal, $thalach, $trewstbps]];
        }
        $heart_Disease = json_encode($Heart_Disease_data);
        $command = "python heart_main.py \"$heart_Disease\" 2>&1";
        $output = shell_exec($command);
        echo "Output: $output\n";

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
    