ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

name := "persistence"

lazy val commonDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  "com.google.inject" % "guice" % "5.1.0",
  ("net.codingwell" %% "scala-guice" % "5.1.0").cross(CrossVersion.for3Use2_13),
  ("com.typesafe.play" %% "play-json" % "2.9.3").cross(CrossVersion.for3Use2_13),
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-http" % "10.5.3"
)

lazy val persistence = project
  .in(file("."))
  .settings(
    libraryDependencies ++= commonDependencies
  )
