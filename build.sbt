name := "CuddlegangBot"
version := "0.0.1"
scalaVersion := "2.13.1"
autoScalaLibrary := false
description := "A bot for the elements RPG server."

developers := List(
  Developer(
    id = "xaanit",
    name = "Jacob Frazier",
    email = "shadowjacob1@gmail.com",
    url = new URL("https://www.xaan.it")
  )
)

resolvers ++= Seq(Resolver.JCenterRepository)

libraryDependencies ++= Seq(
  "com.novocode"             % "junit-interface" % "0.11" % "test", // For sbt junit testing.
  "junit"                    % "junit"           % "4.13" % "test", // For testing
  "com.google.code.findbugs" % "jsr305"          % "3.0.2", // For the helpful annotations
  "it.xaan"                  % "random-core"     % "1.0.2", // For the helpful annotation
  "net.katsstuff"           %% "ackcord"         % "0.16.1", // For Discord stuff
  "org.scalikejdbc"         %% "scalikejdbc"     % "3.4.2", // For accessing databases
  "org.postgresql"           % "postgresql"      % "42.2.12", // For accessing postgres
  "com.beachape"            %% "enumeratum"      % "1.6.1", // For enums
  "org.reflections"          % "reflections"     % "0.9.12" // For nice reflections
)

testOptions += Tests.Argument(
  TestFrameworks.JUnit,
  "-q",
  "-v",
  "-s",
  "--summary=2"
)
