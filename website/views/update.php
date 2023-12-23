<?php
    header('Accept: application/json');
  include_once 'header.php';

  include '../includes/fetch_patient_data.inc.php';

  
  // Database connection
  require_once '../php-firebase/dbcon.php'; //'dbh.inc.php';
  
  // Query to fetch the list of names from DB
  $refTable = "PatientUsers";
  
  $result = $database->getReference($refTable)->getValue();
  
  // Array to store names
  $patientList = array();
  $patientID = array();
  
  // Getting names and storing them in array
  foreach ($result as $key => $row){
      $patientList[] = $row["name"];
      $patientID[] = $row["userId"];
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
        <form method="post">
          <div class="dropdown_list">
          <label for="patient_list">Patient Name*:&emsp;
            <select id="patient_list" name="selectedPatient">
              <?php
              foreach ($patientList as $patient) {
                echo '<option value="' . $patient . '">' , $patient , '</option>';
              }
              ?>
            </select>
            <button type="submit" name="generate">Fill Form</button>
            <?php
            
            if (isset($_POST['generate'])) {
              fetchPatientData($database, $patientList);
            }
            
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
                <label>Resting Blood Pressure: <input type="number" name="trewstbps" required autocomplete="on"></label>
                <label>Serum Cholestoral: <input type="number" name="chol" required autocomplete="on"></label>
                <label>Fasting Blood Sugar: <input type="number" name="fbs" required autocomplete="on"></label>
                <label>Resting Electocardiographic Results: <input type="number" name="restecg" min="0" max="2" required autocomplete="on"></label>
                <label>Maximum Heart Rate Achieved: <input type="number" name="thalach" required autocomplete="on"></label>
                <label>ST Depression Induced by Exercise Relative to Rest: <input type="number" name="oldpeak" required autocomplete="on"></label>
                <label>Slope of the Peak Exercise ST Segment: <input type="number" name="slope" min="0" max="2" required autocomplete="on"></label>
                <label>Number of Major Vessels Coloured by Flourosopy: <input type="number" name="ca" min="0" max="3" required autocomplete="on"></label>
                <label>Body Mass Index: <input type="number" step="0.01" name="bmi" required autocomplete="on"></label>
                <label>Hemoglobin A1c: <input type="number" step="any" name="HbA1c_level" required autocomplete="on"></label>
                <label>Blood Glucose Level: <input type="number" name="blood_glucose_level" required autocomplete="on"></label>
                <fieldset>
                    <legend>Exercise Induced Angina:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="exang" value="True">Yes</label>
					            <label><input type="radio" name="exang" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Thal:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="thal" value="0">Normal</label>
					    <label><input type="radio" name="thal" value="1">Fixed Defect</label>
                      <label><input type="radio" name="thal" value="2">Reversable Defect</label>
                      <label><input type="radio" name="thal" value="3">3</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Smoking:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Smoking_history" value="0" onclick="toggleSmokingStatus(false)">No info</label>
					            <label><input type="radio" name="Smoking_history" value="1" onclick="toggleSmokingStatus(false)">Never</label>
                                <label><input type="radio" name="Smoking_history" value="2" onclick="toggleSmokingStatus(false)">Formerly</label>
					            <label><input type="radio" name="Smoking_history" value="3" onclick="toggleSmokingStatus(true)">Currently</label>

                    </div>
                </fieldset>
                <fieldset>
                    <legend>Yellow Fingers:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Yellow_Fingers" value="True">Yes</label>
					            <label><input type="radio" name="Yellow_Fingers" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Alcohol Consuming:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="Alcohol_Consuming" value="True">Yes</label>
					    <label><input type="radio" name="Alcohol_Consuming" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Coughing:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="Coughing" value="True">Yes</label>
					    <label><input type="radio" name="Coughing" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Shortness of Breath:</legend>
                    <div class="radio_btn">
					    <label><input type="radio" name="Shortness_of_Breath" value="True">Yes</label>
					    <label><input type="radio" name="Shortness_of_Breath" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Anxiety:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Anxiety" value="True">Yes</label>
					            <label><input type="radio" name="Anxiety" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Chronic Disease:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Chronic_Disease" value="True">Yes</label>
					            <label><input type="radio" name="Chronic_Disease" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Fatigue:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Fatigue" value="True">Yes</label>
					            <label><input type="radio" name="Fatigue" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Allergies:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Allergy" value="True">Yes</label>
					            <label><input type="radio" name="Allergy" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Wheezing:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Wheezing" value="True">Yes</label>
					            <label><input type="radio" name="Wheezing" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Difficulty Swallowing:</legend>
                    <div class="radio_btn">
					            <label><input type="radio" name="Swallowing_Difficulty" value="True">Yes</label>
					            <label><input type="radio" name="Swallowing_Difficulty" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Chest Pain:</legend>
                    <div class="radio_btn">
					<label><input type="radio" name="Chest_pain" value="True" onclick="toggleChestPainType()">Yes</label>
					<label><input type="radio" name="Chest_pain" value="False" onclick="toggleChestPainType()">No</label>
                    </div>
                </fieldset>
                <fieldset id="cpField" style="display: none;">
                    <legend>Chest Pain Type:</legend>
                    <label><input type="radio" name="cp" value="0" checked>0</label>
                    <label><input type="radio" name="cp" value="1">1</label>
                    <label><input type="radio" name="cp" value="2">2</label>
                    <label><input type="radio" name="cp" value="3">3</label>
                </fieldset>
                <fieldset>
                    <legend>Hypertension:</legend>
                    <div class="radio_btn">
					<label><input type="radio" name="hypertension" value="True">Yes</label>
					<label><input type="radio" name="hypertension" value="False">No</label>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Heart Disease:</legend>
                    <div class="radio_btn">
					<label><input type="radio" name="heart_disease" value="True">Yes</label>
					<label><input type="radio" name="heart_disease" value="False">No</label>
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

<?php
  include_once 'footer.php';
?>
