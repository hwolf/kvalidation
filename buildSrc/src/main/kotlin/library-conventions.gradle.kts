import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlinx.kover")
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.2"))
    testImplementation(platform("io.strikt:strikt-bom:0.35.1"))
    testImplementation(platform("io.kotest:kotest-bom:5.9.1"))

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-framework-datatest")
    testImplementation("io.kotest:kotest-property")
    testImplementation("io.strikt:strikt-core")
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("org.tinylog:tinylog-impl:2.7.0")
    testImplementation("org.tinylog:slf4j-tinylog:2.7.0") {
        exclude(module = "org.slf4j")
    }
}

kover {
    reports {
        total {
            xml {
                xmlFile = layout.buildDirectory.file("kover/result.xml")
            }
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        apiVersion.set(KotlinVersion.KOTLIN_1_9)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Jar>("sourceJar") {
    dependsOn(tasks.classes)
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register<Jar>("dokkaJar") {
    dependsOn(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}
