<!-- index.php -->
<?php
  include_once 'header.php';
?>

<main>
  <div id="container">
    <h2>Vision Statement</h2>
    <p>
      A societal transformation is taking place whereby healthcare professionals, and patients alike, are 
      shifting from reactive to predictive HealthAI care management.
      <br>Rather than waiting for health problems to be manifested as life-threatening conditions, AI technology 
      enables the prediction of future health problems using datasets of patient information.
      <br>Enter <b>LYFERISK</b>, an AI-trained model that will assist doctors and patients alike in more quickly
      diagnosing impending issues and taking action faster and more efficiently, thus increasing the chances of 
      success for treatment.
    </p>

    <!-- Add the Google Sign-In button -->
    <div class="g-signin2" data-onsuccess="onSignIn"></div>

    <!-- Include the Google Sign-In JavaScript -->
    <script src="https://apis.google.com/js/platform.js" async defer></script>

    <!-- Your existing JavaScript and other scripts go here -->

    <!-- Add a script to handle the Google Sign-In response -->
    <script>
        function onSignIn(googleUser) {
            var profile = googleUser.getBasicProfile();
            console.log('ID: ' + profile.getId());
            console.log('Name: ' + profile.getName());
            console.log('Email: ' + profile.getEmail());

            window.location.href = '../includes/login.inc.php?googleId=' + profile.getId() + '&googleEmail=' + profile.getEmail();
        }
    </script>
     <script src="js/google_signin.js"></script>
  </div>
</main>

<?php
  include_once 'footer.php';
?>
