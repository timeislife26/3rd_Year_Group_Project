<?php
  session_start();
  
  require_once '../php-firebase/dbcon.php';
?>

<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="description" content="LyfeRisk">
		<meta name="keywords" content="Diagnosis, AI Assisted, Tool">
		<meta name="author" content="Alex Fenlon, Jason Fleming, Laiba Asif, Miguel Reis">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../styles/baseStyle.css">
  </head>

  <body>
    <title>LyfeRisk</title>
    <div id="header">
      <h1><a href='index.php'>LyfeRisk</a></h1>

      <div id="navbar">
        <ul>
          <li><a href="index.php">Home</a></li>
          <?php
          if(isset($_SESSION["verified_uid"])){
            //Check if token has expired
            try {
              $verifiedIdToken = $auth->verifyIdToken($_SESSION['idToken']);
            } catch (FailedToVerifyToken $e) {
              header("location: ../includes/logout.inc.php");
              exit();
            }
            echo "<li><a href='menu.php'>Menu</a></li>";
            echo "<li><a href='predict.php'>Predicting Algorithm</a></li>";
            echo "<li><a href='profiling.php'>Profiling Algorithm</a></li>";
            echo "<li><a href='export.php'>Export Information</a></li>";
            echo "<li><a href='../includes/logout.inc.php'>Logout</a></li>";
          }
          else {
            echo "<li><a href='login.php'>Login</a></li>";
            echo "<li><a href='register.php'>Register</a></li>";
          }
          ?>
        </ul>
      </div>
    </div>
    