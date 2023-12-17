<?php
  include_once 'header.php';
  ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PHP Form</title>
</head>
<body>
        <label for="bigTextBox">Big Text Box:</label><br>
        <textarea id="bigTextBox" name="bigTextBox" rows="5" cols="50"></textarea><br><br>

        <label for="inputBox">Input Box:</label>
        <input type="text" id="inputBox" name="inputBox"><br><br>

        <button onclick="getDocBotResponse()" name="send">Send</button>

</body>
</html>
<script>
        //Ajax
    function getDocBotResponse(){
        message = document.getElementById("inputBox").value;
        var x = new XMLHttpRequest();
        var url = "../includes/docbot.inc.php?message=" + message;
        
        console.log("Sent Message: ", message);


        x.open("GET", url);
            x.onreadystatechange = function () {
            if (x.readyState == 4 && x.status == 200) {
                // Handle the response if needed
                document.getElementById("bigTextBox").value = x.responseText;
            }
            };
        x.send();
        }

    </script>

<?php
  include_once 'footer.php';
?>
