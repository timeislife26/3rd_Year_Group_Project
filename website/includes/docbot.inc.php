<?php
session_start();
$prompt_list = array();
?>


<?php
$message = isset($_GET["message"]) ? $_GET["message"] : null;
require_once '../php-firebase/dbcon.php';


$function_url = 'https://on-request-docbot-ef42g3nnla-uc.a.run.app';

$prompt_array = isset($_SESSION['prompt_array']) ? $_SESSION['prompt_array'] : array();
$chat_message = isset($_SESSION['chat_message']) ? $_SESSION['chat_message'] : array();



$new_message = array("role" => "user", "content" => "$message");
array_push($prompt_array, $new_message);

$_SESSION['prompt_array'] = $prompt_array;

$prompt = json_encode(["message" => $prompt_array]);

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
    $recieved_message = array("role" => "user", "content" => "$response");
    array_push($prompt_array, $recieved_message);
    array_push($chat_message, $message);
    array_push($chat_message, $response);
    for($i = 0; $i < count($chat_message); $i++){
        if ($i % 2 === 0){
            echo "<b>Query:</b> $chat_message[$i]<br><br>";
        }
        else {
            echo "<b>DocBot:</b> $chat_message[$i]<br><br>";

        }
    }
    $_SESSION['chat_message'] = $chat_message;
}


?>
