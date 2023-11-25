<?php
  include_once 'header.php';

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
    
    <main>
      <div id="container">
        <h2>AI Predict</h2>
        <p>
          Select one of your patients from the drop down menu and click on "Generate" to receive a prediction 
          for the selected the selected person.
        </p>
        <form action="../includes/predict.inc.php" method="POST">
        <label for="patient_list">Patient Name*: 
            <select id="patient_list" name="selectedPatient" onchange="updateSelectedUserID()">
            <?php
                    // Loop through names and corresponding userIDs
                    for ($i = 0; $i < count($patientList); $i++) {
                        echo '<option value="' . $patientID[$i] . '">' , $patientList[$i] , '</option>';
                    }
                    ?>
            </select>
            <input type="hidden" name="selectedUserID" id="selectedUserID">
            <button type="submit" name="generate">Fill Form</button>
          </label>
        </form>
        <label id="textlabel"><p>Profile</p>
					<textarea name="info" rows="10" cols="80">
            <?php
            if (isset($_POST['generate'])) {
              displayPatientPrediction($database, $patientList);
            }
            ?>
          </textarea>
        </label>
      </div>
    </main>
  </body>
  <script>
    var patientID;

    window.onload = function() {
        patientID = document.getElementById("patient_list").value;
        console.log("Initial Patient ID: ", patientID);
        document.getElementById("selectedUserID").value = patientID;
    };
    function updateSelectedUserID() {
        patientID = document.getElementById("patient_list").value;
        console.log("Selected Patient ID: ", patientID);
        document.getElementById("selectedUserID").value = patientID;
    }
    </script>

<?php
  include_once 'footer.php';
?>

