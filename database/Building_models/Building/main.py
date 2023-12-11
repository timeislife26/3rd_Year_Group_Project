import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.linear_model import LogisticRegression
import joblib
import openpyxl


def build_heart_disease_model():
    # Load the data into a DataFrame
    data = pd.read_excel("heart disease.xlsx")

    # Encode categorical variables
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
    joblib.dump(model, 'heart_disease_model.joblib')

def build_lung_disease_model():
    # Load the data
    data = pd.read_csv("lung cancer.csv")
    data = data.drop("PEER_PRESSURE", axis=1)

    # Encode categorical variables (if any)
    categorical_columns = ["GENDER"]
    for column in categorical_columns:
        le = LabelEncoder()
        data[column] = le.fit_transform(data[column])

    # Split the data
    X = data.drop("LUNG_CANCER", axis=1)
    y = data["LUNG_CANCER"]
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    # print(X.iloc[0])
    # Train the model on Logistic Regression
    model = LogisticRegression(max_iter=1000)
    model.fit(X_train, y_train)
    joblib.dump(model, 'lung_disease_model.joblib')



def build_diabetes_model():
    data = pd.read_excel("diabetes.xlsx")
    data = data.drop(data.columns[0], axis=1)
    le = LabelEncoder()
    data["gender"] = le.fit_transform(data["gender"])  # Female == 0,  Male == 1
    data["smoking_history"] = le.fit_transform(
        data["smoking_history"])  # 0 == No info, 1 == current, 2 == former, 3 == never

    # Split the data
    X = data.drop("diabetes", axis=1)
    y = data["diabetes"]
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    # Train the model on Logistic Regression
    model = LogisticRegression(max_iter=1000)
    model.fit(X_train, y_train)
    joblib.dump(model, 'diabetes_model.joblib')


build_heart_disease_model()
build_lung_disease_model()
build_diabetes_model()