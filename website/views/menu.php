<?php
  include_once 'header.php';
?>
    
    <main>
      <div id="container">
        <h2>Menu</h2>
        <p>
          Welcome 
          <?php 
          echo $_SESSION['fullName']; 
          ?>
        </p>
        <p>
          Here you can:
          <ul>
            <li>Use <b>Profiling Algorithm</b> to get the full profile for a selected patient</li>
            <li>Use <b>Update Profile</b> to update your patient's profiles with more accurate data</li>
            <li>Use <b>Predicting Algorithm</b> to use our AI tools to make a prediction for a selected patient's risk of having certain diseases</li>
            <li>Use <b>Export Information</b> to download a file with information for a selected patient</li>
          </ul>
        </p>
        <p>
          <a href="profiling.php">Profiling Algorithm</a>
          <a href='update.php'>Update Patient Profile</a>
          <a href="predict.php">Predicting Algorithm</a>
          <a href="export.php">Export Information</a>
        </p>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>

