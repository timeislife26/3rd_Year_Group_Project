<?php
  include_once 'header.php';

  include '../includes/patient_list.inc.php';
?>

    <main>
      <div id="container">
        <h2>User Profiling</h2>
        <p>
          Select one of your patients from the drop down menu and click on "Generate" to receive the full 
          profile of the selected person.
        </p>
        <form method="post">
          <div class="dropdown_list">
          <label for="patient_list">Patient Name*:&emsp;
            <select id="patient_list" name="selectedPatient">
              <?php
              foreach ($result as $entry) {
                if (strcasecmp(trim($entry['linkedDoctorIMC']), $_SESSION['imc']) === 0) {
                  echo '<option value="' . $entry['name'] . '">' , $entry['name'] , '</option>';
                }
              }
              ?>
            </select>
            <button type="submit" name="generate">Generate</button>
          </label>
          </div>
        </form>
        <label id="textlabel"><p>Profile</p>
					<textarea name="info" rows="10" cols="800">
            <?php
            if (isset($_POST['generate'])) {
              displayPatientProfile($database, $patientList);
            }
            ?>
          </textarea>
        </label>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>
