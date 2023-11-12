<?php

// Database connection
require_once '../php-firebase/dbcon.php'; //'dbh.inc.php';

// Query to fetch the list of names from DB
$refTable = "DoctorUsers";

$result = $database->getReference($refTable)->getValue();

// Array to store names
$patientList = array();

// Getting names and storing them in array
foreach ($result as $key => $row)
    $patientList[] = $row["fullName"];
?>
