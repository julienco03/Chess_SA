ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

name := "ui"

lazy val commonDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  "com.google.inject" % "guice" % "5.1.0",
  ("net.codingwell" %% "scala-guice" % "5.1.0").cross(CrossVersion.for3Use2_13)
)

lazy val ui = project
  .in(file("."))
  .settings(
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(controller)
