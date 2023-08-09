plugins {
    id("io.micronaut.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.jakarta.inject.api)
}
