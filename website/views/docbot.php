<?php
  include_once 'header.php';
?>

        <main>
            <div id="container">
                <h2>DocBot</h2>

                <p>
                    Please use the text box below to send your query to our AI doctor which will generate an answer from 
                    our knowledge database to help assist with any issues you might have.
                </p>
                <label for="inputBox">Enter your query:<input type="text" id="inputBox" name="inputBox"></label>
                <p><button onclick="getDocBotResponse()" name="send">Ask</button></p>

                <label id="textlabel" for="bigTextBox"><p>DocBot Reply:</p>
                    <textarea id="bigTextBox" name="bigTextBox" rows="10" cols="1600"></textarea>
                </label>
            </div>
        </main>
    </body>

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
