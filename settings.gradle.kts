pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "NeoForge"
            url = uri("https://maven.neoforged.net/")
            content {
                includeGroupAndSubgroups("net.neoforged")
                includeGroup("net.covers1624")
                includeGroup("codechicken")
            }
        }
        maven {
            name = "Forge" // Forge not in the template but VanillaGradle needs the repo.
            url = uri("https://maven.minecraftforge.net/")
            content {
                includeGroupAndSubgroups("net.minecraftforge")
            }
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
            content {
                includeGroupAndSubgroups("net.fabricmc")
                includeGroup("fabric-loom")
            }
        }
        maven {
            name = "Parchment"
            url = uri("https://maven.parchmentmc.org")
            content {
                includeGroupAndSubgroups("org.parchmentmc")
            }
        }
        maven {
            name = "Sponge"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
            content {
                includeGroupAndSubgroups("org.spongepowered")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Modopedia"

include("common")
include("fabric")
include("neoforge")
