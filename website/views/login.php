<?php
  include_once 'header.php';
?>
  
    <main>
      <div id="container">
        <h2>Login</h2>
        <form action="../includes/login.inc.php" method="POST">
          <p><label>Email*:&nbsp;&nbsp;&nbsp;&nbsp;<input type="email" name="email" autocomplete="on"></label></p>
          <p><label>Password*:&nbsp;<input type="password" name="password" required></label></p>
          <p>Fields marked with * are mandatory</p>
          <p><button type="submit" name="submit">Register</button></p>
        </form>
      </div>
    </main>
  </body>

<?php
  include_once 'footer.php';
?>
