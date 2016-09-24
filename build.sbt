name := "scalac-chess-challenge"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.github.scopt" %% "scopt" % "3.5.0"
)