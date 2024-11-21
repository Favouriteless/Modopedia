plugins {
    id("modopedia-convention")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.loom)
}

version = libs.versions.modopedia.get()
val minecraftVersion = libs.versions.minecraft.asProvider().get();

java {
    sourceCompatibility =  JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

base {
    archivesName = "modopedia-fabric-${minecraftVersion}"
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

dependencies {
    compileOnly( project(":common") )
    minecraft( libs.minecraft )
    mappings(loom.layered() {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${libs.versions.parchment.minecraft.get()}:${libs.versions.parchment.asProvider().get()}@zip")
    })

    modImplementation( libs.fabric )
    modImplementation( libs.fabric.api )

    runtimeOnly( project(":test_books") )
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