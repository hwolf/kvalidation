plugins {
    id("library-conventions")
    id("publish-conventions")
}

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("org.tinylog:tinylog-api-kotlin:2.7.0")
    implementation("io.pebbletemplates:pebble:3.2.3")
}
