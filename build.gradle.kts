plugins {
    id("org.sonarqube") version "4.3.0.3225"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-1"
}

sonarqube {
    properties {
        property("sonar.projectKey", "hwolf_kvalidation")
        property("sonar.organization", "hwolf")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/kover/result.xml")
        property("sonar.sources", "src/main")
        property("sonar.tests", "src/test")
    }
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
