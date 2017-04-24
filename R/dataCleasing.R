#1. dental.csv cleansing
##read and process denal data.
dental<-read.csv('Attributes/dental.csv', header = T, sep= ',', stringsAsFactors = F)


## add label for each column
names(dental)<-c("PlanId", "CombInnOonIndividualMOOP", "DedCombInnOonIndividual", 
                 "DedInnTier1Coinsurance", "DedInnTier1Individual", "DedOutOfNetIndividual", 
                 "InnTier1IndividualMOOP", "OutOfNetIndividualMOOP", "MetalLevel", "IssurActualValue")

## replace '$' and ',', and convert to numeric value
dental$DedCombInnOonIndividual<- gsub(",","", dental$DedCombInnOonIndividual)
dental$CombInnOonIndividualMOOP<- gsub(",","", dental$CombInnOonIndividualMOOP)
dental$DedInnTier1Coinsurance<- gsub(",","", dental$DedInnTier1Coinsurance)
dental$DedOutOfNetIndividual<- gsub(",","", dental$DedOutOfNetIndividual)
dental$InnTier1IndividualMOOP<- gsub(",","", dental$InnTier1IndividualMOOP)
dental$OutOfNetIndividualMOOP<- gsub(",","", dental$OutOfNetIndividualMOOP)
dental$DedInnTier1Individual<- gsub(",","", dental$DedInnTier1Individual)
dental$DedInnTier1Individual<- gsub("\\$","", dental$DedInnTier1Individual)
dental$CombInnOonIndividualMOOP<- gsub("\\$","", dental$CombInnOonIndividualMOOP)
dental$DedInnTier1Coinsurance<- gsub("\\$","", dental$DedInnTier1Coinsurance)
dental$DedOutOfNetIndividual<- gsub("\\$","", dental$DedOutOfNetIndividual)
dental$InnTier1IndividualMOOP<- gsub("\\$","", dental$InnTier1IndividualMOOP)
dental$OutOfNetIndividualMOOP<- gsub("\\$","", dental$OutOfNetIndividualMOOP)
dental$DedCombInnOonIndividual<- gsub("\\$","", dental$DedCombInnOonIndividual)
dental$DedCombInnOonIndividual<- as.numeric(dental$DedCombInnOonIndividual)
dental$CombInnOonIndividualMOOP<- as.numeric(dental$CombInnOonIndividualMOOP)
dental$DedInnTier1Coinsurance<- as.numeric(dental$DedInnTier1Coinsurance)
dental$DedOutOfNetIndividual<- as.numeric(dental$DedOutOfNetIndividual)
dental$DedInnTier1Individual<- as.numeric(dental$DedInnTier1Individual)
dental$OutOfNetIndividualMOOP<- as.numeric(dental$OutOfNetIndividualMOOP)
dental$InnTier1IndividualMOOP<- as.numeric(dental$InnTier1IndividualMOOP)

##remove old coinsurance value, which doesn't contains any value for a dental plan;
##and issuractualvalue, which is used for tax information. 
dental<dental[c(1, 2, 3, 5, 6, 7, 8, 9)]
summary(dental)
##write to directory for future reference. 
write.csv(data, "dentalDataFinal")

##2. rate.csv cleansing
rate<-read.csv('Ratess/rates.csv', header = T, sep= ',', stringsAsFactors = F)
names(rate)<-c("BusinessYear", "Loc", "PlanId", "Age", "Married", "Dependents", "Rate")

##consider age as numeric file
rate$Age<- gsub("0-20","20",rate$Age)
rate$Age<- gsub("65 and over","65",rate$Age)
#get the state name
rate$Loc<-substr(rate$Loc, 1, 2)

#3. join each file rate and dental together
library(dply)
data<-inner_join(rate, dental, by="PlanId")
summary(data)
##remove married and dependents because only individual rate is considered here.

##remove empty age rows to exclude family option dental plan
data<-subset(data, !is.na(data$Age))

data<-data[c(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)]
##get unique data and save to directory. #1,509,057 observations of 10 variables
data<unique(data)
write.csv(data, "dentalDataFinal.csv")

