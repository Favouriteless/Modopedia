plugins {
    `java`
    `maven-publish`
    `idea`
    `eclipse`
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)

    withSourcesJar()
    withJavadocJar()
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

val libs = project.versionCatalogs.find("libs")

val mod_id: String by project
val mod_name: String by project
val version = libs.get().findVersion("modopedia").get()
val author: String by project
val license: String by project
val mod_description: String by project
val display_url: String by project

val minecraft_version = libs.get().findVersion("minecraft").get()
val minecraft_version_range = libs.get().findVersion("minecraft.range").get()

val neoforge_version = libs.get().findVersion("neoforge").get()
val neoforge_version_range = libs.get().findVersion("neoforge.range").get()
val neoforge_loader_range = libs.get().findVersion("neoforge.loader.range").get()

val fapi_version = libs.get().findVersion("fabric.api").get()
val fabric_version = libs.get().findVersion("fabric").get()

tasks.withType<Jar>().configureEach {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${mod_name}" }
    }

    manifest {
        attributes(mapOf(
            "Specification-Title"     to mod_name,
            "Specification-Vendor"    to author,
            "Specification-Version"   to version,
            "Implementation-Title"    to mod_name,
            "Implementation-Version"  to version,
            "Implementation-Vendor"   to author,
            "Built-On-Minecraft"      to minecraft_version
        ))
    }
}

tasks.withType<JavaCompile>().configureEach {
    this.options.encoding = "UTF-8"
    this.options.getRelease().set(21)
}

tasks.withType<ProcessResources>().configureEach {
    val expandProps = mapOf(
            "version" to version,
            "group" to project.group, // Else we target the task's group.
            "display_url" to display_url, // Else we target the task's group.
            "minecraft_version" to minecraft_version,
            "neoforge_version" to neoforge_version,
            "neoforge_version_range" to neoforge_version_range,
            "neoforge_loader_range" to neoforge_loader_range,
            "minecraft_version_range" to minecraft_version_range,
            "fabric_api_version" to fapi_version,
            "fabric_loader_version" to fabric_version,
            "mod_name" to mod_name,
            "author" to author,
            "mod_id" to mod_id,
            "license" to license,
            "description" to mod_description
    )

    filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
        expand(expandProps)
    }

    inputs.properties(expandProps)
}

publishing {
    repositories {
        mavenLocal()
    }
}
