import net.darkhax.curseforgegradle.TaskPublishCurseForge
import org.gradle.kotlin.dsl.register

plugins {
    id("modopedia-convention")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.moddevgradle)
}

version = libs.versions.modopedia.get()
val mcVersion = libs.versions.minecraft.asProvider().get()

base {
    archivesName = "modopedia-neoforge-${mcVersion}"
}

neoForge {
    version = libs.versions.neoforge.asProvider().get()

    accessTransformers.files.setFrom(project(":common").file("src/main/resources/META-INF/accesstransformer.cfg"))

    parchment {
        minecraftVersion = libs.versions.parchment.minecraft.get()
        mappingsVersion = libs.versions.parchment.asProvider().get()
    }

    runs {
        configureEach {
            logLevel = org.slf4j.event.Level.DEBUG
        }

        create("client") {
            client()
            gameDirectory = file("runs/client")
            programArguments.addAll(
                "--username", "Favouriteless",
                "--uuid", "9410df73-6be3-41d5-a620-51b2e9be667b"
            )
        }

        create("server") {
            server()
            gameDirectory = file("runs/server")
            programArgument("--nogui")
        }

        val output = project(":common").file("src/generated/resources/").absolutePath
        val existing = project(":common").file("src/main/resources/").absolutePath

        create("data") {
            data()
            gameDirectory = file("runs/data")
            programArguments.addAll(
                "--mod", "modopedia", "--all",
                "--output", output,
                "--existing", existing
            )
        }
    }

    mods.create("modopedia") {
        sourceSet(sourceSets.main.get())
    }
}

val localRuntimeOnly = configurations.create("localRuntimeOnly")
configurations.get("runtimeClasspath").extendsFrom(localRuntimeOnly)

dependencies {
    compileOnly( project(":common") )

    localRuntimeOnly( project(":test_books") )
}

tasks.withType<Test>().configureEach {
    enabled = false;
}

tasks.named<JavaCompile>("compileJava").configure {
    source(project(":common").sourceSets.getByName("main").allSource)
}

tasks.named<Jar>("sourcesJar").configure {
    from(project(":common").sourceSets.getByName("main").allSource)
}

tasks.withType<Javadoc>().configureEach {
    source(project(":common").sourceSets.getByName("main").allJava)
}

tasks.withType<ProcessResources>().configureEach {
    from(project(":common").sourceSets.getByName("main").resources)
}

publishing {
    publications {
        create<MavenPublication>("modopedia") {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN") ?: "Invalid/No API Token Found"

    projectId.set("SYrakyVL")

    versionName.set("NeoForge $mcVersion")
    versionNumber.set(project.version.toString())
    versionType.set(if(project.version.toString().endsWith("beta")) "beta" else "release")
    uploadFile.set(tasks.jar)
    changelog.set(rootProject.file("changelog.txt").readText(Charsets.UTF_8))

    loaders.set(listOf("neoforge"))
    gameVersions.set(listOf(mcVersion))

    //debugMode = true
    //https://github.com/modrinth/minotaur#available-properties
}

tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
    group = "publishing"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"

    val mainFile = upload(1132038, tasks.jar)
    mainFile.releaseType = if(project.version.toString().endsWith("beta")) "beta" else "release"
    mainFile.changelog = rootProject.file("changelog.txt").readText(Charsets.UTF_8)

    mainFile.addModLoader("NeoForge")
    mainFile.addGameVersion(mcVersion)
    mainFile.addJavaVersion("Java 21")

    //debugMode = true
    //https://github.com/Darkhax/CurseForgeGradle#available-properties
}

tasks.named<DefaultTask>("publish").configure {
    finalizedBy("modrinth")
    finalizedBy("publishToCurseForge")
}