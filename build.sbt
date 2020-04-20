name := "CuddlegangBot"
version := "0.0.1"
scalaVersion := "2.13.1"
autoScalaLibrary := false
description := "A bot for the cuddlegang RPG server."

developers := List(
  Developer(id = "xaanit",
    name = "Jacob Frazier",
    email = "shadowjacob1@gmail.com",
    url = new URL("https://www.xaan.it"))
)

libraryDependencies ++= Seq(
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "junit" % "junit" % "4.13" % "test",
  "com.google.code.findbugs" % "jsr305" % "3.0.2",
  "it.xaan" % "random-core" % "1.0.2",
  "org.jdbi" % "jdbi3-postgres" % "3.12.2"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v", "-s", "--summary=2")
