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
          <label>Password*: <input type="password" name="password" required></label>
          <label>Confirm Password*: <input type="password" name="confirm-password" required></label>
          <label>Address: <input type="text" name="address" autocomplete="on"></label>
          <label>Phone Number: <input type="tel" name="phone" autocomplete="on"></label>
          <label>Date of Birth: <input type="date" name="dob"></label>
          <label>Gender: 
            <select name="gender">
              <option value="male">Male</option>
              <option value="female">Female</option>
              <option value="other">Other</option>
            </select>
          </label>
        </p>
        <p>Fields marked with * are mandatory</p>
        <p><button type="submit" name="submit">Register</button></p>
      </form>
      <div class="g-signin2" data-onsuccess="onSignIn"></div>
      <script src="https://apis.google.com/js/platform.js" async defer></script>
      <script src="js/google_signin.js"></script>
    </p>
  </div>
  <script>
    function onSignIn(googleUser) {
      var profile = googleUser.getBasicProfile();
      console.log('ID: ' + profile.getId());
      console.log('Name: ' + profile.getName());
      console.log('Email: ' + profile.getEmail());
      var googleId = profile.getId();
      var googleEmail = profile.getEmail();
      $.ajax({
        type: "POST",
        url: "../includes/register.inc.php",
        data: {
          googleId: googleId,
          googleEmail: googleEmail
        },
        success: function(response) {
          console.log(response);
        },
        error: function(error) {
          console.error(error);
        }
      });
    }
  </script>
</main>
</body>

<?php
  include_once 'footer.php';
?>

