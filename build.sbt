import org.scoverage.coveralls.Imports.CoverallsKeys._

val scala3Version = "3.3.1"

val slickVersion = "3.5.1"

lazy val commonDependencies = Seq(
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.2.10" % "test",
  "org.scalafx" %% "scalafx" % "18.0.1-R28",
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  "com.google.inject" % "guice" % "5.1.0",
  ("net.codingwell" %% "scala-guice" % "5.1.0").cross(CrossVersion.for3Use2_13),
  ("com.typesafe.play" %% "play-json" % "2.9.3").cross(CrossVersion.for3Use2_13),
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
  "mysql" % "mysql-connector-java" % "8.0.28",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.5.6",
    "org.slf4j" % "slf4j-api" % "2.0.12"
)

lazy val commonSettings = Seq(
  scalaVersion := scala3Version,
  libraryDependencies ++= commonDependencies,
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "chess_sa",
    version := "0.1.0-SNAPSHOT",
    commonSettings
  )
  .enablePlugins(CoverallsPlugin)
  .aggregate(controller, logic, persistence, rest, ui, utils)
  .dependsOn(controller, logic, persistence, rest, ui, utils)

lazy val controller = project
  .in(file("controller"))
  .dependsOn(logic, utils)
  .settings(
    name := "controller",
    commonSettings
  )
  .enablePlugins(CoverallsPlugin)
  .dependsOn(logic, persistence, utils)

lazy val logic = project
  .in(file("logic"))
  .settings(
    name := "logic",
    commonSettings
  )
  .enablePlugins(CoverallsPlugin)

lazy val persistence = project
  .in(file("persistence"))
  .settings(
      name := "persistence",
      commonSettings
    )
  .enablePlugins(CoverallsPlugin)
  .dependsOn(logic)

lazy val rest = project
  .in(file("rest"))
  .settings(
      name := "rest",
      commonSettings
    )
  .enablePlugins(CoverallsPlugin)
  .dependsOn(controller, utils)

lazy val ui = project
  .in(file("ui"))
  .settings(
      name := "ui",
      commonSettings
    )
  .enablePlugins(CoverallsPlugin)
  .dependsOn(controller, utils)

lazy val utils = project
  .in(file("utils"))
  .settings(
    name := "utils",
    commonSettings
  )
  .enablePlugins(CoverallsPlugin)