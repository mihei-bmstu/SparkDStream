
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.16"

ThisBuild / organization := "ru.digitalleague"

lazy val root = (project in file("."))
  .settings(
    name := "SparkDStream",
    libraryDependencies := Dependencies.all
  )
