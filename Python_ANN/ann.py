#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Apr 23 18:18:33 2017

@author: dongqi
"""

from sklearn import datasets
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from neupy import environment
from neupy import algorithms, layers
from neupy import plots
from neupy.estimators import rmsle


dataset = datasets.load_boston()
data, target = dataset.data, dataset.target


data_scaler = preprocessing.MinMaxScaler()
target_scaler = preprocessing.MinMaxScaler()


data = data_scaler.fit_transform(data)
target = target_scaler.fit_transform(target)


environment.reproducible()

x_train, x_test, y_train, y_test = train_test_split(
    data, target, train_size=0.85
)


cgnet = algorithms.ConjugateGradient(
    connection=[
        layers.Input(13),
        layers.Sigmoid(50),
        layers.Sigmoid(1),
    ],
    search_method='golden',
    show_epoch=25,
    verbose=True,
    addons=[algorithms.LinearSearch],
)

cgnet.train(x_train, y_train, x_test, y_test, epochs=100)
plots.error_plot(cgnet)

y_predict = cgnet.predict(x_test).round(1)

real = target_scaler.inverse_transform(y_test)
predicted = target_scaler.inverse_transform(y_predict)

error = rmsle(target_scaler.inverse_transform(y_test),
              target_scaler.inverse_transform(y_predict))

print(len(real))
print(len(predicted))