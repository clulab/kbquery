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

To run the server from the standalone JAR file using Java 1.8:

```
   > java -jar kbquery.jar                  # use server defaults (localhost:8888)
   OR
   > java -jar kbquery.jar --port 8080      # specify an alternate server port
```

To run the server from the command line with `sbt`:

```
   > sbt run                                # use server defaults (localhost:8888)
   OR
   > sbt 'run --port 8080'                  # specify an alternate server port
```

Run Options:

```
Usage: kbquery [server|loader] [--host <hostname>]  [--port <port#>]

 server               Run the standalone KB server (this is the default)
 loader               Load the SQL database from local knowledge base files
 -h, --host           Hostname for server (defaults to 'localhost', not used by loader)
 -p, --port           Port number to run server on (defaults to 8888, not used by loader)
```


## Database

The KBQuery Server application uses a [MySQL](http://mysql.org/) installation for the
database and [ScalikeJDBC](http://scalikejdbc.org/) as the database access library.


### Building a new Database

To build and load a new database, you must use specify the `loader` option to the
KBQuery application.

**NOTE:** If you choose to re-run the `loader` program to reload the database,
**all existing tables and contents will be deleted**.

To run the `loader` from the standalone JAR file using Java 1.8:

```
   > java -jar kbquery.jar loader
```

To run the `loader` from the command line with `sbt`:

```
   > sbt 'run-main org.clulab.kbquery.KBQuery loader'

```

By default, the `loader` program expects its `Bioresources` input KB files to
be located in a subdirectory of the startup directory named `KBs`. (__Note:__ the
subdirectory `KBs` can simply be a symbolic link to the `Bioresources` directory
`bioresources/src/main/resources/org/clulab/reach/kb`).

The name of the created database is, by default, `kbqdb`. This and other `server`,
`loader`, and database parameters can be changed in the `application.conf`
configuration file.

**NOTE:** If you choose to re-run the `loader` program to reload the database,
**all existing tables and contents will be deleted**.


### Saving the Database

Once the MySQL database has been loaded, it can be backed up, moved to another
MySQL server, and/or reloaded very quickly using the MySQL Dump utility.
A utility shell script to dump the `kbqdb` database is located in the
`kbquery/src/main/resources/sql` subdirectory. This directory also contains an
SQL script to initially create the `kbqdb` database and its dedicated user.

**NOTE:** As, configured, the project's database user has no real security and
**this server should not be exposed on a public port or in an unsafe environment**.


## Licenses

KBQuery Server is licensed under the Apache License, Version 2.0.

ScalikeJDBC is also licensed under the Apache License, Version 2.0.

(c) Copyright by The University of Arizona, 2017. All rights reserved.
