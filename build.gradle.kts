plugins {
    id("org.jetbrains.dokka")
    id("org.sonarqube") version "5.0.0.4638"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-2"
}

repositories {
    mavenCentral()
}

sonarqube {
    properties {
        property("sonar.projectKey", "hwolf_kvalidation")
        property("sonar.organization", "hwolf")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/kover/result.xml")
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
