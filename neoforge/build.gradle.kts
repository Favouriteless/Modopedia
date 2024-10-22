plugins {
    id("modopedia-convention")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.moddevgradle)
}

version = libs.versions.modopedia.get()

base {
    archivesName = "modopedia-neoforge-${libs.versions.minecraft.asProvider().get()}"
}

neoForge {
    version = libs.versions.neoforge.asProvider().get()

    accessTransformers.files.setFrom(project(":common").file("src/main/resources/META-INF/accesstransformer.cfg"))

    parchment {
        minecraftVersion = libs.versions.parchment.minecraft.get()
        mappingsVersion = libs.versions.parchment.asProvider().get()
    }

    mods.create("modopedia").sourceSet(project.sourceSets.getByName("main"))

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

    }
}

dependencies {
    compileOnly( project(":common") )
    implementation( libs.neoforge )
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
        create<MavenPublication>("modopedia") {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

