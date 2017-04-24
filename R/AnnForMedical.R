#read files
read.csv("data.csv")
data1[is.na(data1)] <- 0
pca.dat<-data1[c(4:10)]
pca.c<-princomp(pca.dat)
summary(pca.c, Cor)
#Importance of components:
#                             Comp.1       Comp.2       Comp.3       Comp.4       Comp.5       Comp.6       Comp.7
#Standard deviation     9187.6701340 5214.7823531 3.427140e+03 2.473055e+03 1.613010e+03 1.269317e+03 1.468045e-01
#Proportion of Variance    0.6314508    0.2034235 8.786025e-02 4.575053e-02 1.946268e-02 1.205226e-02 1.612158e-10
#Cumulative Proportion     0.6314508    0.8348743 9.227345e-01 9.684851e-01 9.879477e-01 1.000000e+00 1.000000e+00
write.csv(data1, 'medicalConverted.csv')

#linear regression
index <- sample(1:nrow(data1),round(0.8*nrow(data1)))
train <- data1[index,]
test <- data1[-index,]
lmMod <- lm(Rate~., data=train)
summary(lmMod)
predLm <- predict(lmMod,test)

actual<- data.frame(cbind(actuals=test$Rate, predicteds = predLm))
#min_max_accuracy
min_max_accuracy <- mean(apply(actual, 1, min) / apply(actual, 1, max)) 
#mean-square accuracy
lmAcc<-sum(predLm-test$Rate)^2/nrow(test)
print(lmAcc)
head(actual)
#actuals predicteds
#4447877   156.45   141.7654
#11952685  673.95   702.7647
#5048799   288.51   366.3031
#11262997  180.22   188.9435
#2633533   357.24   294.0902
#12062191  414.98   344.1626
print(min_max_accuracy)

library(neuralnet)
#convert categorical attributes to numeric
m <- model.matrix( 
  ~ Loc + Age + Rate + DedCombInnOonIndividual + CombInnOonIndividualMOOP + DedOutOfNetIndividual + DedInnTier1Individual + OutOfNetIndividualMOOP
  +InnTier1IndividualMOOP, 
  data = train
)

#scale the value for entire database
maxisum <- apply(m, 2, max) 
minsium <- apply(m, 2, min)
scaled <- as.data.frame(scale(m, 
                              center = minsium, scale = maxsium - minsium))

#build model for neuron network visualization.
n <- names(m)
formulaN <- as.formula(paste("Rate ~", paste(m[!m %in% "Rate"], collapse = " + ")))
network<- neuralnet(formulaN,data=m,hidden=c(5,3),linear.output=T)

#predict using compute method. 
prNet <- compute(network,test[,1:10])
#apply the wight back .
prNetW<- prNet$net.result*(max(data1$Rate)-min(data1$Rate))+min(data1$Rate)
testR <- (test_$Rate)*(max(data1$Rate)-min(data1$Rate))+min(data1$Rate)
#accuracy use mean square
MSEacc <- sum((testR - prNetW)^2)/nrow(test)

print(MSEacc)