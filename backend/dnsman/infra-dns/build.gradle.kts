plugins {
    id("io.micronaut.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation("io.netty:netty-transport")
    implementation("io.netty:netty-codec-dns")
}
