import sbt._
import Keys._
import Resolvers._
import sbt.Package.FixedTimestamp


lazy val commonSettings = Seq(
  organization := "com.self.demo",
  version := "0.1.0",
  scalaVersion := "2.11.12"
)
lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "ScalaKafka",
    Compile / scalaSource := baseDirectory.value / "src/main/scala",
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions += "-deprecation",
    assembly / mainClass := Some("main.scala.KafkaProducerApp"),
    assembly / assemblyJarName := "ScalaKafka.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
    libraryDependencies ++= Seq(
      "org.apache.spark" % "spark-sql_2.11" % "2.4.5",
      "org.apache.spark" % "spark-mllib_2.11" % "1.5.2",
      "org.apache.spark" % "spark-core_2.11" % "2.4.5",
      "org.apache.kafka" % "kafka-clients" % "2.1.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "com.novocode" % "junit-interface" % "0.11" % "test"
    )
)



