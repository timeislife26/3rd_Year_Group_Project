

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
    
    // Make an AJAX request to fetch data
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../includes/fetch_patient_data.inc.php", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert("this is " + xhr.responseText) // WHY IS THIS EMPTY???!!!!!!
            // Parse the JSON response
            var data = JSON.parse(xhr.responseText);
                        
            // Populate the form fields
            populateForm(data);
        }
    };

    // Send the request with the selected patient's ID
    xhr.send('selectedPatient=' + selectedPatientID);
}

function populateForm(data) {
    document.getElementById("name").value = data.name;
    document.getElementById("email").value = data.email;
    document.getElementById("Age").value = data.Age;
    if (data.gender === true) {
        document.getElementById("genderM").checked = true;
    } else {
        document.getElementById("genderF").checked = true;
    }
    
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
    if (data.exang === true) {
        document.getElementById("exangT").checked = true;
    } else {
        document.getElementById("exangF").checked = true;
    }
    if (data.thal === 0) {
        document.getElementById("thal0").checked = true;
    } else if (data.thal === 1) {
        document.getElementById("thal1").checked = true;
    } else if (data.thal === 2) {
        document.getElementById("thal2").checked = true;
    } else {
        document.getElementById("thal3").checked = true;
    }
    if (data.Smoking === true) {
        document.getElementById("Smoking_historyT").checked = true;
    } else {
        document.getElementById("Smoking_historyF").checked = true;
    }
    if (data.Yellow_Fingers === true) {
        document.getElementById("Yellow_FingersT").checked = true;
    } else {
        document.getElementById("Yellow_FingersF").checked = true;
    }
    if (data.Anxiety === true) {
        document.getElementById("AnxietyT").checked = true;
    } else {
        document.getElementById("AnxietyF").checked = true;
    }
    if (data.Chronic_Disease === true) {
        document.getElementById("Chronic_DiseaseT").checked = true;
    } else {
        document.getElementById("Chronic_DiseaseF").checked = true;
    }
    if (data.Fatigue === true) {
        document.getElementById("FatigueT").checked = true;
    } else {
        document.getElementById("FatigueF").checked = true;
    }
    if (data.Allergy === true) {
        document.getElementById("AllergyT").checked = true;
    } else {
        document.getElementById("AllergyF").checked = true;
    }
    if (data.Wheezing === true) {
        document.getElementById("WheezingT").checked = true;
    } else {
        document.getElementById("WheezingF").checked = true;
    }
    if (data.Swallowing_Difficulty === true) {
        document.getElementById("Swallowing_DifficultyT").checked = true;
    } else {
        document.getElementById("Swallowing_DifficultyF").checked = true;
    }
    if (data.Chest_pain === true) {
        document.getElementById("Chest_painT").checked = true;
    } else {
        document.getElementById("Chest_painF").checked = true;
    }
    if (data.cp === 0) {
        document.getElementById("cp0").checked = true;
    } else if (data.cp === 1) {
        document.getElementById("cp1").checked = true;
    } else if (data.cp === 2) {
        document.getElementById("cp2").checked = true;
    } else {
        document.getElementById("cp3").checked = true;
    }
    if (data.hypertension === true) {
        document.getElementById("hypertensionT").checked = true;
    } else {
        document.getElementById("hypertensionF").checked = true;
    }
    if (data.heart_disease === true) {
        document.getElementById("heart_diseaseT").checked = true;
    } else {
        document.getElementById("heart_diseaseF").checked = true;
    }
    
}
