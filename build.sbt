val scala3Version = "3.3.3"

val akkaVersion = "2.8.5"
val akkaHttpVersion = "10.5.3"

lazy val commonDependencies = Seq(
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.2.10" % "test",
  "org.scalafx" %% "scalafx" % "18.0.1-R28",
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  "com.google.inject" % "guice" % "5.1.0",
  ("net.codingwell" %% "scala-guice" % "5.1.0").cross(CrossVersion.for3Use2_13),
  ("com.typesafe.play" %% "play-json" % "2.10.4").cross(CrossVersion.for3Use2_13),
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
)

lazy val root = (project in file("."))
  .settings(
    name := "Chess",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(controller, logic, persistence, rest, utils)
  .aggregate(controller, logic, persistence, rest, utils)

lazy val controller = (project in file("controller"))
  .settings(
      name := "controller",
      version := "0.1.0-SNAPSHOT",
      libraryDependencies ++= commonDependencies
    )
  .dependsOn(logic, utils)

lazy val logic = (project in file("logic"))
  .settings(
    name := "logic",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= commonDependencies
  )

lazy val persistence = (project in file("persistence"))
  .settings(
    name := "persistence",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(logic, utils)

lazy val rest = (project in file("rest"))
  .settings(
    name := "rest",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(controller, utils)

// lazy val ui = (project in file("ui"))
//  .settings(
//     name := "ui",
//     version := "0.1.0-SNAPSHOT",
//     libraryDependencies ++= commonDependencies
//   )
//   .dependsOn(controller, logic, utils)

lazy val utils = (project in file("utils"))
  .settings(
    name := "utils",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= commonDependencies
  )
