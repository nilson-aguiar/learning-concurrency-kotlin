# Demo project for learning concurrency with Kotlin 

I've created this repo as a learning opportunity to try/learn different approaches to do requests in batch as fast as possible keeping the code simple and readable.

## Main idea

The idea is to simulate a scenario where an application has to do multiple HTTP requests to an specific API retrieving the result as fast as possible

## Approaches

These options below will be used for this first version

* Sequential blocking requests
* Parallel requests
  * with CompletableFuture
  * with ParallelStream
  * with WebFlux/Reactor
  * with CoRoutines


## Metrics

Collect those metrics to have a better understanding of the application results

* Measure application execution time
* Identify CPU/Memory utilization

## Structure

The repo contain two modules, the `webapp` that should always be running, so the `batch-requester` module can crete the requests.

### webapp

This module is a simple SpringBoot application using webflux to allocate a low amount of resources.

There are two endpoints:

* `/millis` -> that will respond with a random delay between 100ms to 500ms
* `/seconds` -> that will respond with a random delay between 1s to 5s

Both endpoints receive an optional RequestParam that will be included in the String used in its response so it can be used to tracked which request has been received.

### batch-requester

This module is using OpenFeign to create the http client to realize the requests to the `webapp`.

Even though, its a SpringBoot application, it is not going to start as a web application and the application will stop after the requests are done

Also to be able to measure the resources usage there is a `wait for input` before the application starts