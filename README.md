# AlgoStore.Algo.Archetype
Java maven Algo Archetype

Generating an algo
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

This will create a java maven project for you in myalgo folder. 
Please go inside it. 
cd myalgo

In order to make your algo to work please create a file called hft-client.properties 
with the following content in myalgo/src/main/resources
```
HFT_API_BASE_PATH=https://hft-service-dev.lykkex.net
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
In order to verify is it trading you may start it with:
```
java -jar myalgo/target/myalgo-1.0-SNAPSHOT-jar-with-dependencies.jar
```
If you see an output like 
```
------------Check wallet!!!------------
------------Buy some coins on the market price!------------
------------Selling XXX coins with some profit on YYY price!!!------------
```
Then your algo is working and you are ready to upload it in algostore.



