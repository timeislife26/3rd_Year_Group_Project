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
          <li><a href="predict.php">Predicting Algorithm</a></li>
          <li><a href="profiling.php">Profiling Algorithm</a></li>
          <li><a href="export.php">Export Information</a></li>
        </ul>
      </div>
    </div>
    
    <main>
      <div id="container">
        <h2>Menu</h2>
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

