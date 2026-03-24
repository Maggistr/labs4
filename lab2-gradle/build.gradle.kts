import java.time.LocalDateTime
import java.util.Properties
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.provider.Property
import org.gradle.api.file.RegularFileProperty
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "9.4.0"
}


group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":string-utils"))
    implementation("org.apache.commons:commons-lang3:3.18.0")
    implementation("ch.qos.logback:logback-classic:1.5.32")
    implementation("org.slf4j:slf4j-api:2.0.9")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes("Main-Class" to "org.example.Main")
    }
}

abstract class PrintInfoTask : DefaultTask() {
    @get:Input
    abstract val projectName: Property<String>

    @get:Input
    abstract val gradleVersion: Property<String>

    @TaskAction
    fun print() {
        println("======================================")
        println("Это моя первая пользовательская задача!")
        println("Проект: ${projectName.get()}")
        println("Версия Gradle: ${gradleVersion.get()}")
        println("======================================")
    }
}

tasks.register<PrintInfoTask>("printInfo") {
    group = "Custom"
    description = "Выводит информацию о проекте"
    projectName.set(project.name)
    gradleVersion.set(project.version.toString())
}

abstract class Barking : DefaultTask() {
    @get:Input
    @get:Optional
    abstract val dog: Property<String>

    @get:Input
    @get:Optional
    abstract val breed: Property<String>

    @TaskAction
    fun print() {
        println("Введите имя собаки: ")
        val name = readln().uppercase()

        println("Введите породу собаки: ")
        val dogBreed = readln().uppercase()

        println("======================================")
        println("Это моя собака!")
        println("Собака: $name")
        println("Порода: $dogBreed")
        println("======================================")
        println("$name лает на вас...")
    }
}

tasks.register<Barking>("bark") {
    group = "Custom"
    description = "Лает...Просто лает"
    doFirst {
        println("Надеюсь она не быстро бегает...")
    }
    doFirst {
        println("Вы явно чем-то ей не угодили...")
    }
    doFirst {
        println("*Какая-то собака злобно уставилась на вас*")
    }
    doLast {
        println("*Собака убежала*")
    }
    doLast {
        println("Вы облегченно вздохнули...")
    }
}

abstract class GenerateBuildInfoTask : DefaultTask() {
    @get:Inject
    abstract val execOperations: ExecOperations

    @get:OutputFile
    abstract val passportFile: RegularFileProperty

    @get:Internal
    abstract val buildNumberFile: RegularFileProperty


    private fun getGitHash(): String {
        return try {
            val stdout = ByteArrayOutputStream()
            val stderr = ByteArrayOutputStream()
            execOperations.exec {
                commandLine("git", "rev-parse", "--short", "HEAD")
                standardOutput = stdout
                errorOutput = stderr
                isIgnoreExitValue = true
            }.let { result ->
                if (result.exitValue == 0) {
                    stdout.toString(Charsets.UTF_8.name()).trim()
                } else {
                    "unknown"
                }
            }
        } catch (e: Exception) {
            "unknown"
        }
    }

    @TaskAction
    fun generate() {
        val props = Properties()

        props["user"] = System.getenv("USERNAME") ?: System.getenv("USER") ?: "unknown"
        props["os"] = System.getProperty("os.name")
        props["java"] = System.getProperty("java.version")
        props["build.time"] = LocalDateTime.now().toString()
        props["message"] = "Hello from Gradle build!"
        props["git.commit.hash"] = getGitHash()

        val buildNumberFile = buildNumberFile.get().asFile
        val buildProps = Properties()
        if (buildNumberFile.exists()) {
            buildNumberFile.inputStream().use { buildProps.load(it) }
        }
        val currentNumber = buildProps.getProperty("build.number", "0").toInt()
        val newNumber = currentNumber + 1
        buildProps["build.number"] = newNumber.toString()
        buildNumberFile.outputStream().use { buildProps.store(it, "Build number") }
        props["build.number"] = newNumber.toString()

        val file = passportFile.get().asFile
        file.parentFile.mkdirs()
        file.outputStream().use { props.store(it, "Build passport") }
    }
}

tasks.register<GenerateBuildInfoTask>("generateBuildInfo") {
    group = "Build"
    description = "Generates build passport with git hash and build number"
    passportFile.set(layout.projectDirectory.file("src/main/resources/build-passport.properties"))
    buildNumberFile.set(layout.projectDirectory.file("build-number.properties"))
    outputs.upToDateWhen { false }
}

tasks.named("processResources") {
    dependsOn(tasks.named("generateBuildInfo"))
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}