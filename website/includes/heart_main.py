import firebase_admin
import pandas as pd
import sys
import json
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report
from firebase_admin import credentials, db
import warnings

#To ignore the warning
warnings.filterwarnings("ignore", category=UserWarning)


cred = credentials.Certificate("../php-firebase/lyferisk-76065-firebase-adminsdk-fzn3c-e57685df6d.json")
firebase_admin.initialize_app(cred, {'databaseURL': 'https://lyferisk-76065-default-rtdb.europe-west1.firebasedatabase.app/'})
collection_ref = db.reference('/HeartDisease')

firebase_data = collection_ref.get()

# Get the parameter value from command-line arguments
if len(sys.argv) != 2:
    print("Usage: python heart_main.py <parameter>")
    sys.exit(1)

# Deserialize the JSON parameter to a Python list
parameter_value_json = sys.argv[1]
parameter_value = json.loads(parameter_value_json)

# Load the data
data = pd.DataFrame(firebase_data)

# Encode categorical variables (if any)
categorical_columns = ["sex", "cp", "fbs", "restecg", "exang", "slope", "ca", "thal"]
for column in categorical_columns:
    le = LabelEncoder()
    data[column] = le.fit_transform(data[column])

# Split the data
X = data.drop("Heart Disease", axis=1)
y = data["Heart Disease"]
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)


# Train the model on Logistic Regression
model = LogisticRegression(max_iter=1000)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(parameter_value)
if y_pred == ["Yes"]:
    result = "Based on this persons medical records, there is a possibilty that they may have heart disease. Further tests should be taken"
else:
    result = "Based on this persons medical records, it seems unlikely that they may have heart disease. However this is just a prediction. If there are worries please consult a doctor and get further tests"
print(result)
'''

# Evaluate accuracy
accuracy = accuracy_score(y_test, y_pred)
print("Accuracy:", accuracy)

# Print a detailed classification report
print(classification_report(y_test, y_pred))
'''


