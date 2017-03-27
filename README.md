## KB Query Server

This is a public code repository of the Computational Language Understanding (CLU) Lab led by [Mihai Surdeanu](http://surdeanu.info/mihai/) at [University of Arizona](http://www.arizona.edu).

Author: [Tom Hicks](https://github.com/hickst), based on a project by
[Gus Hahn-Powell](https://github.com/myedibleenso)

Purpose: An `Akka`-based microserver which provides data from knowledge bases of the
CLU Lab [Bioresources](https://github.com/clulab/bioresources) project. This is a
specialized HTTP server which provides only a few specific queries, relevant to
researchers in the CLU Lab and our collaborators.

## Installation

This project requires Java 1.8 and `sbt` (the Scala Built Tool)

To build a standalone JAR file in the target/scala-2.11 subdirectory:

```
   > sbt assembly
```

To run the standalone JAR file using Java 1.8:

```
   > java -jar kbquery.jar
```

To run from the command line with sbt:

```
   > sbt run                                # accepting defaults
   OR
   > sbt 'run --port 8080'                  # specify an alternate server port
```

Run Options:

```
Usage: kbquery [--host <hostname>]  [--port <port#>]

 -h, --host           Hostname for server (defaults to 'localhost')
 -p, --port           Port number to run server on (defaults to 8888)
```

## License

Licensed under Apache License Version 2.0.

(c) The University of Arizona, 2017