name := "scala-cats-functional-dependency-injection-workshop"

version := "0.1"

scalaVersion := "2.13.8"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)

libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.1"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.4"
libraryDependencies += "dev.zio" %% "izumi-reflect" % "2.2.5"
libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % "test"
