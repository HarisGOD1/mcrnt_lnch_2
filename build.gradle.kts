buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.8.2")
    }
}
repositories {
    mavenCentral()
    maven {
        url = uri("https://company/com/maven2")
    }
    mavenLocal()
    flatDir {
        dirs("libs")
    }
}

plugins {
//    jacoco
    jacoco
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
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")

    implementation("io.micronaut.flyway:micronaut-flyway")                  // db part
    implementation("io.micronaut.data:micronaut-data-jdbc")                 // db part
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")                // db part
//    implementation("org.jacoco:jacoco-maven-plugin:0.8.12")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")

    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("jakarta.persistence:jakarta.persistence-api")              // db part
    runtimeOnly("org.flywaydb:flyway-database-postgresql:11.8.2")           // db part
    runtimeOnly("org.postgresql:postgresql:42.2.14")                        // db part
//    testImplementation("org.testcontainers:postgresql:1.19.0")              // db part
//    testImplementation("org.testcontainers:testcontainers:1.19.0")          // db part
    annotationProcessor("io.micronaut.data:micronaut-data-processor")       // db part

    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("io.micronaut:micronaut-http-client")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
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
//    testResources {
//        additionalModules.add("jdbc-postgresql")
//    }
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
    url = "jdbc:postgresql://localhost:5433/mcrnt_main"
    user = "postgres"
    password ="thegodpsql_pswd"
}
