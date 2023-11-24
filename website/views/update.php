<?php
  include_once 'header.php';

  include '../includes/patient_list.inc.php';
?>

    <main>
      <div id="container">
        <h2>User Profiling</h2>
        <p>
          Select one of your patients from the drop down menu and click on "Fill Form" to enter the data 
          of the selected person into the fields. If any changes are made you can then click on "Update" 
          to update the information about the patient.<br>
          <b>All fields in the form are mandatory</b>
        </p>
        <form method="post">
          <label for="patient_list">Patient Name*: 
            <select id="patient_list" name="selectedPatient">
              <?php
              foreach ($patientList as $patient) {
                echo '<option value="' . $patient . '">' , $patient , '</option>';
              }
              ?>
            </select>
            <button type="submit" name="generate">Fill Form</button>
          </label>
        </form>
        <form method="post">
            <p>
                <label>Name: <input type="text" name="name" required autocomplete="on"></label>
                <label>Email: <input type="email" name="email" autocomplete="on"></label>
                <label>Age: <input type="number" name="Age" min="0" max="125" required autocomplete="on"></label>
                <fieldset>
                    <legend>Gender at Birth:</legend>
					<label><input type="radio" name="gender" value="True">Male</label>
					<label><input type="radio" name="gender" value="False">Female</label>
                </fieldset>
                <label>Resting Blood Pressure: <input type="number" name="trewstbps" required autocomplete="on"></label>
                <label>Serum Cholestoral: <input type="number" name="chol" required autocomplete="on"></label>
                <label>Fasting Blood Sugar: <input type="number" name="fbs" required autocomplete="on"></label>
                <label>Resting Electocardiographic Results: <input type="number" name="restecg" min="0" max="2" required autocomplete="on"></label>
                <label>Maximum Heart Rate Achieved: <input type="number" name="thalach" required autocomplete="on"></label>
                <label>ST Depression Induced by Exercise Relative to Rest: <input type="number" name="oldpeak" required autocomplete="on"></label>
                <label>Serum Cholestoral: <input type="number" name="chol" required autocomplete="on"></label>
                <label>Slope of the Peak Exercise ST Segment: <input type="number" name="slope" min="0" max="2" required autocomplete="on"></label>
                <label>Number of Major Vessels Coloured by Flourosopy: <input type="number" name="ca" min="0" max="3" required autocomplete="on"></label>
                <fieldset>
                    <legend>Exercise Induced Angina:</legend>
					<label><input type="radio" name="exang" value="True">Yes</label>
					<label><input type="radio" name="exang" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Thal:</legend>
					<label><input type="radio" name="thal" value="0">Normal</label>
					<label><input type="radio" name="thal" value="1">Fixed Defect</label>
                    <label><input type="radio" name="thal" value="2">Reversable Defect</label>
                </fieldset>
                <fieldset>
                    <legend>Smoking:</legend>
					<label><input type="radio" name="Smoking" value="True">Yes</label>
					<label><input type="radio" name="Smoking" value="False">No</label>
                </fieldset>>
                <fieldset>
                    <legend>Yellow Fingers:</legend>
					<label><input type="radio" name="Yellow_Fingers" value="True">Yes</label>
					<label><input type="radio" name="Yellow_Fingers" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Anxiety:</legend>
					<label><input type="radio" name="Anxiety" value="True">Yes</label>
					<label><input type="radio" name="Anxiety" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Chronic Disease:</legend>
					<label><input type="radio" name="Chronic_Disease" value="True">Yes</label>
					<label><input type="radio" name="Chronic_Disease" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Fatigue:</legend>
					<label><input type="radio" name="Fatigue" value="True">Yes</label>
					<label><input type="radio" name="Fatigue" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Allergies:</legend>
					<label><input type="radio" name="Allergy" value="True">Yes</label>
					<label><input type="radio" name="Allergy" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Wheezing:</legend>
					<label><input type="radio" name="Wheezing" value="True">Yes</label>
					<label><input type="radio" name="Wheezing" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Difficulty Swallowing:</legend>
					<label><input type="radio" name="Swallowing_Difficulty" value="True">Yes</label>
					<label><input type="radio" name="Swallowing_Difficulty" value="False">No</label>
                </fieldset>
                <fieldset>
                    <legend>Chest Pain:</legend>
					<label><input type="radio" name="Chest_pain" value="True">Yes</label>
					<label><input type="radio" name="Chest_pain" value="False">No</label>
                </fieldset>
            </p>
            <p>
                <button type="submit" name="update">Update</button>
            </p><p></p>
        </form>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>
