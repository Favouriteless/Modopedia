plugins {
    id("modopedia-convention")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.neogradle)
}

val mod_id: String by project
version = libs.versions.modopedia.get()
val minecraft_version = libs.versions.minecraft.asProvider().get()
val parchment_minecraft_version = libs.versions.parchment.minecraft.get()
val parchment_version =libs.versions.parchment.asProvider().get()

base {
    archivesName = "${mod_id}-neoforge-${minecraft_version}"
}

subsystems {
    parchment {
        minecraftVersion = parchment_minecraft_version
        mappingsVersion = parchment_version
    }
}

minecraft {
    //minecraft.accessTransformers.file( rootProject.file('src/main/resources/META-INF/accesstransformer.cfg') )
}

runs {
    configureEach {
        systemProperty("forge.logging.console.level", "debug")

        modSource(project.sourceSets.getByName("main"))
    }

    create("client") {
        workingDirectory(project.file("runs/$name"))
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        programArguments.addAll(
            "--username", "Favouriteless",
            "--uuid", "9410df73-6be3-41d5-a620-51b2e9be667b"
        )
    }

    create("server") {
        workingDirectory(project.file("runs/$name"))
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        programArgument("--nogui")
    }

    create("data") {
        workingDirectory(project.file("runs/$name"))
        programArguments.addAll("--mod", mod_id, "--all",
            "--output", project(":common").file("src/generated/resources/").absolutePath,
            "--existing", project(":common").file("src/main/resources/").absolutePath
        )
    }

}

dependencies {
    compileOnly( project(":common") )
    implementation( libs.neoforge )
    implementation( libs.jsr305 )
}


tasks.withType<JavaCompile>().matching{!it.name.startsWith("neo")}.configureEach {
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
        create<MavenPublication>(mod_id) {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

