plugins {
    id("io.micronaut.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))
}
