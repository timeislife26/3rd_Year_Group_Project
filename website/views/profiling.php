<?php
  include_once 'header.php';

  include '../includes/patient_list.inc.php';

  if (isset($_POST['generate'])) {
    displayPatientProfile($database, $patientList);
  }
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
          </label>
          <input type="submit" name="generate" value="Generate">
        </form>
        <label><p>Profile</p>
					<textarea name="info" rows="10" cols="80"></textarea>
        </label>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>
