#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr 22 18:14:11 2017

@author: dongqi
"""


import numpy
import pandas
from keras.models import Sequential
from keras.layers import Dense
from keras.wrappers.scikit_learn import KerasClassifier
from keras.utils import np_utils
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from sklearn.preprocessing import LabelEncoder
from sklearn.pipeline import Pipeline

inputfile = "/Users/dongqi/Desktop/BigData/data.csv"
outputfile = "/Users/dongqi/Desktop/BigData/sample.csv"

data = pandas.read_csv(inputfile, header=None)
data.drop(data.columns[[0,6]], axis=1, inplace=True)
data.columns = ["Loc","Age", "Rate", "CombInnOonIndividualMOOP",  "DedCombInnOonIndividual",  "DedInnTier1Individual", "DedOutOfNetIndividual",  "InnTier1IndividualMOOP",  "OutOfNetIndividualMOOP", "MetalLevel"]
print(data.head(5))

data.to_csv(outputfile)