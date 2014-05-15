import AssemblyKeys._

name:="Simple First Project"

version:="1.0"

scalaVersion := "2.10.3"

assemblySettings

test in assembly := {}

libraryDependencies += "org.apache.spark" %% "spark-core" % "0.9.0-incubating"

jarName in assembly := s"""build_fat_${version.value}.jar"""

mainClass in assembly := Some("FirstApp")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
      case "application.conf" => MergeStrategy.concat
        case "reference.conf" => MergeStrategy.concat
          case "unwanted.txt"     => MergeStrategy.discard
            case "MANIFEST.MF" => MergeStrategy.discard
              case PathList("META-INF", xs @ _*) =>
                  (xs map {_.toLowerCase}) match {
                          case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) => MergeStrategy.discard
                                case _ => MergeStrategy.discard
                                    }
                                      case x => MergeStrategy.first
}
}
