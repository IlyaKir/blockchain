ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "blockchain",
    libraryDependencies += "org.bouncycastle" % "bcprov-jdk18on" % "1.78.1",
    libraryDependencies += "io.circe" %% "circe-core" % "0.14.7",
    libraryDependencies += "io.circe" %% "circe-generic" % "0.14.7",
    libraryDependencies += "io.circe" %% "circe-parser" % "0.14.7"
  )
