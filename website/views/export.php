<?php
  include_once 'header.php';

  include '../includes/patient_list.inc.php';
?>
  
    <main>
      <div id="container">
        <h2>Export Patient Information</h2>
        <p>
          Select one of your patients from the drop down menu and click on "Export" to save the full 
          profile of the selected person in a text file.
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
            <button type="submit" name="export">Export</button>
          </label>
        </form>
        <?php
          if (isset($_POST['export'])) {
            displayPatientExport($database, $patientList);
          }
        ?>
      </div>
    </main>
  </body>

<?php
  include_once 'footer.php';
?>
