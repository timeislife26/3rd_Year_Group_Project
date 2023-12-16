import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report
"""Colon Cancer"""
# Load the data
data = pd.read_csv('/content/drive/MyDrive/Dataset - ML Research/colon cancer.csv')

# Drop the "ID_REF" column, as it's not needed for modeling
data = data.drop("ID_REF", axis=1)

# Encode categorical variables
le = LabelEncoder()
data["Dukes Stage"] = le.fit_transform(data["Dukes Stage"])
data["Gender"] = le.fit_transform(data["Gender"])
data["Location"] = le.fit_transform(data["Location"])

# Split the data
X = data.drop("Colon_Cancer", axis=1)
y = data["Colon_Cancer"]
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Train the model on Logistic Regression
model = LogisticRegression(max_iter=1000)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate accuracy
accuracy = accuracy_score(y_test, y_pred)
print("Accuracy:", accuracy)

# Print a detailed classification report
print(classification_report(y_test, y_pred))

"""Heart Disease"""
# Load the data
data = pd.read_excel('/content/drive/MyDrive/Dataset - ML Research/heart disease.xlsx')

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

# Make predictions
y_pred = model.predict(X_test)

# Evaluate accuracy
accuracy = accuracy_score(y_test, y_pred)
print("Accuracy:", accuracy)

# Print a detailed classification report
print(classification_report(y_test, y_pred))

"""LUNG CANCER"""
# Load the data
data = pd.read_csv('/content/drive/MyDrive/Dataset - ML Research/lung cancer.csv')

# Encode categorical variables
categorical_columns = ["GENDER"]
for column in categorical_columns:
    le = LabelEncoder()
    data[column] = le.fit_transform(data[column])

# Split the data
X = data.drop("LUNG_CANCER", axis=1)
y = data["LUNG_CANCER"]
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train the model on Logistic Regression
model = LogisticRegression(max_iter=1000)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate accuracy
accuracy = accuracy_score(y_test, y_pred)
print("Accuracy:", accuracy)

# Print a detailed classification report
print(classification_report(y_test, y_pred))

"""Diabetes"""
# Load the data
data = pd.read_excel('/content/drive/MyDrive/Dataset - ML Research/diabetes.xlsx')

# Encode categorical variables
categorical_columns = ["gender", "smoking_history"]
for column in categorical_columns:
    le = LabelEncoder()
    data[column] = le.fit_transform(data[column])

# Drop irrelevant columns
data = data.drop(["ID"], axis=1)

# Split the data
X = data.drop("diabetes", axis=1)
y = data["diabetes"]
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train the model on Logistic Regression
model = LogisticRegression(max_iter=1000)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate accuracy
accuracy = accuracy_score(y_test, y_pred)
print("Accuracy:", accuracy)

# Print a detailed classification report
print(classification_report(y_test, y_pred))
