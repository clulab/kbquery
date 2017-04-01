## KBQuery Server

This is a public code repository of the Computational Language Understanding (CLU) Lab
led by [Mihai Surdeanu](http://surdeanu.info/mihai/) at the
[University of Arizona](http://www.arizona.edu).

Author: [Tom Hicks](https://github.com/hickst), based on a project by
[Gus Hahn-Powell](https://github.com/myedibleenso)

The KBQuery Server is an `Akka`-based microserver which provides data from knowledge bases
of the CLU Lab [Bioresources](https://github.com/clulab/bioresources) project. This is a
specialized HTTP server which provides only a few specific queries, relevant to
researchers in the CLU Lab and our collaborators.


## Installation

This project requires Java 1.8 and `sbt` (the Scala Built Tool) to build. You can run
the server application from the command line using `sbt` or build a standalone JAR
file and run that from the command line using Java 1.8.

To build a standalone JAR file in the target/scala-2.11 subdirectory:

```
   > sbt assembly
```

To run the standalone JAR file using Java 1.8:

```
   > java -jar kbquery.jar
```

To run from the command line with `sbt`:

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


## Database

The KBQuery Server application uses [HyperSQL](http://hsqldb.org/) (aka HSQLDB)
as the database and [Slick](http://slick.lightbend.com/) as the database
access library. By default, the server looks for HSQLDB database files
with names `kbqdb.*` in the directory in which the application was started.


### Building a new Database

To build a new database, you must use `sbt` to build and run a different main
class; the `KBLoader`. To run the `KBLoader` class from the command line with `sbt`:

```
   > sbt 'run-main org.clulab.kbquery.load.KBLoader'

```

By default, the `KBLoader` program expects its `Bioresources` input KB files to
be located in a subdirectory of the startup directory named `KBs`. The name of
the created database is, by default, `kbqdb` and HSQLDB database files with
this prefix are generated into the startup directory. The `KBLoader` and
database parameters can be changed in the `application.conf` configuration file.

**NOTE:** If you intend to rebuild the database, you must first move or delete
the existing HSQLDB files (the `kbqdb.*` files).

## Licenses

KBQuery Server is licensed under the Apache License Version 2.0.

A copy of the [HSQLDB license](http://hsqldb.org/web/hsqlLicense.html) is provided
in the LICENSES subdirectory.

A copy of the [Slick license](https://github.com/slick/slick/blob/master/LICENSE.txt)
is provided in the LICENSES subdirectory.

(c) Copyright by The University of Arizona, 2017. All rights reserved.
