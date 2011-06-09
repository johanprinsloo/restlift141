name := "restlift141"
 
scalaVersion := "2.9.0-1"
 
seq(WebPlugin.webSettings: _*)
 
libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-webkit" % "2.4-M1" % "compile->default",
  "net.liftweb" %% "lift-mapper" % "2.4-M1" % "compile->default",
  "net.liftweb" %% "lift-wizard" % "2.4-M1" % "compile->default")
 
 
libraryDependencies ++= Seq(
  "junit" % "junit" % "4.5" % "test->default",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "com.h2database" % "h2" % "1.2.138",
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default",
  "org.scala-tools.testing" %% "specs" % "1.6.8" % "test"
)