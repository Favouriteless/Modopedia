plugins {
    id("modopedia-convention")

    alias(libs.plugins.moddevgradle)
}

val mod_id: String by project
version = libs.versions.modopedia.get()
val minecraft_version = libs.versions.minecraft.asProvider().get()
val neoform_version = libs.versions.neoform.get()
val parchment_minecraft_version = libs.versions.parchment.minecraft.get()
val parchment_version =libs.versions.parchment.asProvider().get()

base {
    archivesName = "${mod_id}-common-${minecraft_version}"
}

neoForge {
    neoFormVersion = neoform_version

    accessTransformers.add(file("src/main/resources/META-INF/accesstransformer.cfg").absolutePath)

    parchment {
        minecraftVersion = parchment_minecraft_version
        mappingsVersion = parchment_version
    }
}

dependencies {
    implementation( libs.jsr305 )
    compileOnly(libs.mixinextras.common)
}

publishing {
    publications {
        create<MavenPublication>(mod_id) {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

sourceSets.main.get().resources.srcDir(project(":common").file("src/generated/resources"))