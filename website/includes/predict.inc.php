<?php
    require_once '../php-firebase/dbcon.php';
    $userID = isset($_GET["userID"]) ? $_GET["userID"] : null;


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
            $Smoking_History = $result["Smoking_history"];
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
            $alcoholC = $result["Alcohol_Consuming"];
            $coughing = $result["Coughing"];
            $SOB = $result["Shortness_of_Breath"];
            $hypertension = $result["hypertension"];
            $heart_disease = $result["heart_disease"];
            $bmi = $result["bmi"];
            $HbA1c_level = $result["HbA1c_level"];
            $blood_glucose_level = $result["blood_glucose_level"];
           /* if ($Allergy === "True"){
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
            if ($alcoholC === "True"){
                $alcoholC = 1;
            }
            else{
                $alcoholC = 0;
            }
            if ($coughing === "True"){
                $coughing = 1;
            }
            else{
                $coughing = 0;
            }
            if ($SOB === "True"){
                $SOB = 1;
            }
            else{
                $SOB = 0;
            }
            if ($hypertension === "True"){
                $hypertension = 1;
            }
            else{
                $hypertension = 0;
            }
            if ($heart_disease === "True"){
                $heart_disease = 1;
            }
            else{
                $heart_disease = 0;
            }
            */

            $full_response = "";
            //Sending heart disease info to the cloud function:
            $heart_function_url = 'https://on-request-heart-ef42g3nnla-uc.a.run.app';


            $Heart_Disease_data = [
                "Age" => $Age,
                "ca" => $ca,
                "chol" => $chol,
                "cp" => $cp,
                "exang" => $exang,
                "fbs" => $fbs,
                "oldpeak" => $oldpeak,
                "restecg" => $restecg,
                "gender" => $gender,
                "slope" => $slope,
                "thal" => $thal,
                "thalach" => $thalach,
                "trewstbps" => $trewstbps,
                ];
            $heart_Disease = json_encode($Heart_Disease_data);

                // Create HTTP context with POST data
            $heart_options = [
                'http' => [
                    'header' => "Content-type: application/json\r\n",
                    'method' => 'POST',
                    'content' => $heart_Disease,
                ],
            ];
            $heart_context = stream_context_create($heart_options);
            $heart_response = file_get_contents($heart_function_url, false, $heart_context);
            

            if ($heart_response === FALSE) {
                die('Error occurred while fetching data from the Heart Disease Cloud Function');
            } else {
                $full_response = $full_response . $heart_response;
            }


            //Sending lung disease info to the cloud function:
            $lung_function_url = 'https://on-request-lung-ef42g3nnla-uc.a.run.app';

            $lung_disease_data = [
                "gender" => $gender,
                "Age" => $Age,
                "smoking" => $Smoking,
                "yf" => $Yellow_Fingers,
                "anxiety" => $Anxiety,
                "cd" => $Chronic_Disease,
                "fatigue" => $Fatigue,
                "allergy" => $Allergy,
                "wheezing" => $Wheezing,
                "Alcohol" => $alcoholC,
                "coughing" => $coughing,
                "sob" => $SOB,
                "chest_pain" => $Chest_pain,
                "sd" => $Swallowing_Difficulty,
            ];

            $lung_Disease = json_encode($lung_disease_data);

            // Create HTTP context with POST data
            $lung_options = [
                'http' => [
                    'header' => "Content-type: application/json\r\n",
                    'method' => 'POST',
                    'content' => $lung_Disease,
                ],
            ];
            $lung_context = stream_context_create($lung_options);
            $lung_response = file_get_contents($lung_function_url, false, $lung_context);

            if ($lung_response === FALSE) {
                die('Error occurred while fetching data from the Lung Disease Cloud Function');
            } else {
                $full_response = $full_response . "\n" . $lung_response;
            }

            //Sending diabestes info to the cloud function:
            $diabetes_function_url = 'https://on-request-diabetes-ef42g3nnla-uc.a.run.app';

            $diabetes_data = [
                "gender" => $gender,
                "Age" => $Age,
                "hypertension" => $hypertension,
                "heart_disease" => $heart_disease,
                "smoking_history" => $Smoking_History,
                "bmi" => $bmi,
                "hbA1c" => $HbA1c_level,
                "blood_glucose_level" => $blood_glucose_level,
            ];

            $diabetes_json = json_encode($diabetes_data);

            // Create HTTP context with POST data
            $diabetes_options = [
                'http' => [
                    'header' => "Content-type: application/json\r\n",
                    'method' => 'POST',
                    'content' => $diabetes_json,
                ],
            ];
            $diabetes_context = stream_context_create($diabetes_options);
            $diabetes_response = file_get_contents($diabetes_function_url, false, $diabetes_context);

            if ($diabetes_response === FALSE) {
                die('Error occurred while fetching data from the Diabetes Cloud Function');
            } else {
                $full_response = $full_response . "\n" . $diabetes_response;
            }
            



            echo $full_response;
        }
        else{
            echo "No data found";
        }


    function existingPatientInfo($id, $database){
        $refTable = "MedicalRecords";
        $result = $database->getReference($refTable)->getValue();
        foreach ($result as $key => $row){
            error_log("Row content: " . print_r($row, true)); // Log the content of $row
            if (strcasecmp($row["userID"], $id) === 0){
            //if ($row["userID"] === $id) {
                return $key; // Found the ID, return the key
            }
        }
        return false;
    }
    