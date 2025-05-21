# DGB - Data Grid Benchmark 
In a project using [Red Hat Data Grid](https://www.redhat.com/en/technologies/jboss-middleware/data-grid) we realized that in a cache with hundreds of millions of entry a specific query with xxx the query engine is not performing well. So we have asked the engineering of Infinispan an RFI to optimize that type of queries.
This is the project to benchmark the RFI.


## Build

`./mvnw clean package`

## Run

`./mvnw quarkus:run`

## Invocations

Adjust `src/main/resources/load.json` to your requirements then invoke:

`curl --json @src/main/resources/load.json http://127.0.0.1:8080/benchmark/load`
