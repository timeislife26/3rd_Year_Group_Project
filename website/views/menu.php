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
          You can check your patients risk profiles with our prediction algorithm.
        </p>
        <p>
          You can run reports establishing the levels of risk for all categories with our profiling algorithm.
        </p>
        <p>
          You can export patient information with our export option.
        </p>
        <p>
          <a href="predict.php">Predicting Algorithm</a>
          <a href="profiling.php">Profiling Algorithm</a>
          <a href="export.php">Export Information</a>
        </p>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>

