<?php

require __DIR__.'/vendor/autoload.php';

use Kreait\Firebase\Factory;
use Kreait\Firebase\Contract\Auth;

$factory = (new Factory)
    ->withServiceAccount('../php-firebase/lyferisk-76065-firebase-adminsdk-fzn3c-e57685df6d.json')
    ->withDatabaseUri('https://lyferisk-76065-default-rtdb.europe-west1.firebasedatabase.app/');

$database = $factory->createDatabase();
$auth = $factory->createAuth();

?>