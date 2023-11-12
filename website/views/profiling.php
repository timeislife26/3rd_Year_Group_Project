<?php
  include_once 'header.php';

  include '../includes/patient_list.inc.php';

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

  }
?>    
    <main>
      <div id="container">
        <h2>User Profiling</h2>
        <form method="post">
          <label for="patient_list">Patient Name*: 
            <select id="patient_list" name="Patient List">
              <?php
              foreach ($patientList as $patient) {
                echo '<option value>' , $patient , '</option>';
              }
              ?>
            </select>
          </label>
          <input type="submit" value="Generate">
        </form>
        <p><label>Profile<br>
					<textarea name="info" cols="80" rows="100"></textarea></label>
        </p>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>

