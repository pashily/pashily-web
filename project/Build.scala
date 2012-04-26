import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {
  import Dependencies._

  val appName         = "pashily"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    liftJson,
    Dispatch.http,
    Dispatch.httpJson
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    // Add your own project settings here
  )

  // libraryDependencies ++= Seq(
  //   LiftJson.json,
  //   LiftJson.dispatch,
  //   LiftJson.time
  // )
}

object Dependencies {
  val liftJson = "net.liftweb" % "lift-json_2.9.1" % "2.4"

  object Dispatch {
    val http = "net.databinder" %% "dispatch-http" % "0.8.8"
    val httpJson = "net.databinder" %% "dispatch-http-json" % "0.8.8"
  }
}
