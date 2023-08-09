plugins {
    alias(libs.plugins.micronaut.application)
    alias(libs.plugins.micronaut.aot)
    alias(libs.plugins.shadow)
}

version = "0.1"
group = "net.jfsanchez.dnsman"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

allprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }
}

subprojects {
    val libs = rootProject.libs
    dependencies {
        annotationProcessor(libs.lombok)
        compileOnly(libs.lombok)

        implementation(libs.reactor.core)
        testImplementation(libs.reactor.test)

        testImplementation(libs.junit.jupiter.api)
        testImplementation(libs.mockito.core)
    }

    tasks.test {
        useJUnitPlatform()
    }
}

dependencies {
    compileOnly("org.graalvm.nativeimage:svm")
    runtimeOnly("org.yaml:snakeyaml")

    implementation(project(":infra-web"))
}

application {
    mainClass.set("net.jfsanchez.dnsman.Application")
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("net.jfsanchez.dnsman.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
        configurationProperties.put("micronaut.security.jwks.enabled", "false")
    }
}
