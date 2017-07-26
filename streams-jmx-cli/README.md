# streamsjmx

IBM InfoSphere Streams <strong>streamtool</strong>-like command line tool to interact with remote InfoSphere Streams domain and instances over JMX.

## Build

The build of this command requires running on a host with IBM InfoSphere Streams 4.x installed and $STREAMS_INSTALL set.

To build the package:

```
mvn package
```

## Run

streamsjmx is command based.  Over time it will evolve to add more and more commands.
To get a list of current commands and command syntax:

```
java -jar target/executable-streamsjmx-4.0.0.0-SNAPSHOT.jar help
```
