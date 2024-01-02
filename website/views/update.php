<?php
include_once 'header.php';

require_once '../php-firebase/dbcon.php';

include '../includes/fetch_patient_data.inc.php';

// Query to fetch the list of names from DB
$refTable = "PatientUsers";

$result = $database->getReference($refTable)->getValue();

// Array to store names
$patientList = array();
$patientID = array();

// Getting names and storing them in array
/*foreach ($result as $key => $row){
    $patientList[] = $row["name"];
    $patientID[] = $row["userId"];
}*/

if (isset($_POST["update"])) {
    $name = $_POST["name"];
    $email = $_POST["email"];
    $age = $_POST["Age"];
    $gender = $_POST["gender"];
    $phone = $_POST["phone"];
    $address = $_POST["address"];
    $interests = $_POST["interests"];

    $userData = [
        'name' => $name,
        'email' => $email,
        'age' => $age,
        'gender' => $gender,
        'phone' => $phone,
        'address' => $address,
        'interests' => $interests,
    ];

    updateUserProfile($database, $email, $userData);

    $verificationCode = mt_rand(100000, 999999);
    saveVerificationCode($database, $email, $verificationCode);
    sendVerificationCodeByEmail($email, $verificationCode);

    header("Location: ../views/2fa_verification.php?email=$email");
    exit();
}
?>

<script src="../includes/update_scripts.js"></script>

