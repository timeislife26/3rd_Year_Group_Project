<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../styles/baseStyle.css">
    <title>LyfeRisk</title>
  </head>
  
  <body>
    <div id="header">
      <h1>LyfeRisk</h1>

      <div id="navbar">
        <ul>
            <li><a href="menu.php">Menu</a></li>
            <li><a href="predict.php">Predicting Algorithm</a></li>
            <li><a href="export.php">Export Information</a></li>
        </ul>
      </div>
    </div>
    
    <main>
      <div id="container">
        <h2>User Profiling</h2>
        <p>
          <form method="post">
            <p><label>Patient Name*:&nbsp;<input type="text" name="firstname" required autocomplete="on"></label></p>
          </form>
        </p>
        <p><a href="">Generate</a></p>
        <p><label>Profile<br>
					<textarea name="info" cols="80" rows="10"></textarea></label>
        </p>
      </div>
    </main>
  </body>


<?php
  include_once 'footer.php';
?>

