import sbt.Package.ManifestAttributes

name         := "cytoscape-bundle-scala"

version      := "1.0"

organization := "org.systemsbiology"

scalaVersion := "2.9.2"

javacOptions in Compile ++= Seq("-target", "6", "-source", "6")

scalacOptions ++= Seq("-unchecked", "-deprecation")

packageOptions := Seq(ManifestAttributes(               
               ("Bundle-Name", "sample02"),
               ("Bundle-Description", "This is sample02 written in Scala"),
               ("Bundle-ManifestVersion", "2"),
               ("Bundle-Activator", "org.cytoscape.sample.internal.CyActivator"),
               ("Import-Package", "javax.swing,org.cytoscape.application;version=\"[3.0,4)\",org.cytoscape.application.swing;version=\"[3.0,4)\",org.cytoscape.service.util;version=\"[3.0,4)\",org.osgi.framework;version=\"[1.5,2)\""),
               ("Bundle-SymbolicName", "sample02"),
               ("Cytoscape-API-Compatibility", "3.0")))

resolvers += "official Maven mirror" at "http://mirrors.ibiblio.org/pub/mirrors/maven2/"

resolvers += "Gaggle maven" at "http://como.systemsbiology.net/maven/repo-releases"

libraryDependencies ++= Seq("org.cytoscape" % "cytoscape-api" % "3.0.0-beta2" % "provided",
                    "org.osgi" % "org.osgi.core" % "4.2.0" % "provided")

seq(ProguardPlugin.proguardSettings: _*)

proguardLibraryJars <++= (update) map (_.select(module = moduleFilter(name = "cytoscape-api")))

proguardLibraryJars <++= (update) map (_.select(module = moduleFilter(name = "org.osgi.core")))

proguardOptions ++= Seq("-keep class * { public protected *; }")

