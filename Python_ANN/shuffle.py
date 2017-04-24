#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Apr 23 14:00:33 2017

@author: dongqi
"""

import random
import sys
op=open("/Users/dongqi/Desktop/BigData/shufflesample.csv",'w+')
ip=open("/Users/dongqi/Desktop/BigData/sample.csv",'r')
data=ip.read()
data1=str(random.choices(data))
op.write(data1)
op.close()