name := """BlockPuzzle in Scala for WebTech"""

version := "1.3"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb, SbtVuefy) // Enable the plugin

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.7"

libraryDependencies += guice

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies += "com.h2database" % "h2" % "1.4.196"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.1"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"

// The commands that triggers production build when running Webpack, as in `webpack -p`.
Assets / VueKeys.vuefy / VueKeys.prodCommands := Set("stage")

// The location of the webpack binary. For windows, it might be `webpack.cmd`.
Assets / VueKeys.vuefy / VueKeys.webpackBinary := "./node_modules/.bin/webpack"

// The location of the webpack configuration.
Assets / VueKeys.vuefy / VueKeys.webpackConfig := "./webpack.config.js"


