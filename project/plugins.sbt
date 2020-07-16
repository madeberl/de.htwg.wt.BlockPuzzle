addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.3")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.11.0")

resolvers += Resolver.bintrayRepo("givers", "maven")

addSbtPlugin("givers.vuefy" % "sbt-vuefy" % "5.0.0")