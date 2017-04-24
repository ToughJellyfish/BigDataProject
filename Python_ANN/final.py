#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr 22 12:59:49 2017

@author: dongqi
"""
from keras.layers import Input, Embedding, LSTM, Dense
from keras.models import Model
import pandas as pd 
import numpy as np 
import matplotlib.pyplot as plt 
import seaborn as sns 
import math
import keras
from keras.models import Sequential
from keras.models import Model
from keras.layers import Input, Dense
from keras.layers.core import Dense, Activation 
from keras.callbacks import Callback
import matplotlib.animation as animation
from keras.optimizers import Adam 
from scipy.interpolate import lagrange
from keras import optimizers
from keras.layers import Input, Dense
from keras.models import Model
from keras.wrappers.scikit_learn import KerasClassifier
from keras.utils import np_utils
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from sklearn.preprocessing import LabelEncoder
from sklearn.pipeline import Pipeline



def ployinternp_column(s, n, k = 5):
    y = s[list(range(n - k, n)) + list(range(n + 1, n + 1 + k))]
    y = y[y.notnull()]
    return lagrange(y.index, list(y))(n)

inputfile = "/Users/dongqi/Desktop/BigData/train.csv"
outputfile = "/Users/dongqi/Desktop/BigData/aftersmallset.xls"

data = pd.read_csv(inputfile, header=None)
data = data.iloc[:,1:]
data.columns = ["Serial","BusinessYear","Age", "Married" , "Dependents","Rate", "CombInnOonIndividualMOOP",  "DedCombInnOonIndividual",  "DedInnTier1Individual", "DedOutOfNetIndividual",  "InnTier1IndividualMOOP",  "OutOfNetIndividualMOOP", "MetalLevel"]


data.fillna(0)

print(data.head())
print(data.dtypes)

#df = pd.DataFrame({"BussinessYear":list(data.BusinessYear)})
#data["BusinessYear"] = df["BussinessYear"].astype('category')
#df_year = pd.get_dummies(data["BusinessYear"])
#data["BusinessYear"] = df_year

#df = pd.DataFrame({"Loc":list(data.Loc)})
#data["Loc"] = df["Loc"].astype('category')
#df_loc = pd.get_dummies(data["Loc"])
#data["Loc"] = df_loc

#df = pd.DataFrame({"Age":list(data.Age)})
#data["Age"] = df["Age"].astype('category')
#df_age = pd.get_dummies(data["Age"])
#data["Age"] = df_age

df = pd.DataFrame({"Married":list(data.Married)})
data["Married"] = df["Married"].astype('category')
df_married = pd.get_dummies(data["Married"])
data["Married"] = df_married


df = pd.DataFrame({"Dependents":list(data.Dependents)})
data["Dependents"] = df["Dependents"].astype('category')
df_dependents = pd.get_dummies(data["Dependents"])
data["Dependents"] = df_dependents

#df = pd.DataFrame({"MetalLevel":list(data.MetalLevel)})
#data["MetalLevel"] = df["MetalLevel"].astype('category')
#df_metalLevel = pd.get_dummies(data["MetalLevel"])
#data["MetalLevel"] = df_metalLevel

print(data.dtypes)
X = data.iloc[:,2].astype(float)
#X["InnTier1IndividualMOOP"] = data["InnTier1IndividualMOOP"]
XV = X.values

#X["CombInnOonIndividualMOOP"] = data.CombInnOonIndividualMOOP
#X["DedCombInnOonIndividual"] = data.DedCombInnOonIndividual 
#X["DedInnTier1Individual"] = data.DedInnTier1Individual 
#X["DedOutOfNetIndividual"] = data.DedOutOfNetIndividual 
#X["InnTier1IndividualMOOP"] = data.InnTier1IndividualMOOP 
#X["OutOfNetIndividualMOOP"] = data.OutOfNetIndividualMOOP
#X["MetalLevel"] = data.MetalLevel
print(X.head())

Y = data.iloc[:,5]
YV = Y.values

model = Sequential() 
model.add(Dense(output_dim=5, input_dim=1)) 
model.add(Activation("relu")) 
model.add(Dense(output_dim=5)) 
model.add(Activation("relu")) 
model.add(Dense(output_dim=1)) 
#model.compile(loss='mean_squared_error', optimizer='sgd')


print(XV)
print(YV)
model.compile(loss='mean_squared_error', optimizer='sgd', metrics=['accuracy'])
model.fit(x=XV, y=YV, batch_size=10, epochs=10, verbose=1, validation_split=0.3)
model.predict(XV, batch_size=10, verbose=1)
model.evaluate(XV, YV, batch_size=10, verbose=1)
