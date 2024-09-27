@file:Suppress("UnstableApiUsage")

import java.util.Properties

include(":parallax")


val localPropertiesFile = file("local.properties")
val localProperties = Properties()

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

// Access a property
val gprUser: String = localProperties.getProperty("gpr.user")
val gprToken: String = localProperties.getProperty("gpr.token")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven {
            url = uri("https://maven.pkg.github.com/sgcodigo/tools-belt")
            credentials {
                username = gprUser
                password = gprToken
            }
        }
    }
}

rootProject.name = "Parallax"
include(":app")
