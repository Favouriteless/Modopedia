plugins {
    alias(libs.plugins.minotaur) apply false
    alias(libs.plugins.curseforgegradle) apply false

    // Required for NeoGradle
    alias(libs.plugins.ideaext)
}

subprojects {

    repositories {
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") { name = "Fuzs Mod Resources" }
        maven("https://maven.blamejared.com/") { name = "Jared's maven" }
    }

}