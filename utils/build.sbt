ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

name := "utils"

lazy val commonDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalafx" %% "scalafx" % "18.0.1-R28",
  "com.google.inject" % "guice" % "5.1.0",
  ("net.codingwell" %% "scala-guice" % "5.1.0").cross(CrossVersion.for3Use2_13),
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-http" % "10.5.3"
)

lazy val utils = project
  .in(file("."))
  .settings(
    libraryDependencies ++= commonDependencies
  )
