#read files
data1<-read.csv("data.csv")
data1[is.na(data1)] <- 0
pca.dat<-data1[c(4:10)]
pca.c<-princomp(pca.dat)
summary(pca.c, Cor)
#Comp.1    Comp.2    Comp.3    Comp.4     Comp.5    Comp.6     Comp.7
#Standard deviation     1.7975767 0.9896331 0.9697439 0.8951678 0.76690642 0.5161894 0.43933878
#Proportion of Variance 0.4616117 0.1399105 0.1343433 0.1144751 0.08402078 0.0380645 0.02757408
#Cumulative Proportion  0.4616117 0.6015222 0.7358656 0.8503406 0.93436142 0.9724259 1.00000000
write.csv(data1, 'medicalConverted.csv')
data1<-data1[c(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)]
#linear regression
index <- sample(1:nrow(data1),round(0.80*nrow(data1)))
train <- data1[index,]
test <- data1[-index,]
lmMod <- lm(Rate~Loc+Age+ CombInnOonIndividualMOOP +DedCombInnOonIndividual+DedInnTier1Individual+DedOutOfNetIndividual+InnTier1IndividualMOOP+OutOfNetIndividualMOOP +MetalLevel, data=train)
summary(lmMod)
predLm <- predict(lmMod,test)

actual<- data.frame(cbind(actuals=test$Rate, predicteds = predLm))
#min_max_accuracy
min_max_accuracy <- mean(apply(actual, 1, min) / apply(actual, 1, max)) 
print(min_max_accuracy)
#[1] 0.8136303
#mean-square accuracy
lmAcc<-sum(predLm-test$Rate)^2/nrow(test)
print(lmAcc)
#[1] 12.56
head(actual)
#actuals predicteds
#4447877   156.45   141.7654
#11952685  673.95   702.7647
#5048799   288.51   366.3031
#11262997  180.22   188.9435
#2633533   357.24   294.0902
#12062191  414.98   344.1626


library(neuralnet)
#convert categorical attributes to numeric
m <- model.matrix( 
  ~ Rate + Loc + Age + DedCombInnOonIndividual + CombInnOonIndividualMOOP + DedOutOfNetIndividual + DedInnTier1Individual + OutOfNetIndividualMOOP
  +InnTier1IndividualMOOP+MetalLevel, 
  data = train
)

##scale the value for entire database
maxium <- apply(data1, 2, max) 
minium <- apply(data1, 2, min)
scaled <- as.data.frame(scale(data1, 
                              center = minium, scale = maxium - minium))

##build model for neuron network visualization.
n <- names(train)
formulaN <- as.formula(paste("Rate ~", paste(n[!n %in% "Rate"], collapse = "+ ")))
sunetwork<- neuralnet(formulaN,data=train,hidden=c(100, 60),threshold=0.1, linear.output=T)
plot(sunetwork)

#predict using compute method. 
prNet <- compute(sunetwork,test[,1:9])
#apply the wight back .
prNetW<- prNet$net.result*(max(data1$Rate)-min(data1$Rate))+min(data1$Rate)
testR <- (test$Rate)*(max(data1$Rate)-min(data1$Rate))+min(data1$Rate)
#accuracy use mean square
MSEacc <- sum((testR - prNetW)^2)/nrow(test)

print(MSEacc)
#[1] 8.0116