#4. process medical csv, drug csv, and combined csv files
drug<-read.csv('Attributes/drug.csv', header = T, sep= ',', stringsAsFactors = F)
medical<-read.csv('Attributes/medical.csv', header = T, sep= ',', stringsAsFactors = F)
combined<-read.csv('Attributes/combined.csv', header = T, sep= ',', stringsAsFactors = F)
names(drug)<-c("PlanId", "CombInnOonIndividualMOOP", "DedCombInnOonIndividual", 
                 "DedInnTier1Coinsurance", "DedInnTier1Individual", "DedOutOfNetIndividual", 
                 "InnTier1IndividualMOOP", "OutOfNetIndividualMOOP", "MetalLevel")
names(medical)<-c("PlanId", "CombInnOonIndividualMOOP", "DedCombInnOonIndividual", 
                 "DedInnTier1Coinsurance", "DedInnTier1Individual", "DedOutOfNetIndividual", 
                 "InnTier1IndividualMOOP", "OutOfNetIndividualMOOP", "MetalLevel")
names(combined)<-c("PlanId", "CombInnOonIndividualMOOP", "DedCombInnOonIndividual", 
                 "DedInnTier1Coinsurance", "DedInnTier1Individual", "DedOutOfNetIndividual", 
                 "InnTier1IndividualMOOP", "OutOfNetIndividualMOOP", "MetalLevel")
#join these files together
allmedical<-cbind(drug, medical, combined)
allmedical$DedCombInnOonIndividual<- gsub(",","", allmedical$DedCombInnOonIndividual)
allmedical$CombInnOonIndividualMOOP<- gsub(",","", allmedical$CombInnOonIndividualMOOP)
allmedical$DedInnTier1Coinsurance<- gsub(",","", allmedical$DedInnTier1Coinsurance)
allmedical$DedOutOfNetIndividual<- gsub(",","", allmedical$DedOutOfNetIndividual)
allmedical$InnTier1IndividualMOOP<- gsub(",","", allmedical$InnTier1IndividualMOOP)
allmedical$OutOfNetIndividualMOOP<- gsub(",","", allmedical$OutOfNetIndividualMOOP)
allmedical$DedInnTier1Individual<- gsub(",","", allmedical$DedInnTier1Individual)
allmedical$DedInnTier1Individual<- gsub("\\$","", allmedical$DedInnTier1Individual)
allmedical$CombInnOonIndividualMOOP<- gsub("\\$","", allmedical$CombInnOonIndividualMOOP)
allmedical$DedInnTier1Coinsurance<- gsub("\\$","", allmedical$DedInnTier1Coinsurance)
allmedical$DedOutOfNetIndividual<- gsub("\\$","", allmedical$DedOutOfNetIndividual)
allmedical$InnTier1IndividualMOOP<- gsub("\\$","", allmedical$InnTier1IndividualMOOP)
allmedical$OutOfNetIndividualMOOP<- gsub("\\$","", allmedical$OutOfNetIndividualMOOP)
allmedical$DedCombInnOonIndividual<- gsub("\\$","", allmedical$DedCombInnOonIndividual)
allmedical$DedCombInnOonIndividual<- as.numeric(allmedical$DedCombInnOonIndividual)
allmedical$CombInnOonIndividualMOOP<- as.numeric(allmedical$CombInnOonIndividualMOOP)
allmedical$DedInnTier1Coinsurance<- as.numeric(allmedical$DedInnTier1Coinsurance)
allmedical$DedOutOfNetIndividual<- as.numeric(allmedical$DedOutOfNetIndividual)
allmedical$DedInnTier1Individual<- as.numeric(allmedical$DedInnTier1Individual)
allmedical$OutOfNetIndividualMOOP<- as.numeric(allmedical$OutOfNetIndividualMOOP)
allmedical$InnTier1IndividualMOOP<- as.numeric(allmedical$InnTier1IndividualMOOP)

##5. join rate and all medical plan
data1<-inner_join(rate, dental, by="PlanId")

data1<-data[c(1, 3, 4, 5, 6, 7, 8, 9, 10,11)]
data1[is.na(data1)] <- 0
data1<-unique(data1)
#12,000,000+
write.csv(data1, "data.csv")


