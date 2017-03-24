[![Build Status](https://travis-ci.org/myedibleenso/kbquery.svg?branch=master)](https://travis-ci.org/myedibleenso/kbquery)

Current version: 2.9

# kbquery

## What is it?

An `akka-http` server exposing a REST API for querying biological KB information
from the CLU Lab [`Bioresources`](https://github.com/clulab/bioresources) library.

## Requirements
1. [Java 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
2. [`sbt`](http://www.scala-sbt.org/download.html)

## How is this useful?

## Running `kbquery`

```bash
git clone https://github.com/clulab/kbquery.git
```

Fire up the server.

```bash
cd kbquery
sbt "runMain KBQueryServer"
```

By default, the server will run on port `8888` and `localhost`, though you can start the server using a different port and host:

```bash
sbt "runMain KBQueryServer --host <your favorite host here> --port <your favorite port here>"
```
## Logging

A server log is written to `.kbquery.log` in the home directory of the user who launches the server.

# Communicating with the server

_NOTE: Once the server has started, a summary of the services currently available (including links to demos) can be found at the following url: `http://<your host name here>:<your port here>`


### An example using `cURL`

To see it in action, you can try to `POST` `json` using `cuRL`.  The text to parse should be given as the value of the `json`'s `text` field:
```bash
curl -H "Content-Type: application/json" -X POST -d '{"text": "My name is Inigo Montoya. You killed my father. Prepare to die."}' http://localhost:8888/annotate
```

```json
{
}
```

## `json` schema for responses

Response schema can be found at [`src/main/resources/json/schema`](src/main/resources/json/schema)

Examples of each can be found at [`src/main/resources/json/examples`](src/main/resources/json/examples)

# Tidbits

## Shutting down the server

You can shut down the server by posting anything to `/shutdown`

## Checking on the server's status

send a `GET` to `/status`

# Creating a fat `jar` for distribution:

Cloning the project and running `sbt assembly` ensures the latest `jar`.
