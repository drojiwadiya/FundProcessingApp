# FundProcessingApp
Generate funds outperformance csv report 

Build application & run tests
----------------------
mvn clean install


Execute Application
----------------------
java -jar FundProcessingApp-0.0.1-SNAPSHOT.jar  -f  \
{absolutePath}/fund.csv  \
 -bmr {absolutePath}/BenchReturnSeries.csv  \
 -fr  {absolutePath}/FundReturnSeries.csv  \
-o {absolutePath}/monthlyOutperformance.csv


Assumptions
--------------------
BenchReturnSeries.csv will have dates in yyyy-MM-dd
FundReturnSeries.csv will have dates in dd/MM/yyyy


More Details
------------------
App.java has the starting point of the application

