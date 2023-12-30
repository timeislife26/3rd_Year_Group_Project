# Welcome to Cloud Functions for Firebase for Python!
# To get started, simply uncomment the below code or create your own.
# Deploy with `firebase deploy`

from firebase_functions import https_fn
from firebase_admin import initialize_app
import joblib
#import warnings
from openai import OpenAI

initialize_app()


@https_fn.on_request()
def on_request_heart(req: https_fn.Request) -> https_fn.Response:
    if req.method == 'POST':
        data = req.get_json()  # Assuming the data is sent as JSON
        if 'Age' in data:
            age = int(data['Age'])
            ca = int(data['ca'])
            chol = int(data['chol'])
            cp = int(data['cp'])
            exang = int(data['exang'])
            fbs = int(data['fbs'])
            oldpeak = float(data['oldpeak'])
            restecg = int(data['restecg'])
            gender = int(data['gender'])
            slope = int(data['slope'])
            thal = int(data['thal'])
            thalach = int(data['thalach'])
            trewstbps = int(data['trewstbps'])
            loaded_model = joblib.load('heart_disease_model.joblib')
            heart_prediction = loaded_model.predict([[age, gender, cp, trewstbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal]])
            heart_disease_str = "Heart Disease: "
            if heart_prediction == ['Yes']:
                heart_disease_str += "Based on this persons medical records, there is a possibility that they may have heart disease.\n"
            else:
                heart_disease_str += "Based on this persons medical records, it seems unlikely that they may have heart disease.\n"
            return https_fn.Response(heart_disease_str)
    return https_fn.Response("No data sent for heart disease prediction")

@https_fn.on_request()
def on_request_lung(req: https_fn.Request) -> https_fn.Response:
    if req.method == 'POST':
        data = req.get_json()  # Assuming the data is sent as JSON
        if 'Age' in data:
            gender = int(data["gender"])
            age = int(data["Age"])
            smoking = int(data["smoking"]) + 1
            yf = int(data["yf"]) + 1
            anxiety = int(data["anxiety"]) + 1
            cd = int(data["cd"]) + 1
            fatigue = int(data["fatigue"]) + 1
            allergy = int(data["allergy"]) + 1
            wheezing = int(data["wheezing"]) + 1
            alcohol = int(data["Alcohol"]) + 1
            coughing = int(data["coughing"]) + 1
            sob = int(data["sob"]) + 1
            chest_p = int(data["chest_pain"]) + 1
            sd = int(data["sd"]) + 1
            loaded_model = joblib.load('lung_disease_model.joblib')
            lung_prediction = loaded_model.predict([[gender, age, smoking, yf, anxiety, cd, fatigue, allergy, wheezing, alcohol, coughing, sob, sd, chest_p]])
            lung_disease_str = "Lung Disease: "
            if lung_prediction[0] == 'YES':
                lung_disease_str += "Based on this persons medical records, there is a possibility that they may have lung cancer.\n"
            else:
                lung_disease_str += "Based on this persons medical records, it seems unlikely that they may have lung cancer.\n"
            return https_fn.Response(lung_disease_str)
    return https_fn.Response("No data sent for heart disease prediction")


@https_fn.on_request()
def on_request_diabetes(req: https_fn.Request) -> https_fn.Response:
    if req.method == 'POST':
        data = req.get_json()  # Assuming the data is sent as JSON
        if 'Age' in data:
            age = int(data['Age'])
            gender = int(data['gender'])
            hypertension = int(data['hypertension'])
            heart_disease = int(data['heart_disease'])
            smoking_history = int(data['smoking_history'])
            bmi = float(data['bmi'])
            HbA1c = float(data['hbA1c'])
            blood_glucose_level = int(data['blood_glucose_level'])
            #return https_fn.Response(f"gender: {gender}") #,age: {age},hypertension: {hypertension},heart_disease: {heart_disease},smoking_history: {smoking_history},bmi: {bmi},HbA1c: {HbA1c},blood_glucose_level: {blood_glucose_level }")
            loaded_model = joblib.load('diabetes_model.joblib')
            diabetes_prediction = loaded_model.predict([[gender,age,hypertension,heart_disease,smoking_history,bmi,HbA1c,blood_glucose_level]])
            diabetes_disease_str = "Diabetes: "
            if diabetes_prediction[0] == 0:
                diabetes_disease_str += "Based on this persons medical records, there is a possibility that they may have diabtese.\n"
            else:
                diabetes_disease_str += "Based on this persons medical records, it seems unlikely that they may have diabetes.\n"
            return https_fn.Response(diabetes_disease_str)
    return https_fn.Response("No data sent for diabetes prediction")



@https_fn.on_request()
def on_request_docBot(req: https_fn.Request) -> https_fn.Response:
    if req.method == 'POST':
        data = req.get_json()
        client = OpenAI(
            api_key="Enter your own API key here"
        )
        prompt = data['message']
        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=prompt,
            stream=False
        )
        return https_fn.Response(response.choices[0].message.content)
