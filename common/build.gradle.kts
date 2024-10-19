plugins {
    id("modopedia-convention")

    alias(libs.plugins.moddevgradle)
}

version = libs.versions.modopedia.get()

base {
    archivesName = "modopedia-common-${libs.versions.minecraft.asProvider().get()}"
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()

    accessTransformers.files.setFrom("src/main/resources/META-INF/accesstransformer.cfg")

    parchment {
        minecraftVersion = libs.versions.parchment.minecraft.get()
        mappingsVersion = libs.versions.parchment.asProvider().get()
    }
}

dependencies {
    compileOnly(libs.mixinextras.common)
}

publishing {
    publications {
        create<MavenPublication>("modopedia") {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

sourceSets.main.get().resources.srcDir(project(":common").file("src/generated/resources"))