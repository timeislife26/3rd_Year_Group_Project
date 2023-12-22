<?php
$message = isset($_GET["message"]) ? $_GET["message"] : null;
require_once '../php-firebase/dbcon.php';


$function_url = 'https://on-request-docbot-ef42g3nnla-uc.a.run.app';

$prompt_array = [
    "message" => $message,
    ];
$prompt = json_encode($prompt_array);

$options = [
    'http' => [
        'header' => "Content-type: application/json\r\n",
        'method' => 'POST',
        'content' => $prompt,
    ],
];
$context = stream_context_create($options);
$response = file_get_contents($function_url, false, $context);
if ($response === FALSE) {
    die('Error occurred while fetching data from the Cloud Function');
} else {
    echo "<b>Query:</b> $message<br><br>";
    echo "<b>DocBot:</b> $response";
}


?>
