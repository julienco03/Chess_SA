import org.scoverage.coveralls.Imports.CoverallsKeys._

ThisBuild / scalaVersion := "3.3.3"

val testVersion = "3.2.10"
val slickVersion = "3.5.1"
val akkaVersion = "2.8.5"

lazy val guiceDependencies = Seq(
  "com.google.inject" % "guice" % "5.1.0",
  ("net.codingwell" %% "scala-guice" % "5.1.0").cross(CrossVersion.for3Use2_13),
)

lazy val testDependencies = Seq(
  "org.scalameta" %% "munit" % "0.7.29" % Test,
  "org.scalactic" %% "scalactic" % testVersion,
  "org.scalatest" %% "scalatest" % testVersion % "test",
)

lazy val guiDependencies = Seq(
  "org.scalafx" %% "scalafx" % "18.0.1-R28",
  ("org.scala-lang.modules" %% "scala-swing" % "3.0.0").cross(CrossVersion.for3Use2_13),
)

lazy val xmlDependencies = Seq(
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
)

lazy val jsonDependencies = Seq(
  ("com.typesafe.play" %% "play-json" % "2.9.3").cross(CrossVersion.for3Use2_13),
)

lazy val akkaDependencies = Seq(
  ("com.typesafe.akka" %% "akka-actor-typed" % akkaVersion).cross(CrossVersion.for3Use2_13),
  ("com.typesafe.akka" %% "akka-stream" % akkaVersion).cross(CrossVersion.for3Use2_13),
  ("com.typesafe.akka" %% "akka-http" % "10.5.3").cross(CrossVersion.for3Use2_13),
)

lazy val loggingDependencies = Seq(
  ("com.typesafe.scala-logging" %% "scala-logging" % "3.9.5").cross(CrossVersion.for3Use2_13),
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  "org.slf4j" % "slf4j-api" % "2.0.12",
)

lazy val slickDependencies = Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
)

lazy val mysqlDependencies = Seq(
  "mysql" % "mysql-connector-java" % "8.0.28",
)

lazy val mongoDependencies = Seq(
  ("org.mongodb.scala" %% "mongo-scala-driver" % "4.9.0").cross(CrossVersion.for3Use2_13),
)

lazy val gatlingDependencies = Seq(
  "io.gatling" % "gatling-test-framework" % "3.11.3" % Test,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.11.3" % Test
)

lazy val commonSettings = Seq(
  libraryDependencies ++= guiceDependencies ++
    testDependencies ++
    guiDependencies ++
    xmlDependencies ++
    jsonDependencies ++
    akkaDependencies ++
    loggingDependencies ++
    slickDependencies ++
    mysqlDependencies ++
    mongoDependencies ++
    gatlingDependencies
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "chess_sa",
    version := "0.1.0-SNAPSHOT",
    commonSettings
  )
  .enablePlugins(CoverallsPlugin)
  .enablePlugins(GatlingPlugin)
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

//libraryDependencies ++= Seq(
//  "io.gatling" % "gatling-test-framework" % "3.11.3" % Test,
//  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.11.3" % Test
//)

//scalacOptions := Seq(
//  "-encoding", "UTF-8", "release:8", "-deprecation", "-feature", "-unchecked",
//  "-language:implicitConversions", "-language:postfixOps"
//)

//val gatlingVersion = "3.11.3"
//libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "test,it"
//libraryDependencies += "io.gatling"            % "gatling-test-framework"    % gatlingVersion % "test,it"
