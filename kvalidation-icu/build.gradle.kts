plugins {
    id("library-conventions")
    id("publish-conventions")
}

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.tinylog:tinylog-api-kotlin:2.7.0")
    implementation("com.ibm.icu:icu4j:77.1")
}
