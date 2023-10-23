<?php
  include_once 'header2.php';
?>
    
    <main>
      <div id="container">
        <h2>AI Predict</h2>
        <p>
          <form method="post">
            <p><label>Patient Name*:&nbsp;<input type="text" name="firstname" required autocomplete="on"></label></p>
          </form>
        </p>
        <p><a href="">Generate</a></p>
        <p><label>Predictions<br>
					<textarea name="info" cols="80" rows="10"></textarea></label>
        </p>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>

