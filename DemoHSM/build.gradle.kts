import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.6.21"
    application
}

group = "com.redcpt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jpos:jpos:2.1.7")
    implementation("com.amazonaws:aws-encryption-sdk-java:2.4.0")
    implementation("log4j:log4j:1.2.17")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.json:json:20220320")
    implementation("org.simpleframework:simple-http:6.0.1")
    implementation("com.amazonaws:aws-java-sdk-core:1.12.270")
    implementation("com.amazonaws:aws-java-sdk-kms:1.12.270")
    implementation(files("libs/datedFileAppender-1.0.2.jar"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.redcpt.demohsm.main.MainClass")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
        exclude("META-INF/**.SF", "META-INF/**.DSA")
    }

    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}