<main>
      <div id="container">
        <h2>Profile Update</h2>
        <p>
          Select one of your patients from the drop down menu and click on "Fill Form" to enter the data 
          of the selected person into the fields. If any changes are made you can then click on "Update" 
          to update the information about the patient.<br>
          <b>All fields in the form are mandatory</b>
        </p>
        <form method="post" id="">
          <div class="dropdown_list">
          <label for="patient_list">Patient Name*:&emsp;
            <select id="patient_list" name="selectedPatient" onchange="updateSelectedUserID()">
            <<?php
              foreach ($result as $entry) {
                if (strcasecmp(trim($entry['linkedDoctorIMC']), $_SESSION['imc']) === 0) {
                    $patientList[] = $entry['name'];
                    $patientID[] = $entry['userID'];
                  echo '<option value="' . $entry['name'] . '">' , $entry['name'] , '</option>';
                }
              }
            ?>
            </select>
            <button type="button" name="generate" onclick="fillForm()">Fill Form</button>
            <?php
            /*
            if (isset($_POST['generate'])) {
                fetchPatientData($database, $patientList);
            }
            */
            ?>
          </label>
          </div>
        </form>
        <form action="../includes/update.inc.php" method="POST">
            <p>
                <label>Name: <input type="text" name="name" id="name" required autocomplete="on"></label>
                <label>Email: <input type="email" name="email" id="email" autocomplete="on"></label>
                <label>Age: <input type="number" name="Age" id="Age" min="0" max="125" required autocomplete="on"></label>
                <fieldset>
                    <legend>Gender at Birth:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="gender" id="genderM" value="True">Male</label>
					            <label><input type="radio" name="gender" id="genderF" value="False">Female</label>
                    </div>
                </fieldset>
                <label>Resting Blood Pressure: <input type="number" name="trewstbps" id="trewstbps" required autocomplete="on"></label>
                <label>Serum Cholestoral: <input type="number" name="chol" id="chol" required autocomplete="on"></label>
                <label>Resting Electocardiographic Results: <input type="number" name="restecg" id="restecg" min="0" max="2" required autocomplete="on"></label>
                <label>Maximum Heart Rate Achieved: <input type="number" name="thalach" id="thalach" required autocomplete="on"></label>
                <label>ST Depression Induced by Exercise Relative to Rest: <input type="number" name="oldpeak" id="oldpeak" required autocomplete="on"></label>
                <label>Slope of the Peak Exercise ST Segment: <input type="number" name="slope" id="slope" min="0" max="2" required autocomplete="on"></label>
                <label>Number of Major Vessels Coloured by Flourosopy: <input type="number" name="ca" id="ca" min="0" max="3" required autocomplete="on"></label>
                <label>Body Mass Index: <input type="number" step="0.01" name="bmi" id="bmi" required autocomplete="on"></label>
                <label>Hemoglobin A1c: <input type="number" step="any" name="HbA1c_level" id="HbA1c_level" required autocomplete="on"></label>
                <label>Blood Glucose Level: <input type="number" name="blood_glucose_level" id="blood_glucose_level" required autocomplete="on"></label>
                <fieldset>
                    <legend>Exercise Induced Angina:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="exang" id="exangT" value="True">Yes</label>
					            <label><input type="radio" name="exang" id="exangF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Is Fasting Blood Sugar above 120:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="fbs" id="fbs_true" value="True">Yes</label>
					    <label><input type="radio" name="fbs" id="fbs_false" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Thal:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="thal" id="thal0" value="0">Normal</label>
					    <label><input type="radio" name="thal" id="thal1" value="1">Fixed Defect</label>
                      <label><input type="radio" name="thal" id="thal2" value="2">Reversable Defect</label>
                      <label><input type="radio" name="thal" id="thal3" value="3">3</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Smoking:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Smoking_history" id="Smoking_historyF" value="0" onclick="toggleSmokingStatus(false)">No info</label>
					            <label><input type="radio" name="Smoking_history"id="Smoking_never" value="1" onclick="toggleSmokingStatus(false)">Never</label>
                                <label><input type="radio" name="Smoking_history" id="Smoking_formerly" value="2" onclick="toggleSmokingStatus(false)">Formerly</label>
					            <label><input type="radio" name="Smoking_history" id="Smoking_historyT" value="3" onclick="toggleSmokingStatus(true)">Currently</label>

                    </div>
                </fieldset>
                <fieldset>
                    <legend>Yellow Fingers:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Yellow_Fingers" id="Yellow_FingersT" value="True">Yes</label>
					            <label><input type="radio" name="Yellow_Fingers" id="Yellow_FingersF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Alcohol Consuming:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="Alcohol_Consuming" id="alcoholT" value="True">Yes</label>
					    <label><input type="radio" name="Alcohol_Consuming" id="alcoholF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Coughing:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="Coughing" id="coughingT" value="True">Yes</label>
					    <label><input type="radio" name="Coughing" id="coughingF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Shortness of Breath:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="Shortness_of_Breath" id="sobT" value="True">Yes</label>
					    <label><input type="radio" name="Shortness_of_Breath" id="sobF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Anxiety:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Anxiety" id="AnxietyT" value="True">Yes</label>
					            <label><input type="radio" name="Anxiety" id="AnxietyF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Chronic Disease:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Chronic_Disease" id="Chronic_DiseaseT" value="True">Yes</label>
					            <label><input type="radio" name="Chronic_Disease" id="Chronic_DiseaseF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Fatigue:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Fatigue" id="FatigueT" value="True">Yes</label>
					            <label><input type="radio" name="Fatigue" id="FatigueF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Allergies:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Allergy" id="AllergyT" value="True">Yes</label>
					            <label><input type="radio" name="Allergy" id="AllergyF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Wheezing:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Wheezing" id="WheezingT" value="True">Yes</label>
					            <label><input type="radio" name="Wheezing" id="WheezingF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Difficulty Swallowing:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Swallowing_Difficulty" id="Swallowing_DifficultyT" value="True">Yes</label>
					            <label><input type="radio" name="Swallowing_Difficulty" id="Swallowing_DifficultyF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Chest Pain:</legend>
                    <div class="radio_btn">
					<label><input type="radio" name="Chest_pain" id="Chest_painT" value="True" onclick="toggleChestPainType()">Yes</label>
					<label><input type="radio" name="Chest_pain" id="Chest_painF" value="False" onclick="toggleChestPainType()">No</label>
                    </div>
                </fieldset>
                <fieldset id="cpField" style="display: none;">
                    <legend>Chest Pain Type:</legend>
                    <label><input type="radio" name="cp" id="cp0" value="0" checked>0</label>
                    <label><input type="radio" name="cp" id="cp1" value="1">1</label>
                    <label><input type="radio" name="cp" id="cp2" value="2">2</label>
                    <label><input type="radio" name="cp" id="cp3" value="3">3</label>
                </fieldset>
                <fieldset>
                    <legend>Hypertension:</legend>
                    <div class="radio_btn">
					<label><input type="radio" name="hypertension" id="hypertensionT" value="True">Yes</label>
					<label><input type="radio" name="hypertension" id="hypertensionF" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Heart Disease:</legend>
                    <div class="radio_btn">
					<label><input type="radio" name="heart_disease" id="heart_diseaseT" value="True">Yes</label>
					<label><input type="radio" name="heart_disease" id="heart_diseaseF" value="False">No</label>
                    </div>
                </fieldset>
                <input type="hidden" name="smokingStatus" id="smokingStatus" value="False">
            </p>
            <p>
            <input type="hidden" name="selectedUserID" id="selectedUserID">
                <button type="submit" name="update">Update</button>
            </p><p></p>
        </form>
      </div>
    </main>
  </body>
  <script>
    var patientID;

    window.onload = function() {
        var selectedIndex = document.getElementById("patient_list").selectedIndex;
        patientID = <?php echo json_encode($patientID); ?>; 
        console.log("Initial Patient ID: ", patientID);
        document.getElementById("selectedUserID").value = patientID[selectedIndex];
    };
    function updateSelectedUserID() {
        var selectedIndex = document.getElementById("patient_list").selectedIndex;
        patientID = <?php echo json_encode($patientID); ?>; 
        console.log("Selected Patient ID: ", patientID[selectedIndex]);
        document.getElementById("selectedUserID").value = patientID[selectedIndex];
}

    function toggleChestPainType() {
        var chestPainRadio = document.querySelector('input[name="Chest_pain"]:checked');
        var chestPainTypeField = document.getElementById('cpField');

        if (chestPainRadio && chestPainRadio.value === 'True') {
            chestPainTypeField.style.display = 'block';
        } else {
            chestPainTypeField.style.display = 'none';
            // If chest pain is false, set chest pain type to 0
            document.getElementById('cp').value = '0';
        }
    }
    function toggleSmokingStatus(isCurrently) {
        var smokingStatusField = document.getElementById('smokingStatus');
        smokingStatusField.value = isCurrently ? 'True' : 'False';
    }
    function fillForm() {
    var selectedUserID = document.getElementById("selectedUserID").value;
    var x = new XMLHttpRequest();
    var url = "../includes/fillForm.inc.php?fillID=" + selectedUserID;

    console.log("Sent Message: ", selectedUserID);

    x.open("GET", url);
    x.onreadystatechange = function () {
        if (x.readyState == 4 && x.status == 200) {
            var responseData = JSON.parse(x.responseText);

            // Check if userData is defined before accessing Age
            if (responseData.userData) {
                document.getElementById("Age").value = responseData.userData.Age;
                document.getElementById("trewstbps").value = responseData.userData.trewstbps;
                document.getElementById("chol").value = responseData.userData.chol;
                document.getElementById("restecg").value = responseData.userData.restecg;
                document.getElementById("thalach").value = responseData.userData.thalach;
                document.getElementById("oldpeak").value = responseData.userData.oldpeak;
                document.getElementById("slope").value = responseData.userData.slope;
                document.getElementById("ca").value = responseData.userData.ca;
                document.getElementById("bmi").value = responseData.userData.bmi;
                document.getElementById("HbA1c_level").value = responseData.userData.HbA1c_level;
                document.getElementById("blood_glucose_level").value = responseData.userData.blood_glucose_level;
                if(responseData.userData.fbs === true){
                    document.getElementById("fbs_true").checked = true;
                }
                else{
                    document.getElementById("fbs_false").checked = true;
                }
                if(responseData.userData.gender === true){
                    document.getElementById("genderM").checked = "True";
                } 
                else{
                    document.getElementById("genderF").checked = "True";
                }
                if(responseData.userData.exang === true){
                    document.getElementById("exangT").checked = "True";
                } 
                else{
                    document.getElementById("exangF").checked = "True";
                }
                if(responseData.userData.thal === 0){
                    document.getElementById("thal0").checked = "True";
                } 
                else if (responseData.userData.thal === 1){
                    document.getElementById("thal1").checked = "True";
                }
                else if (responseData.userData.thal === 2){
                    document.getElementById("thal2").checked = "True";
                }
                else{
                    document.getElementById("thal3").checked = "True";
                }
                if(responseData.userData.Smoking_history === 0){
                    document.getElementById("Smoking_historyF").checked = "True";
                } 
                else if (responseData.userData.Smoking_history === 1){
                    document.getElementById("Smoking_never").checked = "True";
                }
                else if (responseData.userData.Smoking_history === 2){
                    document.getElementById("Smoking_formerly").checked = "True";
                }
                else {
                    document.getElementById("Smoking_historyT").checked = "True";
                }
                if(responseData.userData.Yellow_Fingers === true){
                    document.getElementById("Yellow_FingersT").checked = "True";
                } 
                else{
                    document.getElementById("Yellow_FingersF").checked = "True";
                }
                if(responseData.userData.Alcohol_Consuming === true){
                    document.getElementById("alcoholT").checked = "True";
                } 
                else{
                    document.getElementById("alcoholF").checked = "True";
                }
                if(responseData.userData.Coughing === true){
                    document.getElementById("coughingT").checked = "True";
                } 
                else{
                    document.getElementById("coughingF").checked = "True";
                }
                if(responseData.userData.Shortness_of_Breath === true){
                    document.getElementById("sobT").checked = "True";
                } 
                else{
                    document.getElementById("sobF").checked = "True";
                }
                if(responseData.userData.Anxiety === true){
                    document.getElementById("AnxietyT").checked = "True";
                } 
                else{
                    document.getElementById("AnxietyF").checked = "True";
                }
                if(responseData.userData.Chronic_Disease === true){
                    document.getElementById("Chronic_DiseaseT").checked = "True";
                } 
                else{
                    document.getElementById("Chronic_DiseaseF").checked = "True";
                }
                if(responseData.userData.Fatigue === true){
                    document.getElementById("FatigueT").checked = "True";
                } 
                else{
                    document.getElementById("FatigueF").checked = "True";
                }
                if(responseData.userData.Allergy === true){
                    document.getElementById("AllergyT").checked = "True";
                } 
                else{
                    document.getElementById("AllergyF").checked = "True";
                }
                if(responseData.userData.Wheezing === true){
                    document.getElementById("WheezingT").checked = "True";
                } 
                else{
                    document.getElementById("WheezingF").checked = "True";
                }
                if(responseData.userData.Swallowing_Difficulty === true){
                    document.getElementById("Swallowing_DifficultyT").checked = "True";
                } 
                else{
                    document.getElementById("Swallowing_DifficultyF").checked = "True";
                }
                if(responseData.userData.Chest_pain === true){
                    document.getElementById("Chest_painT").checked = "True";
                } 
                else{
                    document.getElementById("Chest_painF").checked = "True";
                }
                if(responseData.userData.cp === 0){
                    document.getElementById("cp0").checked = "True";
                } 
                else if (responseData.userData.cp === 1){
                    document.getElementById("cp1").checked = "True";
                }
                else if (responseData.userData.cp === 2){
                    document.getElementById("cp2").checked = "True";
                }
                else {
                    document.getElementById("cp3").checked = "True";
                }
                if(responseData.userData.hypertension === true){
                    document.getElementById("hypertensionT").checked = "True";
                } 
                else{
                    document.getElementById("hypertensionF").checked = "True";
                }
                if(responseData.userData.heart_disease === true){
                    document.getElementById("heart_diseaseT").checked = "True";
                } 
                else{
                    document.getElementById("heart_diseaseF").checked = "True";
                }

            } else {
                console.error("User data not found");
            }
        }
    };
    x.send();
}

</script>

<?php
include_once 'footer.php';
?>
