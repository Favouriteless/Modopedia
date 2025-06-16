import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("modopedia-convention")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.loom)
}

version = libs.versions.modopedia.get()
var mcVersion = libs.versions.minecraft.asProvider().get()

java {
    sourceCompatibility =  JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    maven {
        name = "Parchment"
        url = uri("https://maven.parchmentmc.org")
        content {
            includeGroupAndSubgroups("org.parchmentmc")
        }
    }
}

val localRuntimeOnly = configurations.create("localRuntimeOnly")
configurations.get("runtimeClasspath").extendsFrom(localRuntimeOnly)

dependencies {
    compileOnly( project(":common") )
    minecraft( libs.minecraft )
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.parchment.minecraft.get()}:${libs.versions.parchment.asProvider().get()}@zip")
    })

    modImplementation( libs.fabric )
    modImplementation( libs.fabric.api )
    modImplementation( libs.forgeconfigapi.fabric )

    localRuntimeOnly( project(":test_books") )
}

loom {
	accessWidenerPath = file("src/main/resources/modopedia.accesswidener")

    runs {
        named("client") {
            configName = "Fabric Client"

            client()
            ideConfigGenerated(true)
            runDir("runs/$name")
            programArgs("--username=Favouriteless", "--uuid=9410df73-6be3-41d5-a620-51b2e9be667b")
        }

        named("server") {
            configName = "Fabric Server"

            server()
            ideConfigGenerated(true)
            runDir("runs/$name")
        }
    }

    mixin {
        defaultRefmapName.convention("modopedia.refmap.json")
    }
}

tasks.withType<JavaCompile>().configureEach {
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

    versionName.set("Fabric $mcVersion")
    versionNumber.set(project.version.toString())
    versionType.set(if(project.version.toString().endsWith("beta")) "beta" else "release")
    uploadFile.set(tasks.named<RemapJarTask>("remapJar"))
    changelog.set(rootProject.file("changelog.txt").readText(Charsets.UTF_8))

    loaders.set(listOf("fabric"))
    gameVersions.set(listOf(mcVersion))

    val debug_publish: String by project
    debugMode = debug_publish.toBoolean()
    //https://github.com/modrinth/minotaur#available-properties
}

tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
    group = "publishing"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"

    val mainFile = upload(1132038, tasks.named<RemapJarTask>("remapJar"))
    mainFile.releaseType = if(project.version.toString().endsWith("beta")) "beta" else "release"
    mainFile.changelog = rootProject.file("changelog.txt").readText(Charsets.UTF_8)

    mainFile.addModLoader("Fabric")
    mainFile.addGameVersion(mcVersion)
    mainFile.addJavaVersion("Java 21")

    val debug_publish: String by project
    debugMode = debug_publish.toBoolean()
    //https://github.com/Darkhax/CurseForgeGradle#available-properties
}

tasks.named<DefaultTask>("publish").configure {
    finalizedBy("modrinth")
    finalizedBy("publishToCurseForge")
}