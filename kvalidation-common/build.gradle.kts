plugins {
    id("library-conventions")
    id("publish-conventions")
}

dependencies {
    implementation(project(":kvalidation-core"))

    implementation("commons-validator:commons-validator:1.9.0") {
        exclude(module = "commons-beanutils")
        exclude(module = "commons-collections")
        exclude(module = "commons-digester")
        exclude(module = "commons-logging")
    }

    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.43")
}
