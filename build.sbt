name := "kbquery"

version := "0.14.0"

scalaVersion := "2.11.8"

// options for forked jvm
javaOptions += "-Xmx3G"

// forward sbt's stdin to forked process
connectInput in run := true

// don't show output prefix
outputStrategy := Some(StdoutOutput)

organization  := "clulab"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

//logLevel := Level.Info

libraryDependencies ++= {
  val akkaV = "2.4.17"
  val akkaHTTPV = "10.0.5"
  val json4sV = "3.5.0"
  val scalikeV = "2.5.1"

  Seq(
    "com.typesafe"                 %  "config"                             % "1.3.0",
    "org.json4s"                  %%  "json4s-native"                      % json4sV,
    "org.json4s"                  %%  "json4s-jackson"                     % json4sV,
    "de.heikoseeberger"           %%  "akka-http-json4s"                   % "1.14.0",

    // akka
    "com.typesafe.akka"           %%  "akka-actor"                         % akkaV,
    "com.typesafe.akka"           %%  "akka-stream"                        % akkaV,
    "com.typesafe.akka"           %%  "akka-slf4j"                         % akkaV,

    // database
    "mysql"                        %  "mysql-connector-java"               % "5.1.38",
    "org.scalikejdbc"             %%  "scalikejdbc"                        % scalikeV,
    "org.scalikejdbc"             %%  "scalikejdbc-config"                 % scalikeV,

    // akka-http
    "com.typesafe.akka"           %%  "akka-http-core"                     % akkaHTTPV,
    "com.typesafe.akka"           %%  "akka-http"                          % akkaHTTPV,
    "com.typesafe.akka"           %%  "akka-http-testkit"                  % akkaHTTPV,
    "com.typesafe.akka"           %%  "akka-http-xml"                      % akkaHTTPV,

    // testing
    "org.scalatest"               %%  "scalatest"                          % "2.2.6"   % "test",
    "org.scalikejdbc"             %%  "scalikejdbc-test"                   % "2.5.1"   % "test",
    "com.typesafe.akka"           %%  "akka-testkit"                       % akkaV     % "test",
    "com.typesafe.akka"           %%  "akka-http-testkit"                  % akkaV     % "test",

    // logging
    "ch.qos.logback"               %  "logback-classic"                    % "1.1.7",
    "com.typesafe.scala-logging"  %%  "scala-logging"                      % "3.4.0"
  )
}

// main class is the server and not the KB loader
mainClass in assembly := Some("org.clulab.kbquery.KBQuery")

// skip the tests when assembling the FAT JAR
test in assembly := {}

assemblyJarName := { s"kbquery.jar" }

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter { x => x.data.getName.matches("sbt.*") || x.data.getName.matches(".*macros.*") }
}
