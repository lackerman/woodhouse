ThisBuild / scalaVersion := "2.12.8"

lazy val woodhouse = project.in(file("."))
  .settings(
    name := "woodhouse",
    libraryDependencies ++= Seq(
      "org.apache.httpcomponents" % "httpclient" % "4.5.7",
      "com.typesafe.akka" %% "akka-actor" % "2.5.21",
      "io.spray" %%  "spray-json" % "1.3.5",
      "junit" % "junit" % "4.12" % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )