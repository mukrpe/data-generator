import Dependencies._

name := "SketchBench"

lazy val commonSettings = Seq(
  organization := "com.sketchbench",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.12",
  test in assembly := {},
  logBuffered in test := false,
  parallelExecution in Test := false,
  scalacOptions ++= Seq("-Xmax-classfile-name", "78")
)

lazy val root = (project in file(".")).
  settings(commonSettings).
  aggregate(datasender, util, commons)

lazy val commons = (project in file("tools/commons")).
  settings(commonSettings,
    name := "Commons",
    libraryDependencies ++= pureConfig,
    libraryDependencies ++= logging,
    libraryDependencies ++= csv,
    libraryDependencies ++= testUtils
  )

lazy val datasender = (project in file("tools/datasender")).
  settings(commonSettings,
    name := "DataSender",
    mainClass in assembly := Some("com.sketchbench.ingestion.datasender.Main"),
    libraryDependencies ++= kafkaClients,
    libraryDependencies ++= slick
  ).
  dependsOn(util, commons % "test->test;compile->compile")


lazy val util = (project in file("tools/util")).
  settings(commonSettings,
    name := "Util",
    mainClass in(Compile, run) := Some("com.sketchbench.ingestion.util.Main"),
    libraryDependencies ++= kafka,
    libraryDependencies ++= json,
    libraryDependencies ++= scopt,
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.28"
  ).
  dependsOn(commons % "test->test;compile->compile")

