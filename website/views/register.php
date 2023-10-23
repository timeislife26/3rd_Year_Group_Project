<?php
  include_once 'header.php';
?>
  
    <main>
      <div id="container">
        <h2>Login</h2>
        <p>
        <form action="../includes/register.inc.php" method="POST">
            <p><label>Name*:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="name" required autocomplete="on"></label></p>
            <p><label>Email*:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="email" name="email" autocomplete="on"></label></p>
            <p><label>IMC Number*:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="IMC" autocomplete="on"></label></p>
            <p><label>Registration Number*:&nbsp;<input type="text" name="regNum" autocomplete="on"></label></p>
            <p><label>Password*:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="password" required></label></p>
            <p><label>Confirm Password*:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="confirm-password" required></label></p>
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

