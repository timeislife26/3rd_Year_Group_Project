<?php
  include_once 'header.php';

  include '../includes/patient_list.inc.php';
?>

    <main>
      <div id="container">
        <h2>User Profiling</h2>
        <form method="post">
          <label for="patient_list">Patient Name*: 
            <select id="patient_list" name="selectedPatient">
              <?php
              foreach ($patientList as $patient) {
                echo '<option value="' . $patient . '">' , $patient , '</option>';
              }
              ?>
            </select>
            <button type="submit" name="generate">Generate</button>
          </label>
        </form>
        <label id="textlabel"><p>Profile</p>
					<textarea name="info" rows="10" cols="80">
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
