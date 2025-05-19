buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.8.2")
    }
}


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("io.micronaut.application") version "4.5.3"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.test-resources") version "4.5.3"
    id("io.micronaut.aot") version "4.5.3"
    id("org.flywaydb.flyway") version "11.8.2"
}

version = "0.1"
group = "ru.thegod"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:11.8.2")
    runtimeOnly("org.postgresql:postgresql:42.2.14")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    testImplementation("org.testcontainers:postgresql:1.19.0")
//    testImplementation("org.testcontainers:testcontainers")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
}


application {
    mainClass = "ru.thegod.ApplicationKt"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
}
kotlin {
    jvmToolchain(21)
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("ru.thegod.*")
    }
    testResources {
        additionalModules.add("jdbc-postgresql")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}
tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {

    jdkVersion = "21"

}

flyway {

    cleanDisabled = false
    url = "jdbc:postgresql://localhost:5432/mcrnt_lnch_db2"
    user = "postgres"
    password = "thegodpsql_pswd"
}


