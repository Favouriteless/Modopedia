plugins {
    java
    `maven-publish`
    idea
    eclipse
}

val libs = project.versionCatalogs.find("libs")

val version = libs.get().findVersion("modopedia").get()
val mcVersion = libs.get().findVersion("minecraft").get()
val mcVersionRange = libs.get().findVersion("minecraft.range").get()
val neoVersion = libs.get().findVersion("neoforge").get()
val neoVersionRange = libs.get().findVersion("neoforge.range").get()
val neoLoaderRange = libs.get().findVersion("neoforge.loader.range").get()
val fapiVersion = libs.get().findVersion("fabric.api").get()
val fabricVersion = libs.get().findVersion("fabric").get()

base {
    archivesName = "modopedia-${project.name}-${mcVersion}"
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

tasks.withType<Jar>().configureEach {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_Modopedia" }
    }

    manifest {
        attributes(mapOf(
            "Specification-Title"     to "Modopedia",
            "Specification-Vendor"    to "Favouriteless",
            "Specification-Version"   to version,
            "Implementation-Title"    to "Modopedia",
            "Implementation-Version"  to version,
            "Implementation-Vendor"   to "Favouriteless",
            "Built-On-Minecraft"      to mcVersion
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
            "minecraft_version" to mcVersion,
            "neoforge_version" to neoVersion,
            "neoforge_version_range" to neoVersionRange,
            "neoforge_loader_range" to neoLoaderRange,
            "minecraft_version_range" to mcVersionRange,
            "fabric_api_version" to fapiVersion,
            "fabric_loader_version" to fabricVersion
    )

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml")) {
        expand(expandProps)
    }

    inputs.properties(expandProps)
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}

publishing {
    repositories {
        if(System.getenv("FAVOURITELESS_MAVEN_USER") == null || System.getenv("FAVOURITELESS_MAVEN_PASS") == null) {
            mavenLocal()
        }
        else maven {
            name = "FavouritelessReleases"
            url = uri("https://maven.favouriteless.net/releases")

            credentials {
                username = System.getenv("FAVOURITELESS_MAVEN_USER")
                password = System.getenv("FAVOURITELESS_MAVEN_PASS")
            }
        }
    }
}
