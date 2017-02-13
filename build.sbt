name := """vessels-scala"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.11",
  "com.wix" %% "accord-core" % "0.6.1",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
)



fork in run := true