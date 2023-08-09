plugins {
    id("io.micronaut.library")
    alias(libs.plugins.micronaut.aot)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))

    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.tracing:micronaut-tracing-opentelemetry-annotation")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut:micronaut-retry")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
//    implementation("io.micronaut.problem:micronaut-problem-json")
    implementation("io.micronaut.reactor:micronaut-reactor")
    testImplementation("io.projectreactor:reactor-test")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.tracing:micronaut-tracing-opentelemetry-http")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.opentelemetry:opentelemetry-exporter-jaeger")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("jakarta.validation:jakarta.validation-api")
    testImplementation("io.micronaut:micronaut-http-client")
    aotPlugins(libs.micronaut.security.aot)
}
