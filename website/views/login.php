<?php
  include_once 'header.php';
?>

<main>
  <div id="container">
    <h2>Login</h2>
    <form action="../includes/login.inc.php" method="POST">
      <p>
        <label>Email*:&nbsp;&nbsp;&nbsp;&nbsp;<input type="email" name="email" autocomplete="on"></label>
        <label>Password*:&nbsp;<input type="password" name="password" required></label>
      </p>
      <p>Fields marked with * are mandatory</p>
      <p><button type="submit" name="submit">Login</button></p>
    </form>
    <div class="g-signin2" data-onsuccess="onSignIn"></div>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script>
        function onSignIn(googleUser) {
            var profile = googleUser.getBasicProfile();
            console.log('ID: ' + profile.getId());
            console.log('Name: ' + profile.getName());
            console.log('Email: ' + profile.getEmail());#
            var googleId = profile.getId();
            var googleEmail = profile.getEmail();
            $.ajax({
                type: "POST",
                url: "../includes/login.inc.php",
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
     <script src="js/google_signin.js"></script>
  </div>
</main>

<?php
  include_once 'footer.php';
?>
