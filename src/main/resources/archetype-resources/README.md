AlgoStore archetype 

# To generate an archetype 

```
mvn archetype:create-from-project

```

# To install the archetype 

```
cd target\generated-sources\archetype; mvn clean install
```


# To generate a a new java application from the archetype 

```
mvn archetype:generate -DarchetypeCatalog=local
```