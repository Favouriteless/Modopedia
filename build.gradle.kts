plugins {
    alias(libs.plugins.minotaur) apply false
    alias(libs.plugins.curseforgegradle) apply false
    alias(libs.plugins.wikitoolkit)

    alias(libs.plugins.ideaext)
}

subprojects {

    repositories {
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") { name = "Fuzs Mod Resources" }
        maven("https://maven.blamejared.com/") { name = "Jared's maven" }
    }

}

wiki {
    docs.create("modopedia") {
        root = file("docs")
    }

    wikiAccessToken = System.getenv("MODDEDMC_WIKI_TOKEN")
}