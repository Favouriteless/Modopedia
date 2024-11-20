plugins {
    id("modopedia-convention")
    alias(libs.plugins.moddevgradle)
}

version = libs.versions.modopedia.get()
val mcVersion = libs.versions.minecraft.asProvider().get()

base {
    archivesName = "modopedia-common-${mcVersion}"
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
    compileOnly( libs.mixin )
    compileOnly( libs.mixinextras.common )
}

sourceSets.main.get().resources.srcDir(file("src/generated/resources"))

publishing {
    publications {
        create<MavenPublication>("modopedia") {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}