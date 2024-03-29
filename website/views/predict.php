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
  

?>
    
    <main>
      <div id="container">
        <h2>AI Predict</h2>
        <p>
          Select one of your patients from the drop down menu and click on "Generate" to receive a prediction 
          for the selected the selected person.
        </p>
          <div class="dropdown_list">
        <label for="patient_list">Patient Name*:&emsp;
            <select id="patient_list" name="selectedPatient" onchange="updateSelectedUserID()">
            <?php
            foreach ($result as $entry) {
              if (strcasecmp(trim($entry['linkedDoctorIMC']), $_SESSION['imc']) === 0) {
                $patientList[] = $entry['name'];
                $patientID[] = $entry['userID'];
                echo '<option value="' . $entry['name'] . '">' , $entry['name'] , '</option>';
              }
            }
            ?>
            </select>
            <input type="hidden" name="selectedUserID" id="selectedUserID">
            <button onclick="getPrediction()" name="generate">Generate</button>
          </label>
          </div>
        <label id="textlabel"><p>Profile</p>
        <textarea id="info" rows="10" cols="80">
          </textarea>
        </label>
      </div>
      <div id="output"></div>
    </main>
  </body>
  <script>
    var patientID;
    var selectedIndex

window.onload = function() {
    selectedIndex = document.getElementById("patient_list").selectedIndex;
    patientID = <?php echo json_encode($patientID); ?>; 
    console.log("Initial Patient ID: ", patientID);
    document.getElementById("selectedUserID").value = patientID[selectedIndex];
};
function updateSelectedUserID() {
    selectedIndex = document.getElementById("patient_list").selectedIndex;
    patientID = <?php echo json_encode($patientID); ?>; 
    console.log("Selected Patient ID: ", patientID[selectedIndex]);
    document.getElementById("selectedUserID").value = patientID[selectedIndex];
}

    //Ajax
    function getPrediction(){
      document.getElementById("info").value = "loading...";

      console.log("Clicked");
      var x = new XMLHttpRequest();
      var url = "../includes/predict.inc.php?userID=" + patientID[selectedIndex];
      
      console.log("Selected Patient ID: ", patientID[selectedIndex]);


      x.open("GET", url);
        x.onreadystatechange = function () {
          if (x.readyState == 4 && x.status == 200) {
            // Handle the response if needed
            document.getElementById("info").value = x.responseText;
          }
        };
      x.send();
      }

    </script>

<?php
  include_once 'footer.php';
?>

