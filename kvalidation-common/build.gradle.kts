apply(plugin = "java-library")
apply(plugin = "org.jetbrains.dokka")

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("commons-validator:commons-validator:1.7") {
        exclude(module = "commons-beanutils")
        exclude(module = "commons-collections")
        exclude(module = "commons-digester")
        exclude(module = "commons-logging")
    }

    implementation("com.googlecode.libphonenumber:libphonenumber:8.12.52")
}
