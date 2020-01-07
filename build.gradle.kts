/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.41"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}
group = "com.hedvig"
version = "0.2"

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api("com.squareup.okhttp3:okhttp:4.2.2")

    api("com.fasterxml.jackson.core:jackson-databind:2.10.1")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.10.1")

    testImplementation("com.squareup.okhttp3:okhttp:4.2.2")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

fun MavenPom.addDependencies() = withXml {
    asNode().appendNode("dependencies").let { depNode ->
        configurations.compile.allDependencies.forEach {
            depNode.appendNode("dependency").apply {
                appendNode("groupId", it.group)
                appendNode("artifactId", it.name)
                appendNode("version", it.version)
            }
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}


publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = true
    setPublications("mavenJava")
    pkg(closureOf<com.jfrog.bintray.gradle.BintrayExtension.PackageConfig> {
        repo = "hedvig-java"
        name = "customerio"
        userOrg = "hedvig"
        vcsUrl = "https://github.com/HedvigInsurance/customerio.git"
        setLicenses("MIT")
    })
}
