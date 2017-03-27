name := "kbquery"

version := "0.0.4"

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
  val slickV = "3.2.0"

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
    "org.hsqldb"                   %  "hsqldb"                             % "2.3.4",
    "com.typesafe.slick"          %%  "slick"                              % slickV,
    "com.typesafe.slick"          %%  "slick-hikaricp"                     % slickV,

    // akka-http
    "com.typesafe.akka"           %%  "akka-http-core"                     % akkaHTTPV,
    "com.typesafe.akka"           %%  "akka-http"                          % akkaHTTPV,
    "com.typesafe.akka"           %%  "akka-http-testkit"                  % akkaHTTPV,
    "com.typesafe.akka"           %%  "akka-http-xml"                      % akkaHTTPV,

    // testing
    "org.specs2"                  %%  "specs2-core"                        % "2.3.11"  % "test",
    "com.typesafe.akka"           %%  "akka-testkit"                       % akkaV     % "test",
    "org.scalatest"               %% "scalatest"                           % "2.2.6"   % "test",

    // logging
    "ch.qos.logback"               %  "logback-classic"                    % "1.1.7",
    "com.typesafe.scala-logging"  %%  "scala-logging"                      % "3.4.0"
  )
}

assemblyJarName := { s"kbquery.jar" }

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter { x => x.data.getName.matches("sbt.*") || x.data.getName.matches(".*macros.*") }
}
