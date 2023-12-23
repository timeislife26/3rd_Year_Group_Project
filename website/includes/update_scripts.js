

var patientID;

window.onload = function() {
    patientID = document.getElementById("patient_list").value;
    console.log("Initial Patient ID: ", patientID);
    document.getElementById("selectedUserID").value = patientID;
};
function updateSelectedUserID() {
    patientID = document.getElementById("patient_list").value;
    console.log("Selected Patient ID: ", patientID);
    document.getElementById("selectedUserID").value = patientID;
}

function toggleChestPainType() {
    var chestPainRadio = document.querySelector('input[name="Chest_pain"]:checked');
    var chestPainTypeField = document.getElementById('cpField');

    if (chestPainRadio && chestPainRadio.value === 'True') {
        chestPainTypeField.style.display = 'block';
    } else {
        chestPainTypeField.style.display = 'none';
        // If chest pain is false, set chest pain type to 0
        document.getElementById('cp').value = '0';
    }
}

function toggleSmokingStatus(isCurrently) {
    var smokingStatusField = document.getElementById('smokingStatus');
    smokingStatusField.value = isCurrently ? 'True' : 'False';
}


function fillForm() {
    // Get the selected patient's ID
    var selectedPatientID = document.getElementById("patient_list").value;
    console.log("Selected Patient ID: ", selectedPatientID);
    
    // Make an AJAX request to fetch data
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../includes/fetch_patient_data.inc.php", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Parse the JSON response
            var data = JSON.parse(xhr.responseText);
            
            
            // Populate the form fields
            populateForm(data);
            /*
            document.getElementById("name").value = data.name;
            document.getElementById("email").value = data.email;
            document.getElementById("Age").value = data.Age;
            document.getElementById("gender").value = data.gender;
            document.getElementById("Age").value = data.Age;
            document.getElementById("trewstbps").value = data.trewstbps;
            document.getElementById("chol").value = data.chol;
            document.getElementById("fbs").value = data.fbs;
            document.getElementById("restecg").value = data.restecg;
            document.getElementById("thalach").value = data.thalach;
            document.getElementById("oldpeak").value = data.oldpeak;
            document.getElementById("slope").value = data.slope;
            document.getElementById("ca").value = data.ca;
            document.getElementById("bmi").value = data.bmi;
            document.getElementById("HbA1c_level").value = data.HbA1c_level;
            document.getElementById("blood_glucose_level").value = data.blood_glucose_level;
            document.getElementById("exang").value = data.exang;
            document.getElementById("thal").value = data.thal;
            document.getElementById("Smoking").value = data.Smoking;
            document.getElementById("Yellow_Fingers").value = data.Yellow_Fingers;
            document.getElementById("Alcohol_Consuming").value = data.Alcohol_Consuming;
            document.getElementById("Coughing").value = data.Coughing;
            document.getElementById("Shortness_of_Breath").value = data.Shortness_of_Breath;
            document.getElementById("Anxiety").value = data.Anxiety;
            document.getElementById("Chronic_Disease").value = data.Chronic_Disease;
            document.getElementById("Fatigue").value = data.Fatigue;
            document.getElementById("Allergy").value = data.Allergy;
            document.getElementById("Wheezing").value = data.Wheezing;
            document.getElementById("Swallowing_Difficulty").value = data.Swallowing_Difficulty;
            document.getElementById("Chest_pain").value = data.Chest_pain;
            //document.getElementById("cp").value = data.cp;
            document.getElementById("hypertension").value = data.hypertension;
            document.getElementById("heart_disease").value = data.heart_disease;
            */
        }
    };

    // Send the request with the selected patient's ID
    xhr.send("selectedPatientID=" + selectedPatientID);
}

function populateForm(data) {
    document.getElementById("name").value = data.name;
    document.getElementById("email").value = data.email;
    document.getElementById("Age").value = data.Age;
    document.getElementById("gender").value = data.gender;
    /*
    document.getElementById("trewstbps").value = data.trewstbps;
    document.getElementById("chol").value = data.chol;
    document.getElementById("fbs").value = data.fbs;
    document.getElementById("restecg").value = data.restecg;
    document.getElementById("thalach").value = data.thalach;
    document.getElementById("oldpeak").value = data.oldpeak;
    document.getElementById("slope").value = data.slope;
    document.getElementById("ca").value = data.ca;
    document.getElementById("bmi").value = data.bmi;
    document.getElementById("HbA1c_level").value = data.HbA1c_level;
    document.getElementById("blood_glucose_level").value = data.blood_glucose_level;
    document.getElementById("exang").value = data.exang;
    document.getElementById("thal").value = data.thal;
    document.getElementById("Smoking").value = data.Smoking;
    document.getElementById("Yellow_Fingers").value = data.Yellow_Fingers;
    document.getElementById("Alcohol_Consuming").value = data.Alcohol_Consuming;
    document.getElementById("Coughing").value = data.Coughing;
    document.getElementById("Shortness_of_Breath").value = data.Shortness_of_Breath;
    document.getElementById("Anxiety").value = data.Anxiety;
    document.getElementById("Chronic_Disease").value = data.Chronic_Disease;
    document.getElementById("Fatigue").value = data.Fatigue;
    document.getElementById("Allergy").value = data.Allergy;
    document.getElementById("Wheezing").value = data.Wheezing;
    document.getElementById("Swallowing_Difficulty").value = data.Swallowing_Difficulty;
    document.getElementById("Chest_pain").value = data.Chest_pain;
    //document.getElementById("cp").value = data.cp;
    document.getElementById("hypertension").value = data.hypertension;
    document.getElementById("heart_disease").value = data.heart_disease;
    */
}
