name := "Elements"
version := "0.0.1"
scalaVersion := "2.13.2"
description := "A bot for the elements RPG server."

developers := List(
  Developer(
    id = "xaanit",
    name = "Jacob Frazier",
    email = "shadowjacob1@gmail.com",
    url = new URL("https://www.xaan.it")
  )
)

resolvers ++= Seq(Resolver.JCenterRepository, "twitter-repo" at "http://maven.twttr.com")

libraryDependencies ++= Seq(
  "com.novocode"        % "junit-interface" % "0.11" % "test", // For sbt junit testing.
  "junit"               % "junit"           % "4.13" % "test", // For testing
  "it.xaan"             % "random-core"     % "1.0.2", // For the helpful annotation
  "net.dv8tion"         % "JDA"             % "4.1.1_161", // For Discord stuff
  "com.typesafe.slick" %% "slick"           % "3.3.2", // For accessing databases
  "org.postgresql"      % "postgresql"      % "42.2.12", // For accessing postgres
  "com.beachape"       %% "enumeratum"      % "1.6.1", // For enums
  "org.reflections"     % "reflections"     % "0.9.12", // For nice reflections
  "org.scala-lang"      % "scala-compiler"  % scalaVersion.value, // For eval
  "org.scala-lang"      % "scala-reflect"   % "2.11.7", // For eval
  "com.typesafe"        % "config"          % "1.4.0" // For config
)

testOptions += Tests.Argument(
  TestFrameworks.JUnit,
  "-q",
  "-v",
  "-s",
  "--summary=2"
)
