<?php

require __DIR__.'/vendor/autoload.php';

use Kreait\Firebase\Factory;

$factory = (new Factory)
    ->withServiceAccount('../php-firebase/group14-3rd-year-project-firebase-adminsdk-7v2dl-f57a5789f4.json')
    ->withDatabaseUri('https://group14-3rd-year-project-default-rtdb.europe-west1.firebasedatabase.app/');

$database = $factory->createDatabase();

?>