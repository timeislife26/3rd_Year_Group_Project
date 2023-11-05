<?php
  include_once 'header.php';
?>
  
    <main>
      <div id="container">
        <h2>Register</h2>
        <p>
        <form action="../includes/register.inc.php" method="POST">
            <p>
              <label>Name*: <input type="text" name="name" required autocomplete="on"></label>
              <label>Email*: <input type="email" name="email" autocomplete="on"></label>
              <label>IMC Number*: <input type="text" name="IMC" autocomplete="on"></label>
              <label>Registration Number*: <input type="text" name="regNum" autocomplete="on"></label>
              <label>Password*: <input type="password" name="password" required></label>
              <label>Confirm Password*: <input type="password" name="confirm-password" required></label>
            </p>
            <p>Fields marked with * are mandatory</p>
            <p><button type="submit" name="submit">Register</button></p>
          </form>
        </p>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>

