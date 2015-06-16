name := "foobal"
description := "Predicts the results of next week's soccer match!"
organization := "nl.jqno.foobal"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.6"
scalacOptions += "-deprecation"
mainClass := Some("nl.jqno.foobal.Main")

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.1",
  "org.joda" % "joda-convert" % "1.2",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "org.drools" % "drools-core" % "5.4.0.Final",
  "org.drools" % "drools-compiler" % "5.4.0.Final",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4",

  "junit" % "junit" % "4.10" % Test,
  "org.scalatest" %% "scalatest" % "2.2.0" % Test,
  "org.mockito" % "mockito-all" % "1.9.0" % Test,
  "nl.jqno.equalsverifier" % "equalsverifier" % "1.1.3" % Test
)

