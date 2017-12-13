# AlgoStore.Algo.Archetype
Java maven Algo Archetype

Generating an algo
In order to generate an algo you may your Lykke Algo java archetype 

```
mvn archetype:generate    \
-DarchetypeGroupId=com.lykke.algos    \
-DarchetypeArtifactId=algos-archetype    \
-DarchetypeVersion=1.0-SNAPSHOT  \
-DgroupId=com.algo  \
-DartifactId=myalgo \ 
-Dversion=1.0-SNAPSHOT  \
-DarchetypeCatalog=https://oss.sonatype.org/content/repositories/snapshots/
```

This will create a java maven project for you with groupId com.algo, artifact id myalgo in directory myalgo in your current folder. 
Please go inside and review the contentns.
```
cd myalgo
```
In order to make your algo to work please create a file called hft-client.properties 
with the following content in myalgo/src/main/resources
```
HFT_API_BASE_PATH=<LYKKE HFT API trading endpoint, e.g https://hft-service-dev.lykkex.net>
HFT_KEY=<YOUR HFT API client key>
WALLET_KEY=<YOUR WALLET KEY>
ASSET_PAIR=<THE ASSET PAIR YOU WANT TO TRADE, e.g BTCUSD> 
ASSET=<THE ASSET YOU WANT TO BUY AND SELL, e.g BTC>
VOLUME=<THE VOLUME YOU WANT TO BUY, e.g 0.001>
MARGIN=<THE MARGINE WITH WHICH YOU WANT TO SELL, e.g 1.1>
```
Once you are ready with that you may package your algo.
```
mvn clean package 
```
This will generate an executable jar with dependencies file in your target folder. 
```
target/myalgo-1.0-SNAPSHOT-jar-with-dependencies.jar
```
In order to verify it you may execute:
```
java -jar myalgo/target/myalgo-1.0-SNAPSHOT-jar-with-dependencies.jar
```
If you see an output like 
```
------------Check wallet!!!------------
------------Buy some coins on the market price!------------
------------Selling XXX coins with some profit on YYY price!!!------------
```
Then your algo is working and you are ready to deploy it in AlgoStore.
For the purpose upload your jar-with-dependencies.jar file there.



