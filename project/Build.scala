import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {
  import Dependencies._

  val appName         = "pashily"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    Scalaz.core,
    LiftJson.core,
    Dispatch.http,
    Dispatch.httpJson
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers ++= Resolvers.all
  )
}

object Dependencies {
  object LiftJson {
    val core = "net.liftweb" % "lift-json_2.9.1" % "2.4"
  }

  object Dispatch {
    val http = "net.databinder" %% "dispatch-http" % "0.8.8"
    val httpJson = "net.databinder" %% "dispatch-http-json" % "0.8.8"
  }

  object Scalaz {
    val core = "org.scalaz" %% "scalaz-core" % "6.0.4"
  }
}

object Resolvers {
  val scalaTools = "Scala Tools Snapshots" at "https://oss.sonatype.org/content/groups/scala-tools/"

  val all = Seq(scalaTools)
}
