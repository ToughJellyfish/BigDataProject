#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Apr 23 18:55:43 2017

@author: dongqi
"""

from sklearn import datasets
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from neupy import environment
from neupy import algorithms, layers
from neupy import plots
from neupy.estimators import rmsle
from neupy.estimators import mse
from neupy.estimators import rmse
import pandas as pd
from sklearn import metrics
from neupy import layers



inputfile = "/Users/dongqi/Desktop/BigData/sample.csv"
dataset = pd.read_csv(inputfile, header=None)
dataset = dataset.iloc[:,1:]
dataset.columns = ["Loc","Age","Rate", "CombInnOonIndividualMOOP",  "DedCombInnOonIndividual",  "DedInnTier1Individual", "DedOutOfNetIndividual",  "InnTier1IndividualMOOP",  "OutOfNetIndividualMOOP", "Metal"]
dataset = dataset.fillna(0)


print(dataset.head())
#print(dataset.Metal)

# data process
df = pd.DataFrame({"Metal":list(dataset.Metal)})
dataset["Metal"] = df["Metal"].astype('category')
df_metalLevel = pd.get_dummies(dataset["Metal"])
dataset["Metal"] = df_metalLevel


df = pd.DataFrame({"Loc":list(dataset.Loc)})
dataset["Loc"] = df["Loc"].astype('category')
df_loc = pd.get_dummies(dataset["Loc"])
dataset["Loc"] = df_loc

X = dataset.iloc[:,3:9].astype(float)
X["Age"] = dataset.Age
X["MetalLevel"] = dataset.Metal
X["Loc"] = dataset.Loc


print(X.head())
Y = dataset.iloc[:,2]
print(Y.head())



data, target = X, Y
data_scaler = preprocessing.MinMaxScaler()
target_scaler = preprocessing.MinMaxScaler()

data = data_scaler.fit_transform(data)
target = target_scaler.fit_transform(target)

environment.reproducible()

x_train, x_test, y_train, y_test = train_test_split(
    data, target, train_size=0.8
)

input_layer = layers.Input(9)
output_layer = layers.Sigmoid(1)

#sci-learn model create
cgnet = algorithms.ConjugateGradient(
    connection=[
        input_layer,
        layers.Sigmoid(30),
        output_layer,
    ],
    search_method='golden',
    show_epoch=25,
    verbose=True,
    addons=[algorithms.LinearSearch],
)
#print(help(cgnet))

cgnet.train(x_train, y_train, epochs=10)
y_predict = cgnet.predict(x_test).round(1)
plots.error_plot(cgnet)

#calculate erro and accuracy
real = target_scaler.inverse_transform(y_test)
y_predicted = target_scaler.inverse_transform(y_predict)

error1 = rmsle(real, y_predicted)
error2 = mse(real, y_predicted)
error3 = rmse(real, y_predicted)

#print(metrics.classification_report(y_test, y_predicted))
print(real)
print(y_predicted)
print(len(y_predicted))
print(len(real))
print(error1)
print(error2)
print(error3)

weight = output_layer.weight.get_value()
bias = output_layer.bias.get_value()
print(weight)
print(bias)