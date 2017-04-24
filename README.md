How to perform this project. 
data set come form kaggle https://www.kaggle.com/hhs/health-insurance-marketplace
-------------------------------------
MapReduce:contains 2 mapreduce java program. rateCleaning and PlanAttribtuesCleaning for Rate csv file and attribtue csv file. run on Hadoop-0.20.2 fully distributed mode. use RateCleaning.jar to process rate.csv; use PlanAttributesCleaning to process planAttributes.csv file. 
-------------------------------------
Visualization: contains 3 mapreduce for data visualization. run on hadoop 0.20.2 fully distributed mode. 
-------------------------------------
R: contains datacleasing.R, and annForMedical.R. Run on Rstudio with R-3.3.3. datacleasing.R to process file from MapReduce. use AnnForMedical.R to process two csv files from datacleasing.R. 
-------------------------------------
Python: includes 4 parts of data analysis and virtualization-data retrieve, data clean, data process and data virtualization.
Run on Anaconda with sklearn, neupy, numpy and pandas machine learning and artificial neural network libraries. implmenting a conjugategradient algorithm model contains 3 layers- input layer with 9 parameters(weights), hidden layer with 30 neutrons to handle input data, output layer to output rate price. The model divide train data and test data eight to two, using optimizer sigmoid and linearsearch algorithm to optimize the weights for predictions. 
