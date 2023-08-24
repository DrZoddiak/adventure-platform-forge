import net.minecraftforge.gradle.patcher.tasks.ReobfuscateJar
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

plugins {
    id("idea")
    kotlin("jvm") version "1.8.22"
    id("net.minecraftforge.gradle") version ("[6.0,6.2)")
}

base {
    archivesName.set("adventureplatformforge")
}

version = "0.1.0"
group = "info.tritusk.adventure"


repositories {
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/") {
        content { includeGroup("thedarkcolour") }
    }
}

dependencies {
    minecraft("net.minecraftforge:forge:1.20.1-47.1.44")

    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-api:4.3.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.6.0-SNAPSHOT")

    compileOnly("thedarkcolour:kotlinforforge:4.3.0")
    compileOnly("net.luckperms:api:5.4")
}

minecraft {
    mappings("official", "1.20")

    runs {

        val consoleLevel = "forge.logging.console.level" to "info"

        create("client") {
            workingDirectory(project.file("run_client"))
            property(consoleLevel.first, consoleLevel.second)

            mods {
                create("adventureplatformforge") {
                    source(sourceSets.main.get())
                }
            }

        }
        create("server") {
            workingDirectory(project.file("run_server"))
            property(consoleLevel.first, consoleLevel.second)
            mods {
/*                "adventure-platform-forge" {
                    //source sourceSets.main
                }*/
            }

        }

        // There is no data to generate, so the runs.data was removed to keep things compact.
    }
}

tasks.withType<Jar> {
    finalizedBy(tasks.withType<ReobfuscateJar>())
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to "adventure",
                "Specification-Vendor" to "KyoriPowered",
                "Specification-Version" to "1", // We are version 1 of ourselves
                "Implementation-Title" to project.name,
                "Implementation-Version" to version,
                "Implementation-Vendor" to "Zodd",
                "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(
                    Instant.now().truncatedTo(ChronoUnit.SECONDS)
                )
            )
        )
    }
}


tasks.withType<JavaCompile>().configureEach {
    options.encoding = "utf-8"
    targetCompatibility = "17"
    sourceCompatibility = "17"
}


