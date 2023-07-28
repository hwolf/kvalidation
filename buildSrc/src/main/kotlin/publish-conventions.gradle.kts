plugins {
    id("maven-publish")
}

publishing {
    repositories {
        maven {
            url = uri("${rootProject.rootDir}/build/repository")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            if (plugins.hasPlugin("java-platform")) {
                from(components["javaPlatform"])
            }
            if (plugins.hasPlugin("library-conventions")) {
                from(components["java"])
                artifact(tasks.named("sourceJar"))
                artifact(tasks.named("dokkaJar"))
            }
            pom {
                name.set("Validation DSL for Kotlin")
                description.set("Validation DSL for Kotlin is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin")
                url.set("https://github.com/hwolf/kvalidation")
                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://raw.githubusercontent.com/hwolf/kvalidation/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("hwolf")
                        name.set("hwolf")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/hwolf/kvalidation.git")
                    developerConnection.set("scm:git:git://github.com/hwolf/kvalidation.git")
                    url.set("https://github.com/hwolf/kvalidation")
                }
            }
        }
    }
}
