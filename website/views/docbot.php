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

                <div id="replyBox" contenteditable="true">
                    Please let me know how I can help today!
                </div>

                
                <label for="inputBox">Enter your query:<input type="text" id="inputBox" name="inputBox" onkeyup="checkEnter(event)"></label>
                <p><button onclick="getDocBotResponse()" name="send">Ask</button><br></p>
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
                    document.getElementById("replyBox").innerHTML = x.responseText;
                }
            };
            x.send();
        }

        function checkEnter(event) {
            if (event.keyCode === 13) {
                getDocBotResponse();
            }
        }
    </script>

<?php
  include_once 'footer.php';
?>
