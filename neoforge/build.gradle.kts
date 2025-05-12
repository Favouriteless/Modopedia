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

