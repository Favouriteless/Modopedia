plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.blamejared.com") { name = "BlameJared Maven" }
}

dependencies {
    gradleApi()
    implementation( libs.schoomp )
}